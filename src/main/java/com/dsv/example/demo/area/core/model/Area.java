package com.dsv.example.demo.area.core.model;


import lombok.Value;

@Value
public class Area {
    Long id;
    AreaPoint leftUpperCorner;
    AreaPoint rightBottomCorner;
    String link;
    Integer pageNumber;

    public Area(Long id, Integer pageNumber, String link, Long x1, Long y1, Long x2, Long y2) {
        this.id = id;
        this.pageNumber = pageNumber;
        this.link = link;
        this.leftUpperCorner = new AreaPoint(x1, y1);
        this.rightBottomCorner = new AreaPoint(x2, y2);

    }
}

