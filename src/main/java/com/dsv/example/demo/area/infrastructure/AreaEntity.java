package com.dsv.example.demo.area.infrastructure;

import com.dsv.example.demo.area.core.model.Area;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class AreaEntity {
    Long id;
    Integer pageNumber;
    String link;
    Long x1;
    Long y1;
    Long x2;
    Long y2;


    Area toArea() {
        return new Area(id, pageNumber, link, x1, y1, x2, y2);
    }
}
