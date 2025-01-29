package iplm;

import iplm.managers.ApplicationManager;

public class Application {

    public static void main(String[] args) {
        new Application();
    }

    public Application() {
        ApplicationManager.getInstance().start();
    }
}
