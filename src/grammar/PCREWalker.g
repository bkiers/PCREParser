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
 | boundaryMatch
 | backReference
 | group
 | shorthandCharacterClass
 | posixCharacterClass
 | Dot
 | LITERAL
 ;

quantifier
 : ^(GREEDY greedy)
 | ^(POSSESSIVE greedy)
 | ^(RELUCTANT greedy)
 ;

greedy
 : ^(MIN_MAX INT INT)
 ;

charClass
 : ^(NEG_CHAR_CLASS charClassAtom+)
 | ^(CHAR_CLASS charClassAtom+)
 ;

charClassAtom
 : charClassRange
 | shorthandCharacterClass
 | posixCharacterClass
 | LITERAL
 ;

charClassRange
 : ^(RANGE LITERAL LITERAL)
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
 : ^(FLAGS ^(ENABLE singleFlags?) ^(DISABLE singleFlags?))
 ;

singleFlags
 : FLAG+
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
 : Digit+
 ;
