package ind.liuer.mybatis.io;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 类扫描器
 *
 * @author Mingの
 * @date 2022/6/15 0:28
 */
public class ClassScanner {

    private final String[] packageNames;
    private final boolean recursion;
    private final ClassLoader classLoader;

    public ClassScanner(String packageName) {
        this(packageName, true);
    }

    public ClassScanner(String[] packageNames) {
        this(packageNames, true);
    }

    public ClassScanner(String packageName, boolean recursion) {
        this(new String[]{packageName}, recursion);
    }

    public ClassScanner(String[] packageNames, boolean recursion) {
        this.classLoader = Thread.currentThread().getContextClassLoader();
        this.packageNames = packageNames;
        this.recursion = recursion;
    }

    public Set<Class<?>> scanPackages() throws IOException {
        Set<Class<?>> typeSet = new HashSet<>();
        for (String packageName : packageNames) {
            if (packageName != null && packageName.length() > 0) {
                typeSet.addAll(this.scanPackage(packageName));
            }
        }
        return typeSet;
    }

    private Set<Class<?>> scanPackage(String packageName) throws IOException {
        Set<Class<?>> typeSet = new HashSet<>();
        String packageNameFilePath = packageName.replace(".", "/");
        Enumeration<URL> resources = this.classLoader.getResources(packageNameFilePath);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String protocol = resource.getProtocol();
            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8.name());
                this.doScanPackage(new File(filePath), packageName, typeSet);
            }
        }
        return typeSet;
    }

    private void doScanPackage(File file, String packageName, Set<Class<?>> typeSet) {
        if (file != null && file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    if (child.exists()) {
                        String nameWithPackage = packageName + "." + child.getName();
                        if (child.isFile() && child.getName().endsWith(".class")) {
                            String className = nameWithPackage.substring(0, nameWithPackage.length() - 6);
                            try {
                                Class<?> type = this.classLoader.loadClass(className);
                                typeSet.add(type);
                            } catch (ClassNotFoundException e) {
                                // do nothing
                            }
                        } else if (child.isDirectory() && this.recursion) {
                            this.doScanPackage(child, nameWithPackage, typeSet);
                        }
                    }
                }
            }
        }
    }
}
