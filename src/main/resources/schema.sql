--------------- MySQL ---------------
drop table if exists token_id_chat_id;
create table token_id_chat_id (
  token_access VARCHAR(255),
  chatid VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255)
);