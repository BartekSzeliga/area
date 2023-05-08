package com.dsv.example.demo.area.infrastructure;

import com.dsv.example.demo.area.core.model.Area;
import com.dsv.example.demo.area.core.model.CompositeAreaId;
import com.dsv.example.demo.area.core.model.CreateAreaCommand;
import com.dsv.example.demo.area.core.model.LeafletArea;
import com.dsv.example.demo.area.core.ports.outgoing.LeafletAreaDatabase;
import com.dsv.example.demo.area.infrastructure.mapper.AreaRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class LeafletAreaDatabaseAdapter implements LeafletAreaDatabase {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<LeafletArea> getLeaflet(Long leafletId) {
        return getLeafletById(leafletId).map(LeafletEntity::toLeafletArea);
    }

    @Override
    public Optional<LeafletArea> getLeafletByNaturalId(String naturalId) {
        return getLeafletByNatural(naturalId).map(LeafletEntity::toLeafletArea);
    }

    private Optional<LeafletEntity> getLeafletByNatural(String naturalId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT id, number_of_pages FROM leaflets WHERE natural_id = ?",
                            new BeanPropertyRowMapper<>(LeafletEntity.class),
                            naturalId));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }


    private Optional<LeafletEntity> getLeafletById(Long leafletId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT id, number_of_pages FROM leaflets WHERE id = ?",
                            new BeanPropertyRowMapper<>(LeafletEntity.class),
                            leafletId));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<Area> getAreasForPageNumber(CompositeAreaId compositeAreaId) {
        try {
            return jdbcTemplate.query(
                    "SELECT id, x1, y1, x2, y2, link, page_number FROM areas WHERE leaflet_id = ? AND page_number = ?",
                    new AreaRowMapper(),
                    compositeAreaId.getLeafletId().getId(),
                    compositeAreaId.getPageNumber());
        } catch (EmptyResultDataAccessException exception) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Area> getAreasForLeaflet(Long leafletId) {
        try {
            return jdbcTemplate.query(
                    "SELECT id, x1, y1, x2, y2, link, page_number FROM areas WHERE leaflet_id = ?",
                    new AreaRowMapper(),
                    leafletId);
        } catch (EmptyResultDataAccessException exception) {
            return new ArrayList<>();
        }
    }

    @Override
    public void save(CreateAreaCommand createAreaCommand) {
        jdbcTemplate.update(
                "INSERT INTO areas (leaflet_id, page_number, link, x1, x2, y1, y2) VALUES (?, ?, ?, ?, ?, ?, ?)",
                createAreaCommand.getLeafletId().getId(),
                createAreaCommand.getPageNumber(),
                createAreaCommand.getLink(),
                createAreaCommand.getAreaRectangle().getX1(),
                createAreaCommand.getAreaRectangle().getX2(),
                createAreaCommand.getAreaRectangle().getY1(),
                createAreaCommand.getAreaRectangle().getY2()
        );
    }

    @Override
    public void updateAreaLink(Long id, String link) {
        jdbcTemplate.update(
                "UPDATE areas SET link = ? WHERE id=?",
                link,
                id
        );
    }

    @Override
    public void deleteArea(Long areaId) {
        jdbcTemplate.update(
                "DELETE FROM areas WHERE id = ?",
                areaId
        );
    }

    @Override
    public Optional<Area> findBy(Long areaId) {
        return findById(areaId).
                map(AreaEntity::toArea);

    }

    @Override
    public void deleteAreas(Long leafletId, Integer pageNumber) {
        jdbcTemplate.update(
                "DELETE FROM areas WHERE leaflet_id = ? AND page_number = ?",
                leafletId,
                pageNumber
        );
    }

    private Optional<AreaEntity> findById(Long areaId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT id, page_number, link, x1, y1, x2, y2 FROM areas where id = ?",
                            new BeanPropertyRowMapper<>(AreaEntity.class),
                            areaId));
        } catch (DataAccessException exception) {
            return Optional.empty();
        }
    }
}
