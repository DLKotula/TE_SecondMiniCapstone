package com.techelevator.dao;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface ReservationDao {

    List<Reservation> selectReservation(Site site);

    Reservation getReservation(long reservationId);

    Long createReservation(Site site, String name, LocalDate arrivalDate, LocalDate departureDate);

}
