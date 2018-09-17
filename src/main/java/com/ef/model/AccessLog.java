package com.ef.model;

import com.ef.properties.CliProperties;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "accessTime", "ipAddress", "httpMethod", "httpStatus", "httpClient" })
public class AccessLog {

    @JsonFormat(pattern = CliProperties.LOG_DATE_FORMAT)
    private LocalDateTime accessTime;
    private String ipAddress;
    private String httpMethod;
    private Integer httpStatus;
    private String httpClient;
}
