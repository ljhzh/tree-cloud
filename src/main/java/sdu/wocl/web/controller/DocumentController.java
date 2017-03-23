package sdu.wocl.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sdu.wocl.algorithm.DataView;
import sdu.wocl.algorithm.view.XYPoint;
import sdu.wocl.dataFactory.entity.Article;
import sdu.wocl.dataFactory.entity.SentenceContainer;
import sdu.wocl.dataFactory.entity.wordtree.WordTreeMessage;
import sdu.wocl.dataSource.server.DataSourceManager.ListenerSelector;
import sdu.wocl.tool.DocumentCounter.Counter;
import sdu.wocl.web.service.DocumentService;

@Controller
@RequestMapping("/trecloud")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @RequestMapping(value="/save",method=RequestMethod.POST)
    public String saveSubmitForm(@ModelAttribute Article document,Model model) {
	documentService.saveDocument(document.getDoc_title(), document.getType(), document.getContext());
	return "document";
    }
    
    @RequestMapping(value="/document/dataView",method=RequestMethod.GET)
    @ResponseBody
    public List<XYPoint> getPoints(@RequestParam("type") String path) throws InstantiationException, IllegalAccessException {
	return DataView.DocumentCollectorPath(path).getAdapter().
		setType(path).
		showing().
		getPointList();
    }
    
    @RequestMapping(value="/save/listen",method=RequestMethod.GET)
    @ResponseBody
    public List<ListenerSelector> getlistening(Model model) {
	Map map = model.asMap();
	if(map.get("setListen")!=null) {
	    return (List<ListenerSelector>) map.get("listener");
	} 
	return null;
    }

    @RequestMapping(value="/index",method=RequestMethod.GET)
    public String saveSubmit(Model model) {
	model.addAttribute("document",new Article());
	return "index";
    }
    
    @RequestMapping(value="/resporitory",method=RequestMethod.GET)
    public String resporitory(Model model) {
	model.addAttribute("document",new Article());
	return "docResporitory";
    }

    @ResponseBody
    @RequestMapping(value="/document/listType", method=RequestMethod.GET) 
    public String[] documentList(Model model) {
	return documentService.getTypes();
    }

    @ResponseBody
    @RequestMapping(value="/document/all")
    public List<Article> getdocumentNames(@RequestParam("type") String type,Model model) {   
	return documentService.getDocumentsName(type);
    }
    
    @RequestMapping(value="/document/message")
    @ResponseBody
    public List<WordTreeMessage> getDocumentsSentences(@RequestParam("title") String documentTitle,Model model) {
	SentenceContainer sc = documentService.getSentenceByDocument(documentTitle);
	return sc.getMessages();
    }
    
    @RequestMapping(value="/document/base")
    @ResponseBody
    public List<Counter> getDocumentsbase() {
	return documentService.getDocumentBase();
    }
    
    @RequestMapping(value="/document/mkbase")
    @ResponseBody
    public boolean createBase(@RequestParam("base") String base) {
	List<String> names = documentService.getDocumentBaseName();
	for (String name : names) {
	    if(base==name) {
		return false;
	    }
	}
	return documentService.setBorningBase(base);
    }
    
    @RequestMapping(value="/document/rmbase")
    @ResponseBody
    public boolean removeBase(@RequestParam("base") String base) {
	return documentService.removeBase(base);
    }
}
