package com.dsv.example.demo.area.infrastructure.mapper;

import com.dsv.example.demo.area.core.model.Area;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AreaRowMapper implements RowMapper<Area> {

    @Override
    public Area mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Area(
                rs.getLong("id"),
                rs.getInt("page_number"),
                rs.getString("link"),
                rs.getLong("x1"), rs.getLong("y1"),
                rs.getLong("x2"), rs.getLong("y2")

        );
    }
}
