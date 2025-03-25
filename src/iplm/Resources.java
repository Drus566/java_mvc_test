package iplm;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.sun.tools.javac.Main;
import iplm.utility.FilesystemUtility;
import iplm.utility.IntellijIdeaUtility;
import iplm.utility.JarUtility;

import javax.swing.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Resources {
    private static final Resources INSTANCE = new Resources();
    private static Map png_icons = new HashMap<String, ImageIcon>();
    private static Map svg_icons = new HashMap<String, FlatSVGIcon>();

    public static Resources getInstance() {
        return INSTANCE;
    }

    public static ImageIcon getPngIcon(String filename) {
        return (ImageIcon) png_icons.get(filename);
    }

    public static FlatSVGIcon getSVGIcon(String filename) {
        return (FlatSVGIcon) svg_icons.get(filename);
    }
    
    public void init() {
        loadIcons();
    }

    private void loadIcons() {
        if (IntellijIdeaUtility.isRunWithIdea()) {
            List<Path> result = FilesystemUtility.getFilesPaths(Application.RESOURCES_ICONS);
            if (result != null && result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Path path = result.get(i);
                    String filename = path.getFileName().toString();
//                if (filename.endsWith(".png")) {
//                    ImageIcon image_icon = new ImageIcon(path.toString());
//                    png_icons.put(filename, image_icon);
//                }
                    if (filename.endsWith(".svg")) {
                        Path sub_path = path.subpath(1,3);
                        FlatSVGIcon flat_svg_icon = new FlatSVGIcon( sub_path.toString().replace("\\", "/"));
                        svg_icons.put(filename, flat_svg_icon);
                    }
                }
            }
        }
        else {
            List<Path> result = JarUtility.getFilePathsFromDir(Application.ICONS_PATH);
            if (result != null && result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Path path = result.get(i);
                    String filename = path.getFileName().toString();
                    if (filename.endsWith(".svg")) {
                        FlatSVGIcon flat_svg_icon = new FlatSVGIcon( path.toString().replace("\\", "/"));
                        svg_icons.put(filename, flat_svg_icon);
                    }
                }
            }
        }
    }
}
