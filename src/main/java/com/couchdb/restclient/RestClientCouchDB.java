package com.couchdb.restclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * Cliente REST do Couchdb. Realiza chamadas a um servidor do CouchDB,
 * recuperando o conteúdo da resposta.
 *
 * @author giuliana.bezerra
 *
 */
public class RestClientCouchDB {
	private static String APPLICATION_JSON = "application/json";
	private static String HTTP_ERROR_CODE = "HTTP error code : ";

	public static String executeGetCall(CouchDB couchDB) {
		ClientResponse response = getWebResource(couchDB).accept(APPLICATION_JSON).get(ClientResponse.class);
		validateGetResponse(response);
		return getOutputFromResponse(response);
	}

	public static String executePutCall(CouchDB couchDB, String json) {
		ClientResponse response = getWebResource(couchDB).accept(APPLICATION_JSON).type(APPLICATION_JSON)
				.put(ClientResponse.class, json);
		validatePutResponse(response);
		return getOutputFromResponse(response);
	}

	private static String getOutputFromResponse(ClientResponse response) {
		String output = response.getEntity(String.class);
		System.out.println(output);
		return output;
	}

	private static WebResource getWebResource(CouchDB couchDB) {
		Client client = Client.create();
		client.addFilter(
				new HTTPBasicAuthFilter(couchDB.getCredentials().getLogin(), couchDB.getCredentials().getSenha()));
		return client.resource(couchDB.getURLCompleta());
	}

	private static void validateGetResponse(ClientResponse response) {
		if (isInvalidStatusForGet(response))
			throw new RuntimeException(HTTP_ERROR_CODE + response.getStatus());
	}

	private static boolean isInvalidStatusForGet(ClientResponse response) {
		return response.getStatus() != 200 && response.getStatus() != 404;
	}

	private static void validatePutResponse(ClientResponse response) {
		if (isInvalidStatusForPut(response))
			throw new RuntimeException(HTTP_ERROR_CODE + response.getStatus());
	}

	private static boolean isInvalidStatusForPut(ClientResponse response) {
		return response.getStatus() != 201;
	}
}
