package sourceCodeAST;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.List;

public class DemoVisitor extends ASTVisitor {
    private StringBuilder builder = new StringBuilder();
    private ArrayList<String> interfaceList = new ArrayList<>();
    //private ArrayList<String> methodList = new ArrayList<>();
    //private ArrayList<String> fieldList = new ArrayList<>();
    //private ArrayList<String> classList = new ArrayList<>();
    private ArrayList<String> innerClassList = new ArrayList<>();
    private ArrayList<String> localClassList = new ArrayList<>();
    private ArrayList<String> anonymousClassList = new ArrayList<>();
    private ArrayList<String> enumDeclarationList = new ArrayList<>();
    //private ArrayList<String> lambdaExpressionList = new ArrayList<>();



    public String getStr() {
        check();
        return builder.append("\n")
                .append(interfaceList).append("\n")
                .append(enumDeclarationList).append("\n")
                //.append(classList).append("\n")
                .append(innerClassList).append("\n")
                .append(localClassList).append("\n")
                .append(anonymousClassList).append("\n")
                //.append(lambdaExpressionList).append("\n")
                //.append(fieldList).append("\n")
                //.append(methodList).append("\n")
                .append("------------------------------------------------------------------------------\n").toString();
    }

//     @Override
//    public boolean visit(FieldDeclaration node) {
//        for (Object object : node.fragments()) {
//            VariableDeclarationFragment v = (VariableDeclarationFragment) object;
//            String s = "Field:\t" + v.getName() + "\n";
//            fieldList.add(s);
//        }
//        return true;
//    }
//
//    @Override
//    public boolean visit(MethodDeclaration node) {
//        String s = "Method:\t" + node.getName() + "\n";
//        methodList.add(s);
//        return true;
//    }

    @Override
    public boolean visit(TypeDeclaration node) {
        if (node.isInterface()) {
            String s = "Interface:    " + node.getName() + "\n";
            interfaceList.add(s);
        } else {
            if (!node.isPackageMemberTypeDeclaration()) {
                if (node.isLocalTypeDeclaration()) {
                    String s = "local-class:    " + node.getName() + "\n";
                    localClassList.add(s);
                } else {
                    String s = "inner-class:    " + node.getName() + "\n";
                    innerClassList.add(s);
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
                //System.out.println("lpos is: " + lpos + " rpos is: " + rpos);
                function = "    " + "Override Method:    " + function.substring(lpos + 10, rpos + 1) + "\n";
                functions.append(function);
            }

        }

        //String functions = node.bodyDeclarations().toString();
        int pos = anonymousClass .indexOf("{");
        anonymousClass  = anonymousClass .substring(0, pos);

        String s = "anonymous-class:    " + anonymousClass + "\n" + functions.toString() + "\n";
        anonymousClassList.add(s);
        return true;
    }

    @Override
    public boolean visit(EnumDeclaration node) {
        String s = "Enum declaration:    " + node.getName() + "\n";
        enumDeclarationList.add(s);
        return true;
    }

    /*@Override
    public boolean visit(LambdaExpression node) {
        String s = "lambda expression:\t" + node.toString() + "\n";
        lambdaExpressionList.add(s);
        return true;
    }*/


    private void check() {
//         if (methodList.isEmpty()) {
//            methodList.add("no method".toUpperCase());
//        } else {
//            if (methodList.size() == 1) methodList.add(0, "1 method\n\n".toUpperCase());
//            else methodList.add(0, methodList.size() + " methods\n\n".toUpperCase());
//        }
//        if (fieldList.isEmpty()) {
//            fieldList.add("no field".toUpperCase());
//        } else {
//            if (fieldList.size() == 1) fieldList.add(0, "1 field\n\n".toUpperCase());
//            else fieldList.add(0, fieldList.size() + " fields\n\n".toUpperCase());
//        }
//        if (classList.isEmpty()) {
//            classList.add("no class".toUpperCase());
//        } else {
//            if (classList.size() == 1) classList.add(0, "1 class\n\n".toUpperCase());
//            else classList.add(0, classList.size() + " classes\n\n".toUpperCase());
//        }
        if (innerClassList.isEmpty()) {
            innerClassList.add("no inner class".toUpperCase());
        } else {
            if (innerClassList.size() == 1) innerClassList.add(0, "1 inner class\n".toUpperCase());
            else innerClassList.add(0, innerClassList.size() + " inner classes\n".toUpperCase());
        }
        if (anonymousClassList.isEmpty()) {
            anonymousClassList.add("no anonymous class".toUpperCase());
        } else {
            if (anonymousClassList.size() == 1) anonymousClassList.add(0, "1 anonymous class\n".toUpperCase());
            else anonymousClassList.add(0, anonymousClassList.size() + " anonymous classes\n".toUpperCase());
        }
        if (localClassList.isEmpty()) {
            localClassList.add("no local class".toUpperCase());
        } else {
            if (localClassList.size() == 1) localClassList.add(0, "1 local class\n".toUpperCase());
            else localClassList.add(0, localClassList.size() + " local classes\n".toUpperCase());
        }
        if (interfaceList.isEmpty()) {
            interfaceList.add("no interface".toUpperCase());
        } else {
            if (interfaceList.size() == 1) interfaceList.add(0, "1 interface\n".toUpperCase());
            else interfaceList.add(0, interfaceList.size() + " interface\n".toUpperCase());
        }
        if (enumDeclarationList.isEmpty()) {
            enumDeclarationList.add("no enum declaration".toUpperCase());
        } else {
            if (enumDeclarationList.size() == 1) enumDeclarationList.add(0, "1 enum declaration\n".toUpperCase());
            else enumDeclarationList.add(0, enumDeclarationList.size() + " enum declaration\n".toUpperCase());
        }
//        if (lambdaExpressionList.isEmpty()) {
//            lambdaExpressionList.add("no lambda expression".toUpperCase());
//        } else {
//            if (lambdaExpressionList.size() == 1) lambdaExpressionList.add(0, "1 lambda expression\n".toUpperCase());
//            else lambdaExpressionList.add(0, lambdaExpressionList.size() + " lamda expression\n".toUpperCase());
//        }
    }

}
