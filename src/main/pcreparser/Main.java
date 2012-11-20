/*
 * Copyright (c) 2012 by Bart Kiers
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * Project      : PCRE Parser, an ANTLR grammar for PCRE
 * Developed by : Bart Kiers, bart@big-o.nl
 */
package pcreparser;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    @SuppressWarnings("unchecked")
    public static void walk(CommonTree tree, String[] tokenNames, StringBuilder builder) {

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
        try {
            String regex = "(a)(a)(a)(a)(a)(a)(a)(a)(a)(a)\\1";

            System.out.println("aaaaaaaaaaa23".matches(regex));

            PCRELexer lexer = new PCRELexer(new ANTLRStringStream(regex));
            PCREParser parser = new PCREParser(new CommonTokenStream(lexer));

            CommonTree ast = parser.parse().tree;

            StringBuilder builder = new StringBuilder();
            walk(ast, parser.getTokenNames(), builder);

            System.out.println(ast.toStringTree() + "\n\n" + builder);

            PCREWalker walker = new PCREWalker(new CommonTreeNodeStream(ast));
            walker.walk();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
