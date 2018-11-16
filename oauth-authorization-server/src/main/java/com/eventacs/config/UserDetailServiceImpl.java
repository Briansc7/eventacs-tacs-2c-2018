package com.eventacs.config;

import com.eventacs.UserData.UserDataDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailServiceImpl extends JdbcDaoImpl implements UserDetailsService {

    private boolean enableAuthorities =true;

    public UserDetailServiceImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        List<UserDataDao> users = loadUsersDataDaoByUsername(username);
        if (users.size() == 0) {
            this.logger.debug("Query returned no results for user '" + username + "'");
            throw new UsernameNotFoundException(this.messages.getMessage("UserDetailServiceImpl.notFound", new Object[]{username}, "Username {0} not found"));
        } else {
            UserDataDao user = (UserDataDao) users.get(0);
            Set<GrantedAuthority> dbAuthsSet = new HashSet();
            if (this.enableAuthorities) {
                dbAuthsSet.addAll(this.loadUserAuthorities(user.getUsername()));
            }

            List<GrantedAuthority> dbAuths = new ArrayList(dbAuthsSet);
            this.addCustomAuthorities(user.getUsername(), dbAuths);
            if (dbAuths.size() == 0) {
                this.logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");
                throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.noAuthority", new Object[]{username}, "User {0} has no GrantedAuthority"));
            } else {
                return this.createUserDetails(username, user, dbAuths);
            }
        }
    }

    protected UserDataDao createUserDetails(String username, UserDataDao userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();
        return new UserDataDao(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(), true, true, true, combinedAuthorities,
                userFromUserQuery.getName(), userFromUserQuery.getChatId(), userFromUserQuery.getEmail(),
                userFromUserQuery.getCelPhone(), userFromUserQuery.getTelegramName());
    }

    protected List<UserDataDao> loadUsersDataDaoByUsername(String username) {
        setUsersByUsernameQuery("select username,password,enabled,name,chatId,email,celPhone,telegramName from users where username = ?");
        return this.getJdbcTemplate().query(getUsersByUsernameQuery(), new String[]{username}, new RowMapper<UserDataDao>() {
            public UserDataDao mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = rs.getBoolean(3);
                String name = rs.getString(4);
                String chatId = rs.getString(5);
                String email = rs.getString(6);
                String celPhone = rs.getString(7);
                String telegramName = rs.getString(8);
                return new UserDataDao(username, password, enabled,  true, true, true, AuthorityUtils.NO_AUTHORITIES,
                        name, chatId, email, celPhone, telegramName);
            }
        });
    }
}