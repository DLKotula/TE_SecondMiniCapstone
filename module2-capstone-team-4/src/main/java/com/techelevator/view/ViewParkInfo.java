package com.techelevator.view;

import com.techelevator.model.Park;

import java.util.List;
import java.util.Scanner;

public class ViewParkInfo {
    private final Scanner scanner;

    public ViewParkInfo() {
        this.scanner = new Scanner(System.in);
    }

    public String showParkInformationScreen(Park park) {
        System.out.println("*** Park Information ***");
        System.out.printf("%s%s\n", park.getName(), " National Park");
        System.out.printf("%-20s| %s\n", "Location:", park.getLocation());
        System.out.printf("%-20s| %s\n", "Established:", park.getEstablishDate());
        System.out.printf("%-20s| %,d sq km\n", "Area:", park.getArea());
        System.out.printf("%-20s| %,d\n", "Annual Visitors:", park.getVisitors());
        System.out.println();
        System.out.println(park.getDescription());
        System.out.println();

        System.out.println("*** Select a Command ***");
        System.out.println("(1) View Campgrounds");
        System.out.println("(2) Search for Reservation");
        System.out.println("(3) Return to Previous Screen");
        System.out.println();
        System.out.print("Please enter an option --> ");
        String userInput = "";
        boolean isValid = false;
        while(!isValid){
            userInput = scanner.nextLine();
            isValid = showInvalidMenuOption(userInput);
        }
        System.out.println();
        return userInput;

    }

    public void closeScanner() {
        scanner.close();
    }

    public void quitMessage() {
        System.out.println("Thank you for visiting, I hope you have a nice day!");
    }

    public boolean showInvalidMenuOption(String userInput) {
        if(!userInput.equals("1") /*&& !userInput.equals("2")*/ && !userInput.equals("3")) {
            System.out.println("Invalid menu selection");
            System.out.print("Please enter an option --> ");
            return false;
        }
        return true;
    }
}
