package com.eventacs.external.telegram.client.JdbcDao;

import com.eventacs.account.dto.UserAccountDTO;
import com.eventacs.external.eventbrite.model.GetAccessToken;
import com.eventacs.user.dto.UserDataDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class JdbcDaoUserData extends JdbcDaoSupport {

    public static final String DEF_INSERT_USER_ACCOUNT_SQL = "insert into users (username, password, name, email, enabled) values(?,?,?,?,1)";
    public static final String DEF_INSERT_USER_ROLE_SQL = "insert into authorities (username, authority) values(?,?)";
    public static final String DEF_SELECT_USER_DATA_SQL = "select username, lastAccess from users where username=?";
    public static final String DEF_UPDATE_USER_LAST_LOGIN_TIMESTAMP_SQL = "update users set lastAccess=? where username=?";

    public JdbcDaoUserData(DataSource datasource) {
        setDataSource(datasource);
    }

    public UserDataDTO obtainUserDataFromDB(String userName) {
        List<UserDataDTO> actors = (List<UserDataDTO>)getJdbcTemplate().query(
                DEF_SELECT_USER_DATA_SQL,
                new Object[] {userName},
                new RowMapper<UserDataDTO>() {
                    public UserDataDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                        UserDataDTO userData = new UserDataDTO();
                        userData.setUserName(rs.getString(1));
                        userData.setLastAccess(LocalDateTime.ofInstant(Instant.ofEpochSecond(rs.getLong(2)), ZoneId.systemDefault()));
                        return userData;
                    }
                });
        return actors.get(0);
    }

    public void insertUserDataLastLoginTimestamp(String userName) {
        getJdbcTemplate().update(DEF_UPDATE_USER_LAST_LOGIN_TIMESTAMP_SQL,
                Instant.now().getEpochSecond(),
                userName);
    }

    public void insertUserDataAccount(UserAccountDTO userAccountDTO) {
        getJdbcTemplate().update(DEF_INSERT_USER_ACCOUNT_SQL,
                userAccountDTO.getUserName(),
                userAccountDTO.getPassword(),
                userAccountDTO.getFullName(),
                userAccountDTO.getEmail());
        getJdbcTemplate().update(DEF_INSERT_USER_ROLE_SQL,
                userAccountDTO.getUserName(),
                "ROLE_USER");
    }

}
