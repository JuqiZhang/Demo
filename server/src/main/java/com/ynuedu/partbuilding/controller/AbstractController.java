package com.ynuedu.partbuilding.controller;

import com.ynuedu.partbuilding.Auth.UserDetailsImpl;
import com.ynuedu.partbuilding.Entities.UserInfo;
import com.ynuedu.partbuilding.Repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AbstractController {

    @Autowired
    protected UserInfoRepository usDao;

    /**
     * 当前用户
     */
    UserInfo _currentUser  = null;

    /**
     * 获取当前已登录用户
     */
    protected UserInfo getCurrentUser() {

        if (_currentUser != null)
            return _currentUser;

        //#region 获取当前已登录的账号
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //
        if (auth == null)
            return null;

        Object princ = auth.getPrincipal();
        Object dt = auth.getDetails();
       // logger.info(princ.toString())

        //#endregion

        //查询对应的用户
        UserDetailsImpl us = (UserDetailsImpl)princ;

        _currentUser = usDao.getOne(us.getId());


        return _currentUser;
    }
}
