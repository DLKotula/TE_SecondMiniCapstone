package com.techelevator.dao;

import com.techelevator.model.Park;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcParkDao implements ParkDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcParkDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Park> listAllParks() {
        List<Park> parks = new ArrayList<>();
        String sql = "select * from park;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            parks.add(mapRowToPark(results));
        }
        return parks;
    }

    // ------------------------------------- REVIEW RETURN ----------------------------------------------
    public List<Park> getParkInformation(Park name) {
        List<Park> parks = new ArrayList<>();
        String sql = "select name, location, establish_date, area, visitors, description from park where name = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name.getName());
        while (results.next()) {
            parks.add(mapRowToPark(results));
        }
        return parks;
    }
    // ------------------------------------- REVIEW RETURN ----------------------------------------------

    private Park mapRowToPark(SqlRowSet rowSet) {
        Park park = new Park();
        park.setParkId(rowSet.getLong("park_id"));
        park.setName(rowSet.getString("name"));
        park.setLocation(rowSet.getString("location"));
        if (rowSet.getDate("establish_date") != null) {
            park.setEstablishDate(rowSet.getDate("establish_date").toLocalDate());
        }
        park.setArea(rowSet.getLong("area"));
        park.setVisitors(rowSet.getLong("visitors"));
        park.setDescription(rowSet.getString("description"));
        return park;
    }
}