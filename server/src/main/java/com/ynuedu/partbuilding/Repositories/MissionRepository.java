package com.ynuedu.partbuilding.Repositories;

import com.ynuedu.partbuilding.Entities.Mission;
import com.ynuedu.partbuilding.Entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.SplittableRandom;

public interface MissionRepository
        extends JpaRepository<Mission,String>
{

    /**
     * 根据用户名查询用户
     * @param id
     * @return
     */
    @Query("select d from Mission d where d.userId = ?1")
    List<Mission> findUserMission(String id);
}
