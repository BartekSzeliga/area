package com.dsv.example.demo.area.core.model;

import lombok.Value;

@Value
public class DeletePageAreasCommand {
    Long leafletId;
    Integer pageNumber;
}

