package com.dsv.example.demo.area.application.dto;

import com.dsv.example.demo.area.core.model.LeafletAreas;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class LeafletAreasXMLResponse {
    Integer pageNumber;
    List<LeafletAreaXMLDTO> areas;

    public static LeafletAreasXMLResponse fromModel(LeafletAreas leafletAreas) {
        List<LeafletAreaXMLDTO> areaDTOS = leafletAreas.getAreas().stream()
                .map(area -> new LeafletAreaXMLDTO(area.getLeftUpperCorner().getX(), area.getLeftUpperCorner().getY(),
                        area.getRightBottomCorner().getX(), area.getRightBottomCorner().getY(), area.getLink()))
                .collect(Collectors.toList());
        return new LeafletAreasXMLResponse(leafletAreas.getPageNumber(), areaDTOS);
    }
}

@Value
class LeafletAreaXMLDTO {
    Long x1;
    Long y1;
    Long x2;
    Long y2;
    String link;
}