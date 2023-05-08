package com.dsv.example.demo.area.core.model;


import lombok.Value;

import java.util.List;

@Value
public class LeafletAreas {
    Integer pageNumber;
    List<Area> areas;

    public LeafletAreas(Integer pageNumber, List<Area> areas) {
        this.pageNumber = pageNumber;
        this.areas = areas;
    }
}

