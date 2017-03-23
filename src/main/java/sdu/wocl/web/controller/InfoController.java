package sdu.wocl.web.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sdu.wocl.dataFactory.entity.Result;
import sdu.wocl.dataFactory.entity.wordtree.tools.DatabaseTool;
 
@Controller
@RequestMapping("/trecloud")  
public class InfoController {  

    private final static Logger logger = LoggerFactory.getLogger(InfoController.class);
    
    @RequestMapping("/window")
    public String index(Map<String, Object> model) {
	return "index";
    }
    
    @RequestMapping("/analyse")
    public String analyse(Map<String,Object> model) {
	return "analyse";
    }
    
    @RequestMapping("/search")
    public String search(Map<String,Object> model) {
	return "search";
    }
    
    @RequestMapping("/chart")
    public String chart() {
	return "chart";
    }
    
    @RequestMapping("/struct")
    public String struct() {
	return "struct";
    }
    
    @ResponseBody
    @RequestMapping(value="window/post", method=RequestMethod.POST)
    public List<Result> getResult(@RequestParam("style") String style) {
	logger.debug("Get style from style list and get the sentence whose mainy-struct-style equals "+style);
	return DatabaseTool.getResult(style);
    }
    
    @ResponseBody
    @RequestMapping(value="/styles",method=RequestMethod.GET)
    public String getStyle() {
	JSONArray ja = new JSONArray();
	logger.debug("get all the styles from the sentence which is the mainy-struct-style");
	ja.put(DatabaseTool.getStyles());
	return ja.toString();
    }
}
