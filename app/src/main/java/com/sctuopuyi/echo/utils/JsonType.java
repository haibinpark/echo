package com.sctuopuyi.echo.utils;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class JsonType {
	public static Map jsonStr2Map(String str) {
		String sb = str.substring(0, str.length() - 1);
		String[] name = sb.split("\\\",\\\"");
		String[] nn = null;
		Map map = new TreeMap();
		for (int i = 0; i < name.length; i++) {
			nn = name[i].split("\\\":\\\"");
			map.put(nn[0], nn[1]);
		}
		return map;
	}

	public static String map2JsonStr(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return "null";
		}
		String jsonStr = "{";
		Set<?> keySet = map.keySet();
		for (Object key : keySet) {
			jsonStr += "\"" + key + "\":\"" + map.get(key) + "\",";
		}
		jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		jsonStr += "}";
		return jsonStr;
	}


}
