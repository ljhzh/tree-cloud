package sdu.wocl.dataFactory.resolution;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

import sdu.wocl.dataFactory.entity.Pos;
import sdu.wocl.dataFactory.entity.Sentence;
import sdu.wocl.dataFactory.entity.Word;
import sdu.wocl.dataFactory.entity.Wp;
import sdu.wocl.dataFactory.processor.SentenceProcessorFilter;
import sdu.wocl.dataFactory.resolution.xml.XmlProcessor;

public class InputStreamResolutionFilter {

    private final static Logger logger = LoggerFactory.getLogger(InputStreamResolutionFilter.class);
    private final SentenceProcessorFilter sentenceProcessorFilter = new SentenceProcessorFilter();
    private List<Sentence> sentsTree = null;

    public InputStreamResolutionFilter() {
	logger.debug("初始化解析器");
	sentsTree = new ArrayList<Sentence>();
    }

    public void resolution(InputStream in,int id) {
	try {
	    extract(in,id);
	} catch (SAXParseException e) {
	    // TODO Auto-generated catch block
	    System.out.println("第"+id+"个流解析有误，请重新检查");
	}
	sentenceProcessorFilter.processor(sentsTree);
    }
    
    public List<Sentence> getSentsTree() {
	return sentsTree;
    }
    
    public void clear() {
	sentsTree.clear();
    }
    
    private void extract(InputStream in,int id) throws SAXParseException{
	XmlProcessor xp = new XmlProcessor(in);
	NodeList sents = xp.getNodeList("sent");//获取句子
	if(sents==null) {
	    System.out.println("第"+id+"个流解析有误，请重新检查");
	    return;
	}
	List<Word> ws = null;
	for(int i=0;i<sents.getLength();i++) {
	    ws = new ArrayList<Word>();
	    Node node = sents.item(i);
	    if(node.getNodeType()==Node.ELEMENT_NODE) {
		//获取当前节点的属性
		NamedNodeMap map = node.getAttributes();
		//获取当前节点,所有子标签word的属性
		String[] words = xp.getSubElementAttrs(node);
		for(String word:words) {
		    //构造sql语句
		    if(word.startsWith(",")) 
			word="，"+word.substring(1);
		    String[] values = word.split(",");
		    if(values[0].trim()!=" " && values[5].equalsIgnoreCase("wp")) {
			Wp wp = new Wp(id,
				Integer.parseInt(values[1]),
				values[0],
				Pos.getPos(values[4]),
				Integer.parseInt(values[3]),
				"wp",
				Integer.parseInt(values[6]),
				values[7]);
			ws.add(wp);
		    } else {
			Word w = new Word(id,Integer.parseInt(values[1]),values[0],Pos.getPos(values[4]),Integer.parseInt(values[3]),values[5],Integer.parseInt(values[6]),values[7]);
			ws.add(w);
		    }

		}
		//logger.debug("第"+i+"句完成解析");
		Sentence sent = new Sentence(ws,i);
		sentsTree.add(sent);
	    }
	}
    }
}
