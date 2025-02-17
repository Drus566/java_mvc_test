package iplm.utility;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class JsonUtility {
    private static Gson GSON = new Gson();

    public static <T> boolean toJson(String file_path, T object) {
        boolean result = true;
        try (FileWriter writer = new FileWriter(file_path)) { GSON.toJson(object, writer); }
        catch (IOException e) {
            System.out.println("Error convert to json file " + file_path);
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T fromJson(String file_path, Type type) {
        T result = null;
        try (FileReader reader = new FileReader(file_path)) { result = GSON.fromJson(reader, type); }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found " + file_path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error load json " + file_path);
        }
        return result;
    }
}
