package com.couchdb.restclient;

/**
 * Classe que encapsula a configuração de uma instância do CouchDB.
 * 
 * @author giuliana.bezerra
 *
 */
public class CouchDB {
	private String url;
	private String database;
	private String document;
	private CredentialsCouchDB credentials;
	private String historyJson;
	private String revision;

	public CouchDB(String url, String database, CredentialsCouchDB credentials) {
		this.url = url;
		this.database = database;
		this.credentials = credentials;
	}

	public CouchDB(String url, String database, String document, CredentialsCouchDB credentials) {
		this.url = url;
		this.database = database;
		this.document = document;
		this.credentials = credentials;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public CredentialsCouchDB getCredentials() {
		return credentials;
	}

	public void setCredentials(CredentialsCouchDB credentials) {
		this.credentials = credentials;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getHistoryJson() {
		return historyJson;
	}

	public void setHistoryJson(String historyJson) {
		this.historyJson = historyJson;
	}

	public String getURLCompleta() {
		if (document == null || document.isEmpty())
			return (url + "/" + database);
		else
			return (url + "/" + database + "/" + document);
	}
}
