package com.couchdb.restclient;

/**
 * Cliente REST do Couchdb responsável por manter os atributos de documentos dos
 * bancos de dados do CouchDB. O cliente também gera o histórico das mudanças
 * automaticamente mantendo a fotografia dos dados para a respectiva revisão. O
 * programa recebe 7 parâmetros: <br/>
 * <br/>
 * 
 * 1. URL do servidor <br/>
 * 2. Login <br/>
 * 3. Senha <br/>
 * 4. Nome do banco <br/>
 * 5. Nome do documento <br/>
 * 6. Nome do atributo <br/>
 * 7. Valor do atributo <br/>
 * <br/>
 * 
 * Se o atributo informado não existir, o mesmo é criado no documento. Caso
 * contrário, o valor dele é atualizado para o valor informado no parâmetro 7.
 * As seguintes operações são suportadas: <br/>
 * <br/>
 * 
 * 1. Criação de banco: Informar atributos 1, 2, 3, e 4 <br/>
 * 2. Criação de documento com atributos: Informar todos os atributos. <br/>
 * 3. Criação de atributos em documento existente: Informar todos os atributos.
 * <br/>
 * 4. Alteração de atributos em documento existente: Informar todos os
 * atributos. <br/>
 * <br/>
 * 
 * OBS: Não utilizar espaços em branco para os nomes dos bancos e documentos.
 * 
 * @author giuliana.bezerra
 *
 */
public class Main {
	private static final String ILLEGAL_ARGUMENT_MESSAGE = "Must inform at least 4 arguments!";

	private static CouchDB couchDB;
	private static HistoryManager historyManager = new CouchDBHistoryManager();

	public static void main(String[] args) {
		validateArguments(args);
		manageCouchDB(args);
	}

	private static void validateArguments(String[] args) {
		if (isInvalidArgs(args))
			throw new RuntimeException(ILLEGAL_ARGUMENT_MESSAGE);
	}

	private static void manageCouchDB(String[] args) {
		manageCouchDatabase(args);
		manageOptionalArgs(args);
		manageHistory();
	}

	private static void manageCouchDatabase(String[] args) {
		couchDB = new CouchDB(getURL(args), getDatabase(args), getCredentials(args));
		CouchDBManager.manageDatabase(couchDB);
	}

	private static void manageOptionalArgs(String[] args) {
		if (isManageDocumentAttribute(args))
			manageDocumentAttribute(args, getCredentials(args));
	}

	private static void manageHistory() {
		historyManager.saveHistory(couchDB);
	}

	private static boolean isManageDocumentAttribute(String[] args) {
		return isManageDocument(args) && isManageAttribute(args);
	}

	private static void manageDocumentAttribute(String[] args, CredentialsCouchDB credencials) {
		couchDB = new CouchDB(getURL(args), getDatabase(args), getDocument(args), credencials);
		CouchDBManager.manageDocumentAttribute(couchDB, new CouchDBAttribute(getAttribute(args), getValor(args)));
	}

	private static boolean isInvalidArgs(String[] args) {
		return args.length < 4;
	}

	private static boolean isManageAttribute(String[] args) {
		return (args.length == 7);
	}

	private static boolean isManageDocument(String[] args) {
		return (args.length >= 5);
	}

	public static CredentialsCouchDB getCredentials(String[] args) {
		return new CredentialsCouchDB(getUser(args), getPassword(args));
	}

	public static String getURL(String[] args) {
		return args[0];
	}

	public static String getUser(String[] args) {
		return args[1];
	}

	public static String getPassword(String[] args) {
		return args[2];
	}

	public static String getDatabase(String[] args) {
		return args[3];
	}

	public static String getDocument(String[] args) {
		return args[4];
	}

	public static String getAttribute(String[] args) {
		return args[5];
	}

	public static String getValor(String[] args) {
		return args[6];
	}
}
