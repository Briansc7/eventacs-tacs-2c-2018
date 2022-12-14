--------------- MySQL ---------------
drop table if exists oauth_client_details;
create table oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

drop table if exists oauth_client_token;
create table if not exists oauth_client_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255)
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

drop table if exists oauth_access_token;
create table if not exists oauth_access_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication LONG VARBINARY,
  refresh_token VARCHAR(255)
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

drop table if exists oauth_refresh_token;
create table if not exists oauth_refresh_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication LONG VARBINARY
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

drop table if exists oauth_code;
create table if not exists oauth_code (
  code VARCHAR(255), authentication LONG VARBINARY
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

drop table if exists oauth_approvals;
create table if not exists oauth_approvals (
	userId VARCHAR(255),
	clientId VARCHAR(255),
	scope VARCHAR(255),
	status VARCHAR(10),
	expiresAt TIMESTAMP default CURRENT_TIMESTAMP,
	lastModifiedAt TIMESTAMP default CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

drop table if exists ClientDetails;
create table if not exists ClientDetails (
  appId VARCHAR(255) PRIMARY KEY,
  resourceIds VARCHAR(255),
  appSecret VARCHAR(255),
  scope VARCHAR(255),
  grantTypes VARCHAR(255),
  redirectUrl VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(255)
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

drop table if exists users;
CREATE TABLE users (
        username VARCHAR(50) PRIMARY KEY,
        password VARCHAR(255),
        account_expired boolean,
        account_locked boolean,
        credentials_expired boolean,
        name VARCHAR(100),
        email varchar(50),
        chatId varchar(50),
        celPhone varchar(20),
        telegramName varchar(50),
        enabled TINYINT(1)
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

drop table if exists authorities;
CREATE TABLE authorities (
        username VARCHAR(50) PRIMARY KEY,
        authority VARCHAR(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;
