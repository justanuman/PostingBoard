package com.postingBoard.config.security.JWT;


import com.postingBoard.entity.DbRole;
import com.postingBoard.entity.DbUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(DbUser user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())),
                user.getStatus().equals("ENABLED"),
                user.getUpdated()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<DbRole> userRoles) {
        List<GrantedAuthority> out = new ArrayList<>();
        for (DbRole role : userRoles) {
            out.add(new SimpleGrantedAuthority(role.getName()));
        }
        return out;
    }
}