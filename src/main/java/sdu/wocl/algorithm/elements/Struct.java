package sdu.wocl.algorithm.elements;

/**
 * 定式结构
 * 凡是常出现的定式结构应该被存储起来
 * @author ljh_2015
 *
 */
public class Struct {
    
    private Integer[] struct;
	
    public Struct(Integer[] struct) {
	this.struct = struct;
    }
    
    public Integer[] getStruct() {
	return struct;
    }
    
    @Override
    public boolean equals(Object obj) {
	Struct s = (Struct)obj;
	Integer[] ints = s.getStruct();
	if(ints.length == struct.length) {
	    for(int i=0;i<ints.length;i++) {
		if(ints[i]!=struct[i]) 
		    return false;
	    }
	    return true;
	} else
	    return false;
    }
    
    @Override
    public String toString() {
	String result ="struct--";
	if(struct==null) {
	    return result+="NULL ";
	}
	for (int i = 0; i < struct.length; i++) {
	    result+=struct[i];
	    if(i!=struct.length-1)
		result+=" ";
	}
	return result;
    }
    
    
}
