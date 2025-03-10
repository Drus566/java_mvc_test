package iplm.utility;

import iplm.data.db.OrientDBDriver;

import javax.swing.*;

public class DialogUtility {
    public static void showDialog(String title, String message, int type) {
        JOptionPane.showMessageDialog(null, message, title, type);
    }

    public static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public static void showErrorIfExists() {
        OrientDBDriver instance = OrientDBDriver.getInstance();
        if (instance != null) {
            String last_error = instance.getLastError();
            if (last_error != null && !last_error.isEmpty()) DialogUtility.showErrorDialog(last_error);
            instance.setLastError("");
        }
    }

    public static boolean showConfirmDialog() {
        boolean result = false;
        int response = JOptionPane.showConfirmDialog(null,"Вы уверены?", "Подтверждение",  JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) result = true;
        return result;
    }
}
