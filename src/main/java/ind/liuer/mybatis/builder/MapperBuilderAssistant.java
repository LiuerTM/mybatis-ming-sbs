package ind.liuer.mybatis.builder;

import ind.liuer.mybatis.session.Configuration;

import java.util.Objects;

/**
 * @author Mingの
 * @date 2022/6/16 9:38
 * @since 1.0
 */
public class MapperBuilderAssistant extends BaseBuilder {

    private final String resource;

    private String currentNamespace;

    public MapperBuilderAssistant(Configuration configuration, String resource) {
        super(configuration);
        this.resource = resource;
    }

    public String getCurrentNamespace() {
        return currentNamespace;
    }

    public void setCurrentNamespace(String currentNamespace) {
        if (currentNamespace == null) {
            throw new BuilderException("The mapper element requires a namespace attribute to be specified.");
        }
        if (this.currentNamespace != null && !Objects.equals(this.currentNamespace, currentNamespace)) {
            throw new BuilderException("Wrong namespace. Expected '" + this.currentNamespace + "' but found '" + currentNamespace + "'.");
        }
        this.currentNamespace = currentNamespace;
    }

    public String applyCurrentNamespace(String base, boolean isReference) {
        if (base == null) {
            return null;
        }
        if (isReference) {
            if (base.contains(".")) {
                return base;
            }
        } else {
            if (base.startsWith(currentNamespace + ".")) {
                return base;
            }
            if (base.contains(".")) {
                throw new BuilderException("Dots are not allowed in element names, please remove it from " + base);
            }
        }
        return currentNamespace + "." + base;
    }
}
