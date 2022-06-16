package ind.liuer.mybatis.session;

import ind.liuer.mybatis.binding.MapperRegistry;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Mingの
 * @date 2022/6/15 10:57
 * @since 1.0
 */
public class Configuration {

    /**
     * 加载的配置文件资源集合
     */
    protected final Set<String> loadedResources = new HashSet<>();

    /**
     * 配置的properties属性
     */
    protected Properties variables = new Properties();

    /**
     * 数据库标识
     */
    protected String databaseId;

    /**
     * mapper配置文件
     */
    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);

    public boolean isResourceLoaded(String resource) {
        return loadedResources.contains(resource);
    }

    public void addLoadedResources(String resource) {
        loadedResources.add(resource);
    }

    public void setVariables(Properties variables) {
        this.variables = variables;
    }

    public Properties getVariables() {
        return variables;
    }

    public boolean hasMapper(Class<?> type) {
        return mapperRegistry.hasMapper(type);
    }

    public void addMapper(Class<?> type) {
        mapperRegistry.addMapper(type);
    }

    public String getDatabaseId() {
        return databaseId;
    }
}
