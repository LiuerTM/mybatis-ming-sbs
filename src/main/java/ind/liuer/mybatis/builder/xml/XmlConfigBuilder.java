package ind.liuer.mybatis.builder.xml;

import ind.liuer.mybatis.builder.BaseBuilder;
import ind.liuer.mybatis.builder.BuilderException;
import ind.liuer.mybatis.io.ResourceUtil;
import ind.liuer.mybatis.parsing.XNode;
import ind.liuer.mybatis.parsing.XPathParser;
import ind.liuer.mybatis.session.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Mingの
 * @date 2022/6/15 10:03
 * @since 1.0
 */
public class XmlConfigBuilder extends BaseBuilder {

    private final XPathParser xpathParser;

    public XmlConfigBuilder(InputStream inputStream) {
        this(new XPathParser(inputStream, null), null);
    }

    public XmlConfigBuilder(XPathParser xpathParser, Properties props) {
        super(new Configuration());
        this.configuration.setVariables(props);
        this.xpathParser = xpathParser;
    }

    public Configuration parse() {
        parseConfiguration(xpathParser.evalNode("/configuration"));
        return configuration;
    }

    private void parseConfiguration(XNode root) {
        try {
            // todo  解析 mybatis-config.xml

            propertiesElement(root.evalNode("properties"));

            mapperElement(root.evalNode("mappers"));

        } catch (Exception e) {
            throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
    }

    private void propertiesElement(XNode content) throws IOException {
        if (content != null) {
            Properties properties = content.getChildrenAsProperties();
            String resource = content.getStringAttribute("resource");
            String url = content.getStringAttribute("url");
            if (resource != null && url != null) {
                throw new BuilderException("The properties element cannot specify both a URL and a resource based property file reference. " +
                    "Please specify one or the other.");
            }
            if (resource != null) {
                properties.putAll(ResourceUtil.getResourceAsProperties(resource));
            } else if (url != null) {
                properties.putAll(ResourceUtil.getUrlAsProperties(url));
            }
            Properties variables = configuration.getVariables();
            if (variables != null) {
                properties.putAll(variables);
            }
            xpathParser.setVariables(properties);
            configuration.setVariables(properties);
        }
    }


    private void mapperElement(XNode content) throws Exception {
        if (content != null) {
            for (XNode child : content.getChildren()) {
                String resource = child.getStringAttribute("resource");
                if (resource != null) {
                    try (InputStream inputStream = ResourceUtil.getResourceAsStream(resource)) {
                        XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(inputStream, configuration, resource);
                        xmlMapperBuilder.parse();
                    }
                }
            }
        }
    }
}
