import guiBySwing.MyFrame;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "SimpleFrameTest");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            MyFrame myFrame = new MyFrame();
        });
    }
}
