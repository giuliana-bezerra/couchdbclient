package com.couchdb.restclient;

/**
 * Representa um atributo de um documento do CouchDB que possui valor e nome.
 * 
 * @author giuliana.bezerra
 *
 */
public class CouchDBAttribute {
	public String name;
	public String value;

	public CouchDBAttribute(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
