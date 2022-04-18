package com.techelevator.dao;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Site;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcSiteDao implements SiteDao {

    private final JdbcTemplate jdbcTemplate;
    public List<Site> sites;

    public JdbcSiteDao(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Site> listAllSites() {
        sites = new ArrayList<>();
        String sql = "select * from site";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            sites.add(mapRowToSites(results));
        }

        return sites;
    }

    public List<Site> selectSitesFromCampground(Campground campground) {
        sites = new ArrayList<>();
        String sql = "select * from site " +
                "join campground on campground.campground_id = site.campground_id " +
                "where campground.name ilike ? " +
                "order by site_number " +
                "limit 5;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, campground.getName());
        while (results.next()) {
            sites.add((mapRowToSites(results)));
        }
        return sites;
    }

    public List<Site> siteAvailabilityFromReservations(Long parkId, Long campgroundId,
                                                       LocalDate arrivalDate, LocalDate departureDate) {
        sites = new ArrayList<>();
        String sql = "select * " +
                "from site " +
                "where campground_id = ? " +
                "and site_id not in (select site_id " +
                "from reservation " +
                "where (? between reservation.from_date and reservation.to_date) " +
                "or (? between reservation.from_date and reservation.to_date)) " +
                "Limit 5;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, campgroundId,
                arrivalDate, departureDate);
        while (results.next()) {
            sites.add(mapRowToSites(results));
        }

        return sites;
    }

    public boolean checkResults(Site site, Long parkId, Long campgroundId,
                                LocalDate arrivalDate, LocalDate departureDate) {
        List<Site> sites = new ArrayList<>();
        String sql = "select site.site_id, " +
                "site.campground_id, " +
                "site_number, " +
                "max_occupancy, " +
                "accessible, " +
                "max_rv_length, " +
                "utilities " +
                "from site " +
                "left join campground on campground.campground_id = site.campground_id " +
                "left join park on park.park_id = campground.park_id " +
                "right join reservation on site.site_id = reservation.site_id " +
                "where park.park_id = ? " +
                "and campground.campground_id = ?" +
                "and (reservation.from_date between ? and ? " +
                "or reservation.to_date between ? and ? ) " +
                "or (reservation.from_date < ? " +
                "and reservation.to_date > ?) " +
                "group by site.site_id, site.site_number " +
                "order by site.site_id asc;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId, campgroundId,
                arrivalDate, departureDate, arrivalDate, departureDate, arrivalDate, departureDate);
        while (results.next()) {
                sites.add(mapRowToSites(results));
            }

        for(Site compareSite : sites) {
            if (site.getSiteId() == compareSite.getSiteId()) {
                return true;
            }
        }
        return false;
    }

    public Site mapRowToSites(SqlRowSet rowSet) {
        Site site = new Site();
        site.setSiteId(rowSet.getLong("site_id"));
        site.setCampgroundId(rowSet.getLong("campground_id"));
        site.setSiteNumber(rowSet.getLong("site_number"));
        site.setMaxOccupancy(rowSet.getLong("max_occupancy"));
        site.setAccessible(rowSet.getString("accessible"));
        site.setMaxRvLength(rowSet.getLong("max_rv_length"));
        site.setUtilities(rowSet.getString("utilities"));
        return site;
    }
}
