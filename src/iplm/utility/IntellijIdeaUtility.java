package iplm.utility;

import com.sun.tools.javac.Main;

public class IntellijIdeaUtility {
    public static boolean isRunWithIdea() {
        boolean result = false;
        try { result = Main.class.getClassLoader().loadClass("com.intellij.rt.execution.application.AppMainV2") != null; }
        catch (ClassNotFoundException e) {}
        return result;
    }
}
