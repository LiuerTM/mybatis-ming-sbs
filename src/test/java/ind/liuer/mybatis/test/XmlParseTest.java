package ind.liuer.mybatis.test;

import ind.liuer.mybatis.builder.xml.XmlConfigBuilder;
import ind.liuer.mybatis.io.ResourceUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mingの
 * @date 2022/6/15 11:13
 * @since 1.0
 */
public class XmlParseTest {

    @Test
    public void testXmlConfig() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = ResourceUtil.getResourceAsStream(resource);
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder(inputStream);
        xmlConfigBuilder.parse();
    }
}
