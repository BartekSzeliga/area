package com.dsv.example.demo.area.application;


import com.dsv.example.demo.area.application.dto.AreaResponse;
import com.dsv.example.demo.area.application.dto.LeafletAreaPagesResponse;
import com.dsv.example.demo.area.application.dto.LeafletAreasXMLResponse;
import com.dsv.example.demo.area.core.model.Area;
import com.dsv.example.demo.area.core.model.CompositeAreaId;
import com.dsv.example.demo.area.core.model.CreateAreaCommand;
import com.dsv.example.demo.area.core.model.DeleteAreaCommand;
import com.dsv.example.demo.area.core.model.DeletePageAreasCommand;
import com.dsv.example.demo.area.core.model.LeafletAreas;
import com.dsv.example.demo.area.core.model.LeafletId;
import com.dsv.example.demo.area.core.model.NaturalId;
import com.dsv.example.demo.area.core.model.UpdateAreaLinkCommand;
import com.dsv.example.demo.area.core.ports.incoming.CreateArea;
import com.dsv.example.demo.area.core.ports.incoming.DeleteArea;
import com.dsv.example.demo.area.core.ports.incoming.QueryArea;
import com.dsv.example.demo.area.core.ports.incoming.UpdateAreaLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
@Slf4j
public class AreaController {

    @Qualifier
    private final CreateArea createArea;
    @Qualifier
    private final QueryArea queryArea;
    @Qualifier
    private final UpdateAreaLink updateAreaLink;
    @Qualifier
    private final DeleteArea deleteArea;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createArea(@RequestBody @Valid CreateAreaCommand body) {
        createArea.handle(body);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArea(@PathVariable Long id, @RequestBody @Valid UpdateAreaLinkCommand body) {
        updateAreaLink.handle(new UpdateAreaLinkCommand(id, body.getLink()));
    }

    @GetMapping("/leaflets/{leafletId}/page-numbers/{pageNumber}")
    public ResponseEntity<AreaResponse> getAreas(@PathVariable Long leafletId, @PathVariable Integer pageNumber) {
        List<Area> areas = queryArea.getAreasForPageNumber(new CompositeAreaId(new LeafletId(leafletId), pageNumber));
        return new ResponseEntity<>(AreaResponse.fromModel(areas, pageNumber), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArea(@PathVariable Long id) {
        deleteArea.handle(new DeleteAreaCommand(id));
    }

    @DeleteMapping("/leaflets/{leafletId}/page-numbers/{pageNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAreasFromPage(@PathVariable Long leafletId, @PathVariable Integer pageNumber) {
        deleteArea.handle(new DeletePageAreasCommand(leafletId, pageNumber));
    }

    @GetMapping("/leaflets/{leafletId}/pages")
    public ResponseEntity<LeafletAreaPagesResponse> getAreasPages(@PathVariable Long leafletId) {
        List<LeafletAreas> leafletAreaPages = queryArea.getAreasForLeaflet(new LeafletId(leafletId));
        return new ResponseEntity<>(LeafletAreaPagesResponse.fromModel(leafletAreaPages), HttpStatus.OK);
    }

    @GetMapping("/leaflets/{naturalId}")
    public ResponseEntity<List<LeafletAreasXMLResponse>> getAreasForXML(@PathVariable String naturalId) {
        List<LeafletAreas> areas = queryArea.getAreasForXML(new NaturalId(naturalId));
        List<LeafletAreasXMLResponse> areasXMLResponses = areas.stream()
                .map(LeafletAreasXMLResponse::fromModel)
                .collect(Collectors.toList());
        return new ResponseEntity<>(areasXMLResponses, HttpStatus.OK);
    }
}

