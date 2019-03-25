package com.jhun.yunzhushou.api.apiweb;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.logic.Admin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LogAdminController {

    @RequestMapping("/logadmin")
    public R logadmin(String key) {
        Map<String, Object> map = new HashMap<>();

        if (key.equals(Admin.key)) {
            try {
                String log = new String(Files.readAllBytes(Paths.get("./logi/admin.txt")));
                map.put("log", log);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return R.error();
        }
        return R.ok(map);
    }
}