package sdu.wocl.dataFactory.entity;


/**
 * 标点类
 * @author ljh_2015
 *
 */
public class Wp extends Word {
    
    public Wp(int sentid,int id, String text, Pos pos, int parentid, String rel,int semid,String semrel) {
	super(sentid,id, text, pos, parentid, rel,semid,semrel);
    }

}
