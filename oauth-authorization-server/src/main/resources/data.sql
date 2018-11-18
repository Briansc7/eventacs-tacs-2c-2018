INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('eventacsClientId', '$2a$04$iB5NHiB2II5R2u5Y5ZhPC.ElBGzF39xsE.d.MXeLwTf.7IDWvcYbS', 'foo,read,write',
	'password,authorization_code,refresh_token', null, null, 36000, 36000, null, true);
INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('sampleClientId', '$2a$04$iB5NHiB2II5R2u5Y5ZhPC.ElBGzF39xsE.d.MXeLwTf.7IDWvcYbS', 'read,write,foo,bar',
	'implicit', null, null, 36000, 36000, null, true);

insert into authorities (username, authority) values ('admin','ROLE_ADMIN');
insert into users (username, password, enabled, name, email) values ('admin','$2a$10$uCnrN6rvY8qLgMf6Iqn.0.KqdiSz3CtTkcySgJMhYOilYACtzKKCa',1,'Nombre Admin','email@admin.com.ar');
insert into authorities (username, authority) values ('usuario2','ROLE_USER');
insert into users (username, password, enabled, name, email) values ('usuario2','$2a$10$aOSEPSXWGSGVMFaIH3wSYu7PPDT4pS5Jx8XZv9Eb/qFgld..vanay',1,'Nombre usuario2','email@usuario2.com.ar');
insert into authorities (username, authority) values ('admin1','ROLE_ADMIN');
insert into users (username, password, enabled, name, email) values ('admin1','$2a$10$brzBhCjtIWfPEIItZ0NCsuwLAaoxfZq5CjiYCYt1lKnHMwlcOsyd.',1,'Nombre admin1','email@admin1.com.ar');
insert into authorities (username, authority) values ('usuario','ROLE_USER');
insert into users (username, password, enabled, name, email) values ('usuario','$2a$10$28wfXnVhgGVA4olNheCFbO9ZFUdjDQYTvqBEHl0c90fw/ILLBc/oG',1,'Nombre usuario','email@usuario.com.ar');
insert into authorities (username, authority) values ('User1','ROLE_USER');
insert into users (username, password, enabled, name, email) values ('User1','$2a$10$bPguOl06/is9TgXtbs4T9.aZ7O6ywjoApHxrvWkoDtHYMAkmYnyZG',1,'Nombre User1','email@User1.com.ar');
