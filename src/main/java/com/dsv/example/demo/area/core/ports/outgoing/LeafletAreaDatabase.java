package com.dsv.example.demo.area.core.ports.outgoing;

//import com.dsv.example.demo.area.core.model.Area;
import com.dsv.example.demo.area.core.model.Area;
import com.dsv.example.demo.area.core.model.CompositeAreaId;
import com.dsv.example.demo.area.core.model.CreateAreaCommand;
import com.dsv.example.demo.area.core.model.LeafletArea;

import java.util.List;
import java.util.Optional;

public interface LeafletAreaDatabase {
    Optional<LeafletArea> getLeaflet(Long leafletId);

    Optional<LeafletArea> getLeafletByNaturalId(String naturalId);

    List<Area> getAreasForPageNumber(CompositeAreaId compositeAreaId);

    List<Area> getAreasForLeaflet(Long leafletId);

    void save(CreateAreaCommand createAreaCommand);

    void updateAreaLink(Long id, String link);

    void deleteArea(Long areaId);

    Optional<Area> findBy(Long areaId);

    void deleteAreas(Long leafletId, Integer pageNumber);
}
