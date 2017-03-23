package sdu.wocl.dataFactory.resolution.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sdu.wocl.dataFactory.entity.wordtree.tools.HibernateFactoryUtil;
/** 
 * 
 * @author ljh 
 * DOM生成与解析XML文档 
 */ 
public class XmlProcessor { 

    private Document document; 
    private HibernateFactoryUtil hiber = new HibernateFactoryUtil();

    public XmlProcessor(InputStream in) {
	try { 
	    if(in==null) 
		return;
	    DocumentBuilderFactory factory = DocumentBuilderFactory 
		    .newInstance(); 
	    DocumentBuilder builder = factory.newDocumentBuilder(); 
	    try {
		document = builder.parse(in);
	    } catch (SAXException e) {
		// TODO Auto-generated catch block
		System.out.println("error");
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } 

	} catch (ParserConfigurationException e) { 
	    System.out.println(e.getMessage()); 
	}
    }
    /**
     * 解析xml内容
     * @param xmlPath xml文件路径
     */
    public XmlProcessor(String xmlPath) { 
	try { 
	    DocumentBuilderFactory factory = DocumentBuilderFactory 
		    .newInstance(); 
	    DocumentBuilder builder = factory.newDocumentBuilder(); 
	    try {
		document = builder.parse(new File(xmlPath));
	    } catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } 

	} catch (ParserConfigurationException e) { 
	    System.out.println(e.getMessage()); 
	} 
    } 

    public NodeList getNodeList(String tag) {
	if(document!=null) {
	    NodeList nodes = document.getElementsByTagName(tag); 
	    return nodes;
	} else
	    return null;
    }

    /**
     * 获取一条记录所有属性
     * @param nodeList
     * @return
     */
    public String getElementContent(NamedNodeMap m){
	String strReturn = "";

	try{
	    //遍历每个属性
	    for(int i=0;i<m.getLength();i++) {
		Node nodeAttri=m.item(i);//获得第i个属性的值
		strReturn+=nodeAttri.getNodeValue();
		if(i!=m.getLength()-1) {
		    strReturn+=",";
		}
	    }
	}catch(Exception e){
	    strReturn=e.toString().replaceAll("\n", "");
	}
	return strReturn;
    }

    /**
     * 获取当前标签的子标签
     * 注意：用getChildNodes方法往往得出来的结果与实际结果不符，表现为getlength往往大于实际
     * 子标签数目。这是由于xml空格所造成
     * @param m
     * @return
     */
    private NodeList getSubElement(Node m) {
	return m.getChildNodes();
    }

    /**
     * 遍历节点
     * @param nodes
     */
    private String[] traverse(NodeList nodes) {
	List<String> strs = new ArrayList<String>();
	for(int i=0;i<nodes.getLength();i++) {
	    Node child = nodes.item(i);
	    if(child instanceof Element) {
		NamedNodeMap map = child.getAttributes();
		strs.add(getElementContent(map));
	    } else {
		continue;
	    }
	}
	String[] strss = new String[strs.size()];
	for(int i=0;i<strs.size();i++)
	    strss[i]=strs.get(i);
	return strss;
    }

    /**
     * 获取当前标签的子标签属性,以String[] 字符串数组形式返回
     * @param m
     * @return
     */
    public String[] getSubElementAttrs(Node m) {
	NodeList nodes = getSubElement(m);
	return traverse(nodes);
    }

    /**
     * 获取第i个节点的属性映射
     * @param list
     * @param i 元素标签
     * @return
     */
    public NamedNodeMap getNamedNodeMap(NodeList list,int i) {
	NamedNodeMap map = list.item(i).getAttributes();
	return map;
    } 

    /**
     * 获取第i条属性
     * @param map
     * @param i
     * @return
     */
    public String getAttr(NamedNodeMap map,int i) {
	return map.item(i).getNodeValue();
    }
} 
