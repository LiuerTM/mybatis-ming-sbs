package ind.liuer.mybatis.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * @author Mingの
 * @date 2022/6/15 10:50
 * @since 1.0
 */
public class ResourceUtil {

    public static InputStream getResourceAsStream(String resource) throws IOException {
        return getResourceAsStream(resource, null);
    }

    public static InputStream getResourceAsStream(String resource, ClassLoader classLoader) throws IOException {
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        InputStream in = classLoader.getResourceAsStream(resource);
        if (in == null) {
            throw new IOException("Could not find resource " + resource + ".");
        }
        return in;
    }

    public static InputStream getUrlAsStream(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        URLConnection conn = (URLConnection) url.getContent();
        InputStream in = conn.getInputStream();
        if (in == null) {
            throw new IOException("Could not find url " + urlStr + ".");
        }
        return in;
    }

    public static Properties getResourceAsProperties(String resource) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = getResourceAsStream(resource)) {
            properties.load(inputStream);
        }
        return properties;
    }

    public static Properties getUrlAsProperties(String urlStr) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = getUrlAsStream(urlStr)) {
            properties.load(inputStream);
        }
        return properties;
    }

    public static Class<?> classForName(String type) throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.loadClass(type);
    }
}
