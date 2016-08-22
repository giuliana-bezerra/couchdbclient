package com.couchdb.restclient;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Classe utilitária responsável por gerar as URLs utilizadas por um cliente
 * REST.
 * 
 * @author giuliana.bezerra
 *
 */
public class RestClientUtil {
	public static String convertToJSON(Map<String, Object> map) {
		Gson gson = new Gson();
		String json = gson.toJson(map);
		return json;
	}

	public static Map<String, Object> convertToJSONMap(String documentJSON) {
		java.lang.reflect.Type mapType = new TypeToken<Map<String, Object>>() {
		}.getType();
		Gson gson = new Gson();
		Map<String, Object> map = gson.fromJson(documentJSON, mapType);
		return map;
	}

	public static Map<String, Object> convertToJSONMapFiltered(String documentJSON, List<String> filters) {
		Map<String, Object> jsonMapFiltered = convertToJSONMap(documentJSON);
		for (String filter : filters) {
			if (jsonMapFiltered.containsKey(filter))
				jsonMapFiltered.remove(filter);
		}
		return jsonMapFiltered;
	}
}
