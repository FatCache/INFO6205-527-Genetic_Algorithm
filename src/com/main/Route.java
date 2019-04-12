/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author abdusamed
 */
public class Route {
    private final ArrayList<City> route;
    
	double fittness = 0.0; // each route begins with 0 fitness

	public Route(boolean initalize) {
		if (initalize) {
			route = new ArrayList<>();
			for (int i = 0; i < CityManager.getCityManger().size(); i++) {
                                // Populate the route City objects
				route.add(CityManager.getCityManger().get(i)); 
			}
		} else {
			route = new ArrayList<>(CityManager.getCityManger().size());
			for (int i = 0; i < CityManager.getCityManger().size(); i++) {
				route.add(null);
			}
		}
	}
	
	/**
	 * Calculate the entire distance if traveling salesman goes to each 
         * city and return. This is its fitness value on return
	 * @return
	 */
	public double getDistance() {

		double sum = 0.0;

		for (int i = 0; i < route.size() - 1; i++) {
			if (route.get(i) == null)
				break;
			else { // Elucidian distance between two cities
				int x1 = route.get(i).getX();
				int x2 = route.get(i + 1).getX();
				int y1 = route.get(i).getY();
				int y2 = route.get(i + 1).getY();

				sum = sum + Math.sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
			}
		}
		return sum;
	}
	

	public void generateIndividualRoute() {
		Collections.shuffle(route);
		this.fittness = getDistance();
	}

	public void sortRoute() {
		Collections.sort(route);
	}

	public int getSize() {
		return route.size();
	}

	public double getFittness() {
		return fittness;
	}

	public void setFittness() {
		this.fittness = getDistance();
	}

	public ArrayList<City> getRoute() {
		return route;
	}

	public void show() {
		if (route.get(0) == null) {
			System.out.println("No city to show");
	
		} else {
                    
			System.out.print("{");
                        route.forEach((city) -> {
                            System.out.print(city.getIndex() + ",");
                    });
			System.out.print("}");

		}
	}
	
}
