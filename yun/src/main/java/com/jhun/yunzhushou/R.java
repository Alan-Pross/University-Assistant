package com.jhun.yunzhushou;

import java.util.HashMap;
import java.util.Map;

//服务器返回类
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
    }

    public static R error() {
        R r = new R();
        r.put("code", 500);
        r.put("msg", "error");
        System.out.println(r.toString());
        return r;
    }

    public static R error(String msg) {
        R r = new R();
        r.put("code", 500);
        r.put("msg", msg);
        System.out.println(r.toString());
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        System.out.println(r.toString());
        return r;
    }

    public static R ok() {
        R r = new R();
        r.put("msg", "ok");
        System.out.println(r.toString());
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        System.out.println(r.toString());
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        System.out.println(r.toString());
        return r;
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
