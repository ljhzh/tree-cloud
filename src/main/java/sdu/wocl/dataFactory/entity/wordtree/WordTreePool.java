package sdu.wocl.dataFactory.entity.wordtree;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import sdu.wocl.dataFactory.entity.wordtree.tools.DataBaseCommand;

public class WordTreePool {

    static Queue<WordTree> queue = new LinkedList<WordTree>();
    static Map<String,Integer> styleTable = StyleTable.getStyleTable();
    
    /**
     * 向队列添加一个元素
     * @param e
     * @return
     */
    public boolean poll(WordTree e) {
	return queue.offer(e);
    }
    
    /**
     * 移除队首元素，并返回
     * @return
     */
    public WordTree poll() {
	return queue.poll();
    }
    
    
    
    public void loadDatabase() {
	
    }
    
    class RunnableStorage implements Runnable {

	private WordTree wordTree;
	private DataBaseCommand dbc = new DataBaseCommand();
	
	public RunnableStorage(WordTree wordTree) {
	    this.wordTree = wordTree;
	}
	
	@Override
	public void run() {
	    try {
		List<String> sqls = wordTree.toSqlStatement();
		dbc.setCommands(sqls);
		dbc.excuteBatch();
		Thread.sleep(50);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	
    }
    /**
     * 拿到句式id
     * @param key
     * @return
     */
    private int getStyleId(String key) {
	return styleTable.get(key);
    }
}
