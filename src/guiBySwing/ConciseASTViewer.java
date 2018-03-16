package guiBySwing;

import javax.swing.*;

public class ConciseASTViewer extends SimpleASTViewer {
    public ConciseASTViewer(JFrame parent, String sourceCode) {
        super(parent, sourceCode);
    }

    public void visitASTTree() {
        StringBuffer buffer = new StringBuffer();
        ConciseASTVisitor astVistor = new ConciseASTVisitor(buffer, rootNode);
        rootNode.accept(astVistor);

        astViewerText = buffer.toString();
    }
}
