package common;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class PropertiesHandler {

    private static Properties properties = new Properties();

    public String getProperty(String propertyName) {
        String propertyValue = null;
        InputStream input = null;
        try {
            input = PropertiesHandler.class.getClassLoader().getResourceAsStream("global_fa.properties");
            properties.load(input);
            if (!properties.getProperty(propertyName).isEmpty())
                propertyValue = properties.getProperty(propertyName);
        }catch (NullPointerException e) {
            e.printStackTrace();
            return properties.getProperty("unknown");
        }catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return propertyValue;
    }
}
