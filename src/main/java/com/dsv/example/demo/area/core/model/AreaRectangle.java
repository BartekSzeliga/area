package com.dsv.example.demo.area.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Min;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class AreaRectangle {
    @Min(value = 0)
    Long x1;
    @Min(value = 0)
    Long x2;
    @Min(value = 0)
    Long y1;
    @Min(value = 0)
    Long y2;
}
