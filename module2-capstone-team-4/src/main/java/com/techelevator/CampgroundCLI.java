package com.techelevator;

import javax.sql.DataSource;

import com.techelevator.dao.JdbcCampgroundDao;
import com.techelevator.dao.JdbcParkDao;
import com.techelevator.dao.JdbcReservationDao;
import com.techelevator.dao.JdbcSiteDao;
import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.view.*;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;

public class CampgroundCLI {
	JdbcCampgroundDao jdbcCampgroundDao;
	JdbcParkDao jdbcParkDao;
	JdbcSiteDao jdbcSiteDao;
	JdbcReservationDao jdbcReservationDao;
	ViewParkCampgrounds viewParkCampgrounds;
	ViewParkInfo viewParkInfo;
	ViewPark viewParks;
	ViewCampgroundReservation viewCampgroundReservation;

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		ReservationSystem application = new ReservationSystem();
		welcomeDisplay();
		application.run(dataSource);

	}

	public CampgroundCLI(DataSource datasource) {

	}

	public static void welcomeDisplay() {
		System.out.println("                   _______          __  .__                     .__    __________               __                       ");
		System.out.println("                   \\      \\ _____ _/  |_|__| ____   ____ _____  |  |   \\______   \\_____ _______|  | __                   ");
		System.out.println("                   /   |   \\\\__  \\\\   __\\  |/  _ \\ /    \\\\__  \\ |  |    |     ___/\\__  \\\\_  __ \\  |/ /                   ");
		System.out.println("                  /    |    \\/ __ \\|  | |  (  <_> )   |  \\/ __ \\|  |__  |    |     / __ \\|  | \\/    <                    ");
		System.out.println("                  \\____|__  (____  /__| |__|\\____/|___|  (____  /____/  |____|    (____  /__|  |__|_ \\                   ");
		System.out.println("                          \\/     \\/                    \\/     \\/                       \\/           \\/                   ");
		System.out.println("__________                                          __  .__                  _________               __                  ");
		System.out.println("\\______   \\ ____   ______ ______________  _______ _/  |_|__| ____   ____    /   _____/__.__. _______/  |_  ____   _____  ");
		System.out.println(" |       _// __ \\ /  ___// __ \\_  __ \\  \\/ /\\__  \\\\   __\\  |/  _ \\ /    \\   \\_____  <   |  |/  ___/\\   __\\/ __ \\ /     \\ ");
		System.out.println(" |    |   \\  ___/ \\___ \\\\  ___/|  | \\/\\   /  / __ \\|  | |  (  <_> )   |  \\  /        \\___  |\\___ \\  |  | \\  ___/|  Y Y  \\");
		System.out.println(" |____|_  /\\___  >____  >\\___  >__|    \\_/  (____  /__| |__|\\____/|___|  / /_______  / ____/____  > |__|  \\___  >__|_|  /");
		System.out.println("        \\/     \\/     \\/     \\/                  \\/                    \\/          \\/\\/         \\/            \\/      \\/ ");
	}
}
