package pcreparser;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

public class Main {

    private static void walk(CommonTree tree, String[] tokenNames, int indent) {

        if(tree == null) {
            return;
        }

        for(int i = 0; i < indent; i++) {
            System.out.print("  ");
        }

        String tokenName = tokenNames[tree.getType()];
        String tokenText = tree.getText();

        System.out.println(tokenName + (!tokenName.equals(tokenText) ? "='" + tree.getText() + "'" : ""));

        for(int i = 0; i < tree.getChildCount(); i++) {
            walk((CommonTree)tree.getChild(i), tokenNames, indent + 1);
        }
    }

    public static void main(String[] args) throws Exception {
        String test = "(?-mx)(?s)(?m-sx)";

        PCRELexer lexer = new PCRELexer(new ANTLRStringStream(test));
        PCREParser parser = new PCREParser(new CommonTokenStream(lexer));

        CommonTree ast = parser.parse().tree;

        walk(ast, parser.getTokenNames(), 0);

        PCREWalker walker = new PCREWalker(new CommonTreeNodeStream(ast));

        walker.walk();
    }
}
