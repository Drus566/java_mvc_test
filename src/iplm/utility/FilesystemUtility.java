package iplm.utility;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilesystemUtility {
    public static boolean isFileExists(String filepath) {
        Path path = Paths.get(filepath);
        return Files.exists(path) && !Files.isDirectory(path);
    }

    public static boolean isDirExists(String dir_path) {
        Path path = Paths.get(dir_path);
        return Files.exists(path) && Files.isDirectory(path);
    }

    public static int readFile(String path, byte[] buffer)  {
        int total_read = 0;
        try {
            File file = new File(path);
            FileInputStream input_stream = new FileInputStream(file);
            int bytes_read = 0;
            while ((bytes_read = input_stream.read(buffer)) != -1) {
                total_read += bytes_read;
            }
            input_stream.close();
        }
        catch (FileNotFoundException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        }
        catch (IOException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        }
        return total_read;
    }

    public static byte[] readFile(String file) {
        ByteArrayOutputStream result = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            result = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1; ) {
                result.write(buffer, 0, len);
            }
            fis.close();
        }
        catch (FileNotFoundException e) {
//            System.err.println(e.getMessage());
        }
        catch (IOException e2) {
//            System.err.println(e2.getMessage());
        }
        return result != null ? result.toByteArray() : null;
    }

    public static void writeFile(String path, byte[] buffer) {
        try {
            File file = new File(path);
            FileOutputStream output_stream = new FileOutputStream(file);
            output_stream.write(buffer);
            output_stream.close();
        }
        catch (FileNotFoundException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        }
        catch (IOException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        }
    }

    public static List<Path> getFilesPaths(String folder_path) {
        List<Path> result = new ArrayList<>();
        try {
            result = Files.walk(Paths.get(folder_path))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        catch (IOException e) { e.printStackTrace(); }
        return result;
    }

    public static ArrayList<String> getAllDirsNamesInDir(String dir) {
        ArrayList<String> result = new ArrayList<>();
        Path path = Paths.get(dir);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry) && !Files.isHidden(entry)) result.add(entry.getFileName().toString());
            }
        }
        catch (IOException e) { DialogUtility.showErrorDialog(e.getMessage()); }
        return result;
    }

    public static void deleteAllFiles(String path) {
        File directory = new File(path);
        for (File file : directory.listFiles()) {
            if (file.isFile()) { file.delete(); }
        }
    }

    public static ArrayList<Path> getRootDirs() {
        ArrayList<Path> result = new ArrayList<>();
        Iterable<Path> roots = FileSystems.getDefault().getRootDirectories();
        for (Path root : roots) {
            result.add(root);
        }
        return result;
    }

    public static void openDir(String path) {
        Path directory = Paths.get(path);

        if (Files.exists(directory) && Files.isDirectory(directory)) {
            try {
                if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(directory.toFile());
                else DialogUtility.showErrorDialog("Другие способы открытия директории не поддерживаются");
            }
            catch (IOException e) { DialogUtility.showErrorDialog(e.getMessage()); }
        }
        else DialogUtility.showErrorDialog("Указанная директория не существует");
    }

    public static ArrayList<Path> searchDirs(Path directory, String search_name) {
        ArrayList<Path> result = new ArrayList<>();
        try {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (dir == null) return FileVisitResult.CONTINUE;
                    if (dir.getFileName().toString().equals(search_name)) result.add(dir.toAbsolutePath());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        catch (IOException e) { DialogUtility.showErrorDialog(e.getMessage()); }
        return result;
    }
}
