package sdu.wocl.algorithm.elements;

import java.util.ArrayList;
import java.util.List;

import sdu.wocl.dataFactory.entity.Sentence;
import sdu.wocl.dataFactory.entity.wordtree.WordTreeMessage;

/**
 * 文档的数据预处理
 * @author ljh_2015
 *
 */
public abstract class DocumentBox {


    //文档
    private Model document;


    public void setDocumentBox(Model model) {
	this.document = model;
    }

    public Model getModel() {
	return document;
    }

    public abstract void processing();
}
