package com.dsv.example.demo.area.application.dto;

import com.dsv.example.demo.area.core.model.LeafletAreas;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class LeafletAreaPagesResponse {
    List<AreaResponse> pages;

    public static LeafletAreaPagesResponse fromModel(List<LeafletAreas> leafletAreas) {
        List<AreaResponse> response = leafletAreas.stream()
                .map(areas -> {
                    List<AreaDTO> areaDTOS = areas.getAreas().stream().map(AreaDTO::fromModel).collect(Collectors.toList());
                    return new AreaResponse(areaDTOS, areas.getPageNumber());
                })
                .collect(Collectors.toList());
        return new LeafletAreaPagesResponse(response);
    }
}