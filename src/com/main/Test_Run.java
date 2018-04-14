package com.main;

import java.util.Collections;

public class Test_Run {
	public static void main(String[] args) {
		
		CityManager ciManager = new CityManager();
		
		City city1 = new City("Boston", 0,0);
		
		City city2 = new City("NYC", 0,100);
		City city3 = new City("SF", -100,0);
		City city4 = new City("Seattle", 100,100);
		City city5 = new City("Biejing", 500,250);
		City city6 = new City("Tokyo", 530,-450);

		ciManager.addCity(city1);
		ciManager.addCity(city2);
		ciManager.addCity(city3);
		ciManager.addCity(city4);
		ciManager.addCity(city5);
		ciManager.addCity(city6);
		
		Population p = new Population(15, 3);
		int testRuns = 25;
		
		for(int i = 0; i < testRuns;i++) {
			p.nextGen();
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
