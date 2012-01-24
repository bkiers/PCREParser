tree grammar PCREWalker;

options {
  ASTLabelType=CommonTree;
  tokenVocab=PCRE;
}

@header {
  package pcreparser;
}

walk
 : ^(REGEX regexAlts)
 ;

regexAlts
 : ^(Or regexAlts regexAlts)
 | atoms
 ;

atoms
 : ^(ATOMS regexAtom*)
 ;

regexAtom
 : ^(ATOM unit quantifier?)
 ;

unit
 : charClass
 | singleChar
 | boundaryMatch
 | quotation
 | backReference
 | group
 | ShorthandCharacterClass
 | PosixCharacterClass
 | Dot
 ;

quantifier
 : ^(GREEDY ^(MIN_MAX INT INT))
 | ^(POSSESSIVE ^(MIN_MAX INT INT))
 | ^(RELUCTANT ^(MIN_MAX INT INT))
 ;

charClass
 : ^(NEG_CHAR_CLASS charClassAtom+)
 | ^(CHAR_CLASS charClassAtom+)
 ;

charClassAtom
 : ^(RANGE charClassSingleChar charClassSingleChar)
 | quotation
 | ShorthandCharacterClass
 | BoundaryMatch
 | PosixCharacterClass
 | charClassSingleChar
 ;

charClassSingleChar
 : LITERAL
 | EscapeSequence
 | OctalNumber
 | SmallHexNumber
 | UnicodeChar
 | Or
 | Caret
 | Hyphen
 | Colon
 | Dollar
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

singleChar
 : LITERAL
 | EscapeSequence
 | OctalNumber
 | SmallHexNumber
 | UnicodeChar
 | Hyphen
 | Colon
 | SquareBracketEnd
 | CurlyBracketEnd
 | Equals
 | LessThan
 | GreaterThan
 | ExclamationMark
 | Comma
 | Digit
 | OtherChar
 ;

boundaryMatch
 : Caret
 | Dollar
 | BoundaryMatch
 ;

backReference
 : ^(BACK_REFERENCE integer)
 ;

group
 : ^(FLAG_GROUP flags)
 | ^(NON_CAPTURE_GROUP flags regexAlts)
 | ^(ATOMIC_GROUP regexAlts)
 | ^(NEGATIVE_LOOK_AHEAD regexAlts)
 | ^(POSITIVE_LOOK_AHEAD regexAlts)
 | ^(NEGATIVE_LOOK_BEHIND regexAlts)
 | ^(POSITIVE_LOOK_BEHIND regexAlts)
 | ^(CAPTURE_GROUP regexAlts)
 ;

flags
 : ^(FLAGS ^(ENABLE OtherChar*) ^(DISABLE OtherChar*))
 ;

quotation
 : ^(QUOTATION innerQuotation)
 ;

innerQuotation
 : (~QuotationEnd)*
 ;

integer
 : Digit+
 ;
