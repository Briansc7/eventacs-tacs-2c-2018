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
# INSERT INTO oauth_client_details
# 	(client_id, client_secret, scope, authorized_grant_types,
# 	web_server_redirect_uri, authorities, access_token_validity,
# 	refresh_token_validity, additional_information, autoapprove)
# VALUES
# 	('barClientIdPassword', '$2a$04$iB5NHiB2II5R2u5Y5ZhPC.ElBGzF39xsE.d.MXeLwTf.7IDWvcYbS', 'bar,read,write',
# 	'password,authorization_code,refresh_token', null, null, 36000, 36000, null, true);
