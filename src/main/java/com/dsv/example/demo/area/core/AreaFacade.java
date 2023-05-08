package com.dsv.example.demo.area.core;

import com.dsv.example.demo.area.core.model.Area;
import com.dsv.example.demo.area.core.model.CompositeAreaId;
import com.dsv.example.demo.area.core.model.CreateAreaCommand;
import com.dsv.example.demo.area.core.model.DeleteAreaCommand;
import com.dsv.example.demo.area.core.model.DeletePageAreasCommand;
import com.dsv.example.demo.area.core.model.LeafletArea;
import com.dsv.example.demo.area.core.model.LeafletAreas;
import com.dsv.example.demo.area.core.model.LeafletId;
import com.dsv.example.demo.area.core.model.NaturalId;
import com.dsv.example.demo.area.core.model.UpdateAreaLinkCommand;
import com.dsv.example.demo.area.core.ports.incoming.CreateArea;
import com.dsv.example.demo.area.core.ports.incoming.DeleteArea;
import com.dsv.example.demo.area.core.ports.incoming.QueryArea;
import com.dsv.example.demo.area.core.ports.incoming.UpdateAreaLink;
import com.dsv.example.demo.area.core.ports.outgoing.LeafletAreaDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AreaFacade implements CreateArea, QueryArea, UpdateAreaLink, DeleteArea {
    private final LeafletAreaDatabase leafletAreaDatabase;

    public AreaFacade(LeafletAreaDatabase leafletAreaDatabase) {
        this.leafletAreaDatabase = leafletAreaDatabase;
    }

    @Override
    public void handle(CreateAreaCommand createAreaCommand) {
        LeafletArea leafletArea = getLeafletOrThrow(createAreaCommand.getLeafletId());
        validatePageNumber(createAreaCommand.getPageNumber(), leafletArea.getNumberOfPages());
        leafletAreaDatabase.save(createAreaCommand);
    }

    @Override
    public List<Area> getAreasForPageNumber(CompositeAreaId compositeAreaId) {
        getLeafletOrThrow(compositeAreaId.getLeafletId());
        return leafletAreaDatabase.getAreasForPageNumber(compositeAreaId);
    }

    @Override
    public List<LeafletAreas> getAreasForXML(NaturalId naturalId) {
        LeafletArea leafletArea = getLeafletByNaturalOrThrow(naturalId.getId());
        List<Area> areasForLeaflet = leafletAreaDatabase.getAreasForLeaflet(leafletArea.getLeafletId().getId());
        return getLeafletAreas(leafletArea, areasForLeaflet);
    }

    @Override
    public List<LeafletAreas> getAreasForLeaflet(LeafletId leafletId) {
        LeafletArea leafletArea = getLeafletOrThrow(leafletId);
        List<Area> areasForLeaflet = leafletAreaDatabase.getAreasForLeaflet(leafletArea.getLeafletId().getId());
        return getLeafletAreas(leafletArea, areasForLeaflet);
    }

    private List<LeafletAreas> getLeafletAreas(LeafletArea leafletArea, List<Area> areasForLeaflet) {
        List<LeafletAreas> leafletAreas = new ArrayList<>();

        IntStream.rangeClosed(1, leafletArea.getNumberOfPages())
                .forEach(value -> {
                    List<Area> areas = areasForLeaflet.stream()
                            .filter(area -> area.getPageNumber() == value)
                            .collect(Collectors.toList());
                    leafletAreas.add(new LeafletAreas(value, areas));
                });
        return leafletAreas;
    }

    @Override
    public void handle(UpdateAreaLinkCommand updateAreaLinkCommand) {
        leafletAreaDatabase.updateAreaLink(updateAreaLinkCommand.getId(), updateAreaLinkCommand.getLink());
    }

    @Override
    public void handle(DeleteAreaCommand deleteAreaCommand) {
        getAreaOrThrow(deleteAreaCommand.getId());
        leafletAreaDatabase.deleteArea(deleteAreaCommand.getId());
    }

    @Override
    public void handle(DeletePageAreasCommand deletePageAreasCommand) {
        leafletAreaDatabase.deleteAreas(deletePageAreasCommand.getLeafletId(), deletePageAreasCommand.getPageNumber());
    }

    private void validatePageNumber(Integer pageNumber, Integer numberOfPages) {
        if (pageNumber > numberOfPages) {
            throw new IllegalArgumentException("Page Number: " + pageNumber + " has exceeded number of pages: " + numberOfPages);
        }
    }

    private LeafletArea getLeafletOrThrow(LeafletId leafletId) {
        return leafletAreaDatabase.getLeaflet(leafletId.getId()).orElseThrow(() -> new IllegalArgumentException("Cannot find leaflet with Id: " + leafletId));
    }

    private LeafletArea getLeafletByNaturalOrThrow(String naturalId) {
        return leafletAreaDatabase.getLeafletByNaturalId(naturalId).orElseThrow(() -> new IllegalArgumentException("Cannot find leaflet with natural_id: " + naturalId));
    }

    private Area getAreaOrThrow(Long areaId) {
        return leafletAreaDatabase.findBy(areaId).orElseThrow(() -> new IllegalArgumentException("Cannot find Area with Id: " + areaId));
    }
}
