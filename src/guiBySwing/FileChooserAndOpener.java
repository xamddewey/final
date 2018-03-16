package guiBySwing;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileChooserAndOpener {
    private File file = null;
    private String fileContents = null;
    private String fileContentsWithLineNumber = null;

    private JFrame parent = null;

    public FileChooserAndOpener(JFrame parent) {
        this.parent = parent;
    }

    /**
     * 显示文件选择对话框，并让用户要打开的文件。缺省情况下打开 Java 源程序文件
     * @return 如果选择成功返回 true ，否则返回 false
     */
    public boolean chooseFileName() {
        JFileChooser chooser = new JFileChooser();

        // 只打开 .java 文件
        chooser.setFileFilter(new FileNameExtensionFilter("Java 源程序文件...", "java"));
        // 从上一次打开的文件开始选择，如果上次没有选择，这时file == null，则从缺省目录开始选择
        if (file != null) chooser.setCurrentDirectory(file);
        else chooser.setCurrentDirectory(new File("."));

        int result = chooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            return true;
        } else return false;
    }

    /**
     * 选择文件成功之后装入文件内容。
     * @return 如何没有选择文件，或者在装入过程中发生 I/O 错误返回 false ，装入成功返回 true
     */
    public boolean loadFile() {
        if (file == null) return false;

        try {
            FileInputStream fileIn = new FileInputStream(file);
            ProgressMonitorInputStream progressIn = new ProgressMonitorInputStream(parent, "正在读取文件 [" + file.getName() + "]", fileIn);

            final Scanner in = new Scanner(progressIn);
            StringBuilder buffer = new StringBuilder();
            StringBuilder bufferWithLine = new StringBuilder();
            int lineCounter = 0;
            while (in.hasNextLine()) {
                lineCounter++;
                String line = in.nextLine();
                buffer.append(line).append("\n");
                bufferWithLine.append(lineCounter).append(" ").append(line).append("\n");
            }
            fileContents = buffer.toString();
            fileContentsWithLineNumber = bufferWithLine.toString();
            in.close();
            return true;
        } catch (IOException exc) {
            return false;
        }
    }

    /**
     * 返回已经选择的文件名
     * @return 如果没有选择文件则返回 null
     */
    public String getFileName() {
        if (file == null) return null;
        else return file.getName();
    }

    /**
     * 返回包含文件全部内容的字符串
     * @return 如果文件没有装载成功则返回 null
     */
    public String getFileContents() {
        return fileContents;
    }

    /**
     * 返回含有行号的文件内容
     */
    public String getFileContentsWithLineNumber() {
        return fileContentsWithLineNumber;
    }
}
