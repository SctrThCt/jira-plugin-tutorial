package ru.teamlead.jira.plugins.tutorial.rest;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class TestData {
    public static final Boolean CHECKBOX_1 = false;
    public static final String NAME_1 = "Name1";
    public static final String SELECT_1 = "option1";

    public static Map<String, Object> getParams() {
        HashMap<String, Object> out = new HashMap<>();
        out.put("name", NAME_1);
        out.put("select", "1");
        out.put("checkbox", CHECKBOX_1);
        return out;
    }

    public static HashMap<String, Object> jsonStringToHashMap(String jsonString) {
        Gson gson = new Gson();
        Type type = new HashMap<String, Object>().getClass();
        HashMap<String, Object> out = gson.fromJson(jsonString, type);
        Integer id = Math.toIntExact(Math.round((Double) out.get("id")));
        out.remove("id");
        out.put("id", id);
        return out;
    }

    public static String[] getJsonStringArray(String jsonString) {
        return jsonString.replace("[", "")
                .replace("]", "")
                .split("\\{\\.\\*\\}");
    }

    public static HashMap<String, Object> getReferenceMap() {
        HashMap<String, Object> out = new HashMap<>();
        out.put("id", Integer.valueOf(1));
        out.put("name", NAME_1);
        out.put("select", "1");
        out.put("checkbox", CHECKBOX_1);
        out.put("selectName", SELECT_1);
        return out;
    }
}
