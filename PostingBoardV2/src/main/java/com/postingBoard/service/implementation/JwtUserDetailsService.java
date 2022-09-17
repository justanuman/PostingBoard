package com.postingBoard.service.implementation;


import com.postingBoard.config.security.JWT.JwtUser;
import com.postingBoard.config.security.JWT.JwtUserFactory;
import com.postingBoard.entity.DbUser;
import com.postingBoard.service.interfaces.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

    IUserService userService;

    @Autowired
    public JwtUserDetailsService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DbUser user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        logger.info("user {} loaded", username);
        return jwtUser;
    }
}
