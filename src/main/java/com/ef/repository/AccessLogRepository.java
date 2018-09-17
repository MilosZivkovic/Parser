package com.ef.repository;

import com.ef.model.AccessIp;
import com.ef.model.AccessLog;
import com.ef.model.RestrictData;
import com.ef.model.RestrictedIp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccessLogRepository {

    void insertAccessLog(@Param("logData") AccessLog logData);

    List<RestrictedIp> findDDOSAttempts(@Param("data") RestrictData restrictData);

    void restrictIpAddress(@Param("ip") AccessIp accessIp);

    void restrictIpAddresses(@Param("ips") List<AccessIp> accessIps);
}
