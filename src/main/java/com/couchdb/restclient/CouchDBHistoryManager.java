package com.couchdb.restclient;

/**
 * Manager responsável por gerar o histórico das alterações feitas nos
 * documentos do CouchDB. Os históricos são gerados como bancos no CouchDB
 * seguindo a seguinte hierarquia: <br/>
 * <br/>
 * 
 * - Database: history + nome do banco <br/>
 * - Document: Nome do documento + revisão <br/>
 * - Atributos: Revisão e o respectivo JSON do documento <br/>
 * 
 * @author giuliana.bezerra
 *
 */
public class CouchDBHistoryManager extends CouchDBManager implements HistoryManager {
	private static final String HISTORY_DATABASE_PREFIX = "history";

	public void saveHistory(CouchDB couchDB) {
		CouchDB couchDBHistory = getCouchDBHistory(couchDB);
		manageDatabase(couchDBHistory);
		manageDocument(couchDB, couchDBHistory);
	}

	private void manageDocument(CouchDB couchDB, CouchDB couchDBHistory) {
		couchDBHistory.setDocument(couchDB.getDocument() + couchDB.getRevision());
		if (hasRevision(couchDB))
			manageDocumentAttribute(couchDBHistory,
					new CouchDBAttribute(couchDB.getRevision(), couchDB.getHistoryJson()));
	}

	private boolean hasRevision(CouchDB couchDB) {
		return couchDB.getRevision() != null && !couchDB.getRevision().isEmpty();
	}

	public static CouchDB getCouchDBHistory(CouchDB couchDB) {
		CouchDB couchDBHistory = new CouchDB(couchDB.getUrl(), HISTORY_DATABASE_PREFIX + couchDB.getDatabase(),
				couchDB.getCredentials());
		return couchDBHistory;
	}
}
