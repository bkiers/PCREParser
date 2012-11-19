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
 * Project      : PCRE Parser, an ANTRL grammar for PCRE
 * Developed by : Bart Kiers, bart@big-o.nl
 */
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
