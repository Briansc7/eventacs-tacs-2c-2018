package com.eventacs.external.telegram.client.JdbcDao;

import com.eventacs.account.dto.UserAccountDTO;
import com.eventacs.external.eventbrite.model.GetAccessToken;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcDaoUserData extends JdbcDaoSupport {

    public static final String DEF_INSERT_USER_ACCOUNT_SQL = "insert into users (username, password, name, email) values(?,?,?,?)";

    public JdbcDaoUserData(DataSource datasource) {
        setDataSource(datasource);
    }


    public void insertUserDataAccount(UserAccountDTO userAccountDTO) {
        getJdbcTemplate().update(DEF_INSERT_USER_ACCOUNT_SQL,
                userAccountDTO.getUserName(),
                userAccountDTO.getPassword(),
                userAccountDTO.getFullName(),
                userAccountDTO.getEmail());
    }

}
