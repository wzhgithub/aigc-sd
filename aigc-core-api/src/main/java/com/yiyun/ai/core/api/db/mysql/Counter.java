package com.yiyun.ai.core.api.db.mysql;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Counter {
    private Long id;
    private Long count;
    private Timestamp createAt;
    private Timestamp updateAt;
}
