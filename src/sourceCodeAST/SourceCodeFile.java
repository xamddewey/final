package sourceCodeAST;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.JavaCore;

import java.io.*;
import java.util.Map;

public class SourceCodeFile {
    private String unitName = null;             // 单位名称，与整个源代码文件集的根路径相关
    private String simpleName = null;           // 文件简单名，不包含任何路径
    private String fileContent = null;          // 文件内容
    private File fileHandle = null;             // 文件句柄
    private boolean hasParsingError = false;    // 是否有编译错误
    private String parsingErrorMessage = null;  // 编译错误信息
    private CompilationUnit rootASTNode = null; // 抽象语法树根节点
    private int totalLines = 0;                 // 文件总代码行数
    private long totalSpaces = 0;               // 文件占用的总空间

    public SourceCodeFile(File fileHandle) {
        this.fileHandle = fileHandle;
    }

    public File getFileHandle() {
        return fileHandle;
    }

    /**
     * We use three names of a Java source code file, full name, unit name and simple name. The full name
     * of a file includes all paths, usually starting with a symbol of hard-disk (e.g. C:\). The unit name of a
     * file is related to the start path of the source code file set it belong to. The unit name + the start path
     * equal to the full name of the file. The simple name of a file is the name which does not include any path.
     *
     * Every source code file can be distinguished by its unit name in a source code file set. Of course, they can
     * be distinguished by their full names too.
     */
    public String getFileFullName() {
        return fileHandle.getPath();
    }

    void setFileUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getFileUnitName() {
        return unitName;
    }

    void setFileSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getSimpleName() {
        return simpleName;
    }


    public String getFileContent() {
        if (fileContent == null) {
            loadContent();
        }
        return fileContent;
    }

    /**
     * Check if the AST of the source code file has been created successfully. If the current object does not
     * hold the AST root node (i.e. rootASTNode == null), it will try to create AST for this file automatically.
     * <p>If created AST with no parse error, then return true, otherwise return false.
     */
    public boolean hasCreatedAST() {
        if (hasParsingError) return false;
        if (rootASTNode == null) createAST();
        if (rootASTNode == null) {
            hasParsingError = true;
            parsingErrorMessage = "UNKNOWN ERROR: Can not create AST for " + fileHandle.getPath() + "!";
            return false;
        }

        StringBuilder msg = null;
        IProblem[] errors = rootASTNode.getProblems();
        if (errors != null && errors.length > 0) {
            msg = new StringBuilder();
            for (IProblem problem : errors) {
                if (problem.isError()) {
                    hasParsingError = true;
                    String message = "Line " + problem.getSourceLineNumber() + ": " + problem.getMessage();
                    msg.append(message);
                    msg.append("\r\n\t\t");
                } // Ignore all warnings!
            }
        }
        if (msg != null) parsingErrorMessage = msg.toString();
        else parsingErrorMessage = null;

        return !hasParsingError;
    }

    /**
     * pre-condition The client must have called hasCreatedAST()!
     */
    public CompilationUnit getASTRoot() {
        return rootASTNode;
    }

    /**
     * pre-condition: The client must have called hasCreatedAST()!
     */
    public String getParsingErrorMessage() {
        return parsingErrorMessage;
    }

    public int getTotalLines() {
        if (totalLines == 0) {
            loadContent();
        }
        return totalLines;
    }

    public long getTotalSpaces(){
        if (totalSpaces == 0) {
            loadContent();
        }
        return totalSpaces;
    }

    public void releaseAST() {
         rootASTNode = null;
         parsingErrorMessage = null;
         hasParsingError = false;
    }

    public void releaseFileContent() {
        fileContent = null;
    }

    private void loadContent() {
        if (fileHandle == null) return;
        try {
            LineNumberReader reader = new LineNumberReader(new FileReader(fileHandle));
            String line = reader.readLine();
            StringBuilder buffer = new StringBuilder();
            while (line != null) {
                buffer.append(line + "\n");
                totalLines += 1;
                line = reader.readLine();
            }
            reader.close();
            fileContent = buffer.toString();
            totalSpaces = fileHandle.length();
        } catch (IOException exc) {
            fileContent = null;
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked" })
    // 生成抽象语法树
    private void createAST() {
        if (fileHandle == null) return;
        if (fileContent == null) loadContent();
        if (fileContent == null) return;

        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        // For parsing the source code in Java 1.8, the compile options must be set!
        Map options = JavaCore.getOptions();
        JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
        parser.setCompilerOptions(options);

        parser.setSource(fileContent.toCharArray());
        parsingErrorMessage = null;
        hasParsingError = false;
        rootASTNode = (CompilationUnit) parser.createAST(null);
    }
}
