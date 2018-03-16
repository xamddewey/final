package sourceCodeAST;

import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

public class SourceCodeFileSet implements Iterable<SourceCodeFile> {
    private static final String pathSeparator = "/";               // 文件对象分隔符
    private String startPath = null;                        // 起始路径
    private String rootFile = null;                         // 根文件
    private TreeMap<String, SourceCodeFile> fileMap = null; // 文件单元名与源代码文件对象映射

    /**
     * @param rootFile
     * 构造方法以一个字符串为参数，这个字符串可以是起始路径，也可以是一个完整的Java文件名;
     * 如果是一个Java文件名，则这个文件所在的目录为起始路径，但是整个源代码文件集将只有这一个文件;
     * 如果是一个起始路径，则整个源代码文件集包含该起始路径下(包含子目录下)的所有.java文件。
     */
    public SourceCodeFileSet(String rootFile) {
        this.rootFile = rootFile;
        File dir = new File(rootFile);
        if (dir.isFile()) {
            System.out.println("It's full name of a java source code file.");
            if (dir.getParent() == null) {
                this.startPath = "";
            } else {
                this.startPath = dir.getParent() + pathSeparator;
            }
        } else {
            System.out.println("It's a start path of a source code file set");
            this.startPath = rootFile;
        }

        // load all java files to the array allInfo
        createFileMap();
    }

    /**
     * 返回一个源代码文件集迭代器，以便构件使用者遍历源代码文件集中的源代码文件对象。
     * 类SourceCodeFileSet实现接口java.lang.Iterable，
     * 因此可像遍历其他Java容器类一样使用for-each循环遍历SourceCodeFileSet中的源代码文件集对象
     */
    public SourceCodeFileSetIterator iterator() {
        return new SourceCodeFileSetIterator(this);
    }

    public long getTotalLineNumberOfAllFile() {
        long result = 0;
        Collection<SourceCodeFile> fileSet = fileMap.values();
        for (SourceCodeFile sourceCodeFile : fileSet) {
            result += sourceCodeFile.getTotalLines();
        }
        return result;
    }

    public long getTotalSpacesOfAllFiles(){
        long result = 0;
        Collection<SourceCodeFile> fileSet = fileMap.values();
        for (SourceCodeFile sourceCodeFile : fileSet) {
            result += sourceCodeFile.getTotalSpaces();
        }
        return result;
    }

    public int getFileNumber() {
        return fileMap.size();
    }

    /**
     * 给定源代码文件对象，返回其单元名
     */
    public String getFileUnitName(SourceCodeFile sourceCodeFile) {
        return getFileUnitName(sourceCodeFile.getFileFullName());
    }

    /**
     * 给定单元名查找源代码文件对象
     */
    public SourceCodeFile findSourceCodeFileByFileUnitName(String fileUnitName) {
        return fileMap.get(fileUnitName);
    }

    /**
     * 给定单元名查找源代码文件内容
     */
    public String findSourceCodeFileContentByFileUnitName(String fileUnitName) {
        SourceCodeFile sourceCodeFile = fileMap.get(fileUnitName);
        return sourceCodeFile.getFileContent();
    }

    /**
     * 给定单元名查找源代码文件AST根节点
     */
    public CompilationUnit findSourceCodeFileASTRootByFileUnitName(String fileUnitName) {
        SourceCodeFile sourceCodeFile = fileMap.get(fileUnitName);
        if (sourceCodeFile.hasCreatedAST()) {
            return sourceCodeFile.getASTRoot();
        } else {
            return null;
        }
    }

    public void releaseAllFileContents() {
        Collection<SourceCodeFile> fileSet = fileMap.values();
        for (SourceCodeFile sourceCodeFile : fileSet) {
            sourceCodeFile.releaseFileContent();
        }
    }

    public void releaseAllASTs() {
        Collection<SourceCodeFile> fileSet = fileMap.values();
        for (SourceCodeFile sourceCodeFile : fileSet) {
            sourceCodeFile.releaseAST();
        }
    }

    public void releaseFileContent(String fileUnitName) {
        SourceCodeFile codeFile = fileMap.get(fileUnitName);
        if (codeFile != null) {
            codeFile.releaseFileContent();
        }
    }

    public void releaseAST(String fileUnitName) {
        SourceCodeFile codeFile = fileMap.get(fileUnitName);
        if (codeFile != null) {
            codeFile.releaseAST();
        }
    }

    public String getStartPath() {
        return startPath;
    }

    class JavaSourceFileFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            return pathname.isDirectory() || pathname.isFile() && pathname.getName().endsWith(".java");
        }
    }

    /**
     * 装载constructor给出路径中的所有Java文件
     * 装载起始目录下的所有源代码文件，并建立映射
     */
    private void createFileMap() {
        ArrayList<File> files = getAllJavaSourceFiles(rootFile);
        fileMap = new TreeMap<String, SourceCodeFile>();

        for (File file : files) {
            SourceCodeFile sourceCodeFile = new SourceCodeFile(file);
            String fileUnitName = getFileUnitName(sourceCodeFile.getFileFullName());
            sourceCodeFile.setFileUnitName(fileUnitName);
            fileMap.put(fileUnitName, sourceCodeFile);
        }
    }

    /**
     * 用JavaSourceFileFilter来获取startPath下的所有Java源文件
     */
    private ArrayList<File> getAllJavaSourceFiles(String rootPath) {
        ArrayList<File> files = new ArrayList<File>();
        File dir = new File(rootPath);
        if (dir.isFile()) {
            if (dir.getName().endsWith(".java")) {
                files.add(dir);
            }
            return files;
        }
        FileFilter filter = new JavaSourceFileFilter();
        File[] temp = dir.listFiles(filter);
        if (temp != null) {
            for (File aTemp : temp) {
                if (aTemp.isFile()) {
                    files.add(aTemp);
                }
                if (aTemp.isDirectory()) {
                    List<File> tempResult = getAllJavaSourceFiles(aTemp.getAbsolutePath());
                    files.addAll(tempResult);
                }
            }
        }
        return files;
    }

    /**
     * @param fileFullName
     * @return
     * 把源代码文件的全名转化成单元名，即把全名中的startPath替换成空字符
     */
    private String getFileUnitName(String fileFullName) {
        return fileFullName.replace(startPath, "");
    }

    Map<String, SourceCodeFile> getSourceCodeFileMap() {
        return fileMap;
    }
}
