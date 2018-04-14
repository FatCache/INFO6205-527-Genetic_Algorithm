package com.main;

import java.util.Collections;

public class Test_Run {
	public static void main(String[] args) {
		
		CityManager ciManager = new CityManager();
		
		City city1 = new City("Boston", 0,0);
		
		City city2 = new City("NYC", 1110,100);
		City city3 = new City("SF", -100,30);
		City city4 = new City("Seattle", 100,3020);
		City city5 = new City("Beijing", 500,250);
		City city6 = new City("Tokyo", 530,-450);
		City city7 = new City("Riyadh", 1130,-450);

		ciManager.addCity(city1);
		ciManager.addCity(city2);
		ciManager.addCity(city3);
		ciManager.addCity(city4);
		ciManager.addCity(city5);
		ciManager.addCity(city7);
		
		Population p = new Population(5, 4);
		int testRuns = 50;
		
		for(int i = 0; i < testRuns;i++) {
			System.out.println("****Test Run**** -->" + i);
			p.nextGen();
			p.show();
			System.out.println(p.getMostFitRoute().getDistance());
			//p.getMostFitRoute().show();
			//System.out.println("Most Fit -> " + p.getMostFitRoute().getFittness());
			
		}
		
		
//		for(int i = 0; i<10;i++) {
//			p.nextGen();
//		}
		
//		Route route = new Route(true);
//		System.out.println("Printing route now");
//		route.show();
//		
//		route.generateIndividualRoute();
//		System.out.println("Shuffling");
//		route.show();
//		
//		System.out.println("Calling Sort Function now -> ");
//		//Collections.sort(route.getRoute());
//		
//		route.sortRoute();
//		route.show();
		
		/*ciManager.show();
		
		//Collections.shuffle(ciManager.getCityManger());
		
		
		ciManager.show();*/
		
		
		

	}
}
