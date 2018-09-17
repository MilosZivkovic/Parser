package com.ef.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestrictData {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer threshold;
    public enum Duration {
        HOURLY, DAILY
    }
}
