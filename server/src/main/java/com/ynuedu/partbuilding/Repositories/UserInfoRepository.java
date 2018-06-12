package com.ynuedu.partbuilding.Repositories;

import com.ynuedu.partbuilding.Entities.Mission;
import com.ynuedu.partbuilding.Entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInfoRepository
        extends JpaRepository<UserInfo,String>
{
    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    @Query("select u from UserInfo u where u.userName = ?1")
    UserInfo findUserByUserName(String userName);

    /**
     * 根据用户名查询用户
     * @param name
     * @return
     */
    @Query("select u from UserInfo u where u.name = ?1")
    List<UserInfo> findUserByUsersByName(String name);
}
