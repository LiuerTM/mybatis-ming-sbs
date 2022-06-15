package ind.liuer.mybatis.builder.xml;

import ind.liuer.mybatis.builder.BaseBuilder;
import ind.liuer.mybatis.parsing.XNode;
import ind.liuer.mybatis.parsing.XPathParser;
import ind.liuer.mybatis.session.Configuration;

import java.io.InputStream;

/**
 * @author Mingの
 * @date 2022/6/15 15:50
 * @since 1.0
 */
public class XmlMapperBuilder extends BaseBuilder {

    private final XPathParser xpathParser;
    private final String resource;

    public XmlMapperBuilder(InputStream inputStream, Configuration configuration, String resource) {
        this(new XPathParser(inputStream, configuration.getVariables()), configuration, resource);
    }

    public XmlMapperBuilder(XPathParser xpathParser, Configuration configuration, String resource) {
        super(configuration);
        this.xpathParser = xpathParser;
        this.resource = resource;
    }

    public void parse() {
        if (!configuration.isResourceLoaded(resource)) {
            parseMapper(xpathParser.evalNode("/mapper"));
        }
    }

    private void parseMapper(XNode root) {

    }
}
