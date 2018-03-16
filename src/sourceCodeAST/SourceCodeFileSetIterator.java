package sourceCodeAST;
import org.eclipse.jdt.core.dom.*;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SourceCodeFileSetIterator implements Iterator<SourceCodeFile> {

    private Map<String, SourceCodeFile> fileMap = null;
    private Set<String> fileUnitNameSet = null;
    private String currentFileUnitName = null;
    private Iterator<String> setIterator = null;

    SourceCodeFileSetIterator(SourceCodeFileSet fileSet) {
        fileMap = fileSet.getSourceCodeFileMap();
        this.fileUnitNameSet = fileMap.keySet();
        setIterator = fileUnitNameSet.iterator();
    }

    public boolean hasNext() {
        return  setIterator.hasNext();
    }

    public SourceCodeFile next() {
        currentFileUnitName = setIterator.next();
        return fileMap.get(currentFileUnitName);
    }

    public String getCurrentFileUnitName() {
        return currentFileUnitName;
    }
}
