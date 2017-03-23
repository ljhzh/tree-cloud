package sdu.wocl.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sdu.wocl.web.bean.Context;
import sdu.wocl.web.service.ContextService;
import sdu.wocl.web.service.DocumentService;
import sdu.wocl.web.task.DocumentTask;

@Controller
@RequestMapping("/trecloud/context")
public class ContextController {

    private ContextService contextService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    public ContextController(ContextService contextService) {
	this.contextService = contextService;
    }

    @RequestMapping(value="/get",method=RequestMethod.POST)
    @ResponseBody
    public Page<Context> getContexts(@RequestParam(value="page",defaultValue="0")Integer page,@RequestParam(value="size",defaultValue="30") Integer size) {
	return contextService.findAllContext(page/30,size);
    }

    @RequestMapping(value="/getUnRecord",method=RequestMethod.GET)
    @ResponseBody
    public Page<Context> getContextsUnRecord(@RequestParam(value="page",defaultValue="0")Integer page,@RequestParam(value="size",defaultValue="30") Integer size) {
	return contextService.findAllContextUnRecord(page/30,size);
    }

    @RequestMapping(value="/saveIn",method=RequestMethod.POST)
    @ResponseBody
    public void saveIn(@RequestParam("ctxtid[]")int[] ids) {
	//修改数据库
	DocumentTask.init(documentService, contextService);
	for (int i = 0; i < ids.length; i++) {
	    Context ctx = contextService.updateContexting(ids[i]);
	    DocumentTask.getContext(ctx);
	}
    }

    @RequestMapping(value="/bastpe",method=RequestMethod.GET)
    @ResponseBody
    public List<String> getType() {
	//获取数据库的文档类型
	return contextService.getTypes();
    }

    @RequestMapping(value="/deletes")
    @ResponseBody
    public void delete(@RequestParam(value="array[]")String[] arrays) {
	for (int i = 0; i < arrays.length; i++) {
	    contextService.deleteContext(Integer.parseInt(arrays[i]));
	}
    }
}
