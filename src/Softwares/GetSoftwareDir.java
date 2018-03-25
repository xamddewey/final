package Softwares;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class GetSoftwareDir {
    public static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File dir = new File(path);
        FileFilter filter = new DirFilter();
        File[] tempFiles = dir.listFiles(filter);
        assert tempFiles != null;
        for (File tempFile : tempFiles) {
            files.add(tempFile.toString());
        }
        return files;
    }
}

class DirFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return pathname.isDirectory();
    }
}