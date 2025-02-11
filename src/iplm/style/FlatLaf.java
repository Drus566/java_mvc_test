package iplm.style;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;
import com.formdev.flatlaf.util.SystemInfo;

import javax.swing.*;

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

        SwingUtilities.invokeLater(()->{
            FlatLaf.setupLaf();
            FlatInspector.install( "ctrl shift alt X" );
            FlatUIDefaultsInspector.install( "ctrl shift alt Y" );
        });
    }

    public static void setupLaf() {
        // set look and feel
        FlatLightLaf.setup();
        // remember active look and feel
        UIManager.addPropertyChangeListener( e -> {
            if( "lookAndFeel".equals( e.getPropertyName() ) )
                Style.getState().put( KEY_LAF, UIManager.getLookAndFeel().getClass().getName() );
        } );
    }
}
