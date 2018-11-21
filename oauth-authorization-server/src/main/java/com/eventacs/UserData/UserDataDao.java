package com.eventacs.UserData;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDataDao extends User {

        private static final long serialVersionUID = -3531439484732724601L;

        private final String name;
        private final String chatId;
        private final String email;
        private final String celPhone;
        private final String telegramName;

    public UserDataDao(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String name, String chatId, String email, String celPhone, String telegramName) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.name = name;
        this.chatId = chatId;
        this.email = email;
        this.celPhone = celPhone;
        this.telegramName = telegramName;
    }

    public static long getSerialversionuid() {
            return serialVersionUID;
        }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public String getChatId() {
        return chatId;
    }

    public String getEmail() {
        return email;
    }

    public String getCelPhone() {
        return celPhone;
    }

    public String getTelegramName() {
        return telegramName;
    }
}
