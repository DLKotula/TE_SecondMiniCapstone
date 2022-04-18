package com.techelevator.view;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;

import java.time.Month;
import java.util.List;
import java.util.Scanner;

public class ViewParkCampgrounds {
    private final Scanner scanner;

    public ViewParkCampgrounds() {
        this.scanner = new Scanner(System.in);
    }

    public String showParkCampgrounds(List<Campground> campgrounds, Park park) {
        System.out.println("*** Park Campgrounds ***");
        System.out.println(park.getName() + " Campgrounds");
        System.out.println();
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

        System.out.println("*** Select a Command ***");
        System.out.println("(1) Search for Available Reservation");
        System.out.println("(2) Return to Previous Screen");
        System.out.println();

        System.out.print("Please enter an option --> ");
        String userInput = "";
        boolean isValid = false;
        while (!isValid) {
            userInput = scanner.nextLine();
            isValid = showInvalidMenuOption(userInput);
        }
        System.out.println();
        return userInput;
    }

    public void closeScanner() {
        scanner.close();
    }


    public boolean showInvalidMenuOption(String userInput) {
        if (!userInput.equals("1") && !userInput.equals("2")) {
            System.out.println("Invalid menu selection");
            System.out.print("Please enter an option --> ");
            return false;
        }
        return true;
    }
}
