## PCREParser

### Introduction

An ANTLR grammar that generates a parser able to parse [PCRE](http://www.pcre.org/pcre.txt) (Perl 
compatible regular expressions) and produce an abstract syntax tree (AST) of such expressions.

### Download/get library

To get the library, checkout the project and run `mvn clean install`, or
[download](https://github.com/bkiers/PCREParser/blob/master/PCREParser-0.3.1.jar) the jar.

You can also try the parser online: [pcreparser.appspot.com](http://pcreparser.appspot.com/)

### Some examples

The main class of this library is the `pcreparser.PCRE` class. Below are some examples of supported functionality.

#### 1. Get capture group-count

`source:`

```java
PCRE pcre = new PCRE("((.)\\1+ (?<YEAR>(?:19|20)\\d{2})) [^]-x]");
System.out.println(pcre.getGroupCount());
```

`output:`

```
3
```

Note that the named capture group, `(?<YEAR>(?:19|20)\\d{2})`, also counts. Below is the list of groups:

1. `((.)\\1+ (?<YEAR>(?:19|20)\\d{2}))`
2. `(.)`
3. `(?<YEAR>(?:19|20)\\d{2})`

#### 2. Get named group-count

`source:`

```java
PCRE pcre = new PCRE("((.)\\1+ (?<YEAR>(?:19|20)\\d{2})) [^]-x]");
System.out.println(pcre.getNamedGroupCount());
```

`output:`

```
1
```

#### 3. Print ASCII tree of regex or group

`source:`

```java
PCRE pcre = new PCRE("((.)\\1+ (?<YEAR>(?:19|20)\\d{2})) [^]-x]");
System.out.println(pcre.toStringASCII()); // equivalent to: pcre.toStringASCII(0)
```

`output:`

```
'- ALTERNATIVE
   |- ELEMENT
   |  '- CAPTURING_GROUP
   |     '- ALTERNATIVE
   |        |- ELEMENT
   |        |  '- CAPTURING_GROUP
   |        |     '- ALTERNATIVE
   |        |        '- ELEMENT
   |        |           '- ANY
   |        |- ELEMENT
   |        |  |- NUMBERED_BACKREFERENCE
   |        |  |  '- NUMBER='1'
   |        |  '- QUANTIFIER
   |        |     |- NUMBER='1'
   |        |     |- NUMBER='2147483647'
   |        |     '- GREEDY
   |        |- ELEMENT
   |        |  '- LITERAL=' '
   |        '- ELEMENT
   |           '- NAMED_CAPTURING_GROUP_PERL
   |              |- NAME='YEAR'
   |              '- ALTERNATIVE
   |                 |- ELEMENT
   |                 |  '- NON_CAPTURING_GROUP
   |                 |     '- OR
   |                 |        |- ALTERNATIVE
   |                 |        |  |- ELEMENT
   |                 |        |  |  '- LITERAL='1'
   |                 |        |  '- ELEMENT
   |                 |        |     '- LITERAL='9'
   |                 |        '- ALTERNATIVE
   |                 |           |- ELEMENT
   |                 |           |  '- LITERAL='2'
   |                 |           '- ELEMENT
   |                 |              '- LITERAL='0'
   |                 '- ELEMENT
   |                    |- DecimalDigit='\d'
   |                    '- QUANTIFIER
   |                       |- NUMBER='2'
   |                       |- NUMBER='2'
   |                       '- GREEDY
   |- ELEMENT
   |  '- LITERAL=' '
   '- ELEMENT
      '- NEGATED_CHARACTER_CLASS
         '- RANGE
            |- LITERAL=']'
            '- LITERAL='x'
```

Or to print a specific group or named group:

`source:`

```java
PCRE pcre = new PCRE("((.)\\1+ (?<YEAR>(?:19|20)\\d{2})) [^]-x]");
System.out.println(pcre.toStringASCII(2));
```

`output:`

```
'- CAPTURING_GROUP
   '- ALTERNATIVE
      '- ELEMENT
         '- ANY
```

`source:`

```java
PCRE pcre = new PCRE("((.)\\1+ (?<YEAR>(?:19|20)\\d{2})) [^]-x]");
System.out.println(pcre.toStringASCII("YEAR"));
```

`output:`

```
'- NAMED_CAPTURING_GROUP_PERL
   |- NAME='YEAR'
   '- ALTERNATIVE
      |- ELEMENT
      |  '- NON_CAPTURING_GROUP
      |     '- OR
      |        |- ALTERNATIVE
      |        |  |- ELEMENT
      |        |  |  '- LITERAL='1'
      |        |  '- ELEMENT
      |        |     '- LITERAL='9'
      |        '- ALTERNATIVE
      |           |- ELEMENT
      |           |  '- LITERAL='2'
      |           '- ELEMENT
      |              '- LITERAL='0'
      '- ELEMENT
         |- DecimalDigit='\d'
         '- QUANTIFIER
            |- NUMBER='2'
            |- NUMBER='2'
            '- GREEDY
```

Besides the `toStringASCII()` method demonstrated above, there are some other methods able to display the AST:

* `PCRE#toStringDOT()`: creates a [DOT](http://en.wikipedia.org/wiki/DOT_language)-representation of group `0`
* `PCRE#toStringDOT(int n)`: creates a [DOT](http://en.wikipedia.org/wiki/DOT_language)-representation of group `n`
* `PCRE#toStringDOT(String s)`: creates a [DOT](http://en.wikipedia.org/wiki/DOT_language)-representation of named group `s`

#### 4. get the real AST

In order to get the actual AST from the pattern, use one of the following methods:

* `PCRE#getCommonTree()`: get the AST of group `0`
* `PCRE#getCommonTree(int n)`: get the AST of group `n`
* `PCRE#getCommonTree(String s)`: get the AST of named group `s`

All methods above return a [CommonTree](http://www.antlr.org/api/Java/classorg_1_1antlr_1_1runtime_1_1tree_1_1_common_tree.html)
that has the following attributes:

* `CommonTree#getChildren(): List`: a `java.util.List` of all child nodes/AST's
* `CommonTree#getType(): int`: the token type of the AST (token types can be found as static `int`s in PCRELexer, once generated)
* `CommonTree#getText(): String`: the text the token associated with this node matched during parsing
* [...](http://www.antlr.org/api/Java/classorg_1_1antlr_1_1runtime_1_1tree_1_1_common_tree.html)