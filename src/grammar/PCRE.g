/*
 * Copyright (c) 2012 by Bart Kiers
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * Project      : PCRE Parser, an ANTLR grammar for PCRE
 * Developed by : Bart Kiers, bart@big-o.nl
 */
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
  CHARS;
  ENABLE;
  DISABLE;
  DOT;
  ATOM;
  ATOMS;
  MIN_MAX;
  LITERAL;
  FLAG;
}

@parser::header {
  package pcreparser;
}

@lexer::header {
  package pcreparser;
}

@parser::members {
  @Override
  public void reportError(RecognitionException e) {
    throw new RuntimeException(e);
  }
}

@lexer::members {
  @Override
  public void reportError(RecognitionException e) {
    throw new RuntimeException(e);
  }
}

// parser rules
parse
 : regexAlts EOF -> ^(REGEX regexAlts)
   //(t=. {System.out.printf("\%-25s '\%s'\n", tokenNames[$t.type], $t.text);})* EOF
 ;

regexAlts
 : atoms (Or^ atoms)*
 ;

atoms
 : regexAtom* -> ^(ATOMS regexAtom*)
 ;

regexAtom
 : unit quantifier? -> ^(ATOM unit quantifier?)
 ;

unit
 : charClass
 | singleCharLiteral
 | boundaryMatch
 | Quotation               -> LITERAL[$Quotation.text]
 | backReference
 | group
 | shorthandCharacterClass
 | posixCharacterClass
 | Dot
 ;

quantifier
 : (greedy -> ^(GREEDY greedy)) ('+' -> ^(POSSESSIVE greedy)
                                |'?' -> ^(RELUCTANT greedy)
                                )?
 ;

greedy
 : '+'            -> ^(MIN_MAX INT["1"] INT[String.valueOf(Integer.MAX_VALUE)])
 | '*'            -> ^(MIN_MAX INT["0"] INT[String.valueOf(Integer.MAX_VALUE)])
 | '?'            -> ^(MIN_MAX INT["0"] INT["1"])
 | '{' (a=integer -> ^(MIN_MAX INT[$a.text] INT[$a.text]))
   (
     (','         -> ^(MIN_MAX INT[$a.text] INT[String.valueOf(Integer.MAX_VALUE)]))
     (b=integer   -> ^(MIN_MAX INT[$a.text] INT[$b.text]))?
   )? 
   '}'
 ;

charClass
 : '[' (('^')=> '^' ( SquareBracketEnd charClassAtom* -> ^(NEG_CHAR_CLASS LITERAL["]"] charClassAtom*)
                    | charClassAtom+                  -> ^(NEG_CHAR_CLASS charClassAtom+)
                    )
                    ']'
       |            ( SquareBracketEnd charClassAtom* -> ^(CHAR_CLASS LITERAL["]"] charClassAtom*)
                    | charClassAtom+                  -> ^(CHAR_CLASS charClassAtom+)
                    )
                    ']'
       )
 ;

charClassAtom
 : (charClassRange)=> charClassRange 
 |                    Quotation                  -> LITERAL[$Quotation.text]
 |                    shorthandCharacterClass
 |                    posixCharacterClass
 |                    charClassSingleCharLiteral
 ;

charClassRange
 : charClassSingleCharLiteral '-' charClassSingleCharLiteral -> ^(RANGE charClassSingleCharLiteral charClassSingleCharLiteral)
 ;

charClassSingleCharLiteral
 : charClassEscapeLiteral -> charClassEscapeLiteral
 | EscapeSequence         -> LITERAL[$EscapeSequence.text]
 | OctalChar              -> LITERAL[$OctalChar.text]
 | SmallHexChar           -> LITERAL[$SmallHexChar.text]
 | UnicodeChar            -> LITERAL[$UnicodeChar.text]
 | charClassSingleChar    -> LITERAL[$charClassSingleChar.text]
 ;

charClassSingleChar
 : Or
 | BeginLine
 | Hyphen
 | Colon
 | EndLine
 | SquareBracketStart
 | RoundBracketStart
 | RoundBracketEnd
 | CurlyBracketStart
 | CurlyBracketEnd
 | Equals
 | LessThan
 | GreaterThan
 | ExclamationMark
 | Comma
 | Plus
 | Star
 | QuestionMark
 | Dot
 | Digit
 | OtherChar
 ;

charClassEscapeLiteral
 : Escape ( Escape           -> LITERAL["\\"]
          | BeginLine        -> LITERAL["^"]
          | SquareBracketEnd -> LITERAL["]"]
          | Hyphen           -> LITERAL["-"]
          )
 ;

singleCharLiteral
 : regexEscapeLiteral -> regexEscapeLiteral
 | EscapeSequence     -> LITERAL[$EscapeSequence.text]
 | OctalChar          -> LITERAL[$OctalChar.text]
 | SmallHexChar       -> LITERAL[$SmallHexChar.text]
 | UnicodeChar        -> LITERAL[$UnicodeChar.text]
 | Hyphen             -> LITERAL["-"]
 | Colon              -> LITERAL[":"]
 | SquareBracketEnd   -> LITERAL["]"]
 | CurlyBracketEnd    -> LITERAL["}"]
 | Equals             -> LITERAL["="]
 | LessThan           -> LITERAL["<"]
 | GreaterThan        -> LITERAL[">"]
 | ExclamationMark    -> LITERAL["!"]
 | Comma              -> LITERAL[","]
 | Digit              -> LITERAL[$Digit.text]
 | OtherChar          -> LITERAL[$OtherChar.text]
 ;

regexEscapeLiteral
 : Escape ( Escape             -> LITERAL["\\"]
          | Or                 -> LITERAL["|"]
          | BeginLine          -> LITERAL["^"]
          | EndLine            -> LITERAL["$"]
          | SquareBracketStart -> LITERAL["["]
          | RoundBracketStart  -> LITERAL["("]
          | RoundBracketEnd    -> LITERAL[")"]
          | CurlyBracketStart  -> LITERAL["{"]
          | CurlyBracketEnd    -> LITERAL["}"]
          | Plus               -> LITERAL["+"]
          | Star               -> LITERAL["*"]
          | QuestionMark       -> LITERAL["?"]
          | Dot                -> LITERAL["."]
          )
 ;

boundaryMatch
 : BeginLine
 | EndLine
 | WordBoundary
 | NonWordBoundary
 | StartInput
 | EndInputBeforeFinalTerminator
 | EndInput
 | EndPreviousMatch
 ;

backReference
 : '\\' integer -> ^(BACK_REFERENCE integer)
 ;

group
 : '(' 
   ( '?' ( (flags               -> ^(FLAG_GROUP flags)
           ) 
           (':' regexAlts       -> ^(NON_CAPTURE_GROUP flags regexAlts)
           )?
         | ':' regexAlts        -> ^(NON_CAPTURE_GROUP ^(FLAGS ^(ENABLE) ^(DISABLE)) regexAlts)
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
 : a=singleFlags ('-' b=singleFlags?)? -> ^(FLAGS ^(ENABLE $a) ^(DISABLE $b?))
 | '-' c=singleFlags                   -> ^(FLAGS ^(ENABLE)    ^(DISABLE $c))
 ;

singleFlags
 : singleFlag+
 ;

singleFlag
 : OtherChar -> FLAG[$OtherChar.text]
 ;

shorthandCharacterClass
 : ShorthandCharacterClassDigit
 | ShorthandCharacterClassNonDigit
 | ShorthandCharacterClassSpace
 | ShorthandCharacterClassNonSpace
 | ShorthandCharacterClassWord
 | ShorthandCharacterClassNonWord
 ;

posixCharacterClass
 : PosixCharacterClassLower
 | PosixCharacterClassUpper
 | PosixCharacterClassASCII
 | PosixCharacterClassAlpha
 | PosixCharacterClassDigit
 | PosixCharacterClassAlnum
 | PosixCharacterClassPunct
 | PosixCharacterClassGraph
 | PosixCharacterClassPrint
 | PosixCharacterClassBlank
 | PosixCharacterClassCntrl
 | PosixCharacterClassXDigit
 | PosixCharacterClassSpace
 ;

integer
 : (options{greedy=true;}: Digit)+
 ;

// lexer rules
Quotation
 : '\\Q' .* '\\E' {setText($text.substring(2, $text.length() - 2));}
 ;

PosixCharacterClassLower  : 'p{Lower}';
PosixCharacterClassUpper  : 'p{Upper}';
PosixCharacterClassASCII  : 'p{ASCII}';
PosixCharacterClassAlpha  : 'p{Alpha}';
PosixCharacterClassDigit  : 'p{Digit}';
PosixCharacterClassAlnum  : 'p{Alnum}';
PosixCharacterClassPunct  : 'p{Punct}';
PosixCharacterClassGraph  : 'p{Graph}';
PosixCharacterClassPrint  : 'p{Print}';
PosixCharacterClassBlank  : 'p{Blank}';
PosixCharacterClassCntrl  : 'p{Cntrl}';
PosixCharacterClassXDigit : 'p{XDigit}';
PosixCharacterClassSpace  : 'p{Space}';

ShorthandCharacterClassDigit    : '\\d';
ShorthandCharacterClassNonDigit : '\\D';
ShorthandCharacterClassSpace    : '\\s';
ShorthandCharacterClassNonSpace : '\\S';
ShorthandCharacterClassWord     : '\\w';
ShorthandCharacterClassNonWord  : '\\W';

WordBoundary                  : '\\b';
NonWordBoundary               : '\\B';
StartInput                    : '\\A';
EndInputBeforeFinalTerminator : '\\Z';
EndInput                      : '\\z';
EndPreviousMatch              : '\\G';

OctalChar
 : Escape '0' ('0'..'3' OctDigit OctDigit | OctDigit? OctDigit)
   {
     int oct = Integer.valueOf($text.substring(2), 8);
     setText(Character.valueOf((char)oct).toString());
   }
 ;

SmallHexChar
 : Escape 'x' HexDigit HexDigit
   {
     int hex = Integer.valueOf($text.substring(2), 16);
     setText(Character.valueOf((char)hex).toString());
   }
 ;

UnicodeChar
 : Escape 'u' HexDigit HexDigit HexDigit HexDigit
   {
     int hex = Integer.valueOf($text.substring(2), 16);
     char[] utf16 = Character.toChars(hex);
     setText(new String(utf16));
   }
 ;

EscapeSequence
 : Escape ( 't'         {setText("\t");}
          | 'n'         {setText("\n");}
          | 'r'         {setText("\r");}
          | 'f'         {setText("\f");}
          | 'a'         {setText("\u0007");}
          | 'e'         {setText("\u001B");}
          | NonAlphaNum {setText($NonAlphaNum.text);}
          )
 ;

Escape             : '\\';
Or                 : '|';
Hyphen             : '-';
BeginLine          : '^';
Colon              : ':';
EndLine            : '$';
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
fragment NonAlphaNum : ~('a'..'z' | 'A'..'Z' | '0'..'9');
fragment OctDigit    : '0'..'7';
fragment HexDigit    : ('0'..'9' | 'a'..'f' | 'A'..'F');
