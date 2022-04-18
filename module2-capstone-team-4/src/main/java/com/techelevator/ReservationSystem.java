package com.techelevator;

import com.techelevator.dao.JdbcCampgroundDao;
import com.techelevator.dao.JdbcParkDao;
import com.techelevator.dao.JdbcReservationDao;
import com.techelevator.dao.JdbcSiteDao;
import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;
import com.techelevator.view.*;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationSystem {

    private JdbcCampgroundDao jdbcCampgroundDao;
    private JdbcParkDao jdbcParkDao;
    private JdbcSiteDao jdbcSiteDao;
    private JdbcReservationDao jdbcReservationDao;
    private ViewParkCampgrounds viewParkCampgrounds;
    private ViewParkInfo viewParkInfo;
    private ViewPark viewPark;
    private ViewCampgroundReservation viewCampgroundReservation;

    private List<Park> parks;
    private List<Campground> campgrounds = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private List<Site> sites;
    private String userResponse;
    private Park userPark;
    private Campground userCampground;
    private Site userSite;
    private Long userReservationID;
    private boolean isBroken = false;

    private String[] userStringCampgroundSelection = new String[3];
    private String[] userStringReservation = new String[2];


    public void run(DataSource dataSource) {
        while (true) {
            runParksMenu(dataSource);
            String startAgain = viewPark.startAgain();
            if (startAgain.equalsIgnoreCase("n")) {
                quit();
            }
        }
    }

    public void runParksMenu(DataSource dataSource) {
        jdbcParkDao = new JdbcParkDao(dataSource);
        viewPark = new ViewPark();
        parks = jdbcParkDao.listAllParks();
        userResponse = viewPark.showParksScreen(parks);

        if (userResponse.equalsIgnoreCase("q")) {
            quit();
        }

        userPark = parks.get(Integer.sum(Integer.parseInt(userResponse), -1));
        viewParkInfo = new ViewParkInfo();
        userResponse = viewParkInfo.showParkInformationScreen(userPark);
        parkMenuController(dataSource);
    }

    public void parkMenuController(DataSource dataSource) {
        while (true) {
            jdbcCampgroundDao = new JdbcCampgroundDao(dataSource);
            viewParkCampgrounds = new ViewParkCampgrounds();
            campgrounds = jdbcCampgroundDao.selectCampgroundsFromPark(userPark);

            if (userResponse.equals("1")) {
                userResponse = viewParkCampgrounds.showParkCampgrounds(campgrounds, userPark);
                reservationMenuController(dataSource);
                break;

            } else if (userResponse.equals("2")) {
                campgroundReservationMenuController(dataSource);
                break;

            } else if (userResponse.equals("3")) {
                runParksMenu(dataSource);
                break;

            } else {
                System.out.println("Invalid selection made!");
                System.out.println("Please select 1, 2, or 3.");
                userResponse = viewParkInfo.showParkInformationScreen(userPark);
            }
        }
    }

    public void reservationMenuController(DataSource dataSource) {
        while (true) {
            jdbcReservationDao = new JdbcReservationDao(dataSource);
            viewCampgroundReservation = new ViewCampgroundReservation();
            if (userResponse.equals("1")) {
                userStringCampgroundSelection = viewCampgroundReservation.showSearchCampReservationScreen(campgrounds);
                if (userStringCampgroundSelection[0].equals("0")) {
                    runParksMenu(dataSource);
                    break;
                }
                jdbcSiteDao = new JdbcSiteDao(dataSource);
                userCampground = campgrounds.get(Integer.sum(Integer.parseInt(userStringCampgroundSelection[0]), -1));
                sites = jdbcSiteDao.siteAvailabilityFromReservations(userPark.getParkId(), userCampground.getCampgroundId(),
                        LocalDate.parse(userStringCampgroundSelection[1]), LocalDate.parse(userStringCampgroundSelection[2]));
                campgroundReservationDisplay(dataSource);
                bookReservation(dataSource);
                break;
            } else if (userResponse.equals("2")) {
                userResponse = viewParkInfo.showParkInformationScreen(userPark);
                parkMenuController(dataSource);
                break;
            }
            break;
        }


    }

    public void campgroundReservationDisplay(DataSource dataSource) {
        userStringReservation = viewCampgroundReservation.reservationSearchResultsScreen(sites, userCampground, userStringCampgroundSelection);
        while (true) {
            if (userStringReservation[0].equals("0")) {
                reservationMenuController(dataSource);
                break;
            } else {
                break;
            }
        }

        for(Site site: sites){
            if(site.getSiteNumber() == Integer.parseInt(userStringReservation[0])){
                userSite = site;
                break;
            }
        }
    }

    // ^ Option 1 Path ^

    // v Bonus option Path v
    public void campgroundReservationMenuController(DataSource dataSource) { //gdvsxcbsjhsabhjscgbjkn- Bonus
        jdbcReservationDao = new JdbcReservationDao(dataSource);
        viewCampgroundReservation = new ViewCampgroundReservation();
        jdbcSiteDao = new JdbcSiteDao(dataSource);
        userStringReservation = viewCampgroundReservation.showSearchCampReservationScreen(campgrounds);
        userCampground = campgrounds.get(Integer.sum(Integer.parseInt(userStringCampgroundSelection[0]), -1));

        sites = jdbcSiteDao.siteAvailabilityFromReservations(userPark.getParkId(), userCampground.getCampgroundId(), LocalDate.parse(userStringReservation[1]), LocalDate.parse(userStringReservation[2]));

        viewCampgroundReservation.reservationSearchResultsScreen(sites, userCampground, userStringCampgroundSelection);

    }
    // ^ Bonus Option Path ^

    public void bookReservation(DataSource dataSource) {
        jdbcReservationDao = new JdbcReservationDao(dataSource);
        reservations = jdbcReservationDao.selectReservation(userSite);
        userReservationID = jdbcReservationDao.createReservation(userSite, userStringReservation[1], LocalDate.parse(userStringCampgroundSelection[1]),
                LocalDate.parse(userStringCampgroundSelection[2]));
        System.out.println();
        System.out.printf("The reservation has been made and the confirmation ID is: %d\n", userReservationID);
        System.out.println("Thank you for reserving a stay with us!");
        System.out.println();

    }


    public void quit() {
        System.out.println("Thank you for using the National Park Reservation System...");
        System.out.println();
        System.out.println("Have A Nice Day");
        System.out.println();

        System.out.println();
        System.out.println("...POWERING DOWN...");
        System.exit(0);
    }
}
