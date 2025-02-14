package iplm.utility;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtility {
    public static int read_file(String path, byte[] buffer)
    {
        int total_read = 0;
        try {
            File file = new File(path);
            FileInputStream input_stream = new FileInputStream(file);
            int bytes_read = 0;
            while ((bytes_read = input_stream.read(buffer)) != -1) {
                total_read += bytes_read;
            }
            input_stream.close();
        } catch (FileNotFoundException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        } catch (IOException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        }
        return total_read;
    }

    public static byte[] read_file(String file) {
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
        } catch (FileNotFoundException e) {
//            System.err.println(e.getMessage());
        } catch (IOException e2) {
//            System.err.println(e2.getMessage());
        }
        return result != null ? result.toByteArray() : null;
    }

    public static void write_file(String path, byte[] buffer) {
        try {
            File file = new File(path);
            FileOutputStream output_stream = new FileOutputStream(file);
            output_stream.write(buffer);
            output_stream.close();
        } catch (FileNotFoundException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        } catch (IOException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        }
    }

    public static List<Path> get_files_paths(String folder_path) {
        List<Path> result = new ArrayList<>();
        try {
            result = Files.walk(Paths.get(folder_path))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void delete_all_files(String path) {
        File directory = new File(path);
        for (File file : directory.listFiles()) {
            if (file.isFile()) { file.delete(); }
        }
    }
}
