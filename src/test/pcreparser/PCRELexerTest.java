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

        String[][] tests = {
                {"\\.", "."},
                {"\\\\", "\\"},
                {"\\]", "]"},
                {"\\?", "?"},
                {"\\=", "="}
        };

        for(String[] test : tests) {
            CommonToken token = getToken(test[0]);
            assertThat(token.getType(), is(PCRELexer.Quoted));
            assertThat(token.getText(), is(test[1]));
        }
    }

    @Test
    public void BlockQuotedTest() throws Exception {

        String[][] tests = {
                {"\\Q\\E", ""},
                {"\\Q \\ \\E", " \\ "},
                {"\\Q.\\Q\\Q.\\E", ".\\Q\\Q."},
                {"\\Q\n\n\n\\n\\E", "\n\n\n\\n"}
        };

        for(String[] test : tests) {
            CommonToken token = getToken(test[0]);
            assertThat(token.getType(), is(PCRELexer.BlockQuoted));
            assertThat(token.getText(), is(test[1]));
        }
    }

    @Test
    public void BellCharTest() throws Exception {
        // TODO
    }

    @Test
    public void ControlCharTest() throws Exception {
        // TODO
    }

    @Test
    public void EscapeCharTest() throws Exception {
        // TODO
    }

    @Test
    public void FormFeedTest() throws Exception {
        // TODO
    }

    @Test
    public void NewLineTest() throws Exception {
        // TODO
    }

    @Test
    public void CarriageReturnTest() throws Exception {
        // TODO
    }

    @Test
    public void TabTest() throws Exception {
        // TODO
    }

    @Test
    public void EscapedDigitTest() throws Exception {
        // TODO
    }

    @Test
    public void HexCharTest() throws Exception {
        // TODO
    }

    @Test
    public void AnyTest() throws Exception {
        // TODO
    }

    @Test
    public void OneDataUnitTest() throws Exception {
        // TODO
    }

    @Test
    public void DecimalDigitTest() throws Exception {
        // TODO
    }

    @Test
    public void NotDecimalDigitTest() throws Exception {
        // TODO
    }

    @Test
    public void HorizontalWhiteSpaceTest() throws Exception {
        // TODO
    }

    @Test
    public void NotHorizontalWhiteSpaceTest() throws Exception {
        // TODO
    }

    @Test
    public void NotNewLineTest() throws Exception {
        // TODO
    }

    @Test
    public void CharWithPropertyTest() throws Exception {
        // TODO
    }

    @Test
    public void CharWithoutPropertyTest() throws Exception {
        // TODO
    }

    @Test
    public void NewLineSequenceTest() throws Exception {
        // TODO
    }

    @Test
    public void WhiteSpaceTest() throws Exception {
        // TODO
    }

    @Test
    public void NotWhiteSpaceTest() throws Exception {
        // TODO
    }

    @Test
    public void VerticalWhiteSpaceTest() throws Exception {
        // TODO
    }

    @Test
    public void NotVerticalWhiteSpaceTest() throws Exception {
        // TODO
    }

    @Test
    public void WordCharTest() throws Exception {
        // TODO
    }

    @Test
    public void NotWordCharTest() throws Exception {
        // TODO
    }

    @Test
    public void ExtendedUnicodeCharTest() throws Exception {
        // TODO
    }

    @Test
    public void CharacterClassStartTest() throws Exception {
        // TODO
    }

    @Test
    public void CharacterClassEndTest() throws Exception {
        // TODO
    }

    @Test
    public void CaretTest() throws Exception {
        // TODO
    }

    @Test
    public void HyphenTest() throws Exception {
        // TODO
    }

    @Test
    public void POSIXNamedSetTest() throws Exception {
        // TODO
    }

    @Test
    public void POSIXNegatedNamedSetTest() throws Exception {
        // TODO
    }

    @Test
    public void QuestionMarkTest() throws Exception {
        // TODO
    }

    @Test
    public void PlusTest() throws Exception {
        // TODO
    }

    @Test
    public void StarTest() throws Exception {
        // TODO
    }

    @Test
    public void OpenBraceTest() throws Exception {
        // TODO
    }

    @Test
    public void CloseBraceTest() throws Exception {
        // TODO
    }

    @Test
    public void CommaTest() throws Exception {
        // TODO
    }

    @Test
    public void WordBoundaryTest() throws Exception {
        // TODO
    }

    @Test
    public void NonWordBoundaryTest() throws Exception {
        // TODO
    }

    @Test
    public void StartOfSubjectTest() throws Exception {
        // TODO
    }

    @Test
    public void EndOfSubjectOrLineTest() throws Exception {
        // TODO
    }

    @Test
    public void EndOfSubjectOrLineEndOfSubjectTest() throws Exception {
        // TODO
    }

    @Test
    public void EndOfSubjectTest() throws Exception {
        // TODO
    }

    @Test
    public void PreviousMatchInSubjectTest() throws Exception {
        // TODO
    }

    @Test
    public void ResetStartMatchTest() throws Exception {
        // TODO
    }

    @Test
    public void SubroutineOrNamedReferenceStartGTest() throws Exception {
        // TODO
    }

    @Test
    public void NamedReferenceStartKTest() throws Exception {
        // TODO
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
