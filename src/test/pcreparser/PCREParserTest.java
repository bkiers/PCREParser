package pcreparser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
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
        // TODO
    }

    @Test
    public void shared_atomTest() throws Exception {
        // TODO
    }

    @Test
    public void literalTest() throws Exception {
        // TODO
    }

    @Test
    public void cc_literalTest() throws Exception {
        // TODO
    }

    @Test
    public void shared_literalTest() throws Exception {
        // TODO
    }

    @Test
    public void numberTest() throws Exception {
        // TODO
    }

    @Test
    public void digitsTest() throws Exception {
        // TODO
    }

    @Test
    public void digitTest() throws Exception {
        // TODO
    }

    @Test
    public void nameTest() throws Exception {
        // TODO
    }

    @Test
    public void lettersTest() throws Exception {
        // TODO
    }

    @Test
    public void non_close_parensTest() throws Exception {
        // TODO
    }

    @Test
    public void non_close_parenTest() throws Exception {
        // TODO
    }

    @Test
    public void letterTest() throws Exception {
        // TODO
    }
}
