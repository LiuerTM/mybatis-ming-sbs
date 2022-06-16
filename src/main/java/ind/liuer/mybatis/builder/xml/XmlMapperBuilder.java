package ind.liuer.mybatis.builder.xml;

import ind.liuer.mybatis.builder.BaseBuilder;
import ind.liuer.mybatis.builder.BuilderException;
import ind.liuer.mybatis.builder.MapperBuilderAssistant;
import ind.liuer.mybatis.io.ResourceUtil;
import ind.liuer.mybatis.parsing.XNode;
import ind.liuer.mybatis.parsing.XPathParser;
import ind.liuer.mybatis.session.Configuration;

import java.io.InputStream;
import java.util.List;

/**
 * @author Mingの
 * @date 2022/6/15 15:50
 * @since 1.0
 */
public class XmlMapperBuilder extends BaseBuilder {

    private final XPathParser xpathParser;
    private final MapperBuilderAssistant mapperBuilderAssistant;
    private final String resource;

    public XmlMapperBuilder(InputStream inputStream, Configuration configuration, String resource) {
        this(new XPathParser(inputStream, configuration.getVariables()), configuration, resource);
    }

    public XmlMapperBuilder(XPathParser xpathParser, Configuration configuration, String resource) {
        super(configuration);
        this.mapperBuilderAssistant = new MapperBuilderAssistant(configuration, resource);
        this.xpathParser = xpathParser;
        this.resource = resource;
    }

    public void parse() {
        if (!configuration.isResourceLoaded(resource)) {
            mapperElement(xpathParser.evalNode("/mapper"));
            configuration.addLoadedResources(resource);
            bindMapperForNamespace();
        }

    }

    private void mapperElement(XNode root) {
        try {
            String namespace = root.getStringAttribute("namespace");
            if (namespace == null || namespace.length() == 0) {
                throw new BuilderException("Mapper's namespace cannot be empty.");
            }
            mapperBuilderAssistant.setCurrentNamespace(namespace);
            // todo other element
//            cacheRefElement(root.evalNode("cache-ref"));
//            cacheElement(root.evalNode("cache"));
//            parameterMapElements(root.evalNode("/mapper/parameterMap"));
//            resultMapElements(root.evalNodes("/mapper/resultMap"));
//            sqlElements(root.evalNodes("/mapper/sql"));
            buildStatementFromContext(root.evalNodes("select|insert|update|delete"));
        } catch (Exception e) {
            throw new BuilderException("Error parsing Mapper XML. The XML location is '" + resource + "'. Cause: " + e, e);
        }
    }

    private void bindMapperForNamespace() {
        String namespace = mapperBuilderAssistant.getCurrentNamespace();
        if (namespace != null) {
            Class<?> boundType = null;
            try {
                boundType = ResourceUtil.classForName(namespace);
            } catch (ClassNotFoundException e) {
                // do nothing
            }
            if (boundType != null && !configuration.hasMapper(boundType)) {
                configuration.addLoadedResources("namespace:" + namespace);
                configuration.addMapper(boundType);
            }
        }
    }

    private void buildStatementFromContext(List<XNode> xNodeList) {
        if (configuration.getDatabaseId() != null) {
            buildStatementFromContext(xNodeList, configuration.getDatabaseId());
        }
        buildStatementFromContext(xNodeList, null);
    }

    private void buildStatementFromContext(List<XNode> xNodeList, String databaseId) {
        for (XNode content : xNodeList) {
            XmlStatementBuilder xmlStatementBuilder = new XmlStatementBuilder(configuration, mapperBuilderAssistant, content, databaseId);
            xmlStatementBuilder.parseStatementNode();
        }
    }
}
