package guiBySwing;

import Softwares.SourceCodeFileMechanism;
import org.eclipse.jdt.core.dom.CompilationUnit;
import sourceCodeAST.SourceCodeFile;
import sourceCodeAST.SourceCodeFileSet;
import sourceCodeAST.Visitor;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class MyFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 1000;

    private JTabbedPane tabbedPane;
    private JPanel panel1;
    private JPanel panel2;
    private SummaryPanel summaryPanel;
    private JFileChooser fileChooser;
    private JLabel label;
    private JButton selectButton;
    private JButton runButton;
    private JTextArea analysisArea;
    private JTextArea infoArea;
    private JScrollPane fileListScrollPane;
    private JScrollPane analysisAreaScrollPane;
    private JList<String> fileList;
    private String path = "";
    private SourceCodeFileSet codeFileSet;


    public MyFrame() {
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setTitle("JAnalyzer");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        //this.setResizable(false);

        // create menubar
        makeMenuBar();

        // create tabbed pane
        tabbedPane = new JTabbedPane();
        //tabbedPane.addTab("Run");

        // create panels
        panel1 = new JPanel(new GridBagLayout());
        panel2 = new JPanel(new GridBagLayout());
        summaryPanel = new SummaryPanel();

        // create text area
        analysisArea = new JTextArea(40, 20);
        analysisArea.setEditable(false);
        analysisArea.setBackground(this.getBackground());
        analysisArea.setLineWrap(true);
        infoArea = new JTextArea(40, 60);
        infoArea.setEditable(false);
        infoArea.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 10, true), "Summary"));
        infoArea.setBackground(this.getBackground());

        // create label
        label = new JLabel("selected directory or file is: ");
        label.setBackground(this.getBackground());

        // create buttons
        selectButton = new JButton("Select");
        runButton = new JButton("Run");

        // create file chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //String path = fileChooser.getSelectedFile().getPath();

        // create file list
        fileList = new JList<>();
        fileList.setVisibleRowCount(50);
        fileList.setFixedCellWidth(450);
        fileList.setLayoutOrientation(JList.VERTICAL);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // create ScrollPane
        fileListScrollPane = new JScrollPane(fileList);
        fileListScrollPane.setPreferredSize(new Dimension(450, 650));
        fileListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        fileListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        analysisAreaScrollPane = new JScrollPane(analysisArea);
        analysisAreaScrollPane.setPreferredSize(new Dimension(450, 650));
        analysisAreaScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        analysisAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // add modules to panel1
        panel1.add(label, new GBC(0, 0,3,1).setAnchor(GBC.WEST).setInsets(10));
        panel1.add(selectButton, new GBC(0,1).setAnchor(GBC.WEST).setInsets(10));
        panel1.add(runButton, new GBC(1, 1).setAnchor(GBC.WEST).setInsets(10));
        // panel1.add(infoArea, new GBC(0, 2, 3,1).setAnchor(GBC.CENTER).setInsets(10));
        panel1.add(summaryPanel, new GBC(0,2,3,1).setAnchor(GBC.CENTER).setInsets(10));

        panel2.add(fileListScrollPane, new GBC(0,0).setAnchor(GBC.CENTER).setInsets(10, 0, 0, 10));
        panel2.add(analysisAreaScrollPane, new GBC(1, 0).setAnchor(GBC.CENTER).setInsets(10, 10, 0,0));


        // add panel to tabbedpane
        tabbedPane.addTab("Analyze", panel1);
        tabbedPane.addTab("Detail", panel2);

        this.add(tabbedPane);

        // add listeners to buttons
        selectButton.addActionListener(e -> {
            fileChooser.showOpenDialog(new JTextField());
            if (fileChooser.getSelectedFile() != null) {
                path = fileChooser.getSelectedFile().getPath();
                codeFileSet = new SourceCodeFileSet(path);
                Vector<String> codeFiles = new Vector<>();
                for (SourceCodeFile codefile : codeFileSet) {
                    codeFiles.add(codefile.getFileUnitName());
                }
                if (!codeFiles.isEmpty()) {
                    fileList.setListData(codeFiles);
                }
                label.setText("selected directory or file is: " + path);

            } else {
                path = "/Users/dewey/";
            }
            System.out.println(path);
        });

        runButton.addActionListener(e -> {
            new Thread(() -> {
                SourceCodeFileMechanism.resetCounts();
                if (codeFileSet != null) {
                    for (SourceCodeFile codeFile : codeFileSet) {
                        if (codeFile.hasCreatedAST()) {
                            CompilationUnit root = codeFile.getASTRoot();
                            Visitor visitor = new Visitor();
                            root.accept(visitor);
                            codeFile.setMechanism(visitor.getMechanism());
                            SourceCodeFileMechanism.setInterfaceCount(codeFile.getMechanism().getInterfaceList().size());
                            SourceCodeFileMechanism.setEnumDeclarationCount(codeFile.getMechanism().getEnumDeclarationList().size());
                            SourceCodeFileMechanism.setInnerClassCount(codeFile.getMechanism().getInnerClassList().size());
                            SourceCodeFileMechanism.setLocalClassCount(codeFile.getMechanism().getLocalClassList().size());
                            SourceCodeFileMechanism.setAnonymousClassCount(codeFile.getMechanism().getAnonymousClassList().size());
                        }
                    }
                    SwingUtilities.invokeLater(() -> {
                        summaryPanel.setInterfaceNum(SourceCodeFileMechanism.getInterfaceCount());
                        summaryPanel.setEnumDeclarationNum(SourceCodeFileMechanism.getEnumDeclarationCount());
                        summaryPanel.setInnerClassNum(SourceCodeFileMechanism.getInnerClassCount());
                        summaryPanel.setLocalClassNum(SourceCodeFileMechanism.getLocalClassCount());
                        summaryPanel.setAnonymousClassNum(SourceCodeFileMechanism.getAnonymousClassCount());
                    });
                }
            }).start();
        });

        // add listSelectionListener to fileList
        fileList.addListSelectionListener(e -> {
            new Thread(() -> {
                String fileUnitName = fileList.getSelectedValue();
                SourceCodeFile file = codeFileSet.findSourceCodeFileByFileUnitName(fileUnitName);
                SwingUtilities.invokeLater(() -> {
                    analysisArea.setText("");
                    analysisArea.append(file.getMechanism().getInterfaceList() +"\n");
                    analysisArea.append(file.getMechanism().getEnumDeclarationList() +"\n");
                    analysisArea.append(file.getMechanism().getInnerClassList() +"\n");
                    analysisArea.append(file.getMechanism().getLocalClassList() +"\n");
                    analysisArea.append(file.getMechanism().getAnonymousClassList() +"\n");
                });
            }).start();
        });


    }

    // create menu bar
    private void makeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");

        JMenuItem quitMenuItem = new JMenuItem("Quit");
        JMenuItem selectMenuItem = new JMenuItem("Select");
        quitMenuItem.addActionListener(event -> {
            this.dispose();
            System.exit(0);
        });
        selectMenuItem.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.showOpenDialog(new JTextField());
        });
        file.add(quitMenuItem);
        file.add(selectMenuItem);
        menuBar.add(file);
        menuBar.add(help);
        this.setJMenuBar(menuBar);
    }

}

interface MyMouseListener extends MouseListener {
    @Override
    default void mousePressed(MouseEvent e) {}
    @Override
    default void mouseReleased(MouseEvent e) {}
    @Override
    default void mouseEntered(MouseEvent e) {}
    @Override
    default void mouseExited(MouseEvent e) {}
}
