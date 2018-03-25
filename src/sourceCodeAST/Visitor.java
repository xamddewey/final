package sourceCodeAST;

import Softwares.SourceCodeFileMechanism;
import org.eclipse.jdt.core.dom.*;

import java.util.List;

public class Visitor extends ASTVisitor {
    private SourceCodeFileMechanism mechanism;

    public SourceCodeFileMechanism getMechanism() {
        //check();
        return mechanism;
    }

    public Visitor(){
        mechanism = new SourceCodeFileMechanism();
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        if (node.isInterface()) {
            String s = "Interface:    " + node.getName() + "\n";
            mechanism.getInterfaceList().add(s);
        } else {
            if (!node.isPackageMemberTypeDeclaration()) {
                if (node.isLocalTypeDeclaration()) {
                    String s = "local-class:    " + node.getName() + "\n";
                    mechanism.getLocalClassList().add(s);
                } else {
                    String s = "inner-class:    " + node.getName() + "\n";
                    mechanism.getInnerClassList().add(s);
                }
            }
        }
        return true;
    }

    @Override
    public boolean visit(AnonymousClassDeclaration node) {
        String anonymousClass = node.getParent().toString();
        List myList =  node.bodyDeclarations();
        StringBuilder functions = new StringBuilder("");
        for (Object aMyList : myList) {
            String function = String.valueOf(aMyList);
            int lpos = function.indexOf("@Override ");
            int rpos = function.indexOf(")");
            if (lpos != -1 && lpos < rpos) {
                function = "    " + "Override Method:    " + function.substring(lpos + 10, rpos + 1) + "\n";
                functions.append(function);
            }
        }
        int pos = anonymousClass.indexOf('{');
        anonymousClass = anonymousClass.substring(0, pos);
        String s = "anonymous-class:    " + anonymousClass + "\n" + functions.toString() + "\n";
        mechanism.getAnonymousClassList().add(s);
        return true;
    }

    @Override
    public boolean visit(EnumDeclaration node) {
        String s = "Enum declaration:     " + node.getName() + "\n";
        mechanism.getEnumDeclarationList().add(s);
        return true;
    }

    private void check() {
        if (mechanism.getInnerClassList().isEmpty()) {
            mechanism.getInnerClassList().add("no inner class".toUpperCase());
        } else {
            if (mechanism.getInnerClassList().size() == 1) mechanism.getInnerClassList().add(0, "1 inner class\n".toUpperCase());
            else mechanism.getInnerClassList().add(0, mechanism.getInnerClassList().size() + " inner classes\n".toUpperCase());
        }
        if (mechanism.getAnonymousClassList().isEmpty()) {
            mechanism.getAnonymousClassList().add("no anonymous class".toUpperCase());
        } else {
            if (mechanism.getAnonymousClassList().size() == 1) mechanism.getAnonymousClassList().add(0, "1 anonymous class\n".toUpperCase());
            else mechanism.getAnonymousClassList().add(0, mechanism.getAnonymousClassList().size() + " anonymous classes\n".toUpperCase());
        }
        if (mechanism.getLocalClassList().isEmpty()) {
            mechanism.getLocalClassList().add("no local class".toUpperCase());
        } else {
            if (mechanism.getLocalClassList().size() == 1) mechanism.getLocalClassList().add(0, "1 local class\n".toUpperCase());
            else mechanism.getLocalClassList().add(0, mechanism.getLocalClassList().size() + " local classes\n".toUpperCase());
        }
        if (mechanism.getInterfaceList().isEmpty()) {
            mechanism.getInterfaceList().add("no interface".toUpperCase());
        } else {
            if (mechanism.getInterfaceList().size() == 1) mechanism.getInterfaceList().add(0, "1 interface\n".toUpperCase());
            else mechanism.getInterfaceList().add(0, mechanism.getInterfaceList().size() + " interface\n".toUpperCase());
        }
        if (mechanism.getEnumDeclarationList().isEmpty()) {
            mechanism.getEnumDeclarationList().add("no enum declaration".toUpperCase());
        } else {
            if (mechanism.getEnumDeclarationList().size() == 1) mechanism.getEnumDeclarationList().add(0, "1 enum declaration\n".toUpperCase());
            else mechanism.getEnumDeclarationList().add(0, mechanism.getEnumDeclarationList().size() + " enum declaration\n".toUpperCase());
        }
    }
}
