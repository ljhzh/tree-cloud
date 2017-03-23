package sdu.wocl.dataFactory.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 词性枚举类
 * 构造函数包含初始评分值
 * @author ljh_2015
 *
 */
public enum Pos {
    a(1.02),//形容词
    b(1.04),//区别词
    c(1.07),//连词
    d(1.1),//副词
    e(1.15),//̾叹词
    g(1.21),//语素字
    h(1.28),//前接成分
    i(1.36),//习用语
    j(1.45),//简称
    k(1.55),//后接成分
    m(1.66),//数词 
    /**
     * 名词系列
     */
    n(1),//名词
    nd(1.001),//方位名词
    nh(1.003),//人名
    ni(1.006),//团体、机构、组织的专名
    nl(1.01),//处所名词
    ns(1.015),//地名
    nt(1.021),//时间名词
    nz(1.028),//其它专名
    o(0.99),//拟声词
    p(1.78),//介词
    q(1.91),//量词
    r(0.97),//代词
    u(0.95),//助词
    v(3),//动词
    wp(0.2),//标点
    ws(0.5),//字符串
    x(0.1),//非语素字
    oth(1.0);
    
    //评分制
    private double index;
    
    private static List<String> other = new ArrayList<String>();

    Pos(double index) {
	this.index = index;
    }

    public double getIndex() {
	return this.index;
    }

    public static Pos getPos(String name) {
	for(Pos p:Pos.values()) {
	    if(p.name().equalsIgnoreCase(name))
		return p;
	}
	other.add(name);
	return Pos.oth; 
    }
    
    public static Pos getPos(double value) {
	for(Pos p:Pos.values()) {
	    if(p.getIndex()==value)
		return p;
	}
	
	return null;
    }
    
    public static void showOther() {
	for(String s:other) {
	    System.out.print(s+" ");
	}
	System.out.println(" ");
    }
}
