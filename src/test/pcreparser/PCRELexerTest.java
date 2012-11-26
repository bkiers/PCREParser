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

        assertThat("The input contains more than token: " + source, tokens.size(), is(1));

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
    public void QuotedTest() throws Exception {

        String[] tests = {
                "\\.",
                "\\\\",
                "\\]",
                "\\?",
                "\\="
        };

        for(String test : tests) {
            CommonToken token = getToken(test);
            assertThat(token.getType(), is(PCRELexer.Quoted));
            assertThat(token.getText(), is(test.substring(1)));
        }
    }

    @Test
    public void BlockQuotedTest() throws Exception {

        String[] tests = {
                "\\Q\\E",
                "\\Q \\ \\E",
                "\\Q.\\Q\\Q.\\E",
                "\\Q\n\n\n\\n\\E"
        };

        for(String test : tests) {
            CommonToken token = getToken(test);
            assertThat(token.getType(), is(PCRELexer.BlockQuoted));
            assertThat(token.getText(), is(test.substring(2, test.length() - 2)));
        }
    }

    @Test
    public void BellCharTest() throws Exception {

        String input = "\\a";
        String expectedOutput = "\u0007";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.BellChar));
        assertThat(token.getText(), is(expectedOutput));
    }

    @Test
    public void ControlCharTest() throws Exception {

        String input = "\\cC";
        String expectedOutput = "C";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.ControlChar));
        assertThat(token.getText(), is(expectedOutput));

        input = "\\cX";
        expectedOutput = "X";
        token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.ControlChar));
        assertThat(token.getText(), is(expectedOutput));
    }

    @Test
    public void EscapeCharTest() throws Exception {

        String input = "\\e";
        String expectedOutput = String.valueOf((char)0x1B);
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.EscapeChar));
        assertThat(token.getText(), is(expectedOutput));
    }

    @Test
    public void FormFeedTest() throws Exception {

        String input = "\\f";
        String expectedOutput = String.valueOf((char)0x0C);
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.FormFeed));
        assertThat(token.getText(), is(expectedOutput));
    }

    @Test
    public void NewLineTest() throws Exception {

        String input = "\\n";
        String expectedOutput = "\n";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.NewLine));
        assertThat(token.getText(), is(expectedOutput));
    }

    @Test
    public void CarriageReturnTest() throws Exception {

        String input = "\\r";
        String expectedOutput = "\r";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.CarriageReturn));
        assertThat(token.getText(), is(expectedOutput));
    }

    @Test
    public void TabTest() throws Exception {

        String input = "\\t";
        String expectedOutput = "\t";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Tab));
        assertThat(token.getText(), is(expectedOutput));
    }

    @Test
    public void EscapedDigitTest() throws Exception {

        List<CommonToken> tokens = getTokens("\\0\\1\\2\\3\\4\\5\\6\\7\\8\\9");
        final String expected = "0123456789";

        assertThat(tokens.size(), is(expected.length()));

        int index = 0;

        for(CommonToken token : tokens) {
            assertThat(token.getType(), is(PCRELexer.EscapedDigit));
            assertThat(token.getText(), is(String.valueOf(expected.charAt(index))));
            index++;
        }
    }

    @Test
    public void HexCharTest() throws Exception {

        String[][] tests = {
                {"\\xFF", String.valueOf((char)0xff)},
                {"\\xfF", String.valueOf((char)0xff)},
                {"\\x{03ab}", "\u03AB"},
                {"\\x{efFF}", "\uEFFF"}
        };

        for(String[] test : tests) {
            CommonToken token = getToken(test[0]);
            assertThat(token.getType(), is(PCRELexer.HexChar));
            assertThat(token.getText(), is(test[1]));
        }
    }

    @Test
    public void DotTest() throws Exception {

        String input = ".";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Dot));
    }

    @Test
    public void OneDataUnitTest() throws Exception {

        String input = "\\C";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.OneDataUnit));
    }

    @Test
    public void DecimalDigitTest() throws Exception {

        String input = "\\d";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.DecimalDigit));
    }

    @Test
    public void NotDecimalDigitTest() throws Exception {

        String input = "\\D";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.NotDecimalDigit));
    }

    @Test
    public void HorizontalWhiteSpaceTest() throws Exception {

        String input = "\\h";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.HorizontalWhiteSpace));
    }

    @Test
    public void NotHorizontalWhiteSpaceTest() throws Exception {

        String input = "\\H";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.NotHorizontalWhiteSpace));
    }

    @Test
    public void NotNewLineTest() throws Exception {

        String input = "\\N";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.NotNewLine));
    }

    @Test
    public void CharWithPropertyTest() throws Exception {

        for(String property : PCRELexer.propertySet) {
            CommonToken token = getToken(String.format("\\p{%s}", property));
            assertThat(token.getType(), is(PCRELexer.CharWithProperty));
        }
    }

    @Test
    public void CharWithoutPropertyTest() throws Exception {

        for(String property : PCRELexer.propertySet) {
            CommonToken token = getToken(String.format("\\P{%s}", property));
            assertThat(token.getType(), is(PCRELexer.CharWithoutProperty));
        }
    }

    @Test
    public void NewLineSequenceTest() throws Exception {

        String input = "\\R";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.NewLineSequence));
    }

    @Test
    public void WhiteSpaceTest() throws Exception {

        String input = "\\s";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.WhiteSpace));
    }

    @Test
    public void NotWhiteSpaceTest() throws Exception {

        String input = "\\S";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.NotWhiteSpace));
    }

    @Test
    public void VerticalWhiteSpaceTest() throws Exception {

        String input = "\\v";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.VerticalWhiteSpace));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void NotVerticalWhiteSpaceTest() throws Exception {

        String input = "\\V";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.NotVerticalWhiteSpace));
    }

    @Test
    public void WordCharTest() throws Exception {

        String input = "\\w";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.WordChar));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void NotWordCharTest() throws Exception {

        String input = "\\W";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.NotWordChar));
    }

    @Test
    public void ExtendedUnicodeCharTest() throws Exception {

        String input = "\\X";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.ExtendedUnicodeChar));
    }

    @Test
    public void CharacterClassStartTest() throws Exception {

        String input = "[";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.CharacterClassStart));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void CharacterClassEndTest() throws Exception {

        String input = "]";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.CharacterClassEnd));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void CaretTest() throws Exception {

        String input = "^";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Caret));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void HyphenTest() throws Exception {

        String input = "-";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Hyphen));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void POSIXNamedSetTest() throws Exception {

        for(String name : PCRELexer.namedSet) {
            CommonToken token = getToken(String.format("[[:%s:]]", name));
            assertThat(token.getType(), is(PCRELexer.POSIXNamedSet));
        }
    }

    @Test
    public void POSIXNegatedNamedSetTest() throws Exception {

        for(String name : PCRELexer.namedSet) {
            CommonToken token = getToken(String.format("[[:^%s:]]", name));
            assertThat(token.getType(), is(PCRELexer.POSIXNegatedNamedSet));
        }
    }

    @Test
    public void QuestionMarkTest() throws Exception {

        String input = "?";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.QuestionMark));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void PlusTest() throws Exception {

        String input = "+";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Plus));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void StarTest() throws Exception {

        String input = "*";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Star));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void OpenBraceTest() throws Exception {

        String input = "{";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.OpenBrace));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void CloseBraceTest() throws Exception {

        String input = "}";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.CloseBrace));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void CommaTest() throws Exception {

        String input = ",";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Comma));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void WordBoundaryTest() throws Exception {

        String input = "\\b";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.WordBoundary));
    }

    @Test
    public void NonWordBoundaryTest() throws Exception {

        String input = "\\B";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.NonWordBoundary));
    }

    @Test
    public void StartOfSubjectTest() throws Exception {

        String input = "\\A";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.StartOfSubject));
    }

    @Test
    public void EndOfSubjectOrLineTest() throws Exception {

        String input = "$";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.EndOfSubjectOrLine));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void EndOfSubjectOrLineEndOfSubjectTest() throws Exception {

        String input = "\\Z";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.EndOfSubjectOrLineEndOfSubject));
    }

    @Test
    public void EndOfSubjectTest() throws Exception {

        String input = "\\z";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.EndOfSubject));
    }

    @Test
    public void PreviousMatchInSubjectTest() throws Exception {

        String input = "\\G";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.PreviousMatchInSubject));
    }

    @Test
    public void ResetStartMatchTest() throws Exception {

        String input = "\\K";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.ResetStartMatch));
    }

    @Test
    public void SubroutineOrNamedReferenceStartGTest() throws Exception {

        String input = "\\g";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.SubroutineOrNamedReferenceStartG));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void NamedReferenceStartKTest() throws Exception {

        String input = "\\k";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.NamedReferenceStartK));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void PipeTest() throws Exception {

        String input = "|";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Pipe));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void OpenParenTest() throws Exception {

        String input = "(";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.OpenParen));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void CloseParenTest() throws Exception {

        String input = ")";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.CloseParen));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void LessThanTest() throws Exception {

        String input = "<";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.LessThan));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void GreaterThanTest() throws Exception {

        String input = ">";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.GreaterThan));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void SingleQuoteTest() throws Exception {

        String input = "'";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.SingleQuote));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void UnderscoreTest() throws Exception {

        String input = "_";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Underscore));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void ColonTest() throws Exception {

        String input = ":";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Colon));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void HashTest() throws Exception {

        String input = "#";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Hash));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void EqualsTest() throws Exception {

        String input = "=";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Equals));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void ExclamationTest() throws Exception {

        String input = "!";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Exclamation));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void AmpersandTest() throws Exception {

        String input = "&";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.Ampersand));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void ALCTest() throws Exception {
        String input = "a";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.ALC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void BLCTest() throws Exception {
        String input = "b";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.BLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void CLCTest() throws Exception {
        String input = "c";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.CLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void DLCTest() throws Exception {
        String input = "d";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.DLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void ELCTest() throws Exception {
        String input = "e";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.ELC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void FLCTest() throws Exception {
        String input = "f";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.FLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void GLCTest() throws Exception {
        String input = "g";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.GLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void HLCTest() throws Exception {
        String input = "h";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.HLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void ILCTest() throws Exception {
        String input = "i";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.ILC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void JLCTest() throws Exception {
        String input = "j";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.JLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void KLCTest() throws Exception {
        String input = "k";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.KLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void LLCTest() throws Exception {
        String input = "l";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.LLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void MLCTest() throws Exception {
        String input = "m";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.MLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void NLCTest() throws Exception {
        String input = "n";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.NLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void OLCTest() throws Exception {
        String input = "o";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.OLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void PLCTest() throws Exception {
        String input = "p";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.PLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void QLCTest() throws Exception {
        String input = "q";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.QLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void RLCTest() throws Exception {
        String input = "r";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.RLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void SLCTest() throws Exception {
        String input = "s";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.SLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void TLCTest() throws Exception {
        String input = "t";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.TLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void ULCTest() throws Exception {
        String input = "u";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.ULC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void VLCTest() throws Exception {
        String input = "v";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.VLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void WLCTest() throws Exception {
        String input = "w";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.WLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void XLCTest() throws Exception {
        String input = "x";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.XLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void YLCTest() throws Exception {
        String input = "y";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.YLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void ZLCTest() throws Exception {
        String input = "z";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.ZLC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void AUCTest() throws Exception {
        String input = "A";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.AUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void BUCTest() throws Exception {
        String input = "B";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.BUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void CUCTest() throws Exception {
        String input = "C";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.CUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void DUCTest() throws Exception {
        String input = "D";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.DUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void EUCTest() throws Exception {
        String input = "E";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.EUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void FUCTest() throws Exception {
        String input = "F";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.FUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void GUCTest() throws Exception {
        String input = "G";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.GUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void HUCTest() throws Exception {
        String input = "H";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.HUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void IUCTest() throws Exception {
        String input = "I";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.IUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void JUCTest() throws Exception {
        String input = "J";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.JUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void KUCTest() throws Exception {
        String input = "K";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.KUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void LUCTest() throws Exception {
        String input = "L";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.LUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void MUCTest() throws Exception {
        String input = "M";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.MUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void NUCTest() throws Exception {
        String input = "N";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.NUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void OUCTest() throws Exception {
        String input = "O";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.OUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void PUCTest() throws Exception {
        String input = "P";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.PUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void QUCTest() throws Exception {
        String input = "Q";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.QUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void RUCTest() throws Exception {
        String input = "R";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.RUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void SUCTest() throws Exception {
        String input = "S";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.SUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void TUCTest() throws Exception {
        String input = "T";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.TUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void UUCTest() throws Exception {
        String input = "U";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.UUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void VUCTest() throws Exception {
        String input = "V";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.VUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void WUCTest() throws Exception {
        String input = "W";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.WUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void XUCTest() throws Exception {
        String input = "X";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.XUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void YUCTest() throws Exception {
        String input = "Y";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.YUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void ZUCTest() throws Exception {
        String input = "Z";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.ZUC));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void D1Test() throws Exception {
        String input = "1";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.D1));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void D2Test() throws Exception {
        String input = "2";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.D2));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void D3Test() throws Exception {
        String input = "3";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.D3));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void D4Test() throws Exception {
        String input = "4";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.D4));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void D5Test() throws Exception {
        String input = "5";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.D5));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void D6Test() throws Exception {
        String input = "6";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.D6));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void D7Test() throws Exception {
        String input = "7";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.D7));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void D8Test() throws Exception {
        String input = "8";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.D8));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void D9Test() throws Exception {
        String input = "9";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.D9));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void D0Test() throws Exception {
        String input = "0";
        CommonToken token = getToken(input);
        assertThat(token.getType(), is(PCRELexer.D0));
        assertThat(token.getText(), is(input));
    }

    @Test
    public void OtherCharTest() throws Exception {
        List<CommonToken> tokens = getTokens("~ ` @ % \" ; / \u01FF \uABCD");

        for(CommonToken token : tokens) {
            assertThat(token.getType(), is(PCRELexer.OtherChar));
        }
    }
}
