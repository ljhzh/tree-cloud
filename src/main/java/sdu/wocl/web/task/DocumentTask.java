package sdu.wocl.web.task;

import java.util.LinkedList;
import java.util.Queue;

import sdu.wocl.web.bean.Context;
import sdu.wocl.web.service.ContextService;
import sdu.wocl.web.service.DocumentService;

public class DocumentTask {
    
    private static Queue<Context> contexts = new LinkedList<Context>();
    //线程池大小
    private static int size=5;
    
    private static DocumentRunnable[] threads = new DocumentRunnable[size];
    
    public static void init(DocumentService documentService,ContextService contextService) {
	for(int i=0;i<threads.length;i++) {
	    threads[i]=new DocumentRunnable(documentService,contextService);
	    new Thread(threads[i]).start();
	}
	//每隔30秒进行一次轮询
	new Thread(new Runnable(){

	    @Override
	    public void run() {
		while(true) {
		    for (int i = 0; i < threads.length; i++) {
			if(threads[i].status()) {
			    Context ctx = contexts.poll();
			    threads[i].poll(ctx);
			    /*
			     * 
			     */
			} else
			    continue;
		    }
		    //每隔30s一次轮询
		    try {
			Thread.sleep(10*1000);
		    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
	    }
	    
	}).start();
    }
    
    public static void getContext(Context c) {
	contexts.offer(c);
    }
}
