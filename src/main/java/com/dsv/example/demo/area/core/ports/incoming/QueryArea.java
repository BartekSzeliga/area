package com.dsv.example.demo.area.core.ports.incoming;

import com.dsv.example.demo.area.core.model.Area;
import com.dsv.example.demo.area.core.model.CompositeAreaId;
import com.dsv.example.demo.area.core.model.LeafletAreas;
import com.dsv.example.demo.area.core.model.LeafletId;
import com.dsv.example.demo.area.core.model.NaturalId;

import java.util.List;

public interface QueryArea {
    List<Area> getAreasForPageNumber(CompositeAreaId compositeAreaId);

    List<LeafletAreas> getAreasForXML(NaturalId naturalId);

    List<LeafletAreas> getAreasForLeaflet(LeafletId leafletId);
}
