package Softwares;

import java.util.ArrayList;

public class SoftwareMechanism {

    private ArrayList<String> interfaceList;
    private ArrayList<String> enumDeclarationList;
    private ArrayList<String> innerClassList;
    private ArrayList<String> localClassList;
    private ArrayList<String> anonymousClassList;

    SoftwareMechanism() {
        interfaceList = new ArrayList<>();
        enumDeclarationList = new ArrayList<>();
        innerClassList = new ArrayList<>();
        localClassList = new ArrayList<>();
        anonymousClassList = new ArrayList<>();
    }

    public ArrayList<String> getInterfaceList() {
        return interfaceList;
    }

    void setInterfaceList(ArrayList<String> interfaceList) {
        this.interfaceList = interfaceList;
    }

    public ArrayList<String> getEnumDeclarationList() {
        return enumDeclarationList;
    }

    void setEnumDeclarationList(ArrayList<String> enumDeclarationList) {
        this.enumDeclarationList = enumDeclarationList;
    }

    public ArrayList<String> getInnerClassList() {
        return innerClassList;
    }

    void setInnerClassList(ArrayList<String> innerClassList) {
        this.innerClassList = innerClassList;
    }

    public ArrayList<String> getLocalClassList() {
        return localClassList;
    }

    void setLocalClassList(ArrayList<String> localClassList) {
        this.localClassList = localClassList;
    }

    public ArrayList<String> getAnonymousClassList() {
        return anonymousClassList;
    }

    void setAnonymousClassList(ArrayList<String> anonymousClassList) {
        this.anonymousClassList = anonymousClassList;
    }
}
