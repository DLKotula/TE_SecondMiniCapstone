package com.techelevator.view;

import com.techelevator.model.Park;

import java.util.List;
import java.util.Scanner;

public class ViewPark {
    private final Scanner scanner;

    public ViewPark() {
        this.scanner = new Scanner(System.in);
    }

    public String showParksScreen(List<Park> parks) {
        System.out.println("Welcome to the National Park Campsite Reservation System!");
        System.out.println();
        for(int i = 0; i < parks.size(); i++) {
            System.out.println("(" + (i+1) + ") " + parks.get(i).getName());
//            if(i == parks.size() - 1) {
//                System.out.println("(" + i + 1 + ") ...");
//            }
        }
        System.out.println("(Q) Quit");
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
        if(!userInput.equalsIgnoreCase("q") && !userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")) {
            System.out.println();
            System.out.println("Invalid menu selection...");
            System.out.print("Please enter an option --> ");
            return false;
        }
        return true;
    }

    public String startAgain() {
        String userInput = "";
        while(true) {
            System.out.print("Reserve another stay? (y/n) --> ");
            userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("n")) {
                break;
            } else {
                System.out.println();
                System.out.println("Invalid response...");
                System.out.println("Please try again");
                System.out.println();
            }
        }
        return userInput;
    }
}
