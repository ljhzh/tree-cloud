package sdu.wocl.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sdu.wocl.dataFactory.entity.wordtree.WordTreeMessage;
import sdu.wocl.web.service.SentenceService;

@Controller
@RequestMapping("/trecloud")
public class SentenceController {

    @Autowired
    private SentenceService sentenceService;

    @ResponseBody
    @RequestMapping("/sentence/show")
    public List<WordTreeMessage> getMessages() {
	return sentenceService.SentenceShow();
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping("/sentence/styling")
    public MyPage getMessagesByStyle(@RequestParam("style") int styleid,@RequestParam("page") int page,Model model) throws SQLException {
	Map<String, Object> map = model.asMap();
	List<String> results = null;
	if(model.containsAttribute("styleid") && (Integer)map.get("styleid")==styleid) {
	    results=(List<String>) map.get("styling");
	} else {
	    results=sentenceService.SentenceStyleId(styleid);
	    System.out.println(results.size());
	    model.addAttribute("styling", results);
	    model.addAttribute("styleid", styleid);
	}
	MyPage show = new MyPage(page,20,results);
	show.setStyleid(styleid);
	return show;
    } 

    class MyPage {

	private int pageNum;
	private int totalElements;
	private int totalPages;
	private int num;
	private int styleid;
	private List<String> context;
	private boolean first;
	private boolean last;

	public MyPage(int page,int pageNum,List<String> refs) {
	    this.num = page;
	    this.pageNum = pageNum;
	    this.totalElements = refs.size();
	    this.totalPages = totalElements%pageNum==0?totalElements/pageNum:totalElements/pageNum+1;
	    if(num==1)
		first = true;
	    else
		first = false;
	    if(num==totalPages) 
		last = true;
	    else
		last = false;
	    int start = (page-1)*pageNum;
	    this.context = new ArrayList<String>();
	    for(int i=0;i<pageNum;i++) {
		context.add(refs.get(start+i));
	    }
	}

	public int getStyleid() {
	    return styleid;
	}

	public void setStyleid(int styleid) {
	    this.styleid = styleid;
	}

	public int getPageNum() {
	    return pageNum;
	}

	public int getTotalElements() {
	    return totalElements;
	}

	public int getTotalPages() {
	    return totalPages;
	}

	public int getNum() {
	    return num;
	}

	public List<String> getContext() {
	    return context;
	}

	public boolean isFirst() {
	    return first;
	}

	public boolean isLast() {
	    return last;
	}
    }

    @RequestMapping("/sentence")
    public String getSentences() {
	return "sentence";
    }

}
