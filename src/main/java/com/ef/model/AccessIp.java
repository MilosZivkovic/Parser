package com.ef.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessIp {
    private String ipAddress;
    private Boolean restricted;
    private String cause;
}
