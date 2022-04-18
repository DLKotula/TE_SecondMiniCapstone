package com.techelevator.dao;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcCampgroundDao implements CampgroundDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Campground> campgrounds;

    public JdbcCampgroundDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Campground> listAllCampgrounds() {
        List<Campground> campgrounds = new ArrayList<>();
        String sql = "select * from campground";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            campgrounds.add(mapRowToCampground(results));
        }
        return campgrounds;
    }

    public List<Campground> selectCampgroundsFromPark(Park park) {
        campgrounds = new ArrayList<>();
        String sql = "select * from campground " +
//                "join park on park.park_id = campground.park_id " +
                "where park_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, park.getParkId());
        while (results.next()) {
            campgrounds.add(mapRowToCampground(results));
        }
        return campgrounds;
    }

    // need to substring userResponse date to get month and use for this query
    public List<Campground> checkIsCampgroundAvailable(Campground campground_id, String userMonth) {
        campgrounds = new ArrayList<>();
        String sql = "select * from campground " +
                "where campground_id = ? and (? between open_from_mm and open_to_mm);";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, campground_id.getCampgroundId(), userMonth);
        while (results.next()) {
            campgrounds.add(mapRowToCampground(results));
        }
        return campgrounds;
    }


    public Campground mapRowToCampground(SqlRowSet rowSet) {
        Campground campground = new Campground();
        campground.setCampgroundId(rowSet.getLong("campground_id"));
        campground.setParkId(rowSet.getLong("park_id"));
        campground.setName(rowSet.getString("name"));
        campground.setOpenFromMm(rowSet.getString("open_from_mm"));
        campground.setOpenToMm(rowSet.getString("open_to_mm"));
        campground.setDailyFee(rowSet.getString("daily_fee"));
        return campground;
    }
}
