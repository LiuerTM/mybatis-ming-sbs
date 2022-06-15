package ind.liuer.mybatis.binding;

import ind.liuer.mybatis.io.ClassScanner;

import java.io.IOException;
import java.util.*;

/**
 * Mapper（代理工厂）注册表
 *
 * @author Mingの
 * @date 2022/6/14 16:19
 * @since 1.0
 */
public class MapperRegistry {

    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> type) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new BindingException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance();
        } catch (Exception e) {
            throw new BindingException("Error getting mapper instance. Cause: " + e.getMessage(), e);
        }
    }

    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMapper(type)) {
                throw new BindingException("Type " + type + " is already known to the MapperRegistry.");
            }
            knownMappers.put(type, new MapperProxyFactory<>(type));
        }
    }

    public void addMappers(String packageName) {
        addMappers(packageName, Object.class);
    }

    public void addMappers(String packageName, Class<Object> superType) {
        Set<Class<?>> mapperSet;
        try {
            ClassScanner classScanner = new ClassScanner(packageName);
            mapperSet = classScanner.scanPackages();
        } catch (IOException e) {
            throw new BindingException("Error scan package " + packageName + ". Cause: " + e.getMessage(), e);
        }
        for (Class<?> type : mapperSet) {
            if (superType.isAssignableFrom(type)) {
                addMapper(type);
            }
        }
    }

    public Collection<Class<?>> getMappers() {
        return Collections.unmodifiableCollection(knownMappers.keySet());
    }

    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }
}
