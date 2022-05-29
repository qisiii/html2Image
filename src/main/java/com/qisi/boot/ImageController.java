package com.qisi.boot;

import com.qisi.image.Html2ImageUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : haokaichao
 * @date: 2022/5/4
 * @description:
 */
@RestController
@RequestMapping("/html2image")
public class ImageController {
    @GetMapping("/test")
    public String test() throws Exception {
        Html2ImageUtil.xhtmlrenderer(Html2ImageUtil.html);
        return "success";
    }
}
