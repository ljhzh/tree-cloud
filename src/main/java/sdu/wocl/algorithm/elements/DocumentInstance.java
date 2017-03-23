package sdu.wocl.algorithm.elements;

import java.util.List;

import sdu.wocl.algorithm.data.StyleMapps;
import sdu.wocl.dataFactory.entity.Sentence;

/**
 * 接收训练的数据训练出一种DocumentModel
 * @author ljh_2015
 *
 */
public abstract class DocumentInstance extends DocumentBox {

    protected static StyleMapps maps = new StyleMapps();

    /**
     * 开启反馈机制
     */
    public void openFeedBack() {
    }


    @Override
    public void processing() {
	//数据预处理
	preparedProcessing(super.getContexts());
	//数据计算
	computingProcessing();

//	if(feedback) {
//	    feedback();
//	    computingProcessing();
//	}
    }

    protected void show() {
	//得到结果数据
	resultProcessing();
    }

    //预处理算法
    protected abstract void preparedProcessing(List<Sentence> context);

    //计算算法
    protected abstract void computingProcessing();

    //结果集收取算法
    protected abstract void resultProcessing();

    //中间数据与预处理数据若是存在某种关系
    //可通过该方法进行反馈练习
    //反馈算法
    protected abstract void feedback();

}
