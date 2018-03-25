package Softwares;

import java.util.ArrayList;

public class SourceCodeFileMechanism {

    private ArrayList<String> interfaceList;
    private ArrayList<String> enumDeclarationList;
    private ArrayList<String> innerClassList;
    private ArrayList<String> localClassList;
    private ArrayList<String> anonymousClassList;

    private static int interfaceCount = 0;
    private static int enumDeclarationCount = 0;
    private static int innerClassCount = 0;
    private static int localClassCount = 0;
    private static int anonymousClassCount = 0;

    public SourceCodeFileMechanism() {
        interfaceList = new ArrayList<>();
        enumDeclarationList = new ArrayList<>();
        innerClassList = new ArrayList<>();
        localClassList = new ArrayList<>();
        anonymousClassList = new ArrayList<>();
    }

    public ArrayList<String> getInterfaceList() {
        return interfaceList;
    }

    public void setInterfaceList(ArrayList<String> interfaceList) {
        this.interfaceList = interfaceList;
    }

    public ArrayList<String> getEnumDeclarationList() {
        return enumDeclarationList;
    }

    public void setEnumDeclarationList(ArrayList<String> enumDeclarationList) {
        this.enumDeclarationList = enumDeclarationList;
    }

    public ArrayList<String> getInnerClassList() {
        return innerClassList;
    }

    public void setInnerClassList(ArrayList<String> innerClassList) {
        this.innerClassList = innerClassList;
    }

    public ArrayList<String> getLocalClassList() {
        return localClassList;
    }

    public void setLocalClassList(ArrayList<String> localClassList) {
        this.localClassList = localClassList;
    }

    public ArrayList<String> getAnonymousClassList() {
        return anonymousClassList;
    }

    public void setAnonymousClassList(ArrayList<String> anonymousClassList) {
        this.anonymousClassList = anonymousClassList;
    }

    public void append(SourceCodeFileMechanism mechanism) {
        this.interfaceList.addAll(mechanism.interfaceList);
        this.enumDeclarationList.addAll(mechanism.enumDeclarationList);
        this.innerClassList.addAll(mechanism.innerClassList);
        this.localClassList.addAll(mechanism.localClassList);
        this.anonymousClassList.addAll(mechanism.anonymousClassList);
    }

    public String getMechanismString() {
        StringBuilder builder = new StringBuilder("");
        return builder.append(interfaceList).append("\n")
                .append(enumDeclarationList).append("\n")
                .append(innerClassList).append("\n")
                .append(localClassList).append("\n")
                .append(anonymousClassList).append("\n")
                .append("------------------------------------------------------------------------------------\n")
                .toString();
    }

    public static int getInterfaceCount() {
        return interfaceCount;
    }

    public static int getEnumDeclarationCount() {
        return enumDeclarationCount;
    }

    public static int getInnerClassCount() {
        return innerClassCount;
    }

    public static int getLocalClassCount() {
        return localClassCount;
    }

    public static int getAnonymousClassCount() {
        return anonymousClassCount;
    }

    public static void setInterfaceCount(int interfaceCount) {
        SourceCodeFileMechanism.interfaceCount += interfaceCount;
    }

    public static void setEnumDeclarationCount(int enumDeclarationCount) {
        SourceCodeFileMechanism.enumDeclarationCount += enumDeclarationCount;
    }

    public static void setInnerClassCount(int innerClassCount) {
        SourceCodeFileMechanism.innerClassCount += innerClassCount;
    }

    public static void setLocalClassCount(int localClassCount) {
        SourceCodeFileMechanism.localClassCount += localClassCount;
    }

    public static void setAnonymousClassCount(int anonymousClassCount) {
        SourceCodeFileMechanism.anonymousClassCount += anonymousClassCount;
    }

    public static void resetCounts() {
        interfaceCount = 0;
        enumDeclarationCount = 0;
        innerClassCount = 0;
        localClassCount = 0;
        anonymousClassCount = 0;
    }
}
