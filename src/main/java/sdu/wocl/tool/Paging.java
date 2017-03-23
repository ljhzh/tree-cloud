package sdu.wocl.tool;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台分页机制
 * @author ljh_2015
 *
 */
public class Paging<T> {

    /**
     * 每页的容量
     */
    private int pagiNum;

    private List<T> allList;
    /**
     * 总页数
     */
    private List<PageContainer<T>> pages = new ArrayList<PageContainer<T>>();

    public Paging(int pagiNum,List<T> allList) {
	this.pagiNum = pagiNum;
	this.allList = allList;
	int sum = allList.size();
	for(int i=0;i<sum;i+=pagiNum) {
	    if(sum-i<pagiNum) {
		pages.add(new PageContainer<T>(allList.subList(i,sum)));
	    } else
		pages.add(new PageContainer<T>(allList.subList(i, i+pagiNum)));
	}
    }
    
    /**
     * 拿到页码
     * @return
     */
    public List<PageContainer<T>> getPages() {
	return pages;
    }

    /**
     * 分页操作接口
     * @author ljh_2015
     * @param <T>
     *
     */
    interface PagingHandle<T> {
	List<T> sort();
    }
    /**
     * 页容
     * @author ljh_2015
     *
     * @param <T>
     */
    class PageContainer<T> {

	List<T> list = null;

	public PageContainer(List<T> list) {
	    this.list = list; 
	}
    }

}
