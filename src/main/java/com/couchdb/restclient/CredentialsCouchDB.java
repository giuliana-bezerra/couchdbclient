package com.couchdb.restclient;

/**
 * Entidade que encapsula as credenciais necessárias para acessar o CouchDB.
 * 
 * @author giuliana.bezerra
 *
 */
public class CredentialsCouchDB {
	private String login;
	private String senha;

	public CredentialsCouchDB(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
