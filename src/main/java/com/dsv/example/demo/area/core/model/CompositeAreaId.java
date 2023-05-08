package com.dsv.example.demo.area.core.model;

import lombok.Value;

@Value
public class CompositeAreaId {
    LeafletId leafletId;
    Integer pageNumber;
}
