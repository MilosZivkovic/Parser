package com.ef.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({"accessTime", "ipAddress", "method", "status", "client"})
public class AccessLog {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime accessTime;
    private String ipAddress;
    private String method;
    private Integer status;
    private String client;
}
