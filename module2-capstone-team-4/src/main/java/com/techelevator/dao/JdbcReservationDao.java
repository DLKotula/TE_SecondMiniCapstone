package com.techelevator.dao;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;
import org.springframework.cglib.core.Local;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcReservationDao implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Site> sites;
    public List<Reservation> reservations;

    public JdbcReservationDao(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<Reservation> selectReservation(Site site) {
        reservations = new ArrayList<>();
        String sql = "select * from reservation " +
                "join site on site.site_id = reservation.site_id " +
                "where site.site_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, site.getSiteId());
        while (results.next()) {
            reservations.add((mapToReservation(results)));
        }
        return reservations;
    }

    public Reservation getReservation(long reservationId) {
        Reservation reservation = null;
        String sql = "select * from reservation where reservation_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reservationId);
        if (results.next()) {
            reservation = mapToReservation(results);
        }
        return reservation;
    }

    public Long createReservation(Site site, String name, LocalDate arrivalDate, LocalDate departureDate) {
        String sql = "insert into reservation (site_id, name, from_date, to_date, create_date)" +
                "values (?,?,?,?,?) returning reservation_id;";
        Long newId = jdbcTemplate.queryForObject(sql, Long.class, site.getSiteId(),
                name, arrivalDate, departureDate, LocalDate.now());
        //return getReservation(newId);
        return newId;
    }

    public Reservation mapToReservation(SqlRowSet rowSet) {
        Reservation reservation = new Reservation();
        reservation.setReservationId(rowSet.getLong("reservation_id"));
        reservation.setSiteId(rowSet.getLong("site_id"));
        reservation.setName(rowSet.getString("name"));
        if (rowSet.getDate("from_date") != null) {
            reservation.setFromDate(rowSet.getDate("from_date").toLocalDate());
        }
        if (rowSet.getDate("to_date") != null) {
            reservation.setToDate(rowSet.getDate("to_date").toLocalDate());
        }
        if (rowSet.getDate("create_date") != null) {
            reservation.setCreateDate(rowSet.getDate("create_date").toLocalDate());
        }
        return reservation;
    }


}
