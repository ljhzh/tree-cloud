package sdu.wocl.web.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdu.wocl.web.bean.Context;
import sdu.wocl.web.service.ContextService;
import sdu.wocl.web.service.DocumentService;

public class DocumentRunnable implements Runnable {

    private DocumentService documentService = null;
    private ContextService contextService = null;
    private static ThreadLocal<Context> threads = new ThreadLocal<Context>();
    final static Logger logger= LoggerFactory.getLogger(DocumentRunnable.class);

    public DocumentRunnable(DocumentService documentService,ContextService contextService) {
	this.documentService = documentService;
	this.contextService = contextService;
    }

    @Override
    public void run() {
	logger.info("线程:"+Thread.currentThread().getName()+"运行中.....");
    }

    public void poll(Context context) {
	if(status()) {
	    threads.set(context);
	    Context ctx =threads.get();
	    if(ctx!=null) {
		if(documentService.saveDocument(ctx.getTitle(), ctx.getType(), ctx.getContext()))
		    contextService.updateContext(ctx.getContextid());
		else
		    contextService.updateFailed(ctx.getContextid());
		threads.remove();
	    }
	}
    } 
    
    public boolean status() {
	if(threads.get()==null) {
	    return true;
	}
	return false;
    }
}
