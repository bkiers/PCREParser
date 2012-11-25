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
    public void QuotedTest() throws Exception {
        // TODO
    }

    @Test
    public void BlockQuotedTest() throws Exception {
        // TODO
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
        // TODO
    }

    @Test
    public void OpenParenTest() throws Exception {
        // TODO
    }

    @Test
    public void CloseParenTest() throws Exception {
        // TODO
    }

    @Test
    public void LessThanTest() throws Exception {
        // TODO
    }

    @Test
    public void GreaterThanTest() throws Exception {
        // TODO
    }

    @Test
    public void SingleQuoteTest() throws Exception {
        // TODO
    }

    @Test
    public void ColonTest() throws Exception {
        // TODO
    }

    @Test
    public void HashTest() throws Exception {
        // TODO
    }

    @Test
    public void EqualsTest() throws Exception {
        // TODO
    }

    @Test
    public void ExclamationTest() throws Exception {
        // TODO
    }

    @Test
    public void AmpersandTest() throws Exception {
        // TODO
    }

    @Test
    public void ALCTest() throws Exception {
        // TODO
    }

    @Test
    public void BLCTest() throws Exception {
        // TODO
    }

    @Test
    public void CLCTest() throws Exception {
        // TODO
    }

    @Test
    public void DLCTest() throws Exception {
        // TODO
    }

    @Test
    public void ELCTest() throws Exception {
        // TODO
    }

    @Test
    public void FLCTest() throws Exception {
        // TODO
    }

    @Test
    public void GLCTest() throws Exception {
        // TODO
    }

    @Test
    public void HLCTest() throws Exception {
        // TODO
    }

    @Test
    public void ILCTest() throws Exception {
        // TODO
    }

    @Test
    public void JLCTest() throws Exception {
        // TODO
    }

    @Test
    public void KLCTest() throws Exception {
        // TODO
    }

    @Test
    public void LLCTest() throws Exception {
        // TODO
    }

    @Test
    public void MLCTest() throws Exception {
        // TODO
    }

    @Test
    public void NLCTest() throws Exception {
        // TODO
    }

    @Test
    public void OLCTest() throws Exception {
        // TODO
    }

    @Test
    public void PLCTest() throws Exception {
        // TODO
    }

    @Test
    public void QLCTest() throws Exception {
        // TODO
    }

    @Test
    public void RLCTest() throws Exception {
        // TODO
    }

    @Test
    public void SLCTest() throws Exception {
        // TODO
    }

    @Test
    public void TLCTest() throws Exception {
        // TODO
    }

    @Test
    public void ULCTest() throws Exception {
        // TODO
    }

    @Test
    public void VLCTest() throws Exception {
        // TODO
    }

    @Test
    public void WLCTest() throws Exception {
        // TODO
    }

    @Test
    public void XLCTest() throws Exception {
        // TODO
    }

    @Test
    public void YLCTest() throws Exception {
        // TODO
    }

    @Test
    public void ZLCTest() throws Exception {
        // TODO
    }

    @Test
    public void AUCTest() throws Exception {
        // TODO
    }

    @Test
    public void BUCTest() throws Exception {
        // TODO
    }

    @Test
    public void CUCTest() throws Exception {
        // TODO
    }

    @Test
    public void DUCTest() throws Exception {
        // TODO
    }

    @Test
    public void EUCTest() throws Exception {
        // TODO
    }

    @Test
    public void FUCTest() throws Exception {
        // TODO
    }

    @Test
    public void GUCTest() throws Exception {
        // TODO
    }

    @Test
    public void HUCTest() throws Exception {
        // TODO
    }

    @Test
    public void IUCTest() throws Exception {
        // TODO
    }

    @Test
    public void JUCTest() throws Exception {
        // TODO
    }

    @Test
    public void KUCTest() throws Exception {
        // TODO
    }

    @Test
    public void LUCTest() throws Exception {
        // TODO
    }

    @Test
    public void MUCTest() throws Exception {
        // TODO
    }

    @Test
    public void NUCTest() throws Exception {
        // TODO
    }

    @Test
    public void OUCTest() throws Exception {
        // TODO
    }

    @Test
    public void PUCTest() throws Exception {
        // TODO
    }

    @Test
    public void QUCTest() throws Exception {
        // TODO
    }

    @Test
    public void RUCTest() throws Exception {
        // TODO
    }

    @Test
    public void SUCTest() throws Exception {
        // TODO
    }

    @Test
    public void TUCTest() throws Exception {
        // TODO
    }

    @Test
    public void UUCTest() throws Exception {
        // TODO
    }

    @Test
    public void VUCTest() throws Exception {
        // TODO
    }

    @Test
    public void WUCTest() throws Exception {
        // TODO
    }

    @Test
    public void XUCTest() throws Exception {
        // TODO
    }

    @Test
    public void YUCTest() throws Exception {
        // TODO
    }

    @Test
    public void ZUCTest() throws Exception {
        // TODO
    }

    @Test
    public void D1Test() throws Exception {
        // TODO
    }

    @Test
    public void D2Test() throws Exception {
        // TODO
    }

    @Test
    public void D3Test() throws Exception {
        // TODO
    }

    @Test
    public void D4Test() throws Exception {
        // TODO
    }

    @Test
    public void D5Test() throws Exception {
        // TODO
    }

    @Test
    public void D6Test() throws Exception {
        // TODO
    }

    @Test
    public void D7Test() throws Exception {
        // TODO
    }

    @Test
    public void D8Test() throws Exception {
        // TODO
    }

    @Test
    public void D9Test() throws Exception {
        // TODO
    }

    @Test
    public void D0Test() throws Exception {
        // TODO
    }

    @Test
    public void OtherCharTest() throws Exception {
        // TODO
    }
}
