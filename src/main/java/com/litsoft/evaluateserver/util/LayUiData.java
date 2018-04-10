package com.litsoft.evaluateserver.util;

import java.util.HashMap;
import java.util.List;

public class LayUiData extends HashMap<String, Object>{

    public static LayUiData data(Integer count, List<?> data){
        LayUiData r = new LayUiData();
        r.put("code", 0);
        r.put("msg", "");
        r.put("count", count);
        r.put("data", data);
        return r;
    }
}
