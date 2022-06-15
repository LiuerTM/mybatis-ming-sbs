package ind.liuer.mybatis.parsing;

import ind.liuer.mybatis.builder.BuilderException;
import ind.liuer.mybatis.builder.xml.XmlMybatisEntityResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.*;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Mingの
 * @date 2022/6/15 10:05
 * @since 1.0
 */
public class XPathParser {

    /**
     * 校验
     */
    private final EntityResolver entityResolver =  new XmlMybatisEntityResolver();

    /**
     * XML文档
     */
    private final Document document;

    /**
     * 解析XML对象
     */
    private final XPath xpath;

    /**
     * 解析出Properties属性存储以便后续使用
     */
    private Properties variables;

    public XPathParser(InputStream inputStream, Properties variables) {
        this.xpath = XPathFactory.newInstance().newXPath();
        this.document = createDocument(new InputSource(inputStream));
        this.variables = variables;
    }

    private Document createDocument(InputSource inputSource) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setValidating(true);

            factory.setNamespaceAware(false);
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(false);
            factory.setCoalescing(false);
            factory.setExpandEntityReferences(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(entityResolver);
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    // do nothing
                }
            });
            return builder.parse(inputSource);
        } catch (Exception e) {
            throw new BuilderException("Error creating document instance. Cause: " + e, e);
        }
    }

    public void setVariables(Properties variables) {
        this.variables = variables;
    }

    public XNode evalNode(String expression) {
        return evalNode(document, expression);
    }

    public XNode evalNode(Object root, String expression) {
        Node node = (Node) evaluate(root, expression, XPathConstants.NODE);
        if (node == null) {
            return null;
        }
        return new XNode(this, node, variables);
    }

    public Object evaluate(Object root, String expression, QName returnType) {
        try {
            return xpath.evaluate(expression, root, returnType);
        } catch (XPathExpressionException e) {
            throw new BuilderException("Error evaluating XPath. Cause: " + e, e);
        }
    }
}
