package com.ynuedu.partbuilding.Auth;

import com.ynuedu.partbuilding.Entities.UserInfo;
import com.ynuedu.partbuilding.Repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 用户dao
     */
    @Autowired
    protected UserInfoRepository  userDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsImpl udi = null;

        List<SimpleGrantedAuthority> auLst = Arrays.asList(
                new SimpleGrantedAuthority("admin")

        );

        //
        if (userDao == null) {
            // TODO 从数据库中查找用户
            udi = new UserDetailsImpl("d236e3a8-01fd-4360-947f-d1b51d77d833",
                    "cstadmin", "{noop}123123",
                    auLst,
                    true, new Date());
        } else {
            UserInfo us = userDao.findUserByUserName(username);
            if (us != null) {
                udi = new UserDetailsImpl(us.getId(),
                        us.getUserName(), "{noop}" + us.getPassword(),
                        auLst,
                        true, new Date());
            }
        }


        return udi;
    }

}