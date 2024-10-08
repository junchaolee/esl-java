package com.xbstar.esl.util;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
 
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
 
/**
 * 操作XML文件的工具类
 * 
 * @author glw
 */
public class XMLUtilLjc {
    /**
     * 得到XML文档
     * 
     * @param xmlFile
     *            文件名（路径）
     * @return XML文档对象
     * @throws DocumentException
     */
    public static Document getDocument(String xmlFile) {
        SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");
        File file = new File(xmlFile);
        try {
            if (!file.exists()) {
                return null;
            } else {
                return reader.read(file);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e + "->指定文件【" + xmlFile + "】读取错误");
        }
    }
 
    /**
     * 得到XML文档(编码格式-gb2312)
     * 
     * @param xmlFile
     *            文件名（路径）
     * @return XML文档对象
     * @throws DocumentException
     */
    public static Document getDocument_gb2312(String xmlFile) {
        SAXReader reader = new SAXReader();
        reader.setEncoding("gb2312");
        File file = new File(xmlFile);
        try {
            if (!file.exists()) {
                return null;
            } else {
                return reader.read(file);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e + "->指定文件【" + xmlFile + "】读取错误");
        }
    }
 
    public static String getText(Element element) {
        try {
            return element.getTextTrim();
        } catch (Exception e) {
            throw new RuntimeException(e + "->指定【" + element.getName() + "】节点读取错误");
        }
 
    }
 
    /**
     * 增加xml文件节点
     * 
     * @param document
     *            xml文档
     * @param elementName
     *            要增加的元素名称
     * @param attributeNames
     *            要增加的元素属性
     * @param attributeValues
     *            要增加的元素属性值
     */
    public static Document addElementByName(Document document, String elementName, Map<String, String> attrs, String cdata) {
        try {
            Element root = document.getRootElement();
            Element subElement = root.addElement(elementName);
            for (Map.Entry<String, String> attr : attrs.entrySet()) {
                subElement.addAttribute(attr.getKey(), attr.getValue());
            }
            subElement.addCDATA(cdata);
        } catch (Exception e) {
            throw new RuntimeException(e + "->指定的【" + elementName + "】节点增加出现错误");
        }
        return document;
    }
 
    /**
     * 删除xml文件节点
     */
    @SuppressWarnings("unchecked")
    public static Document deleteElementByName(Document document, String elementName) {
        Element root = document.getRootElement();
        Iterator<Object> iterator = root.elementIterator("file");
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            // 根据属性名获取属性值
            Attribute attribute = element.attribute("name");
            if (attribute.getValue().equals(elementName)) {
                root.remove(element);
                document.setRootElement(root);
                break;
            }
        }
        return document;
    }
 
    /**
     * 输出xml文件
     * 
     * @param document
     * @param filePath
     * @throws IOException
     */
    public static void writeXml(Document document, String filePath) throws IOException {
        File xmlFile = new File(filePath);
        XMLWriter writer = null;
        try {
            if (xmlFile.exists())
                xmlFile.delete();
            writer = new XMLWriter(new FileOutputStream(xmlFile), OutputFormat.createPrettyPrint());
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }
 
    /**
     * 创建Document及根节点
     * 
     * @param rootName
     * @param attributeName
     * @param attributeVaule
     * @return
     */
    public static Document createDocument(String rootName, String attributeName, String attributeVaule) {
        Document document = null;
        try {
            document = DocumentHelper.createDocument();
            Element root = document.addElement(rootName);
            root.addAttribute(attributeName, attributeVaule);
        } catch (Exception e) {
            throw new RuntimeException(e + "->创建的【" + rootName + "】根节点出现错误");
        }
        return document;
    }
 
    /**
     * 删除xml文件节点
     */
    @SuppressWarnings("unchecked")
    public static Document deleteElementAddressByName(Document document, String elementName) {
        Element root = document.getRootElement();
        Iterator<Object> iterator = root.elementIterator("address");
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            // 根据属性名获取属性值
            Attribute attribute = element.attribute("name");
            if (attribute.getValue().equals(elementName)) {
                root.remove(element);
                document.setRootElement(root);
                break;
            }
        }
        return document;
    }
    
    /**
     *    删除属性等于某个值的元素
     *    @param document  XML文档
     *    @param xpath xpath路径表达式
     *    @param attrName 属性名
     *    @param attrValue 属性值
     *    @return      
     */
    @SuppressWarnings("unchecked")
    public static Document deleteElementByAttribute(Document document, String xpath, String attrName, String attrValue) {
        Iterator<Object> iterator = document.selectNodes(xpath).iterator();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            Element parentElement = element.getParent();
            // 根据属性名获取属性值
            Attribute attribute = element.attribute(attrName);
            if (attribute.getValue().equals(attrValue)) {
                parentElement.remove(element);
            }
        }
        return document;
    }
}