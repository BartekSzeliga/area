package com.dsv.example.demo.area.core.model;

import lombok.Value;

import javax.validation.constraints.Min;

@Value
public class CreateAreaCommand {
    AreaRectangle areaRectangle;
    String link;
    LeafletId leafletId;
    @Min(value = 1)
    Integer pageNumber;

    public CreateAreaCommand(Long x1, Long x2, Long y1, Long y2, String link, LeafletId leafletId, Integer pageNumber) {
        this.areaRectangle = new AreaRectangle(x1, x2, y1, y2);
        this.link = link;
        this.leafletId = leafletId;
        this.pageNumber = pageNumber;
    }
}

