package guiBySwing;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SummaryPanel extends JPanel {
    private JLabel interfaceLabel;
    private JLabel enumDeclarationLabel;
    private JLabel innerClassLabel;
    private JLabel localClassLabel;
    private JLabel anonymousClassLabel;

    private JLabel interfaceNum;
    private JLabel enumDeclarationNum;
    private JLabel innerClassNum;
    private JLabel localClassNum;
    private JLabel anonymousClassNum;

    public SummaryPanel() {
        this.setLayout(new GridBagLayout());
        this.setBorder(new LineBorder(Color.lightGray, 5, true));
        this.setPreferredSize(new Dimension(800, 600));

        // create labels
        interfaceLabel = new JLabel("Total Number of Interfaces: ");
        enumDeclarationLabel = new JLabel("Total Number of Enums: ");
        innerClassLabel = new JLabel("Total Number of Inner-Classes: ");
        localClassLabel = new JLabel("Total Number of Local-Classes: ");
        anonymousClassLabel = new JLabel("Total Number of Anonymous-Classes: ");

        interfaceNum = new JLabel("None");
        enumDeclarationNum = new JLabel("None");
        innerClassNum = new JLabel("None");
        localClassNum = new JLabel("None");
        anonymousClassNum = new JLabel("None");

        // set labels' fonts
        interfaceLabel.setFont(new Font("Menlo", Font.BOLD, 20));
        enumDeclarationLabel.setFont(new Font("Menlo", Font.BOLD, 20));
        innerClassLabel.setFont(new Font("Menlo", Font.BOLD, 20));
        localClassLabel.setFont(new Font("Menlo", Font.BOLD, 20));
        anonymousClassLabel.setFont(new Font("Menlo", Font.BOLD, 20));

        interfaceNum.setFont(new Font("Menlo", Font.PLAIN, 20));
        enumDeclarationNum.setFont(new Font("Menlo", Font.PLAIN, 20));
        innerClassNum.setFont(new Font("Menlo", Font.PLAIN, 20));
        localClassNum.setFont(new Font("Menlo", Font.PLAIN, 20));
        anonymousClassNum.setFont(new Font("Menlo", Font.PLAIN, 20));

        // add labels to panel
        this.add(interfaceLabel, new GBC(0,0).setAnchor(GBC.WEST).setInsets(5));
        this.add(interfaceNum, new GBC(1, 0).setAnchor(GBC.EAST).setInsets(5));
        this.add(enumDeclarationLabel, new GBC(0, 1).setAnchor(GBC.WEST).setInsets(5));
        this.add(enumDeclarationNum, new GBC(1,1).setAnchor(GBC.EAST).setInsets(5));
        this.add(innerClassLabel, new GBC(0, 2).setAnchor(GBC.WEST).setInsets(5));
        this.add(innerClassNum, new GBC(1, 2).setAnchor(GBC.EAST).setInsets(5));
        this.add(localClassLabel, new GBC(0,3).setAnchor(GBC.WEST).setInsets(5));
        this.add(localClassNum, new GBC(1, 3).setAnchor(GBC.EAST).setInsets(5));
        this.add(anonymousClassLabel, new GBC(0, 4).setAnchor(GBC.WEST).setInsets(5));
        this.add(anonymousClassNum, new GBC(1, 4).setAnchor(GBC.EAST).setInsets(5));
    }

    public void setInterfaceNum(int num) {
        this.interfaceNum.setText(String.valueOf(num));
    }

    public void setEnumDeclarationNum(int num) {
        this.enumDeclarationNum.setText(String.valueOf(num));
    }

    public void setInnerClassNum(int num) {
        this.innerClassNum.setText(String.valueOf(num));
    }

    public void setLocalClassNum(int num) {
        this.localClassNum.setText(String.valueOf(num));
    }

    public void setAnonymousClassNum(int num) {
        this.anonymousClassNum.setText(String.valueOf(num));
    }

    public int getInterfaceNum() {
        return Integer.valueOf(interfaceNum.getText());
    }

    public int getEnumDeclarationNum() {
        return Integer.valueOf(enumDeclarationNum.getText());
    }

    public int getInnerClassNum() {
        return Integer.valueOf(innerClassNum.getText());
    }

    public int getLocalClassNum() {
        return Integer.valueOf(localClassNum.getText());
    }

    public int getAnonymousClassNum() {
        return Integer.valueOf(anonymousClassNum.getText());
    }
}
