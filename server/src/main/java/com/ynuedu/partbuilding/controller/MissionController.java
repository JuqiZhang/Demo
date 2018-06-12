package com.ynuedu.partbuilding.controller;

import com.ynuedu.partbuilding.Entities.Mission;
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
@RestController(value = "api/user")
public class MissionController {

    //引入数据访问器，自动注入
    @Autowired
    MissionRepository missionDao;

    @Autowired
    UserInfoRepository usDao;

    /**
     * 获取用户对应的任务
     *
     * @return
     */
    @GetMapping(value = "api/mission")
    public List<Mission> getMission(@RequestParam String id) {

        List<Mission> ms = missionDao.findUserMission(id);


        return ms;
    }


    @PostMapping(value = "api/mission")
    public Mission addMission(@RequestBody Mission ms) {

        if (ms == null)
            return null;

        ms.createDate = LocalDateTime.now();//保证更新创建时间

        missionDao.save(ms);

        return ms;
    }


}
