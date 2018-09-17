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

    void insertAccessLogs(@Param("logData") List<AccessLog> logData);

    List<RestrictedIp> findRestrictedIps(@Param("data") RestrictData restrictData);

    void restrictIpAddresses(@Param("ips") List<AccessIp> accessIps);

    List<AccessLog> getAccessLog(@Param("ipAddresses") List<String> ipAddresses);
}
