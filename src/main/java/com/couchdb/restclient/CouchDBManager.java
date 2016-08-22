package com.couchdb.restclient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Gerenciador dos dados de um servidor CouchDB. Atualiza e adiciona atributos
 * dos documentos de um banco.
 * 
 * @author giuliana.bezerra
 *
 */
public class CouchDBManager {
	private static final String ID = "_id";
	private static final String REVISION = "_rev";
	private static List<String> UNMANAGEABLE_ATTRIBUTES = Arrays.asList(new String[] { ID, REVISION });
	private static final String NOT_FOUND_RESPONSE = "\"error\":\"not_found\"";

	private static Map<String, Object> documentJSONMap = new HashMap<String, Object>();

	/**
	 * Cria ou atualiza o documento informado com seus atributos. Para isso, é
	 * necessário verificar se o documento existe no CouchDB e então recuperar o
	 * seu conteúdo para permitir a atualização, uma vez que o CouchDB não
	 * permite atualizações parciais.
	 * 
	 * @param couchDB
	 * @param couchDBAttribute
	 */
	public static void manageDocumentAttribute(CouchDB couchDB, CouchDBAttribute couchDBAttribute) {
		documentJSONMap = new HashMap<String, Object>();
		String documentJSON = RestClientCouchDB.executeGetCall(couchDB);

		if (isFound(documentJSON))
			loadDocument(couchDB, documentJSON);
		updateDocument(couchDB, couchDBAttribute);
	}

	private static void loadDocument(CouchDB couchDB, String documentJSON) {
		loadJsonMap(couchDB, documentJSON);
		couchDB.setRevision(documentJSONMap.get(REVISION).toString());
		String historyJson = RestClientUtil.convertToJSON(getHistoryJsonMap(documentJSON));
		couchDB.setHistoryJson(historyJson);
	}

	private static void loadJsonMap(CouchDB couchDB, String documentJSON) {
		documentJSONMap = RestClientUtil.convertToJSONMap(documentJSON);
		removeUnmanageableAttributes();
	}

	private static void updateDocument(CouchDB couchDB, CouchDBAttribute couchDBAttribute) {
		manageCouchDBAttribute(documentJSONMap, couchDBAttribute);
		RestClientCouchDB.executePutCall(couchDB, RestClientUtil.convertToJSON(documentJSONMap));
	}

	private static Map<String, Object> getHistoryJsonMap(String documentJSON) {
		return RestClientUtil.convertToJSONMapFiltered(documentJSON, UNMANAGEABLE_ATTRIBUTES);
	}

	private static void removeUnmanageableAttributes() {
		documentJSONMap.remove(ID);
	}

	private static void manageCouchDBAttribute(Map<String, Object> documentoJSONMap,
			CouchDBAttribute couchDBAttribute) {
		for (Entry<String, Object> entryDocumentJSON : documentoJSONMap.entrySet()) {
			if (entryDocumentJSON.getKey().equals(couchDBAttribute.getName())) {
				updateAttribute(entryDocumentJSON, couchDBAttribute);
				return;
			}
		}
		createAttribute(documentoJSONMap, couchDBAttribute);
	}

	private static void createAttribute(Map<String, Object> documentoJSONMap, CouchDBAttribute couchDBAttribute) {
		documentoJSONMap.put(couchDBAttribute.getName(), couchDBAttribute.getValue());
	}

	private static void updateAttribute(Entry<String, Object> entryDocumentJSON, CouchDBAttribute couchDBAttribute) {
		entryDocumentJSON.setValue(couchDBAttribute.getValue());
	}

	/**
	 * Cria ou atualiza o banco do CouchDB solicitado.
	 * 
	 * @param couchDB
	 */
	public static void manageDatabase(CouchDB couchDB) {
		String response = RestClientCouchDB.executeGetCall(couchDB);
		if (isNotFound(response))
			RestClientCouchDB.executePutCall(couchDB, null);
	}

	private static boolean isNotFound(String response) {
		return response.contains(NOT_FOUND_RESPONSE);
	}

	private static boolean isFound(String response) {
		return !isNotFound(response);
	}

}
