package com.techelevator.dao;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;

import java.util.List;

public interface CampgroundDao {
    List<Campground> listAllCampgrounds();

    List<Campground> selectCampgroundsFromPark(Park park);

    List<Campground> checkIsCampgroundAvailable(Campground campground_id, String userMonth);
}
