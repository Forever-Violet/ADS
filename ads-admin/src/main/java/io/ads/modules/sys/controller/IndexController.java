package io.ads.modules.sys.controller;

import io.ads.common.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页提示
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public Result<String> index(){
        String tips = "你好，ads-admin已启动，ads-ui，才能访问页面！";
        return new Result<String>().ok(tips);
    }
}
