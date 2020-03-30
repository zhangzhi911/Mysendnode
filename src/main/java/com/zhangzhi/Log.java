package com.zhangzhi;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document("node")
@Data
public class Log {

    @Id
    private ObjectId id;
    @Field("create_time")
    private Date createTime;

    @Field
    private String method;

    @Field("message")
    private String message;

    @Field("level")
    private String level;

}