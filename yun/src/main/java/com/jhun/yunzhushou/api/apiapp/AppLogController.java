package com.jhun.yunzhushou.api.apiapp;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.tools.Tools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppLogController {

    //用于接收安卓服务器log信息的接口
    @RequestMapping(value = "/applog")
    public R AppLog(String log, String txt) {
        //记录
        Tools.logi("./logi/App" + txt, log);
        return R.ok();
    }
}
