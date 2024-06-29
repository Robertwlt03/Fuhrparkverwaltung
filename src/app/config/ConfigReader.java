package app.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private final Properties properties;

    public ConfigReader() {
        properties = new Properties();

        // Load the properties from the file
        try {
            properties.load(new FileInputStream("src/app/config/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Get the value for the specified key
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
