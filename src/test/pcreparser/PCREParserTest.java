package pcreparser;

import com.sun.istack.internal.NotNull;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
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
        // TODO
    }

    @Test
    public void regexTest() throws Exception {
        // TODO
    }

    @Test
    public void first_alternativeTest() throws Exception {
        // TODO
    }

    @Test
    public void alternativeTest() throws Exception {
        // TODO
    }

    @Test
    public void elementTest() throws Exception {
        // TODO
    }

    @Test
    public void quantifierTest() throws Exception {
        // TODO
    }

    @Test
    public void quantifier_typeTest() throws Exception {
        // TODO
    }

    @Test
    public void character_classTest() throws Exception {
        // TODO
    }

    @Test
    public void cc_atom_end_rangeTest() throws Exception {
        // TODO
    }

    @Test
    public void backreferenceTest() throws Exception {
        // TODO
    }

    @Test
    public void backreference_or_octalTest() throws Exception {
        // TODO
    }

    @Test
    public void captureTest() throws Exception {
        // TODO
    }

    @Test
    public void commentTest() throws Exception {
        // TODO
    }

    @Test
    public void optionTest() throws Exception {
        // TODO
    }

    @Test
    public void option_flagsTest() throws Exception {
        // TODO
    }

    @Test
    public void option_flagTest() throws Exception {
        // TODO
    }

    @Test
    public void look_aroundTest() throws Exception {
        // TODO
    }

    @Test
    public void subroutine_referenceTest() throws Exception {
        // TODO
    }

    @Test
    public void conditionalTest() throws Exception {
        // TODO
    }

    @Test
    public void backtrack_controlTest() throws Exception {
        // TODO
    }

    @Test
    public void newline_conventionTest() throws Exception {
        // TODO
    }

    @Test
    public void calloutTest() throws Exception {
        // TODO
    }

    @Test
    public void atomTest() throws Exception {
        // TODO
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
                "a 4 \\a \\e \\f \\n \\r \\t \\xFF \\x{1234} \\. " +
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
    public void digitsTest() throws Exception {

        String source = "0123456789";
        PCREParser parser = getParser(source);

        PCREParser.digits_return value = parser.digits();
        CommonTree tree = (CommonTree)value.getTree();

        assertThat(tree.getChildCount(), is(10));
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

        PCREParser.letters_return value = parser.letters();
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
