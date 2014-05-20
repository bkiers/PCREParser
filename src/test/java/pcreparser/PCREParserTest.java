package pcreparser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

public class PCREParserTest {

    /**
     * Creates a PCRE-parser given some input <code>source</code>.
     *
     * @param source the input <code>source</code> to create the parser for.
     * @return       a PCRE-parser given some input <code>source</code>.
     */
    public static PCREParser getParser(String source) {
        ANTLRStringStream stream = new ANTLRStringStream(source);
        PCRELexer lexer = new PCRELexer(stream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        return new PCREParser(tokenStream);
    }

    @Test
    public void parseTest() throws Exception {

        // the same as regex()

        String[] tests = {
                "",
                "\\da",
                "(?<!s)[b]",
                "\\2(?=c)",
                "\\d",
                "e?foo",
                "xxyyf?+",
                "MUg??",
                "[aaaaaa]h*",
                "[^|]++i*+",
                "aaaj*?",
                "aaak+",
                "aaal++",
                "bbbm+?",
                "bbbn{42}",
                "CCCo{42,888}",
                "Dp{42,888}+",
                "Eq{42,888}?",
                "Fr{42,}",
                "Gs{42,}+",
                "HHHt{42,}?"
        };

        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.regex_return value = parser.regex();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.ALTERNATIVE));
        }

        tests = new String[]{
                "|",
                "(?!a)|(?<=b)",
                "a|b|c|d"
        };

        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.regex_return value = parser.regex();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.OR));
        }
    }

    @Test
    public void regexTest() throws Exception {

        String[] tests = {
                "",
                "\\da",
                "(?<!s)[b]",
                "\\2(?=c)",
                "\\d",
                "e?foo",
                "xxyyf?+",
                "MUg??",
                "[aaaaaa]h*",
                "[^|]++i*+",
                "aaaj*?",
                "aaak+",
                "aaal++",
                "bbbm+?",
                "bbbn{42}",
                "CCCo{42,888}",
                "Dp{42,888}+",
                "Eq{42,888}?",
                "Fr{42,}",
                "Gs{42,}+",
                "HHHt{42,}?"
        };

        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.regex_return value = parser.regex();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.ALTERNATIVE));
        }

        tests = new String[]{
                "|",
                "(?!a)|(?<=b)",
                "a|b|c|d"
        };

        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.regex_return value = parser.regex();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.OR));
        }
    }

    @Test
    public void first_alternativeTest() throws Exception {

        String[] tests = {
                "",
                "\\da",
                "(?<!s)[b]",
                "\\2(?=c)",
                "\\d",
                "e?foo",
                "xxyyf?+",
                "MUg??",
                "[aaaaaa]h*",
                "[^|]++i*+",
                "aaaj*?",
                "aaak+",
                "aaal++",
                "bbbm+?",
                "bbbn{42}",
                "CCCo{42,888}",
                "Dp{42,888}+",
                "Eq{42,888}?",
                "Fr{42,}",
                "Gs{42,}+",
                "HHHt{42,}?"
        };

        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.first_alternative_return value = parser.first_alternative();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.ALTERNATIVE));
        }
    }

    @Test
    public void alternativeTest() throws Exception {

        String[] tests = {
                "",
                "\\da",
                "(?<!s)[b]",
                "\\2(?=c)",
                "\\d",
                "e?foo",
                "xxyyf?+",
                "MUg??",
                "[aaaaaa]h*",
                "[^|]++i*+",
                "aaaj*?",
                "aaak+",
                "aaal++",
                "bbbm+?",
                "bbbn{42}",
                "CCCo{42,888}",
                "Dp{42,888}+",
                "Eq{42,888}?",
                "Fr{42,}",
                "Gs{42,}+",
                "HHHt{42,}?"
        };

        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.alternative_return value = parser.alternative();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.ALTERNATIVE));
        }
    }

    @Test
    public void elementTest() throws Exception {

        String[] tests = {
                "a",
                "[b]",
                "(?=c)",
                "\\d",
                "e?",
                "f?+",
                "g??",
                "h*",
                "i*+",
                "j*?",
                "k+",
                "l++",
                "m+?",
                "n{42}",
                "o{42,888}",
                "p{42,888}+",
                "q{42,888}?",
                "r{42,}",
                "s{42,}+",
                "t{42,}?"
        };

        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.element_return value = parser.element();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.ELEMENT));
        }
    }

    @Test
    public void quantifierTest() throws Exception {

        String[] tests = {
                "?",
                "?+",
                "??",
                "*",
                "*+",
                "*?",
                "+",
                "++",
                "+?",
                "{42}",
                "{42,888}",
                "{42,888}+",
                "{42,888}?",
                "{42,}",
                "{42,}+",
                "{42,}?"
        };

        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.quantifier_return value = parser.quantifier();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getChildCount(), is(3));
            assertThat(tree.getType(), is(PCRELexer.QUANTIFIER));

            assertThat(tree.getChild(0).getType(), is(PCRELexer.NUMBER));
            assertThat(tree.getChild(1).getType(), is(PCRELexer.NUMBER));
        }
    }

    @Test
    public void quantifier_typeTest() throws Exception {

        Object[][] tests = {
                {"+" ,PCRELexer.POSSESSIVE},
                {"?" ,PCRELexer.LAZY},
                {"", PCRELexer.GREEDY}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            Integer expected = (Integer)test[1];

            PCREParser parser = getParser(input);
            PCREParser.quantifier_type_return value = parser.quantifier_type();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(expected));
        }
    }

    @Test
    public void character_classTest() throws Exception {

        String[] tests = {
                "[^\\da-z]",
                "[^^]",
                "[^]^]",
                "[^\\]^]"
        };

        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.character_class_return value = parser.character_class();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.NEGATED_CHARACTER_CLASS));
        }

        tests = new String[]{
                "[\\da-z]",
                "[\\^]",
                "[]^]",
                "[\\]^]"
        };

        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.character_class_return value = parser.character_class();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.CHARACTER_CLASS));
        }

        PCREParser parser = getParser("[]-\\]]");  // valid range!
        PCREParser.character_class_return value = parser.character_class();

        assertThat(value, notNullValue());

        CommonTree tree = (CommonTree)value.getTree();

        assertThat(tree.getChildCount(), is(1));
        assertThat(tree.getType(), is(PCRELexer.CHARACTER_CLASS));

        CommonTree rangeNode = (CommonTree)tree.getChild(0);
        assertThat(rangeNode.getChildCount(), is(2));
        assertThat(rangeNode.getType(), is(PCRELexer.RANGE));
    }

    @Test
    public void cc_atom_end_rangeTest() throws Exception {

        String[] ranges = {"0-9", "\\]-$", "---", "[-["};

        for(String range : ranges) {

            PCREParser parser = getParser(range);
            PCREParser.cc_atom_end_range_return value = parser.cc_atom_end_range();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getChildCount(), is(2));
            assertThat(tree.getType(), is(PCRELexer.RANGE));
        }

        String[] tests = (
                "a b c [[:digit:]] \\d * $ ?"
        ).split("\\s+");


        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.cc_atom_end_range_return value = parser.cc_atom_end_range();

            assertThat(value, notNullValue());
        }
    }

    @Test
    public void backreferenceTest() throws Exception {

        final String number = "6";
        final String name = "justaname";

        // numbered backreferences

        Object[][] tests = {
                {String.format("\\%s", number) ,PCRELexer.NUMBERED_BACKREFERENCE},
                {String.format("\\g%s", number) ,PCRELexer.NUMBERED_BACKREFERENCE},
                {String.format("\\g{%s}", number) ,PCRELexer.NUMBERED_BACKREFERENCE},
                {String.format("\\g{-%s}", number) ,PCRELexer.RELATIVE_NUMBERED_BACKREFERENCE}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            Integer expected = (Integer)test[1];

            PCREParser parser = getParser(input);
            PCREParser.backreference_return value = parser.backreference();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getChildCount(), is(1));
            assertThat(tree.getType(), is(expected));

            CommonTree nameChild = (CommonTree)tree.getChild(0);

            assertThat(nameChild.getText(), is(number));
        }

        // named backreferences

        tests = new Object[][]{
                {String.format("\\k<%s>", name) ,PCRELexer.NAMED_BACKREFERENCE_PERL},
                {String.format("\\k'%s'", name) ,PCRELexer.NAMED_BACKREFERENCE_PERL},
                {String.format("\\g{%s}", name) ,PCRELexer.NAMED_BACKREFERENCE_PERL},
                {String.format("\\k{%s}", name) ,PCRELexer.NAMED_BACKREFERENCE_NET},
                {String.format("(?P=%s)", name) ,PCRELexer.NAMED_BACKREFERENCE_PYTHON}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            Integer expected = (Integer)test[1];

            PCREParser parser = getParser(input);
            PCREParser.backreference_return value = parser.backreference();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getChildCount(), is(1));
            assertThat(tree.getType(), is(expected));

            CommonTree nameChild = (CommonTree)tree.getChild(0);

            assertThat(nameChild.getText(), is(name));
        }
    }

    @Test
    public void backreference_or_octalTest() throws Exception {

        Object[][] tests = {
                {"\\41", "!"},
                {"\\041", "!"}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            String expected = (String)test[1];

            PCREParser parser = getParser(input);
            PCREParser.backreference_or_octal_return value = parser.backreference_or_octal();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.LITERAL));
            assertThat(tree.getText(), is(expected));
        }

        tests = new Object[][]{
                {"\\1", "1"},
                {"\\9", "9"}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            String expected = (String)test[1];

            PCREParser parser = getParser(input);
            PCREParser.backreference_or_octal_return value = parser.backreference_or_octal();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.NUMBERED_BACKREFERENCE));

            assertThat(tree.getChildCount(), is(1));

            CommonTree numberNode = (CommonTree)tree.getChild(0);

            assertThat(numberNode.getType(), is(PCRELexer.NUMBER));
            assertThat(numberNode.getText(), is(expected));
        }
    }

    @Test
    public void captureTest() throws Exception {

        // named captures

        final String name = "Just_A_Name";

        Object[][] tests = {
                {String.format("(?<%s>regex)", name), PCRELexer.NAMED_CAPTURING_GROUP_PERL},
                {String.format("(?'%s'regex)", name), PCRELexer.NAMED_CAPTURING_GROUP_PERL},
                {String.format("(?P<%s>regex)", name), PCRELexer.NAMED_CAPTURING_GROUP_PYTHON}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            Integer expected = (Integer)test[1];

            PCREParser parser = getParser(input);
            PCREParser.capture_return value = parser.capture();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getChildCount(), is(2));
            assertThat(tree.getType(), is(expected));

            CommonTree nameChild = (CommonTree)tree.getChild(0);

            assertThat(nameChild.getText(), is(name));
        }

        // numbered captures

        PCREParser parser = getParser("(regex)");
        PCREParser.capture_return value = parser.capture();

        assertThat(value, notNullValue());

        CommonTree tree = (CommonTree)value.getTree();

        assertThat(tree.getType(), is(PCRELexer.CAPTURING_GROUP));
    }

    @Test
    public void non_captureTest() throws Exception {

        Object[][] tests = {
                {"(?:a)" ,PCRELexer.NON_CAPTURING_GROUP},
                {"(?|b)" ,PCRELexer.NON_CAPTURING_GROUP_RESET},
                {"(?>c)" ,PCRELexer.ATOMIC_GROUP}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            Integer expected = (Integer)test[1];

            PCREParser parser = getParser(input);
            PCREParser.non_capture_return value = parser.non_capture();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(expected));
        }
    }

    @Test
    public void commentTest() throws Exception {

        String comment = "just a [a-z]+ comment";
        PCREParser parser = getParser(String.format("(?#%s)regex", comment));
        PCREParser.comment_return value = parser.comment();

        assertThat(value, notNullValue());

        CommonTree tree = (CommonTree)value.getTree();

        assertThat(tree.getType(), is(PCRELexer.COMMENT));
        assertThat(tree.getText(), is(comment));
    }

    @Test
    public void optionTest() throws Exception {

        Object[][] tests = {
                {"(*NO_START_OPT)" ,PCRELexer.OPTIONS_NO_START_OPT},
                {"(*UTF8)" ,PCRELexer.OPTIONS_UTF8},
                {"(*UTF16)" ,PCRELexer.OPTIONS_UTF16},
                {"(*UCP)" ,PCRELexer.OPTIONS_UCP},
                {"(?mis)" ,PCRELexer.OPTIONS},
                {"(?-Jx)" ,PCRELexer.OPTIONS},
                {"(?m-isx)" ,PCRELexer.OPTIONS}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            Integer expected = (Integer)test[1];

            PCREParser parser = getParser(input);
            PCREParser.option_return value = parser.option();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(expected));
        }
    }

    @Test
    public void option_flagsTest() throws Exception {

        PCREParser parser = getParser("iJmsUx???");
        PCREParser.option_flags_return value = parser.option_flags();

        assertThat(value, notNullValue());

        CommonTree tree = (CommonTree)value.getTree();

        assertThat(tree.getChildCount(), is(6));
    }

    @Test
    public void option_flagTest() throws Exception {

        String[] tests = {"i", "J", "m", "s", "U", "x"};

        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.option_flag_return value = parser.option_flag();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.OPTION));
        }
    }

    @Test
    public void look_aroundTest() throws Exception {

        Object[][] tests = {
                {"(?=a)" ,PCRELexer.LOOK_AHEAD},
                {"(?!b)" ,PCRELexer.NEGATIVE_LOOK_AHEAD},
                {"(?<=c)" ,PCRELexer.LOOK_BEHIND},
                {"(?<!d)" ,PCRELexer.NEGATIVE_LOOK_BEHIND}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            Integer expected = (Integer)test[1];

            PCREParser parser = getParser(input);
            PCREParser.look_around_return value = parser.look_around();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(expected));
        }
    }

    @Test
    public void subroutine_referenceTest() throws Exception {

        Object[][] tests = {
                {"(?R)" ,PCRELexer.NUMBERED_REFERENCE_ABSOLUTE},
                {"(?123)" ,PCRELexer.NUMBERED_REFERENCE_ABSOLUTE},
                {"(?+123)" ,PCRELexer.NUMBERED_REFERENCE_RELATIVE_PLUS},
                {"(?-123)" ,PCRELexer.NUMBERED_REFERENCE_RELATIVE_MINUS},
                {"(?&name)" ,PCRELexer.NAMED_REFERENCE_PERL},
                {"(?P>name)" ,PCRELexer.NAMED_REFERENCE_PYTHON},
                {"\\g<name>" ,PCRELexer.NAMED_REFERENCE_ONIGURUMA},
                {"\\g'name'" ,PCRELexer.NAMED_REFERENCE_ONIGURUMA},
                {"\\g<123>" ,PCRELexer.NUMBERED_REFERENCE_ABSOLUTE_ONIGURUMA},
                {"\\g'123'" ,PCRELexer.NUMBERED_REFERENCE_ABSOLUTE_ONIGURUMA},
                {"\\g<+123>" ,PCRELexer.NUMBERED_REFERENCE_RELATIVE_PLUS},
                {"\\g'+123'" ,PCRELexer.NUMBERED_REFERENCE_RELATIVE_PLUS},
                {"\\g<-123>" ,PCRELexer.NUMBERED_REFERENCE_RELATIVE_MINUS},
                {"\\g'-123'" ,PCRELexer.NUMBERED_REFERENCE_RELATIVE_MINUS}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            Integer expected = (Integer)test[1];

            PCREParser parser = getParser(input);
            PCREParser.subroutine_reference_return value = parser.subroutine_reference();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(expected));
        }
    }

    @Test
    public void conditionalTest() throws Exception {

        Object[][] tests = {
                {"(?(123)a)", PCRELexer.REFERENCE_CONDITION_ABSOLUTE},
                {"(?(+123)a)", PCRELexer.REFERENCE_CONDITION_RELATIVE_PLUS},
                {"(?(-123)a)", PCRELexer.REFERENCE_CONDITION_RELATIVE_MINUS},
                {"(?(<name>)a)", PCRELexer.NAMED_REFERENCE_CONDITION_PERL},
                {"(?('name')a)", PCRELexer.NAMED_REFERENCE_CONDITION_PERL},
                {"(?(name)a)", PCRELexer.NAMED_REFERENCE_CONDITION},
                {"(?(R)a)", PCRELexer.OVERALL_RECURSION_CONDITION},
                {"(?(R123)a)", PCRELexer.SPECIFIC_GROUP_RECURSION_CONDITION},
                {"(?(R&name)a)", PCRELexer.SPECIFIC_RECURSION_CONDITION},
                {"(?(DEFINE)a)", PCRELexer.DEFINE},
                {"(?(assert)a)", PCRELexer.ASSERT},
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            Integer expected = (Integer)test[1];

            PCREParser parser = getParser(input);
            PCREParser.conditional_return value = parser.conditional();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(expected));
        }
    }

    @Test
    public void backtrack_controlTest() throws Exception {

        Object[][] tests = {
                {"(*ACCEPT)", PCRELexer.BACKTACK_CONTROL_ACCEPT},
                {"(*FAIL)", PCRELexer.BACKTACK_CONTROL_FAIL},
                {"(*MARK:NAME)", PCRELexer.BACKTACK_CONTROL_MARK_NAME},
                {"(*COMMIT)", PCRELexer.BACKTACK_CONTROL_COMMIT},
                {"(*PRUNE)", PCRELexer.BACKTACK_CONTROL_PRUNE},
                {"(*PRUNE:NAME)", PCRELexer.BACKTACK_CONTROL_PRUNE_NAME},
                {"(*SKIP)", PCRELexer.BACKTACK_CONTROL_SKIP},
                {"(*SKIP:NAME)", PCRELexer.BACKTACK_CONTROL_SKIP_NAME},
                {"(*THEN)", PCRELexer.BACKTACK_CONTROL_THEN},
                {"(*THEN:NAME)", PCRELexer.BACKTACK_CONTROL_THEN_NAME}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            Integer expected = (Integer)test[1];

            PCREParser parser = getParser(input);
            PCREParser.backtrack_control_return value = parser.backtrack_control();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(expected));
        }
    }

    @Test
    public void newline_conventionTest() throws Exception {

        Object[][] tests = {
                {"(*CR)", PCRELexer.NEWLINE_CONVENTION_CR},
                {"(*LF)", PCRELexer.NEWLINE_CONVENTION_LF},
                {"(*CRLF)", PCRELexer.NEWLINE_CONVENTION_CRLF},
                {"(*ANYCRLF)", PCRELexer.NEWLINE_CONVENTION_ANYCRLF},
                {"(*ANY)", PCRELexer.NEWLINE_CONVENTION_ANY},
                {"(*BSR_ANYCRLF)", PCRELexer.NEWLINE_CONVENTION_BSR_ANYCRLF},
                {"(*BSR_UNICODE)", PCRELexer.NEWLINE_CONVENTION_BSR_UNICODE}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            Integer expected = (Integer)test[1];

            PCREParser parser = getParser(input);
            PCREParser.newline_convention_return value = parser.newline_convention();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(expected));
        }
    }

    @Test
    public void calloutTest() throws Exception {

        String[] callouts = {"(?C)", "(?C12345)"};

        for(String callout : callouts) {

            PCREParser parser = getParser(callout);
            PCREParser.callout_return value = parser.callout();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.CALLOUT));
        }
    }

    @Test
    public void atomTest() throws Exception {

        Object[][] tests = {
                {"(?R)", PCRELexer.NUMBERED_REFERENCE_ABSOLUTE},
                {"\\w", PCRELexer.WordChar},
                {"]", PCRELexer.LITERAL},
                {"[]]", PCRELexer.CHARACTER_CLASS},
                {"(MU)", PCRELexer.CAPTURING_GROUP},
                {"(?|foo)", PCRELexer.NON_CAPTURING_GROUP_RESET},
                {"(?#...)", PCRELexer.COMMENT},
                {"(?is-mx)", PCRELexer.OPTIONS},
                {"(?<!ppp)", PCRELexer.NEGATIVE_LOOK_BEHIND},
                {"(?P=name)", PCRELexer.NAMED_BACKREFERENCE_PYTHON},
                {"(?(NAME)a|b)", PCRELexer.NAMED_REFERENCE_CONDITION},
                {"(*THEN:NAME)", PCRELexer.BACKTACK_CONTROL_THEN_NAME},
                {"(*LF)", PCRELexer.NEWLINE_CONVENTION_LF},
                {"(?C123456789)", PCRELexer.CALLOUT},
                {".", PCRELexer.ANY},
                {"^", PCRELexer.START_OF_SUBJECT},
                {"\\A", PCRELexer.START_OF_SUBJECT},
                {"\\b", PCRELexer.WordBoundary},
                {"\\B", PCRELexer.NonWordBoundary},
                {"$", PCRELexer.EndOfSubjectOrLine},
                {"\\Z", PCRELexer.EndOfSubjectOrLineEndOfSubject},
                {"\\z", PCRELexer.EndOfSubject},
                {"\\G", PCRELexer.PreviousMatchInSubject},
                {"\\K", PCRELexer.ResetStartMatch},
                {"\\C", PCRELexer.OneDataUnit},
                {"\\X", PCRELexer.ExtendedUnicodeChar}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            Integer expected = (Integer)test[1];

            PCREParser parser = getParser(input);
            PCREParser.atom_return value = parser.atom();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(expected));
        }
    }

    @Test
    public void cc_atomTest() throws Exception {

        String[] ranges = {"0-9", "\\]-$", "---", "[-["};

        for(String range : ranges) {

            PCREParser parser = getParser(range);
            PCREParser.cc_atom_return value = parser.cc_atom();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getChildCount(), is(2));
            assertThat(tree.getType(), is(PCRELexer.RANGE));
        }

        String[] tests = (
                "a b c [[:digit:]] \\d * $ ?"
        ).split("\\s+");


        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.cc_atom_return value = parser.cc_atom();

            assertThat(value, notNullValue());
        }
    }

    @Test
    public void shared_atomTest() throws Exception {

        String[] tests = (
                "[[:digit:]] [[:^upper:]] \\cX \\d \\D \\h \\H \\N " +
                "\\p{Thai} \\P{Thai} \\R \\s \\S \\v \\V \\w \\W"
        ).split("\\s+");


        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.shared_atom_return value = parser.shared_atom();

            assertThat(value, notNullValue());
        }
    }

    @Test
    public void literalTest() throws Exception {

        PCREParser parser = getParser("]");
        PCREParser.literal_return value = parser.literal();

        assertThat(value, notNullValue());

        CommonTree tree = (CommonTree)value.getTree();

        assertThat(tree.getChildCount(), is(0));
        assertThat(tree.getType(), is(PCRELexer.LITERAL));
    }

    @Test
    public void cc_literalTest() throws Exception {

        String[] tests = ". [ ^ ? + * \\b $ | ( )".split("\\s+");

        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.cc_literal_return value = parser.cc_literal();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getChildCount(), is(0));
            assertThat(tree.getType(), is(PCRELexer.LITERAL));
        }
    }

    @Test
    public void shared_literalTest() throws Exception {

        String[] tests = (
                "\\377 a 4 \\a \\e \\f \\n \\r \\t \\xFF \\x{1234} \\. " +
                "\\Q...\\E { } , - < > ' : _ : # = ! & ~ ` @ %"
        ).split("\\s+");


        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.shared_literal_return value = parser.shared_literal();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getChildCount(), is(0));
            assertThat(tree.getType(), is(PCRELexer.LITERAL));
        }
    }

    @Test
    public void numberTest() throws Exception {

        String source = "4567";
        PCREParser parser = getParser(source + " ... ");

        PCREParser.number_return value = parser.number();
        CommonTree tree = (CommonTree)value.getTree();

        assertThat(tree.getChildCount(), is(0));
        assertThat(tree.getType(), is(PCRELexer.NUMBER));
        assertThat(tree.getText(), is(source));
    }

    @Test
    public void octalTest() throws Exception {

        Object[][] tests = {
                {"\\41", "!"},
                {"\\041", "!"}
        };

        for(Object[] test : tests) {

            String input = (String)test[0];
            String expected = (String)test[1];

            PCREParser parser = getParser(input);
            PCREParser.octal_char_return value = parser.octal_char();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getType(), is(PCRELexer.LITERAL));
            assertThat(tree.getText(), is(expected));
        }
    }

    @Test
    public void digitsTest() throws Exception {

        String source = "0123456789";
        PCREParser parser = getParser(source);

        PCREParser.digits_return value = parser.digits();
        CommonTree tree = (CommonTree)value.getTree();

        assertThat(tree.getChildCount(), is(10));
    }

    @Test
    public void octal_digitTest() throws Exception {

        String[] tests = ("0 1 2 3 4 5 6 7").split("\\s+");


        for(String test : tests) {

            PCREParser parser = getParser(test);
            PCREParser.octal_digit_return value = parser.octal_digit();

            assertThat(value, notNullValue());

            CommonTree tree = (CommonTree)value.getTree();

            assertThat(tree.getChildCount(), is(0));
            assertThat(tree.getText(), is(test));
        }
    }

    @Test
    public void digitTest() throws Exception {

        String source = "0123456789";
        PCREParser parser = getParser(source);

        for(int i = 0; i < source.length(); i++) {
            PCREParser.digit_return value = parser.digit();
            assertThat(value, notNullValue());
        }
    }

    @Test
    public void nameTest() throws Exception {

        String source = "justAname";
        PCREParser parser = getParser(source + " ... ");

        PCREParser.name_return value = parser.name();
        CommonTree tree = (CommonTree)value.getTree();

        assertThat(tree.getChildCount(), is(0));
        assertThat(tree.getType(), is(PCRELexer.NAME));
        assertThat(tree.getText(), is(source));
    }

    @Test
    public void lettersTest() throws Exception {

        String source = "abc)";
        PCREParser parser = getParser(source);

        PCREParser.alpha_nums_return value = parser.alpha_nums();
        CommonTree tree = (CommonTree)value.getTree();

        assertThat(tree.getChildCount(), is(3));
    }

    @Test
    public void non_close_parensTest() throws Exception {

        String source = "abc)";
        PCREParser parser = getParser(source);

        PCREParser.non_close_parens_return value = parser.non_close_parens();
        CommonTree tree = (CommonTree)value.getTree();

        assertThat(tree.getChildCount(), is(3));
    }

    @Test
    public void non_close_parenTest() throws Exception {

        String source = "abc)";
        PCREParser parser = getParser(source);

        for(int i = 0; i < source.length() - 1; i++) {
            PCREParser.non_close_paren_return value = parser.non_close_paren();
            assertThat(value, notNullValue());
        }
    }

    @Test
    public void letterTest() throws Exception {

        String source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        PCREParser parser = getParser(source);

        for(int i = 0; i < source.length(); i++) {
            PCREParser.letter_return value = parser.letter();
            assertThat(value, notNullValue());
        }
    }
}
