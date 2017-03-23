package sdu.wocl.web.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name="context")
public class Context {
    
    @Id
    @GeneratedValue
    private int contextid;
    
    @Column
    private String type;
    
    @Column
    private String title;
    
    @Column
    private String context;
    
    @Column(name="iscollected")
    private int iscollected;
    
    @Override
    public String toString() {
        if (StringUtils.isNotBlank(context)&&context.length()>100){
            //方便查看截断下
            context = StringUtils.substring(context,0,100)+"...";
        }
        return ToStringBuilder.reflectionToString(this);
    }

    public int getContextid() {
        return contextid;
    }

    public void setContextid(int contextid) {
        this.contextid = contextid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context.trim();
    }

    public int getIscollected() {
        return iscollected;
    }

    public void setIscollected(int iscollected) {
        this.iscollected = iscollected;
    }

    
}
