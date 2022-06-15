package ind.liuer.mybatis.session;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Mingの
 * @date 2022/6/15 10:57
 * @since 1.0
 */
public class Configuration {

    protected Properties variables = new Properties();
    protected final Set<String> loadedResources = new HashSet<>();


    public void setVariables(Properties variables) {
        this.variables = variables;
    }

    public Properties getVariables() {
        return variables;
    }

    public boolean isResourceLoaded(String resource) {
        return loadedResources.contains(resource);
    }
}
