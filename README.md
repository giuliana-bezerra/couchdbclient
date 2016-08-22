# couchdbclient

#### Table of Contents

1. [Overiview](#overview)
2. [Setup](#setup)
3. [Usage](#usage)
4. [Reference](#reference)
5. [Limitations](#limitations)
6. [Develpment](#development)

## Overview
This app configures a REST client for CouchDB which is responsible for
maintaining their attributes and documents. This client also automatically manages changes using a versioning strategy that keeps json data for each
version of the database.

## Setup
This app depends on:

- Java 6
- Maven 3.2.5

Follow the steps to execute the app:

1. Download the repository:
```
git clone git@www-git.prevnet:DIASRN/couchdbrestclient.git
```
2. Generate the jar with dependencies using Maven:
```
mvn package
```
3. Execute the generated jar:
```
cd target
java -jar restclient-0.0.1-SNAPSHOT-jar-with-dependencies.jar url user password database document attribute value
```

## Usage
The client can be used either to create or update data transparently. The program
receives 7 parameters respectivelly:

1. Server URL
2. Login
3. Password
4. Database name
5. Document name
6. Attribute name
7. Attribute value

If the attribute does not exist, the app will create it in the document.
Otherwise, its value will be updated to the value provided by parameter 7.
The app supports the following operations:

1. Database creation: Provide parameters 1, 2, 3, e 4
2. Document creation with attributes: Provide all parameters.
3. Attribute creation in existing document: Provide all parameters.
4. Attribute update in existing document: Provide all parameters.

For each operation above, the app generates a JSON contaning the database state
before applying the current operation. Couchdb databases keep this history
following the hierachy:

- Database: history + database name
- Document: Document name + revision
- Attributes: Revision and its JSON content

**OBS: Blank spaces are not allowed for database and document names.**

## Reference
See javadoc.

## Limitations
This app does not support bulk operations. Therefore, each execution updates
only one document at a time.

## Develpment
This app implements a base client that allows dinamic changes in CouchDB data.
Taking this into account, the implementation supports only document managment
operations and simple versioning. Contributions could be bulk
operation support and new versioning strategies following the interface `HistoryManager`.
