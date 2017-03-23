package sdu.wocl.dataFactory.entity.wordtree;


/**
 * 用来传递结果信息
 * @author ljh_2015
 *
 */
public abstract class Message {
    
    /**
     * 信息产生时间
     */
    protected String now;
    
    /**
     * 信息类别
     */
    protected String type;
    
    /**
     * 信息显示页面
     */
    protected String window;
    
    /**
     * 
     */
    protected int id;
    
    /**
     * 原句
     */
    protected String context;
    
}
