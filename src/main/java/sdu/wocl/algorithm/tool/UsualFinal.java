package sdu.wocl.algorithm.tool;

import java.lang.reflect.Field;

/**
 * 算法的常量设置
 * @author ljh_2015
 *
 */
public class UsualFinal {

    public static final int ATT = 1; 
    public static final int QUN = 2;
    public static final int COO = 3;
    public static final int APP = 4;
    public static final int LAD = 5;
    public static final int RAD = 6;
    public static final int VOB = 7;
    public static final int POB = 8;
    public static final int SBV = 9;
    public static final int SIM = 10;
    public static final int VV = 11;
    public static final int CNJ = 12;
    public static final int MT = 13;
    public static final int IS = 14;
    public static final int ADV = 15;
    public static final int CMP = 16;
    public static final int DE = 17;
    public static final int DI = 18;
    public static final int DEI = 19;
    public static final int BA = 20;
    public static final int BEI = 21;
    public static final int IC = 22;
    public static final int DC = 23;
    public static final int DBL = 24;
    public static final int FOB = 25;
    public static final int IOB = 26;

    public static final int HED = 100;

    public static final int getInteger(String rel) throws IllegalArgumentException, IllegalAccessException {

	Field[] fields = UsualFinal.class.getDeclaredFields();
	for (Field field : fields) {
	    if(field.getName().equals(rel.toUpperCase())) {
		return field.getInt(rel);
	    }
	}

	return -1;
    }
    
    public static final String getString(int value) throws IllegalArgumentException, IllegalAccessException {
	Field[] fields = UsualFinal.class.getDeclaredFields();
	for (Field f : fields) {
	    if(getInteger(f.getName())==value) {
		return f.getName();
	    }
	}
	return "null";
    }
}
