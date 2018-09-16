package com.ef.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RestrictData {

    public enum Duration {
        HOURLY, DAILY
    }

    private LocalDateTime startDate;
    private Duration duration;
    private Integer threshold;
}
