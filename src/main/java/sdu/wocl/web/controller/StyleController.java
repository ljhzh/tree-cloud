package sdu.wocl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sdu.wocl.web.bean.Style;
import sdu.wocl.web.service.StyleService;

@Controller
@RequestMapping("/trecloud")
public class StyleController {

    @Autowired
    private StyleService styleService;

    @RequestMapping("/style/list")
    @ResponseBody
    public Page<Style> getStyle(@RequestParam(value="page",defaultValue="0")Integer page,@RequestParam(value="size",defaultValue="20") Integer size) {
	return styleService.findAllContext(page, size);
    }

}
