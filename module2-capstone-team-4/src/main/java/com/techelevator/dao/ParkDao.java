package com.techelevator.dao;
import com.techelevator.model.Park;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;
public interface ParkDao {
    List<Park> listAllParks();
    List<Park> getParkInformation(Park name);
}