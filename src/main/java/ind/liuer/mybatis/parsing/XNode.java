package ind.liuer.mybatis.parsing;

import org.w3c.dom.CharacterData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Mingの
 * @date 2022/6/15 10:10
 * @since 1.0
 */
public class XNode {

    /**
     * Node节点
     */
    private final Node node;

    /**
     * Node节点名称
     */
    private final String name;

    /**
     * Node节点文本
     */
    private final String body;

    /**
     * Node节点属性
     */
    private final Properties attributes;

    /**
     * 整个解析过程中Properties属性
     */
    private final Properties variables;

    /**
     * 整个解析过程中所用XpathParse对象
     */
    private final XPathParser xpathParse;

    public XNode(XPathParser xpathParse, Node node, Properties variables) {
        this.variables = variables;
        this.xpathParse = xpathParse;
        this.node = node;
        this.name = node.getNodeName();
        this.body = parseBody(node);
        this.attributes = parseAttributes(node);
    }

    private Properties parseAttributes(Node node) {
        Properties properties = new Properties();
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attNode = attributes.item(i);
                properties.put(attNode.getNodeName(), attNode.getNodeValue());
            }
        }
        return properties;
    }

    private String parseBody(Node node) {
        String data = getBodyData(node);
        if (data == null) {
            NodeList childNodes = node.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node childNode = childNodes.item(i);
                data = getBodyData(childNode);
                if (data != null) {
                    break;
                }
            }
        }
        return data;
    }

    private String getBodyData(Node node) {
        if (node.getNodeType() == Node.CDATA_SECTION_NODE
            || node.getNodeType() == Node.TEXT_NODE) {
            return ((CharacterData) node).getData();
        }
        return null;
    }


    // ===================================================================================

    public XNode evalNode(String expression) {
        return xpathParse.evalNode(node, expression);
    }

    public Properties getChildrenAsProperties() {
        Properties properties = new Properties();
        for (XNode child : getChildren()) {
            String name = child.getStringAttribute("name");
            String value = child.getStringAttribute("value");
            if (name != null && value != null) {
                properties.getProperty(name, value);
            }
        }
        return properties;
    }

    public List<XNode> getChildren() {
        List<XNode> children = new ArrayList<>();
        NodeList childNodes = node.getChildNodes();
        if (childNodes != null) {
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    children.add(new XNode(xpathParse, node, variables));
                }
            }
        }
        return children;
    }

    public String getStringAttribute(String name) {
        return getStringAttribute(name, null);
    }

    public String getStringAttribute(String name, String defaultVal) {
        String value = attributes.getProperty(name);
        return value == null ? defaultVal : value;
    }
}
