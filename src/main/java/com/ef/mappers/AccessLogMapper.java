package com.ef.mappers;

import com.ef.model.AccessLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccessLogMapper {

    void insertAccessLog(@Param("logData") AccessLog logData);
}
