package sourceCodeAST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import utils.Debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class TestSourceCodeFileSet {
    public static void main(String args[]) {
        String rootPath = "/Users/xudongwei/";
        String path = "/Users/xudongwei/test/";
        String result = rootPath + "result3.txt";

        PrintWriter writer = new PrintWriter(System.out);
        PrintWriter output = new PrintWriter(System.out);

        try {
            writer = new PrintWriter(new FileOutputStream(new File(result)));
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        try {
            String info = rootPath + "debug3.txt";
            output = new PrintWriter(new FileOutputStream(new File(info)));
            Debug.setWriter(output);
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        //testGetAllUnitName(path, writer);
		//testGetAllContents(path, writer);
		//testGetAllASTs(path, writer);

		testGetAllASTsByForEach(path, writer);

        writer.close();
        output.close();
    }

    /**
     *  Demo how to get all unit name by using iterator explicitly and by using for-each loop
     */
    private static void testGetAllUnitName(String startPath, PrintWriter writer) {
        SourceCodeFileSet fileSet = new SourceCodeFileSet(startPath);
        SourceCodeFileSetIterator iterator = fileSet.iterator();
        System.out.println("Use iterator explicitly......");

        while (iterator.hasNext()) {
            SourceCodeFile codeFile = iterator.next();
            System.out.println("SourceCodeFile: " + codeFile.getFileFullName());
            writer.println("Current unit name: " + iterator.getCurrentFileUnitName());
        }
        System.out.println("Total files: " + fileSet.getFileNumber());
        System.out.println("Use for-each loop......");
        for (SourceCodeFile codeFile : fileSet) {
            System.out.println("Source Code File: " + codeFile.getFileFullName());
            writer.println("Unit name: " + fileSet.getFileUnitName(codeFile));
        }
    }

    /**
     * Demo how to get all contents by using iterator explicitly
     */
    private static void testGetAllContents(String startPath, PrintWriter writer) {
        SourceCodeFileSet fileSet = new SourceCodeFileSet(startPath);
        for (SourceCodeFile codeFile : fileSet) {
            String content = codeFile.getFileContent();

            System.out.println("Source Code File: " + codeFile.getFileFullName() + "\n");

            writer.println("Source Code File: \n" + codeFile.getFileFullName() + ", \nunit name: " + fileSet.getFileUnitName(codeFile));
            writer.println(content.substring(0, 100) + ", ...");
            //writer.println(content);
            writer.println("---------------------------------------------------------------------");
            writer.println("Total line: " + codeFile.getTotalLines() + ", total space: " + codeFile.getTotalSpaces() + " Bytes\n\n");
        }

        System.out.println("Total files: " + fileSet.getFileNumber());
        fileSet.releaseAllFileContents();
    }

    /**
     * Demo how to get all ASTs by using for-each loop
     */
    public static void testGetAllASTsByForEach(String startPath, PrintWriter writer) {
        SourceCodeFileSet fileSet = new SourceCodeFileSet(startPath);
        for (SourceCodeFile codeFile : fileSet) {
            System.out.println("Source Code File: " + codeFile.getFileFullName());
            writer.println("Source Code File: \t" + codeFile.getFileFullName() + ", \nunit name: \t" + fileSet.getFileUnitName(codeFile));

            if (codeFile.hasCreatedAST()) {
                CompilationUnit root = codeFile.getASTRoot();
                writer.println("Create AST successful, root type is ".toUpperCase() + root.getNodeType());
                String str = null;
                DemoVisitor visitor = new DemoVisitor();
                root.accept(visitor);
                str = visitor.getStr();
                writer.println(str);
            } else {
                String errorMessage = codeFile.getParsingErrorMessage();
                writer.println("\tError in creating AST: " + errorMessage);
            }
        }
        System.out.println("Total files: " + fileSet.getFileNumber());
        fileSet.releaseAllASTs();
    }
}

