package com.techelevator.view;

import com.techelevator.model.Campground;
import com.techelevator.model.Site;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class ViewCampgroundReservation {
    private final Scanner scanner;


    public ViewCampgroundReservation() {
        this.scanner = new Scanner(System.in);
    }

    // v Option 1 Display v
    public String[] showSearchCampReservationScreen(List<Campground> campgrounds) {
        System.out.println("*** Search for Campground Reservation ***");

        System.out.printf("%-5s%-35s%-15s%-15s%-10s\n", "", "Name", "Open", "Close", "Daily Fee");
        for (int i = 0; i < campgrounds.size(); i++) {
            System.out.printf("#%-4d%-35s%-15s%-15s$%-10.2s\n", i + 1,
                    campgrounds.get(i).getName(), Month.of(Integer.parseInt(campgrounds.get(i).getOpenFromMm())),
                    Month.of(Integer.parseInt(campgrounds.get(i).getOpenToMm())),
                    campgrounds.get(i).getDailyFee());

            if (i == campgrounds.size() - 1) {
                System.out.println();
            }
        }

        String[] selection = campgroundSelection();
        return selection;
    }

    public String[] campgroundSelection() {
        String[] reservationArray = new String[3];
        LocalDate dateParse = LocalDate.now();
        System.out.print("Which campground (enter 0 to cancel)? --> ");
        reservationArray[0] = scanner.next();
        scanner.nextLine();

        boolean isDateVerified = false;
        if (reservationArray[0].equals("0")) {
            reservationArray[1] = "";
            reservationArray[2] = "";
            return reservationArray;
        }
        while (!isDateVerified) {
            System.out.print("What is the arrival date? (YYYY-MM-DD) --> ");
            try {
                reservationArray[1] = scanner.next();
                scanner.nextLine();
                dateParse = (LocalDate.parse(reservationArray[1]));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date entered.");
                continue;
            }
            isDateVerified = verifyArrivalDateEntry(dateParse);
        }
        isDateVerified = false;
        while (!isDateVerified) {
            System.out.print("What is the departure date? (YYYY/MM/DD) --> ");

            try {
                reservationArray[2] = scanner.next();
                scanner.nextLine();
                dateParse = (LocalDate.parse(reservationArray[2]));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date entered.");
                continue;
            }
            isDateVerified = verifyDepartureDateEntry(LocalDate.parse(reservationArray[1]), LocalDate.parse(reservationArray[2]));
        }
        return reservationArray;
    }


    public String[] reservationSearchResultsScreen(List<Site> sites, Campground campground, String[] days) {//, String lengthStay) {
        System.out.println("*** Results Matching Your Search Criteria ***");
        System.out.println();
        System.out.printf("%-10s%-12s%-15s%-15s%-10s%-5s\n", "Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utility", "Cost");

        LocalDate arrivalDate = LocalDate.parse(days[1]);
        LocalDate departureDate = LocalDate.parse(days[2]);
        Long lengthStay = ChronoUnit.DAYS.between(arrivalDate, departureDate);

        Double dailyFee = Double.parseDouble(campground.getDailyFee());

        for (int i = 0; i < sites.size(); i++) {
            System.out.printf("%-10d%-12d%-15b%-15d%-10b$%-4.2f\n", sites.get(i).getSiteNumber(),
                    sites.get(i).getMaxOccupancy(), sites.get(i).getAccessible(), sites.get(i).getMaxRvLength(), sites.get(i).getUtilities(), (lengthStay * dailyFee));
        }
        System.out.println();
        return siteSelection(sites);
    }
    // ^Option 1 Display^


    public String[] parkWideReservationSearchBonus(List<Site> sites, Campground campground) {//, String lengthStay) {
        System.out.println("*** Results Matching Your Search Criteria ***");
        System.out.println();
        System.out.printf("%-15s%-10s%-10s%-15s%-10s%-10s%-5s\n", "Campground", "Site No.", "Max Occup.", "Accessible?", "RV Len", "Utility", "Cost");
        for (int i = 0; i < sites.size(); i++) {
            System.out.printf("%-15d%-10d%-10b%-15d%-10b$%-4.2s\n", sites.get(i).getSiteNumber(),
                    sites.get(i).getMaxOccupancy(), sites.get(i).getAccessible(), sites.get(i).getMaxRvLength(), sites.get(i).getUtilities(), campground.getDailyFee());

            if (i == sites.size() - 1) {
                System.out.println();
            }
        }
        return siteSelection(sites);
    }

    //Need to make this for park campgrounds page
    //Need to make sure if the user selects 2 it returns to previous screen
    //line 70-77 on reservation system file

    public String[] parkWideCampgroundSelectionBonus() {
        String[] reservationArray = new String[2];
        boolean isDateVerified = false;
        while (!isDateVerified) {
            System.out.print("What is the arrival date? (YYYY-MM-DD) --> ");
            reservationArray[0] = scanner.next();
            scanner.nextLine();
            isDateVerified = verifyArrivalDateEntry((LocalDate.parse(reservationArray[1])));
        }
        isDateVerified = false;
        while (!isDateVerified) {
            System.out.print("What is the departure date? (YYYY/MM/DD) --> ");
            reservationArray[1] = scanner.next();
            scanner.nextLine();

            isDateVerified = verifyDepartureDateEntry(LocalDate.parse(reservationArray[1]), LocalDate.parse(reservationArray[2]));
        }
        return reservationArray;
    }

//-------------------------3/18/22-----------------------------------
// need to review the date checks for where is commented in Reservation system

    public Boolean verifyArrivalDateEntry(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        if (!date.isBefore(currentDate)) {
            return true;
        } else {
            System.out.println("Arrival date is prior to the current date.");
            System.out.println("Please enter a valid arrival date.");
            return false;

        }
    }

    public Boolean verifyDepartureDateEntry(LocalDate arrivalDate, LocalDate departureDate) {
        if (departureDate.isAfter(arrivalDate)) {
            return true;
        } else {
            System.out.println("Departure date is prior to your set arrival date.");
            System.out.println("Please enter a valid departure date.");
            return false;
        }
    }


    // Applies to both Paths(Option 1 and Bonus)
    public String[] siteSelection(List<Site> sites) {
        String[] reservationArray = new String[2];
        boolean isValid = false;
        while (!isValid) {
            System.out.print("Which site should be reserved? (enter 0 to cancel)? --> ");
            reservationArray[0] = scanner.nextLine();
            System.out.println();
            if (reservationArray[0].equals("0")) {
                reservationArray[1] = "";
                return reservationArray;
            }
            isValid = showInvalidSiteSelection(reservationArray[0], sites);
        }

        System.out.print("What name should the reservation be made under? --> ");
        reservationArray[1] = scanner.nextLine();

        return reservationArray;
    }

    public boolean showInvalidSiteSelection(String userInput, List<Site> sites) {
        for (int i = 0; i < sites.size(); i++) {
            Site site = sites.get(i);

            if (site.getSiteNumber() == (Long.parseLong(userInput))) {
                return true;
            }

        }
        System.out.println();
        System.out.println("Invalid site selection...");
        return false;

    }

}




