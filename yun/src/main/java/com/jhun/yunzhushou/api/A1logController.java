package com.jhun.yunzhushou.api;

import com.jhun.yunzhushou.tools.Tools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class A1logController {

    @RequestMapping("/a1log")
    public String a1log(String qsh) {
        System.out.println("a1log:qsh=" + qsh);

        if(qsh.charAt(0) == 'B'){
            qsh = "北" + qsh.substring(3);
        }
        else if(qsh.charAt(0) == 'N'){
            qsh = "南" + qsh.substring(3);
        }
        //返回电费查询历史
        String log1 = Tools.readTXT("./logi/queryPower/" + qsh + ".txt");
        //转换为标准json格式
        String log2 = log1.replace('R','r');
        String log3 = log2.replace("{","{\"");
        String log4 = log3.replace("=","\":\"");
        String log5 = log4.replace(", ","\", \"");
        String log6 = log5.replace("}","\"},");
        String log = "[" + log6.substring(0,log6.length() - 1) + "]";
        System.out.println(log);
        return log;
    }
}