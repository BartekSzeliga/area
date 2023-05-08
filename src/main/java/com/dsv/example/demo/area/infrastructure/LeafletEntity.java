package com.dsv.example.demo.area.infrastructure;


import com.dsv.example.demo.area.core.model.LeafletArea;
import com.dsv.example.demo.area.core.model.LeafletId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class LeafletEntity {
    Long id;
    Integer numberOfPages;

    LeafletArea toLeafletArea() {
        return new LeafletArea(new LeafletId(id), numberOfPages);
    }
}