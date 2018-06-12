package com.ynuedu.partbuilding.controller;

import com.ynuedu.partbuilding.Entities.UserInfo;
import com.ynuedu.partbuilding.Repositories.MissionRepository;
import com.ynuedu.partbuilding.Repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户控制器
 */
@RestController()
public class UserController extends AbstractController {

    //引入数据访问器，自动注入
    @Autowired
    MissionRepository missionDao;

    /**
     * 获取用户详情
     * @return
     */
    @GetMapping(value = "api/user/self")
    public UserInfo getUserSelf( ) {

        UserInfo us = getCurrentUser();


        return us;
    }


    /**
     * 获取用户详情
     *
     * @param id id
     * @return
     */
    @GetMapping(value = "api/user/UserDetails")
    public UserInfo getUserDetails(@RequestParam String id) {


        UserInfo us = usDao.getOne(id);


        return us;
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @GetMapping(value = "api/user")
    public List<UserInfo> queryAllUser() {

        List<UserInfo> uss = usDao.findAll();

        //去除敏感信息
        safetyUsers(uss);

        return uss;
    }

    /**
     * 根据名称查询用户集合
     *
     * @param name
     * @return
     */
    @GetMapping(value = "api/user/byName")
    public List<UserInfo> queryUserByName(@RequestParam String name) {


        List<UserInfo> uss = usDao.findUserByUsersByName(name);

        //去除敏感信息
        safetyUsers(uss);

        return uss;
    }

    void safetyUsers(List<UserInfo> uss)
    {
        if(uss==null)
            return;

        uss.forEach(
                userInfo -> {
                    userInfo.setPassword( "");// 隐藏返回的密码
                }
        );
    }


    /**
     * 添加用户
     * @param us
     * @return
     */
    @PostMapping(value = "api/user")
    public UserInfo addUser(@RequestBody UserInfo us) {

        if (us == null)
            return null;

        us.setCreateDate( LocalDateTime.now());//保证更新创建时间

        usDao.save(us);

        return us;
    }


}
