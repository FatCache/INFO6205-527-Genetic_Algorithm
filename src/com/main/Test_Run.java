package com.main;

/**
 *
 * @author yanfei
 */
public class Test_Run {
	public static void main(String[] args){

		CityManager ciManager = new CityManager();

		City city1 = new City("City A", 60, 200);
		City city2 = new City("City B", 180, 200);
		City city3 = new City("City C", 80, 130);
		City city4 = new City("City D", 20, 160);
		City city5 = new City("City E", 100, 160);
		City city6 = new City("City F", 200, 160);
		City city7 = new City("City G", 140, 140);
		City city8 = new City("City H", 40, 120);
		City city9 = new City("City I", 40, 120);
		City city10 = new City("City J", 100, 120);
		City city11 = new City("City K", 180, 100);
		City city12 = new City("City L", 60, 80);
		City city13 = new City("City M", 120, 80);
		City city14 = new City("City N", 180, 60);
		City city15 = new City("City O", 20, 80);
		City city16 = new City("City P", 100, 40);
		City city17 = new City("City Q", 200, 40);
		City city18 = new City("City R", 20, 20);
		City city19 = new City("City S", 60, 20);
		City city20 = new City("City T", 160, 20);

		ciManager.addCity(city1);
		ciManager.addCity(city2);
		ciManager.addCity(city3);
		ciManager.addCity(city4);
		ciManager.addCity(city5);
		ciManager.addCity(city6);
		ciManager.addCity(city7);
		ciManager.addCity(city8);
		ciManager.addCity(city9);
		ciManager.addCity(city10);
		ciManager.addCity(city11);
		ciManager.addCity(city12);
		ciManager.addCity(city13);
		ciManager.addCity(city14);
		ciManager.addCity(city15);
		ciManager.addCity(city16);
		ciManager.addCity(city17);
		ciManager.addCity(city18);
		ciManager.addCity(city19);
		ciManager.addCity(city20);

		Population p = new Population(50, 5, 0.015);
		int testRuns = 50;

		for (int i = 0; i < testRuns; i++) {
			System.out.println("****Test Run**** --> " + i);
			p.nextGen();
			p.show();

		}
		p.getMostFitRoute();

	}
}
