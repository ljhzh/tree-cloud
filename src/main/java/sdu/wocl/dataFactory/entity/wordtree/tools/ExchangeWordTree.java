package sdu.wocl.dataFactory.entity.wordtree.tools;

import sdu.wocl.dataFactory.entity.wordtree.WordTreeMessage;

/**
 * 表示语句树的变换表示方法
 * @author ljh_2015
 *
 */
public interface ExchangeWordTree <T>{

    /**
     * 变换方法
     * @return
     */
    T changeMethod(WordTreeMessage refs);
    
}
