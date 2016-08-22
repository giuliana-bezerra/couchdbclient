package com.couchdb.restclient;

/**
 * Interface que representa um gerenciador de histórico do CouchDB.
 * 
 * @author giuliana.bezerra
 *
 */
public interface HistoryManager {
	public void saveHistory(CouchDB couchDB);
}
