package com.dsv.example.demo.area.application.dto;

import com.dsv.example.demo.area.core.model.Area;
import com.dsv.example.demo.area.core.model.AreaPoint;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class AreaResponse {
    List<AreaDTO> areas;
    Integer pageNumber;

    public static AreaResponse fromModel(List<Area> areas, Integer pageNumber) {
        List<AreaDTO> areaDTOS = areas.stream().map(AreaDTO::fromModel).collect(Collectors.toList());
        return new AreaResponse(areaDTOS, pageNumber);
    }
}

@Value
class AreaDTO {
    Long id;
    String link;
    DimensionsDTO dimensions;

    static AreaDTO fromModel(Area area) {
        return new AreaDTO(area.getId(), area.getLink(), area.getLeftUpperCorner(), area.getRightBottomCorner());
    }

    public AreaDTO(Long id, String link, AreaPoint leftUpperCorner, AreaPoint rightBottomCorner) {
        this.id = id;
        this.link = link;
        this.dimensions = new DimensionsDTO(leftUpperCorner.getX(), rightBottomCorner.getX(), leftUpperCorner.getY(), rightBottomCorner.getY());
    }
}

@Value
class DimensionsDTO {
    Long x1;
    Long x2;
    Long y1;
    Long y2;
}
