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
