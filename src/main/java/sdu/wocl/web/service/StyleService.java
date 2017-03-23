package sdu.wocl.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sdu.wocl.web.bean.Style;
import sdu.wocl.web.dao.StyleRepository;

@Service
public class StyleService {
    
    private StyleRepository styleRepository;
    
    @Autowired
    public StyleService(StyleRepository styleRepository) {
	this.styleRepository = styleRepository;
    }
      
    public Page<Style> findAllContext(Integer page,Integer size) {
	Pageable pageRequest = new PageRequest(page,size,Sort.Direction.DESC,"count");
	Page<Style> pages = styleRepository.findAll(pageRequest);
	return pages;
    }
    
}
