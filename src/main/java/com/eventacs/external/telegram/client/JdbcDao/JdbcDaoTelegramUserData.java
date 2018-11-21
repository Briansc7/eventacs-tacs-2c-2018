package com.eventacs.external.telegram.client.JdbcDao;

import com.eventacs.account.dto.UserAccountDTO;
import com.eventacs.external.eventbrite.model.GetAccessToken;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcDaoTelegramUserData extends JdbcDaoSupport {

    public static final String DEF_DELETE_TOKEN_CHATID_SQL = "delete token_id_chat_id where chat_id = ?";
    public static final String DEF_INSERT_TOKEN_CHATID_SQL = "insert into token_id_chat_id (token_access, chatid, user_name) values(?,?,?)";
    public static final String DEF_INSERT_USER_ACCOUNT_SQL = "insert into users (username, password, name, email) values(?,?,?,?)";
    public static final String DEF_SELECT_TOKEN_CHATID_USERNAME_SQL = "select token_access, chatid, user_name from token_id_chat_id where chatid = ?";

    public JdbcDaoTelegramUserData(DataSource datasource) {
        setDataSource(datasource);
    }


    public void insertTokenChatId(GetAccessToken token, String chatId) {
        getJdbcTemplate().update(DEF_INSERT_TOKEN_CHATID_SQL, token.getAccess_token(), chatId, token.getUsername());
    }

    public void insertUserDataAccount(UserAccountDTO userAccountDTO) {
        getJdbcTemplate().update(DEF_INSERT_USER_ACCOUNT_SQL,
                userAccountDTO.getUserName(),
                userAccountDTO.getPassword(),
                userAccountDTO.getFullName(),
                userAccountDTO.getEmail());
    }

    public void deleteTokenByChatId(String chatId) {
        getJdbcTemplate().update(DEF_DELETE_TOKEN_CHATID_SQL, chatId);
    }

    public List<DataUserTelegram> getDataByChatId(String chatId) {
        return this.getJdbcTemplate().query(DEF_SELECT_TOKEN_CHATID_USERNAME_SQL, new String[]{chatId}, new RowMapper<DataUserTelegram>() {
            public DataUserTelegram mapRow(ResultSet rs, int rowNum) throws SQLException {
                String tokenAccess = rs.getString(1);
                String chatId = rs.getString(2);
                String userName = rs.getString(3);
                return new DataUserTelegram(tokenAccess, chatId, userName);
            }
        });
    }

}
