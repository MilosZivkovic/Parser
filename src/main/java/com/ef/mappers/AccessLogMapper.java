package com.ef.mappers;

import com.ef.model.AccessIp;
import com.ef.model.AccessLog;
import com.ef.model.RestrictData;
import com.ef.model.RestrictedIp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccessLogMapper {

    void insertAccessLog(@Param("logData") AccessLog logData);

    List<RestrictedIp> findDDOSAttempts(@Param("data") RestrictData restrictData);

    void restrictIpAddress(@Param("ip") AccessIp accessIp);
}
