package com.dsv.example.demo.area.core.model;

import lombok.Value;

@Value
public class AreaPoint {
    Long x;
    Long y;

    public AreaPoint(Long x, Long y) {
        if (x == null || x < 0) {
            throw new IllegalArgumentException("X Point cannot be null or minus.");
        }
        if (y == null || y < 0) {
            throw new IllegalArgumentException("Y Point cannot be null or minus.");
        }
        this.x = x;
        this.y = y;
    }
}
