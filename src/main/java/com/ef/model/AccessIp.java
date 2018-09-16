package com.ef.model;

import lombok.Data;

@Data
public class AccessIp {
    private String ipAddress;
    private Boolean restricted;
    private String cause;
}
