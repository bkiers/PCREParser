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

public class PCRE {

    private final PCREParser parser;

    public PCRE(String regex) {
        try {
            PCRELexer lexer = new PCRELexer(new ANTLRStringStream(regex));
            parser = new PCREParser(new CommonTokenStream(lexer));

            ParserRuleReturnScope capture0 = parser.parse();
            parser.captureReturns.put(0, capture0);
        }
        catch (RecognitionException e) {
            throw new RuntimeException(e);
        }
    }

    public String toStringASCII() {
        return toStringASCII(0);
    }

    public String toStringASCII(String name) {

        StringBuilder builder = new StringBuilder();
        walk(getCommonTree(name), builder);

        return builder.toString();
    }

    public String toStringASCII(int group) {

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

    public String toStringDOT() {
        return toStringDOT(0);
    }

    public String toStringDOT(int group) {
        DOTTreeGenerator gen = new DOTTreeGenerator();
        StringTemplate st = gen.toDOT(getCommonTree(group));
        return st.toString();
    }

    public String toStringDOT(String name) {
        DOTTreeGenerator gen = new DOTTreeGenerator();
        StringTemplate st = gen.toDOT(getCommonTree(name));
        return st.toString();
    }

    public int getGroupCount() {
        return parser.captureReturns.size() - 1;
    }

    public String toStringLisp() {
        return toStringLisp(0);
    }

    public String toStringLisp(String name) {
        return getCommonTree(name).toStringTree();
    }

    public String toStringLisp(int group) {
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

                String tokenName = PCREParser.tokenNames[tree.getType()];
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

    public static void main(String[] args) {

        if(args.length != 1) {
            System.err.println("usage: java -jar PCRE.jar 'regex-pattern'");
            System.exit(42);
        }

        PCRE pcre = new PCRE(args[0]);
        System.out.println(pcre.toStringASCII());
    }
}
