package guiBySwing;

import org.eclipse.jdt.core.dom.CompilationUnit;
import sourceCodeAST.DemoVisitor;
import sourceCodeAST.SourceCodeFile;
import sourceCodeAST.SourceCodeFileSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DemoGUI {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "SimpleFrameTest");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            MyFrame.setDefaultLookAndFeelDecorated(true);
            MyFrame frame = new MyFrame();
        });
    }
}

interface MyMouseListener extends MouseListener {
    @Override
    default void mousePressed(MouseEvent e) {
    }
    @Override
    default void mouseReleased(MouseEvent e) {
    }
    @Override
    default void mouseEntered(MouseEvent e) {
    }
    @Override
    default void mouseExited(MouseEvent e) {
    }
}

final class MyFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 1100;

    private static final int BUTTON_INIT_STATE = 0;
    private static final int BUTTON_STATE_CLICKED = 1;

    MyFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setTitle("JAnalyzer");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        // create label and textfield
        JLabel label = new JLabel();
        label.setText("Current file or path: ");
        JTextField field = new JTextField(50);
        field.setText("No file or directory selected");
        field.requestFocus();
        field.setEditable(false);

        // create panels
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel buttonPanel = new JPanel();

        // create textarea
        JTextArea textArea = new JTextArea(50, 80);
        JScrollPane scroller = new JScrollPane(textArea);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        textArea.setLineWrap(true); // 启用自动换行
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createLineBorder(Color.white, 20));
        scroller.setBorder(BorderFactory.createLineBorder(Color.white, 1));

        // create buttons
        JButton runButton = new JButton("Run JAnalyzer");
        JButton clearButton = new JButton("Clear content");
        JButton openButton = new JButton("Open file or directory");

        // add listeners to buttons
        runButton.addActionListener(e -> {
            new Thread(() -> {
                final String path = "/Users/dewey/GitRepositories";
                SourceCodeFileSet fileSet = new SourceCodeFileSet(path);
                for (SourceCodeFile file : fileSet) {
                    StringBuilder builder = new StringBuilder("");
                    builder.append("SourceCodeFile:\n").append(file.getFileUnitName()).append("\n\n");

                    if (file.hasCreatedAST()) {
                        CompilationUnit root = file.getASTRoot();
                        builder.append("Create AST successfully, root type is ".toUpperCase())
                                .append(root.getNodeType())
                                .append("\n\n");
                        String tempString = null;
                        DemoVisitor visitor = new DemoVisitor();
                        root.accept(visitor);
                        tempString = visitor.getStr();
                        builder.append(tempString).append("\n");
                    } else {
                        builder.append("\tError in creating AST")
                                .append(file.getParsingErrorMessage())
                                .append("\n");
                    }
                    SwingUtilities.invokeLater(() -> {
                        field.setText(path);
                        textArea.append(builder.toString());
                    });
                }
                textArea.setCaretPosition(0);

            }).start();
        });
        clearButton.addActionListener(e -> {
            textArea.setText("");
        });
        openButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.showOpenDialog(new JTextField());
        });

        // create menubar
        makeMenuBar();

        // add tools to panel then add panel to frame
        panel1.add(label);
        panel1.add(field);
        panel2.add(scroller);
        panel2.setBorder(new EmptyBorder(20, 20, 20, 0));
        buttonPanel.add(openButton);
        buttonPanel.add(runButton);
        buttonPanel.add(clearButton);
        this.add(BorderLayout.NORTH, panel1);
        this.add(BorderLayout.CENTER, panel2);
        this.add(BorderLayout.SOUTH, buttonPanel);
    }


    // create menu bar
    private void makeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu window = new JMenu("Window");
        JMenu help = new JMenu("Help");

        JMenuItem quitMenuItem = new JMenuItem("Quit");
        JMenuItem openMenuItem = new JMenuItem("Open");
        quitMenuItem.addActionListener(event -> {
            this.dispose();
            System.exit(0);
        });
        openMenuItem.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.showOpenDialog(new JTextField());
        });
        file.add(quitMenuItem);
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(window);
        menuBar.add(help);
        this.setJMenuBar(menuBar);
    }

}

