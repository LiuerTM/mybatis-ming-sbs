package ind.liuer.mybatis.builder.xml;

import ind.liuer.mybatis.builder.BaseBuilder;
import ind.liuer.mybatis.builder.MapperBuilderAssistant;
import ind.liuer.mybatis.parsing.XNode;
import ind.liuer.mybatis.session.Configuration;

/**
 * @author Mingの
 * @date 2022/6/16 10:28
 * @since 1.0
 */
public class XmlStatementBuilder extends BaseBuilder {

    private final MapperBuilderAssistant mapperBuilderAssistant;
    private final XNode content;
    private final String databaseId;


    public XmlStatementBuilder(Configuration configuration, MapperBuilderAssistant mapperBuilderAssistant, XNode content, String databaseId) {
        super(configuration);
        this.mapperBuilderAssistant = mapperBuilderAssistant;
        this.content = content;
        this.databaseId = databaseId;
    }

    public void parseStatementNode() {

    }
}
