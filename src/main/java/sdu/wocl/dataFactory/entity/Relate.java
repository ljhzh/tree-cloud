package sdu.wocl.dataFactory.entity;

/**
 * 依存关系枚举类
 * @author ljh_2015
 *
 */
public enum Relate {
    HED(2),//核心HED
    ATT(0.9),//定中关系ATT（attribute）
    QUN(0.9),//数量关系QUN（quantity）
    PUN(0.5),
    LAD(0.9),//前附加关系LAD（left adjunct）
    RAD(0.9),//后附加关系RAD（right adjunct）
    VOB(1),//动宾关系VOB（verb-object）
    POB(0.9),//介宾关系POB（preposition-object）
    SBV(1),//主谓关系SBV（subject-verb）
    CNJ(1.5),//关联结构CNJ（conjunctive）
    MT(1.2),//语态结构MT（mood-tense）
    IS(1.2),//独立结构IS（independent structure）
    ADV(0.9),//状中结构ADV（adverbial）
    CMP(0.9),//动补结构CMP（complement）

    DE(0.8),//“的”字结构DE
    DI(0.8),//“地”字结构DI
    DEI(0.8), //“得”字结构DEI
    BA(0.8), //“把”字结构BA
    BEI(0.8), //“被”字结构BEI

    IC(2), //独立分句IC（independent clause）
    DC(1.9), //依存分句DC（dependent clause）

    SIM,//比拟关系SIM（similarity）
    APP,//同位关系APP（appositive）
    COO,//并列关系COO（coordinate）
    VV;//连谓结构VV（verb-verb）

    /**
     * 依存关系评分
     */
    private double relate;

    Relate() {

    }

    Relate(double relate) {
	this.relate = relate;
    }

    public void setRelateValue(double relate) {
	this.relate = relate;
    }

    public double getRelateValue() {
	return relate;
    }

    public static double getRelate(String name) {
	for(Relate r:Relate.values()) {
	    if(r.name().equalsIgnoreCase(name))
		return r.getRelateValue();
	}
	return 0; 
    }
    
    public static boolean isleft(String relate){
	if(relate.equalsIgnoreCase("SIM") && relate.equalsIgnoreCase("VV") && relate.equalsIgnoreCase("APP") && relate.equalsIgnoreCase("COO"))
	    return false;
	return true;
    }
}
