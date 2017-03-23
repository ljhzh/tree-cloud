package sdu.wocl.tool;

import java.util.List;

/**
 * 利用前段javascript分页的后台处理
 * 相当于用一个list将所有数据封装起来
 * 具体处理交给前端实现
 * @author ljh_2015
 *
 */
public class PrePaging<T> {

    private List<T> datas;
    
    public PrePaging(List<T> datas) {
	this.datas = datas;
    }
    
    public List<T> getData() {
	return datas;
    }
}
