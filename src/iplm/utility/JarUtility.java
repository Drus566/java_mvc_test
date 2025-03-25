package iplm.utility;

import iplm.Application;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class JarUtility {
//    public static List<Path> getFileNamesFromDir(String folderPath) {
//        List<Path> file_names = new ArrayList<>();
//        URL jar_url = Application.class.getProtectionDomain().getCodeSource().getLocation();
//        URI jar_uri = null;
//        try { jar_uri = jar_url.toURI(); }
//        catch (URISyntaxException e) { e.printStackTrace(); }
//
//        try (FileSystem fs = FileSystems.newFileSystem(jar_uri, Collections.emptyMap())) {
//            Path jar_folder_path = fs.getPath(folderPath);
//
//            if (Files.exists(jar_folder_path)) {
//                try (DirectoryStream<Path> stream = Files.newDirectoryStream(jar_folder_path)) {
//                    for (Path entry : stream) {
//                        if (Files.isRegularFile(entry)) {
//                            file_names.add(entry);
//                        }
//                    }
//                }
//            }
//        }
//        catch (IOException e) { e.printStackTrace(); }
//        return file_names;
//    }

    public static List<Path> getFilePathsFromDir(String dir_path) {
        String jar_path = getCurrentJarPath();
        if (jar_path == null) return null;

        List<Path> file_paths = new ArrayList<>();
        try (JarFile jarFile = new JarFile(jar_path)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                Path entry_path = Paths.get(entry.getName());
                if (entry_path.startsWith(dir_path)) file_paths.add(entry_path);
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        return file_paths;
    }

    public static String getCurrentJarPath() {
        String result = null;
        try {
            result = new File(Application.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .getAbsolutePath();
        }
        catch (URISyntaxException e) { e.printStackTrace(); }
        return result;
    }
}
