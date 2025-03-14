package iplm.style;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.util.SystemInfo;
import iplm.utility.FontUtility;

import javax.swing.*;
import java.awt.*;

public class FlatLaf {
    public static final String KEY_LAF = "laf";

    public static void init() {
        if( SystemInfo.isMacOS ) {
            // enable screen menu bar
            // (moves menu bar from JFrame window to top of screen)
            System.setProperty( "apple.laf.useScreenMenuBar", "true" );

            // application name used in screen menu bar
            // (in first menu after the "apple" menu)
            System.setProperty( "apple.awt.application.name", "FlatLaf Demo" );

            // appearance of window title bars
            // possible values:
            //   - "system": use current macOS appearance (light or dark)
            //   - "NSAppearanceNameAqua": use light appearance
            //   - "NSAppearanceNameDarkAqua": use dark appearance
            // (must be set on main thread and before AWT/Swing is initialized;
            //  setting it on AWT thread does not work)
            System.setProperty( "apple.awt.application.appearance", "system" );
        }

        // Linux
        if( SystemInfo.isLinux ) {
            // enable custom window decorations
            JFrame.setDefaultLookAndFeelDecorated( true );
            JDialog.setDefaultLookAndFeelDecorated( true );
        }

        FlatLaf.setupLaf();
//        FlatInspector.install( "ctrl shift alt X" );
//        FlatUIDefaultsInspector.install( "ctrl shift alt Y" );
    }

    public static void setupLaf() {
        // set look and feel
        // FlatLightLaf.setup();
        FlatIntelliJLaf.setup();
        UIManager.put("defaultFont", new Font(FontUtility.getDefaultFontName(), Font.PLAIN, Style.FONT_SIZE));
        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");
        UIManager.put("OptionPane.cancelButtonText", "Отмена");
        UIManager.put("OptionPane.okButtonText", "Готово");

        UIManager.put("FileChooser.saveButtonText", "Сохранить");
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.fileNameLabelText", "Наименование файла");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Типы файлов");
        UIManager.put("FileChooser.lookInLabelText", "Директория");
        UIManager.put("FileChooser.saveInLabelText", "Сохранить в директории");
        UIManager.put("FileChooser.folderNameLabelText", "Путь директории");

        UIManager.put("FileChooser.lookInLabelText", "Директория");
        UIManager.put("FileChooser.openButtonText", "Выбрать");
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.fileNameLabelText", "Путь к файлу");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Формат");
        UIManager.put("FileChooser.upFolderToolTipText", "Вверх");
        UIManager.put("FileChooser.homeFolderToolTipText", "Домашняя папка");
        UIManager.put("FileChooser.newFolderToolTipText", "Создать папку");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Список");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Плитка");
//        UIManager.getDefaults().put("FileChooser.fileNameHeaderText", "fileNameHeaderText");
//        UIManager.getDefaults().put("FileChooser.fileSizeHeaderText", "fileSizeHeaderText");
//        UIManager.getDefaults().put("FileChooser.fileTypeHeaderText", "fileTypeHeaderText");
//        UIManager.getDefaults().put("FileChooser.fileDateHeaderText", "fileDateHeaderText");
        UIManager.put("FileChooser.folderNameLabelText", "Путь к директории");

        UIManager.put("TitlePane.centerTitle", true);

        // remember active look and feel
        UIManager.addPropertyChangeListener( e -> {
            if( "lookAndFeel".equals( e.getPropertyName() ) )
                Style.getState().put( KEY_LAF, UIManager.getLookAndFeel().getClass().getName() );
        } );
    }
}
