To run the `Main` class, first generate a lexer and parser from the ANTLR grammar
file located in `src/grammar`. This can be done by executing the ant target:

    ant generate.parser

Then `Main` can be executed, by calling:

    ant run

which parses the regex `^(?!x)\w*([a-z\D]++)\1?(?<=xy?+)$` and prints the following output:

    REGEX
      ATOMS
        ATOM
          Caret='^'
        ATOM
          NEGATIVE_LOOK_AHEAD
            ATOMS
              ATOM
                OtherChar='x'
        ATOM
          ShorthandCharacterClass='\w'
          GREEDY
            INT='0'
            INT='2147483647'
        ATOM
          CAPTURE_GROUP
            ATOMS
              ATOM
                CHAR_CLASS
                  RANGE
                    OtherChar='a'
                    OtherChar='z'
                  ShorthandCharacterClass='\D'
                POSSESSIVE
                  INT='1'
                  INT='2147483647'
        ATOM
          BACK_REFERENCE
            Digit='1'
          GREEDY
            INT='0'
            INT='1'
        ATOM
          POSITIVE_LOOK_BEHIND
            ATOMS
              ATOM
                OtherChar='x'
              ATOM
                OtherChar='y'
                POSSESSIVE
                  INT='0'
                  INT='1'
        ATOM
          Dollar='$'
