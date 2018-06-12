package com.ynuedu.partbuilding.Entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Mission {


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    public String id;

    public String level;

    /**
     * 所属用户
     */
    public String userId;

    public String title;
    public String quakeLevel;
    public String responseLevel;

    public LocalDateTime createDate = LocalDateTime.now();
}
