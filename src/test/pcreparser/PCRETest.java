package pcreparser;

import org.antlr.runtime.tree.CommonTree;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PCRETest {

    @Test
    public void getCommonTreeTest() {

        // group[0]: ((x)(?<A>aaa)(?'B'bbb)(?P<C>ccc(?<D>ddd)))
        // group[1]: ((x)(?<A>aaa)(?'B'bbb)(?P<C>ccc(?<D>ddd)))
        // group[2]: (x)
        // group[A]: (?<A>aaa)
        // group[B]: (?'B'bbb)
        // group[C]: (?P<C>ccc(?<D>ddd))
        // group[D]: (?<D>ddd)
        PCRE pcre = new PCRE("((x)(?<A>aaa)(?'B'bbb)(?P<C>ccc(?<D>ddd)))");

        CommonTree group0 = pcre.getCommonTree();

        CommonTree group1 = (CommonTree)group0.getChild(0).getChild(0);
        CommonTree group2 = (CommonTree)group0.getChild(0).getChild(0).getChild(0).getChild(0).getChild(0);
        CommonTree groupA = (CommonTree)group0.getChild(0).getChild(0).getChild(0).getChild(1).getChild(0);
        CommonTree groupB = (CommonTree)group0.getChild(0).getChild(0).getChild(0).getChild(2).getChild(0);
        CommonTree groupC = (CommonTree)group0.getChild(0).getChild(0).getChild(0).getChild(3).getChild(0);
        CommonTree groupD = (CommonTree)groupC.getChild(1).getChild(3).getChild(0);

        assertThat(group1.getType(), is(PCRELexer.CAPTURING_GROUP));
        assertThat(group2.getType(), is(PCRELexer.CAPTURING_GROUP));
        assertThat(groupA.getType(), is(PCRELexer.NAMED_CAPTURING_GROUP_PERL));
        assertThat(groupB.getType(), is(PCRELexer.NAMED_CAPTURING_GROUP_PERL));
        assertThat(groupC.getType(), is(PCRELexer.NAMED_CAPTURING_GROUP_PYTHON));
        assertThat(groupD.getType(), is(PCRELexer.NAMED_CAPTURING_GROUP_PERL));

        assertThat(groupA.getChildCount(), is(2));
        assertThat(groupB.getChildCount(), is(2));
        assertThat(groupC.getChildCount(), is(2));
        assertThat(groupD.getChildCount(), is(2));

        assertThat(groupA.getChild(0).getText(), is("A"));
        assertThat(groupB.getChild(0).getText(), is("B"));
        assertThat(groupC.getChild(0).getText(), is("C"));
        assertThat(groupD.getChild(0).getText(), is("D"));
    }

    @Test
    public void getCommonTreeTestString() {

        // group[0]: ((x)(?<A>aaa)(?'B'bbb)(?P<C>ccc(?<D>ddd)))
        // group[1]: ((x)(?<A>aaa)(?'B'bbb)(?P<C>ccc(?<D>ddd)))
        // group[2]: (x)
        // group[A]: (?<A>aaa)
        // group[B]: (?'B'bbb)
        // group[C]: (?P<C>ccc(?<D>ddd))
        // group[D]: (?<D>ddd)
        PCRE pcre = new PCRE("((x)(?<A>aaa)(?'B'bbb)(?P<C>ccc(?<D>ddd)))");

        CommonTree groupC = pcre.getCommonTree("C");
        CommonTree groupD = (CommonTree)groupC.getChild(1).getChild(3).getChild(0);

        assertThat(groupC.getType(), is(PCRELexer.NAMED_CAPTURING_GROUP_PYTHON));
        assertThat(groupD.getType(), is(PCRELexer.NAMED_CAPTURING_GROUP_PERL));

        assertThat(groupC.getChildCount(), is(2));
        assertThat(groupD.getChildCount(), is(2));

        assertThat(groupC.getChild(0).getText(), is("C"));
        assertThat(groupD.getChild(0).getText(), is("D"));
    }

    @Test
    public void getCommonTreeTestInt() {

        // group[1]: (a)
        // group[2]: (b(c(d)c)b)
        // group[3]: (c(d)c)
        // group[4]: (d)
        PCRE pcre = new PCRE("(a)(b(c(d)c)b)");

        CommonTree group1 = pcre.getCommonTree(1);
        assertThat(group1.getChild(0).getChild(0).getChild(0).getText(), is("a"));

        CommonTree group2 = pcre.getCommonTree(2);
        assertThat(group2.getChild(0).getChild(0).getChild(0).getText(), is("b"));

        CommonTree group3 = pcre.getCommonTree(3);
        assertThat(group3.getChild(0).getChild(0).getChild(0).getText(), is("c"));

        CommonTree group4 = pcre.getCommonTree(4);
        assertThat(group4.getChild(0).getChild(0).getChild(0).getText(), is("d"));
    }

    @Test
    public void getGroupCountTest() {

        PCRE pcre = new PCRE("[a-z]\\d+(?!bar)");
        assertThat(pcre.getNamedGroupCount(), is(0));

        pcre = new PCRE("((x)(?<A>aaa)(?'B'bbb)(?P<C>ccc(?<D>ddd)))");
        assertThat(pcre.getGroupCount(), is(6));
    }

    @Test
    public void getNamedGroupCountTest() {

        PCRE pcre = new PCRE("([a-z]\\d)+(?!bar)");
        assertThat(pcre.getNamedGroupCount(), is(0));

        pcre = new PCRE("(?<A>aaa)(?'B'bbb)(?P<C>ccc(?<D>ddd))");
        assertThat(pcre.getNamedGroupCount(), is(4));
    }
}
