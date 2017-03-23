package sdu.wocl.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sdu.wocl.web.bean.Context;
import sdu.wocl.web.dao.ContextRepository;

@Service
public class ContextService {

    private final ContextRepository contextRepository;
    private final int FAILED = 3;
    private final int LOADING = 2;
    private final int SUCCESS = 1;
    private final int RESET = 0;

    @Autowired
    public ContextService(ContextRepository contextRepository) {
	this.contextRepository = contextRepository;
    }

    private Pageable createPageRequest(Integer page,Integer size) {
	return new PageRequest(page,size,Sort.Direction.DESC,"iscollected");
    }

    public Page<Context> findAllContext(Integer page,Integer size) {
	Pageable pageRequest = createPageRequest(page,size);
	Page<Context> pages = contextRepository.findAll(pageRequest);
	return pages;
    }
    
    public Page<Context> findAllContextUnRecord(Integer page,Integer size) {
	Pageable pageRequest = createPageRequest(page,size);
	Page<Context> pages = contextRepository.findAllUnRecord(pageRequest);
	return pages;
    }
    
    public Context findById(int id) {
	return contextRepository.findByContextId(id);
    }
    
    //标记录入
    public void updateContext(int id) {
	contextRepository.updateStatus(id, this.SUCCESS);
    }
    
    //正在录入
    public Context updateContexting(int id) {
	contextRepository.updateStatus(id, this.LOADING);;
	return findById(id);
    }
    
    //文档重置录入情况
    public void updateReset(int id) {
	contextRepository.updateStatus(id, this.RESET);
    }
    
    //录入失败
    public void updateFailed(int id) {
	contextRepository.updateStatus(id, this.FAILED);
    }
    
    //拿到类型
    public List<String> getTypes() {
	return contextRepository.findTypes();
    }
    
    //删除
    public void deleteContext(int id) {
	contextRepository.delete(contextRepository.findByContextId(id));
    }
}
