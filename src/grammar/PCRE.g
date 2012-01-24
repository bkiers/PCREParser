grammar PCRE;

options {
  ASTLabelType=CommonTree;
  output=AST;
}

tokens {
  REGEX;
  ATOM;
  DOT;
  OR;
  CHAR_CLASS;
  NEG_CHAR_CLASS;
  RANGE;
  QUOTATION;
  INT;
  QUANTIFIER;
  GREEDY;
  RELUCTANT;
  POSSESSIVE;
  BACK_REFERENCE;
  CAPTURE_GROUP;
  FLAG_GROUP;
  ATOMIC_GROUP;
  NON_CAPTURE_GROUP;
  POSITIVE_LOOK_AHEAD;
  NEGATIVE_LOOK_AHEAD;
  POSITIVE_LOOK_BEHIND;
  NEGATIVE_LOOK_BEHIND;
  FLAGS;
  ENABLE;
  DISABLE;
  DOT;
  ATOM;
  ATOMS;
  MIN_MAX;
}

@parser::header {
  package pcreparser;
}

@lexer::header {
  package pcreparser;
}

// parser rules
parse
  :  regexAlts EOF -> ^(REGEX regexAlts)
  ;

regexAlts
  :  atoms (Or^ atoms)*
  ;

atoms
  :  regexAtom* -> ^(ATOMS regexAtom*)
  ;

regexAtom
  :  unit quantifier? -> ^(ATOM unit quantifier?)
  ;

unit
  :  charClass
  |  singleChar
  |  boundaryMatch
  |  quotation
  |  backReference
  |  group
  |  ShorthandCharacterClass
  |  PosixCharacterClass
  |  Dot
  ;

quantifier
  :  (greedy -> ^(GREEDY greedy))
     ('+'    -> ^(POSSESSIVE greedy)
     |'?'    -> ^(RELUCTANT greedy)
     )?
  ;

greedy
  :  '+'            -> ^(MIN_MAX INT["1"] INT["2147483647"])
  |  '*'            -> ^(MIN_MAX INT["0"] INT["2147483647"])
  |  '?'            -> ^(MIN_MAX INT["0"] INT["1"])
  |  '{' (a=integer -> ^(MIN_MAX INT[$a.text] INT[$a.text]))
     (
       (','         -> ^(MIN_MAX INT[$a.text] INT["2147483647"]))
       (b=integer   -> ^(MIN_MAX INT[$a.text] INT[$b.text]))?
     )? 
     '}'
  ;

charClass
  :  '[' (('^')=> '^' charClassAtom+ ']' -> ^(NEG_CHAR_CLASS charClassAtom+)
         |        charClassAtom+ ']'     -> ^(CHAR_CLASS charClassAtom+)
         )
  ;

charClassAtom
  :  (charClassSingleChar '-' charClassSingleChar)=> 
     charClassSingleChar '-' charClassSingleChar -> ^(RANGE charClassSingleChar charClassSingleChar)
  |  quotation
  |  ShorthandCharacterClass
  |  BoundaryMatch
  |  PosixCharacterClass
  |  charClassSingleChar
  ;

charClassSingleChar
  :  charClassEscape
  |  EscapeSequence
  |  OctalNumber
  |  SmallHexNumber
  |  UnicodeChar
  |  Or
  |  Caret
  |  Hyphen
  |  Colon
  |  Dollar
  |  SquareBracketStart
  |  RoundBracketStart
  |  RoundBracketEnd
  |  CurlyBracketStart
  |  CurlyBracketEnd
  |  Equals
  |  LessThan
  |  GreaterThan
  |  ExclamationMark
  |  Comma
  |  Plus
  |  Star
  |  QuestionMark
  |  Dot
  |  Digit
  |  OtherChar
  ;

charClassEscape
  :  '\\' ('\\' | '^' | ']' | '-')
  ;

singleChar
  :  regexEscape
  |  EscapeSequence
  |  OctalNumber
  |  SmallHexNumber
  |  UnicodeChar
  |  Hyphen
  |  Colon
  |  SquareBracketEnd
  |  CurlyBracketEnd
  |  Equals
  |  LessThan
  |  GreaterThan
  |  ExclamationMark
  |  Comma
  |  Digit
  |  OtherChar
  ;

regexEscape
  :  '\\' ('\\' | '|' | '^' | '$' | '[' | '(' | ')' | '{' | '}' | '+' | '*' | '?' | '.')
  ;

boundaryMatch
  :  Caret
  |  Dollar
  |  BoundaryMatch
  ;

backReference
  :  '\\' integer -> ^(BACK_REFERENCE integer)
  ;

group
  :  '(' 
     ( '?' ( (flags                -> ^(FLAG_GROUP flags)
             )? 
             (':' regexAlts       -> ^(NON_CAPTURE_GROUP flags? regexAlts)
             )?
           | '>' regexAlts        -> ^(ATOMIC_GROUP regexAlts)
           | '!' regexAlts        -> ^(NEGATIVE_LOOK_AHEAD regexAlts)
           | '=' regexAlts        -> ^(POSITIVE_LOOK_AHEAD regexAlts)
           | '<' ( '!' regexAlts  -> ^(NEGATIVE_LOOK_BEHIND regexAlts)
                 | '=' regexAlts  -> ^(POSITIVE_LOOK_BEHIND regexAlts)
                 )
           )
     | regexAlts                  -> ^(CAPTURE_GROUP regexAlts)
     )
     ')'
  ;

flags
  :  (a=singleFlags     -> ^(FLAGS ^(ENABLE $a))) 
     ('-' b=singleFlags -> ^(FLAGS ^(ENABLE $a) ^(DISABLE $b))
     )?
  ;

singleFlags
  :  OtherChar+
  ;

quotation
  :  QuotationStart innerQuotation QuotationEnd -> ^(QUOTATION innerQuotation)
  ;

innerQuotation
  :  (~QuotationEnd)*
  ;

integer
  :  (options{greedy=true;}: Digit)+
  ;

// lexer rules

QuotationStart
  :  '\\Q'
  ;

QuotationEnd
  :  '\\E'
  ;

PosixCharacterClass
  :  '\\p{' ('Lower' | 'Upper' | 'ASCII' | 'Alpha' | 'Digit' | 'Alnum' | 'Punct' | 'Graph' | 'Print' | 'Blank' | 'Cntrl' | 'XDigit' | 'Space') '}'
  ;

ShorthandCharacterClass
  :  Escape ('d' | 'D' | 's' | 'S' | 'w' | 'W')
  ;

BoundaryMatch
  :  Escape ('b' | 'B' | 'A' | 'Z' | 'z' | 'G')
  ;

OctalNumber
  :  Escape '0' ( OctDigit? OctDigit 
                | '0'..'3' OctDigit OctDigit
                )
  ;

SmallHexNumber
  :  Escape 'x' HexDigit HexDigit
  ;

UnicodeChar
  :  Escape 'u' HexDigit HexDigit HexDigit HexDigit
  ;

EscapeSequence
  :  Escape ('t' | 'n' | 'r' | 'f' | 'a' | 'e' | ~('a'..'z' | 'A'..'Z' | '0'..'9'))
  ;

Escape             : '\\';
Or                 : '|';
Hyphen             : '-';
Caret              : '^';
Colon              : ':';
Dollar             : '$';
SquareBracketStart : '[';
SquareBracketEnd   : ']';
RoundBracketStart  : '(';
RoundBracketEnd    : ')';
CurlyBracketStart  : '{';
CurlyBracketEnd    : '}';
Equals             : '=';
LessThan           : '<';
GreaterThan        : '>';
ExclamationMark    : '!';
Comma              : ',';
Plus               : '+';
Star               : '*';
QuestionMark       : '?';
Dot                : '.';
Digit              : '0'..'9';
OtherChar          :  . ;

// fragments
fragment OctDigit : '0'..'7';
fragment HexDigit : ('0'..'9' | 'a'..'f' | 'A'..'F');
