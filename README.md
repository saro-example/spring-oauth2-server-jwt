# spring oauth2 server jwt

# STEP 1
## 1.1 create account data
```
CREATE TABLE account (
	no bigserial NOT NULL,
	account varchar(64) NOT NULL,
	password varchar(512) NOT NULL,
	CONSTRAINT account_pk PRIMARY KEY (no)
);

CREATE UNIQUE INDEX account_account ON account USING btree (account);

CREATE TABLE account_role (
	no bigint NOT NULL,
	role varchar(64) NOT NULL,
	CONSTRAINT account_role_pk PRIMARY KEY (no, role)
);

-- test data
-- d760688da522b4dc3350e6fb68961b0934f911c7d0ff337438cabf4608789ba94ce70b6601d7e08a279ef088716c4b1913b984513fea4c557d404d0598d4f2f1 -> sha3_512('1234')
insert into account values (nextval('account_no_seq'), 'saro', 'd760688da522b4dc3350e6fb68961b0934f911c7d0ff337438cabf4608789ba94ce70b6601d7e08a279ef088716c4b1913b984513fea4c557d404d0598d4f2f1');

insert into account_role values (currval('account_no_seq'), 'master');
insert into account_role values (currval('account_no_seq'), 'admin');
```
## 1.2 crate oauth table
```
create table oauth_client_details (
	client_id VARCHAR(256) PRIMARY KEY,
	resource_ids VARCHAR(256),
	client_secret VARCHAR(256),
	scope VARCHAR(256),
	authorized_grant_types VARCHAR(256),
	web_server_redirect_uri VARCHAR(256),
	authorities VARCHAR(256),
	access_token_validity INTEGER,
	refresh_token_validity INTEGER,
	additional_information VARCHAR(4096),
	autoapprove VARCHAR(256)
);

-- testdata
-- 7d9d27f1ad2d9ce85850a4e61298f5e5be3f566fc20a7b54d19c1197e7a509094d4992195a5fb86e21bc10cd77223211c2c5c0beede19eb91cdd9d181eb81b9a -> sha3_512('sec')
INSERT INTO oauth_client_details
(client_id, resource_ids, client_secret, "scope", authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES('saro', 'res-saro', '7d9d27f1ad2d9ce85850a4e61298f5e5be3f566fc20a7b54d19c1197e7a509094d4992195a5fb86e21bc10cd77223211c2c5c0beede19eb91cdd9d181eb81b9a', 'none', 'password,refresh_token', NULL, 'none', 3600, 3600, NULL, NULL);

```
# STEP 2
## set application.yml
```
db.oauth:
  driverClassName: org.postgresql.Driver
  jdbcUrl: jdbc:postgresql://192.168.0.40:5432/dbname?charSet=UTF-8&prepareThreshold=1
  username: username
  password: password
  cachePrepStmts: true
  maximumPoolSize: 50
  minimumIdle: 1
```
