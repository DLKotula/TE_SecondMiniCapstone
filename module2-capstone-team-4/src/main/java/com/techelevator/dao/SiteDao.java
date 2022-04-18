package com.techelevator.dao;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Site;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface SiteDao {

    List<Site> listAllSites();

    List<Site> selectSitesFromCampground(Campground campground);

    List<Site> siteAvailabilityFromReservations(Long parkId, Long campgroundId,
                                                LocalDate arrivalDate, LocalDate departureDate);
}
