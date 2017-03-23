package sdu.wocl.dataFactory.entity.wordtree;

import java.util.List;

/**
 * 生树器
 * @author ljh_2015
 *
 * @param <T>
 */
public interface TreeImp<T> {
    
    //从一堆元素中，创建自定义树
    void create(List<T> elements);
    //添加元素
    boolean put(DoubleTreeNode<T> parent,DoubleTreeNode<T> Dtree);
    //获取元素
    T getElement(int i); 
    //读取元素
    boolean read(T t,double mark,int deep);
    //遍历结果
    void loadResult(DoubleTreeNode<T> dtree);
    //合并树
    boolean merge(DoubleTreeNode<T> parent,DoubleTreeNode<T> son);
 
}
