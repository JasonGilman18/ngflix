package com.ngflix_api;

import java.util.HashMap;

public class JSON {
    
    private HashMap<String, Object> data;

    public JSON() {
        this.data = new HashMap<>();
    }

    public Object get(String key) {
        return data.get(key);
    }

    public void put(String key, Object val) {
        data.put(key, val);
    }

    public String toString() {
        return "IMPLEMENT TOSTRING TO RETURN A JSON FORMAT\n \\t is a tab?";
    }

}
