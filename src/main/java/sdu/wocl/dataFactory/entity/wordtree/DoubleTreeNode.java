package sdu.wocl.dataFactory.entity.wordtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 二叉树
 * @author ljh_2015
 *
 * @param <T>
 */
public abstract class DoubleTreeNode<T> implements DataBaseImpl {
    
    /**
     * 节点编号
     */
    protected int id;

    /**
     * 节点信息元
     */
    protected T t=null;
    
    /**
     * 父节点
     */
    protected DoubleTreeNode<T> parent = null;

    /**
     * 父节点编号
     */
    protected int parentid;

    /**
     * 左子树节点
     */   
    protected List<DoubleTreeNode<T>> left = new ArrayList<DoubleTreeNode<T>>();//左子树

    /**
     * 右子树节点
     */
    protected List<DoubleTreeNode<T>> right = new ArrayList<DoubleTreeNode<T>>();//右子树

    /**
     * 计算有多少个子节点
     * @return
     */
    protected int getNum() {	
	int num = 1;
	for(DoubleTreeNode<T> node:left) {
	    num+=node.getNum();
	}
	for(DoubleTreeNode<T> node:right) {
	    num+=node.getNum();
	}
	return num;
    }
    
    /**
     * 顺延右子节点方法
     * @return
     */
    protected DoubleTreeNode<T> cooNext() {
	if(right.size()>0) {
	    Collections.sort(right, new Comparator<DoubleTreeNode<T>>(){

		@Override
		public int compare(DoubleTreeNode<T> o1, DoubleTreeNode<T> o2) {
		    if(o1.getNum()>o2.getNum())
			return 1;
		    else if(o1.getNum()==o2.getNum())
			return 0;
		    else
			return -1;
			
		}	
	    });
	    return right.get(0);
	} else
	    return null;
    }
    
    /**
     * 寻找主干元素
     * @return
     */
    protected DoubleTreeNode<T> getMainElement() {
	DoubleTreeNode<T> coo = cooNext();
	return coo!=null?(this.getNum()>coo.getNum()?this:coo):coo;
    }
    
    /**
     * 计算有多少个子节点
     * @return
     */
    protected int getLeftNum() {	
	int num = 1;
	for(DoubleTreeNode<T> node:left) {
	    num+=node.getNum();
	}
	return num-1;
    }
    
    /**
     * 计算有多少个子节点
     * @return
     */
    protected int getRightNum() {	
	int num = 1;
	for(DoubleTreeNode<T> node:right) {
	    num+=node.getNum();
	}
	return num-1;
    }
    
    protected int getLeftSize() {
	return left.size();
    }
    
    protected int getRightSize() {
	return right.size();
    }

    protected void putNodeLeft(DoubleTreeNode<T> node) {
	node.parent = this;
	node.parentid = this.id;
	left.add(node);
	sortCollections(left);
    }

    protected void putNodeRight(DoubleTreeNode<T> node) {
	node.parent = this;
	node.parentid = this.id;
	right.add(node);
	sortCollections(right);
    }

    public List<DoubleTreeNode<T>> getLeft() {
	sortCollections(left);
	return left;
    } 
    
    public List<DoubleTreeNode<T>> getRight() {
	sortCollections(right);
	return right;
    } 
    
    public DoubleTreeNode<T> getParent() {
	return parent;
    }
    
    public T getElement() {
	return t;
    }
    
    public void sortCollections(List<DoubleTreeNode<T>> col) {
	Collections.sort(col, new Comparator<DoubleTreeNode<T>>() {

	    @Override
	    public int compare(DoubleTreeNode<T> o1, DoubleTreeNode<T> o2) {
		// TODO Auto-generated method stub
		if(o1.id<=o2.id)
		    return -1;
		else
		    return 1;
	    }
	    
	});
    }
    
    /**
     * 根据id寻找符合节点
     * @param id
     * @return
     */
    protected DoubleTreeNode<T> find(int id) {
	DoubleTreeNode<T> temp = null;
	if(this.id == id)
	    return this;
	for(DoubleTreeNode<T> node:left) {
	    if((temp=node.find(id))!=null) {
		return temp;
	    }
	}
	
	for(DoubleTreeNode<T> node:right) {
	    if((temp=node.find(id))!=null) {
		return temp;
	    }
	}
	return temp;
    }
    

    
    
    //    //获取元素的个数
    //    protected int getElementsAmount(DoubleTreeNode<T> dt) {
    //	if(dt.left!=null)
    //	    getElementsAmount(dt.left);
    //	if(dt.right!=null)
    //	    getElementsAmount(dt.right);
    //	num++;
    //	return num;
    //    }
    //
    //    /**
    //     * 评分标记
    //     * @param mark
    //     */
    //    protected void setMark(double mark) {
    //	this.mark = mark;
    //    }
    //
    //    /**
    //     * 获取当前节点(包含其子节点)的总评分
    //     * @param temp 
    //     * @return
    //     */
    //    public double getMark(double temp) {
    //	temp+=this.getAssDeepResult();
    //	if(getLeft()!=null)
    //	    temp = left.getMark(temp);
    //	if(getRight()!=null)
    //	    temp = right.getMark(temp);
    //	return temp;
    //    }
    //
    //    /**
    //     * 获取当前节点是否有孩子节点，即是否为叶子节点
    //     * @return
    //     */
    //    public boolean hasChildren() {
    //	if(left!=null)
    //	    return true;
    //	if(right!=null)
    //	    return true;
    //	return false;
    //    }
    //
    //    /**
    //     * 获取当前节点的节点评分数组
    //     * @return
    //     */
    //    public List<Double> getAssess() {
    //	assessArray.add(getAssDeepResult());
    //	if(getLeft()!=null)
    //	    left.getAssess();
    //	if(getRight()!=null)
    //	    right.getAssess();
    //	return assessArray;
    //    }
    //
    //    /**
    //     * 计算当前单节点评分(不考虑深度)
    //     * @return
    //     */
    //    public double getAssDeepResult(){
    //	return factor*assess/(getDeep()*getDeep());
    //    }
    //    
    //    public double getNodeAssess() {
    //	return assess;
    //    }
    //
    //    /**
    //     * 获取当前节点信息
    //     * @return
    //     */
    //    public DoubleTreeNode<T> getNode(){
    //	return this;
    //    }
    //
    //    /**
    //     * 获取当前左节点信息
    //     */
    //    public DoubleTreeNode<T> getLeft() {
    //	return left;
    //    }
    //
    //    /**
    //     * 获取当前右节点
    //     * @return
    //     */
    //    public DoubleTreeNode<T> getRight() {
    //	return right;
    //    }
    //
    //    /**
    //     * 根据id寻找节点
    //     * @param id
    //     * @return
    //     */
    //    public DoubleTreeNode<T> find(int id) {
    //	DoubleTreeNode<T> temp = null;
    //	if(this.id==id) {
    //	    return this;
    //	}
    //	if(temp==null && left!=null) {
    //	    temp = left.find(id);
    //	}
    //	if(temp==null && right!=null)
    //	    temp = right.find(id);
    //	return temp;
    //    }
    //    
    //    public T getElement() {
    //	return t;
    //    }
    //    
    //    //通过parentid,设定乘积因子
    //    public void setFactor(DoubleTreeNode<T> node) {
    //	if(node==null)
    //	    factor=1.0;
    //	else
    //	    factor=node.factor*0.9;
    //    }
    //    
    //    /**
    //     * 查看子节点个数
    //     * @return
    //     */
    //    public int getNum() {
    //	Iterator it = new Iterator(this);
    //	return it.size()-1;
    //    }
    //       
    //    public void draw(Graphics g) {
    //    }
}
