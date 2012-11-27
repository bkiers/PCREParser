package pcreparser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.DOTTreeGenerator;
import org.antlr.stringtemplate.StringTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PCRE {

    private final CommonTree root;
    private final String[] tokenNames;
    private final PCREParser parser;

    public PCRE(String regex) throws RecognitionException {

        PCRELexer lexer = new PCRELexer(new ANTLRStringStream(regex));
        parser = new PCREParser(new CommonTokenStream(lexer));

        tokenNames = parser.getTokenNames();
        ParserRuleReturnScope capture0 = parser.parse();
        root = (CommonTree)capture0.getTree();

        parser.captureReturns.put(0, capture0);
    }

    public String getASCIITree() {
        return getASCIITree(0);
    }

    public String getASCIITree(String name) {

        StringBuilder builder = new StringBuilder();
        walk(getCommonTree(name), builder);

        return builder.toString();
    }

    public String getASCIITree(int group) {

        StringBuilder builder = new StringBuilder();
        walk(getCommonTree(group), builder);

        return builder.toString();
    }

    public CommonTree getCommonTree() {
        return getCommonTree(0);
    }

    public CommonTree getCommonTree(String name) {

        ParserRuleReturnScope retval = parser.namedReturns.get(name);

        if(retval == null) {
            throw new RuntimeException("no such named group: " + name);
        }

        return (CommonTree)retval.getTree();
    }

    public CommonTree getCommonTree(int group) {

        ParserRuleReturnScope retval = parser.captureReturns.get(group);

        if(retval == null) {
            throw new RuntimeException("no such capture group: " + group);
        }

        return (CommonTree)retval.getTree();
    }

    public String getDOTTree() {
        return getDOTTree(0);
    }

    public String getDOTTree(int group) {
        DOTTreeGenerator gen = new DOTTreeGenerator();
        StringTemplate st = gen.toDOT(getCommonTree(group));
        return st.toString();
    }

    public String getDOTTree(String name) {
        DOTTreeGenerator gen = new DOTTreeGenerator();
        StringTemplate st = gen.toDOT(getCommonTree(name));
        return st.toString();
    }

    public int getGroupCount() {
        return parser.captureReturns.size() - 1;
    }

    public String getLispTree() {
        return getLispTree(0);
    }

    public String getLispTree(String name) {
        return getCommonTree(name).toStringTree();
    }

    public String getLispTree(int group) {
        return getCommonTree(group).toStringTree();
    }

    public int getNamedGroupCount() {
        return parser.namedReturns.size();
    }

    @SuppressWarnings("unchecked")
    private void walk(CommonTree tree, StringBuilder builder) {

        List<CommonTree> firstStack = new ArrayList<CommonTree>();
        firstStack.add(tree);

        List<List<CommonTree>> childListStack = new ArrayList<List<CommonTree>>();
        childListStack.add(firstStack);

        while (!childListStack.isEmpty()) {

            List<CommonTree> childStack = childListStack.get(childListStack.size() - 1);

            if (childStack.isEmpty()) {
                childListStack.remove(childListStack.size() - 1);
            }
            else {
                tree = childStack.remove(0);

                String indent = "";

                for (int i = 0; i < childListStack.size() - 1; i++) {
                    indent += (childListStack.get(i).size() > 0) ? "|  " : "   ";
                }

                String tokenName = tokenNames[tree.getType()];
                String tokenText = tree.getText();

                builder.append(indent)
                        .append(childStack.isEmpty() ? "'- " : "|- ")
                        .append(tokenName)
                        .append(!tokenName.equals(tokenText) ? "='" + tree.getText() + "'" : "")
                        .append("\n");

                if (tree.getChildCount() > 0) {
                    childListStack.add(new ArrayList<CommonTree>((List<CommonTree>)tree.getChildren()));
                }
            }
        }
    }

        public static void main(String[] args) throws Exception {
            PCRE pcre = new PCRE("(a)(a)(a)(a)(a)(a)(a)(a)(a)(a)(a)\\11");
            System.out.println(pcre.getASCIITree());
            //System.out.println(pcre.getDOTTree(1));
            //System.out.println(pcre.getLispTree(1));
        }
}
