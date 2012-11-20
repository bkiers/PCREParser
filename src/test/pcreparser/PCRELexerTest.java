package pcreparser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PCRELexerTest {

    /**
     * Extracts a single token from <code>source</code>. If there
     * are more than 1 token, an assertion-error occurs.
     *
     * @param source the input to be tokenized.
     * @return       the token created from <code>source</code>.
     */
    public static CommonToken getToken(String source) {

        List<CommonToken> tokens = getTokens(source);

        assertThat(tokens.size(), is(1));

        return tokens.get(0);
    }

    /**
     * Creates a <code>List</code> of tokens from the input
     * <code>source</code>.
     *
     * @param source the input to be tokenized.
     * @return       a <code>List</code> of tokens created from
     *               the input <code>source</code>.
     */
    @SuppressWarnings("unchecked")
    public static List<CommonToken> getTokens(String source) {

        ANTLRStringStream stream = new ANTLRStringStream(source);
        PCRELexer lexer = new PCRELexer(stream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        tokenStream.fill();

        List<CommonToken> tokens = (List<CommonToken>)tokenStream.getTokens();

        // remove the EOF token
        tokens.remove(tokens.size() - 1);

        return tokens;
    }

    @Test
    public void QuotationTest() {

        List<CommonToken> tokens = getTokens(
                "\\Q ... \\E" +
                "\\Q ... \\Q ... \\E" +
                "\\Q \\ \r \n \\E"
        );

        for(CommonToken t : tokens) {
            assertThat(t.getType(), is(PCRELexer.Quotation));
        }
    }

    @Test
    public void UnicodeScriptOrBlockTest() {
        CommonToken token = getToken("\\p{IsGreek}");
        assertThat(token.getType(), is(PCRELexer.UnicodeScriptOrBlock));

        token = getToken("\\p{Lu}");
        assertThat(token.getType(), is(PCRELexer.UnicodeScriptOrBlock));
    }

    @Test
    public void NegatedUnicodeScriptOrBlockTest() {
        CommonToken token = getToken("\\P{Non_Spacing_Mark}");
        assertThat(token.getType(), is(PCRELexer.NegatedUnicodeScriptOrBlock));

        token = getToken("\\P{Lu}");
        assertThat(token.getType(), is(PCRELexer.NegatedUnicodeScriptOrBlock));
    }

    @Test
    public void ShorthandCharacterClassDigitTest() {
        CommonToken token = getToken("\\d");
        assertThat(token.getType(), is(PCRELexer.ShorthandCharacterClassDigit));
    }

    @Test
    public void ShorthandCharacterClassNonDigitTest() {
        CommonToken token = getToken("\\D");
        assertThat(token.getType(), is(PCRELexer.ShorthandCharacterClassNonDigit));
    }

    @Test
    public void ShorthandCharacterClassSpaceTest() {
        CommonToken token = getToken("\\s");
        assertThat(token.getType(), is(PCRELexer.ShorthandCharacterClassSpace));
    }

    @Test
    public void ShorthandCharacterClassNonSpaceTest() {
        CommonToken token = getToken("\\S");
        assertThat(token.getType(), is(PCRELexer.ShorthandCharacterClassNonSpace));
    }

    @Test
    public void ShorthandCharacterClassWordTest() {
        CommonToken token = getToken("\\w");
        assertThat(token.getType(), is(PCRELexer.ShorthandCharacterClassWord));
    }

    @Test
    public void ShorthandCharacterClassNonWordTest() {
        CommonToken token = getToken("\\W");
        assertThat(token.getType(), is(PCRELexer.ShorthandCharacterClassNonWord));
    }

    @Test
    public void WordBoundaryTest() {
        CommonToken token = getToken("\\b");
        assertThat(token.getType(), is(PCRELexer.WordBoundary));
    }

    @Test
    public void NonWordBoundaryTest() {
        CommonToken token = getToken("\\B");
        assertThat(token.getType(), is(PCRELexer.NonWordBoundary));
    }

    @Test
    public void StartInputTest() {
        CommonToken token = getToken("\\A");
        assertThat(token.getType(), is(PCRELexer.StartInput));
    }

    @Test
    public void EndInputBeforeFinalTerminatorTest() {
        CommonToken token = getToken("\\Z");
        assertThat(token.getType(), is(PCRELexer.EndInputBeforeFinalTerminator));
    }

    @Test
    public void EndInputTest() {
        CommonToken token = getToken("\\z");
        assertThat(token.getType(), is(PCRELexer.EndInput));
    }

    @Test
    public void EndPreviousMatchTest() {
        CommonToken token = getToken("\\G");
        assertThat(token.getType(), is(PCRELexer.EndPreviousMatch));
    }

    @Test
    public void OctalCharTest() {
        // 3 octals and the digit 7 at the end
        List<CommonToken> tokens = getTokens("\\0377\\0123\\0777");

        assertThat(tokens.size(), is(4));

        assertThat(tokens.get(0).getType(), is(PCRELexer.OctalChar));
        assertThat(tokens.get(1).getType(), is(PCRELexer.OctalChar));
        assertThat(tokens.get(2).getType(), is(PCRELexer.OctalChar));

        assertThat(tokens.get(3).getType(), is(PCRELexer.Digit));

        assertThat(tokens.get(0).getText(), is(String.valueOf((char)0377)));
        assertThat(tokens.get(1).getText(), is(String.valueOf((char)0123)));
        assertThat(tokens.get(2).getText(), is(String.valueOf((char)077)));
    }

    @Test
    public void SmallHexCharTest() {
        List<CommonToken> tokens = getTokens("\\xA1\\xFF\\xeA");

        assertThat(tokens.size(), is(3));

        assertThat(tokens.get(0).getType(), is(PCRELexer.SmallHexChar));
        assertThat(tokens.get(1).getType(), is(PCRELexer.SmallHexChar));
        assertThat(tokens.get(2).getType(), is(PCRELexer.SmallHexChar));

        assertThat(tokens.get(0).getText(), is(String.valueOf((char)0xA1)));
        assertThat(tokens.get(1).getText(), is(String.valueOf((char)0xFF)));
        assertThat(tokens.get(2).getText(), is(String.valueOf((char)0xEA)));
    }

    @Test
    public void UnicodeCharTest() {
        List<CommonToken> tokens = getTokens("\\uABCD\\uEF00\\u0101");

        assertThat(tokens.size(), is(3));

        assertThat(tokens.get(0).getType(), is(PCRELexer.UnicodeChar));
        assertThat(tokens.get(1).getType(), is(PCRELexer.UnicodeChar));
        assertThat(tokens.get(2).getType(), is(PCRELexer.UnicodeChar));

        assertThat(tokens.get(0).getText(), is("\uABCD"));
        assertThat(tokens.get(1).getText(), is("\uEF00"));
        assertThat(tokens.get(2).getText(), is("\u0101"));
    }

    @Test
    public void EscapeSequenceTest() {
        List<CommonToken> tokens = getTokens("\\t\\n\\r\\f\\a\\e\\cM");

        assertThat(tokens.size(), is(7));

        for(CommonToken t : tokens) {
            assertThat(t.getType(), is(PCRELexer.EscapeSequence));
        }

        assertThat(tokens.get(0).getText(), is("\t"));
        assertThat(tokens.get(1).getText(), is("\n"));
        assertThat(tokens.get(2).getText(), is("\r"));
        assertThat(tokens.get(3).getText(), is("\f"));
        assertThat(tokens.get(4).getText(), is("\u0007"));
        assertThat(tokens.get(5).getText(), is("\u001B"));
        assertThat(tokens.get(6).getText(), is("\\cM"));
    }

    @Test
    public void EscapeTest() {
        CommonToken token = getToken("\\");
        assertThat(token.getType(), is(PCRELexer.Escape));
    }

    @Test
    public void OrTest() {
        CommonToken token = getToken("|");
        assertThat(token.getType(), is(PCRELexer.Or));
    }

    @Test
    public void HyphenTest() {
        CommonToken token = getToken("-");
        assertThat(token.getType(), is(PCRELexer.Hyphen));
    }

    @Test
    public void BeginLineTest() {
        CommonToken token = getToken("^");
        assertThat(token.getType(), is(PCRELexer.BeginLine));
    }

    @Test
    public void ColonTest() {
        CommonToken token = getToken(":");
        assertThat(token.getType(), is(PCRELexer.Colon));
    }

    @Test
    public void EndLineTest() {
        CommonToken token = getToken("$");
        assertThat(token.getType(), is(PCRELexer.EndLine));
    }

    @Test
    public void SquareBracketStartTest() {
        CommonToken token = getToken("[");
        assertThat(token.getType(), is(PCRELexer.SquareBracketStart));
    }

    @Test
    public void SquareBracketEndTest() {
        CommonToken token = getToken("]");
        assertThat(token.getType(), is(PCRELexer.SquareBracketEnd));
    }

    @Test
    public void RoundBracketStartTest() {
        CommonToken token = getToken("(");
        assertThat(token.getType(), is(PCRELexer.RoundBracketStart));
    }

    @Test
    public void RoundBracketEndTest() {
        CommonToken token = getToken(")");
        assertThat(token.getType(), is(PCRELexer.RoundBracketEnd));
    }

    @Test
    public void CurlyBracketStartTest() {
        CommonToken token = getToken("{");
        assertThat(token.getType(), is(PCRELexer.CurlyBracketStart));
    }

    @Test
    public void CurlyBracketEndTest() {
        CommonToken token = getToken("}");
        assertThat(token.getType(), is(PCRELexer.CurlyBracketEnd));
    }

    @Test
    public void EqualsTest() {
        CommonToken token = getToken("=");
        assertThat(token.getType(), is(PCRELexer.Equals));
    }

    @Test
    public void LessThanTest() {
        CommonToken token = getToken("<");
        assertThat(token.getType(), is(PCRELexer.LessThan));
    }

    @Test
    public void GreaterThanTest() {
        CommonToken token = getToken(">");
        assertThat(token.getType(), is(PCRELexer.GreaterThan));
    }

    @Test
    public void ExclamationMarkTest() {
        CommonToken token = getToken("!");
        assertThat(token.getType(), is(PCRELexer.ExclamationMark));
    }

    @Test
    public void CommaTest() {
        CommonToken token = getToken(",");
        assertThat(token.getType(), is(PCRELexer.Comma));
    }

    @Test
    public void PlusTest() {
        CommonToken token = getToken("+");
        assertThat(token.getType(), is(PCRELexer.Plus));
    }

    @Test
    public void StarTest() {
        CommonToken token = getToken("*");
        assertThat(token.getType(), is(PCRELexer.Star));
    }

    @Test
    public void QuestionMarkTest() {
        CommonToken token = getToken("?");
        assertThat(token.getType(), is(PCRELexer.QuestionMark));
    }

    @Test
    public void DotTest() {
        CommonToken token = getToken(".");
        assertThat(token.getType(), is(PCRELexer.Dot));
    }

    @Test
    public void DigitTest() {
        List<CommonToken> tokens = getTokens("0123456789");

        assertThat(tokens.size(), is(10));

        for(CommonToken t : tokens) {
            assertThat(t.getType(), is(PCRELexer.Digit));
        }
    }

    @Test
    public void OtherCharTest() {
        // 11 chars + 26 lower case + 26 upper case
        List<CommonToken> tokens = getTokens("~`@#%&_'\";/abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

        assertThat(tokens.size(), is(11 + 26 + 26));

        for(CommonToken t : tokens) {
            assertThat(t.getType(), is(PCRELexer.OtherChar));
        }
    }
}
