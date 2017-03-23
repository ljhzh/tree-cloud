package sdu.wocl.algorithm.elements;

import sdu.wocl.algorithm.HandleAdapter;
import sdu.wocl.algorithm.data.ComputingData;
import sdu.wocl.algorithm.data.PreparedData;

public class DocumentFactory<P extends PreparedData,C extends ComputingData> {

    private HandleAdapter<P,C> handle;
    
    /**
     * 数据模式加载
     * @param p
     * @param c
     * @param d
     */
    public DocumentFactory(HandleAdapter<P,C> handle) {
	if(handle==null) {
	    this.handle = new HandleAdapter<P, C>();
	} else
	    this.handle = handle;
    }
    
    
    public void DatasLoading(P p) {
	handle.setDatas(p);
    }

    public void openFeedback() {
	handle.openFeedBack();
    }

    public void processing() {
	preparedProcessing();
	handle.processing();
	afterProcessing();
    }

    private void preparedProcessing() {
	System.out.println(getClass().getSimpleName()+" prepared loading----");
    }

    private void afterProcessing() {
	System.out.println(getClass().getSimpleName()+" after loading----");
    }
}
