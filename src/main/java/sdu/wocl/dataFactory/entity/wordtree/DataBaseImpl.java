package sdu.wocl.dataFactory.entity.wordtree;

import java.util.List;

public interface DataBaseImpl {
    /**
     * 数据库取孩子节点
     * @return
     */
    List<WordTreeNode> getLeftNode();
    
    List<WordTreeNode> getRightNode();
}
