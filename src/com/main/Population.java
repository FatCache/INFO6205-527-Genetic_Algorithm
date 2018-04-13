/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author abdusamed
 */
public class Population { // Composed of many routes
	double mutatePercentage = 0.01;
	Route[] routes;
	Random rnd = new Random();
	int tournamentSize; 
	
	public Population(int size, int tournamentSize) { // Say we want to have 50 routes
		this.tournamentSize = tournamentSize;
		routes = new Route[size]; // Initialize the list of 50 size
		for (int i = 0; i < routes.length; i++) {
			Route route = new Route(true);
			route.generateIndividualRoute();
			//System.out.println(route.getFittness());
			
			routes[i] = route;
		}
	}

	// Get most fit, a better way could be to use priority queue i.e most fit at the
	// top of the rack ...
	public Route getFittestRoute() {
		// Tournament Selection
		Route routeFittest = new Route(false); // Has fitness = 0.0 as an initializer

		Random rnd = new Random();
		Route[] tournamentList = new Route[tournamentSize];
		for (int i = 0; i < tournamentSize; i++) { // Populate tournamentList with routes
			tournamentList[i] = routes[rnd.nextInt(routes.length)]; // Randomly pick routes from the entire population
		}

		// Tournament Time!
		for (Route route : tournamentList) {
			if(routeFittest.getFittness() == 0.0) routeFittest = route;
			else {
			if (route.getFittness() <= routeFittest.getFittness())
				routeFittest = route; // We have a new fit!
			}
		}

		return routeFittest;
	}

	public void nextGen() {
		Route routeA = getFittestRoute(); // Parent 1
		System.out.println("Route A and Route B");
		routeA.show();
		Route routeB = getFittestRoute(); // Parent 2
		routeB.show();
		int start, end = 0;
		Route child = new Route(false); // Need nulled filled array

		// Crossover - 1) Sort 2) Pick remaining values
		// Sort
		routeA.sortRoute();
		// Start index < end index
		while (true) { // Start should be less than end
			start = rnd.nextInt(routeA.getSize());
			end = rnd.nextInt(routeA.getSize());
			if (start < end) {
				break;
			}
		}

		for (int i = start; i < end; i++) {
			child.getRoute().add(i, routeA.getRoute().get(i));
		}

		// Fill null values with cities from second parent
		int nullCounter = 0;
		for (int i = 0; i < routeB.getRoute().size(); i++) {
			if (child.getRoute().get(nullCounter) == null) {
				if (!ifDuplicate(routeB, child, i)) {
					child.getRoute().add(nullCounter, routeB.getRoute().get(i));
					nullCounter++;
				}
			}
		}

		// New Child Populated with cross over. Time to mutate
		double toMutateorNotTo = rnd.nextFloat();
		if (toMutateorNotTo < mutatePercentage) {
			start = rnd.nextInt(routeA.getSize());
			end = rnd.nextInt(routeA.getSize());
			Collections.swap(child.getRoute(), start, end);
		}

		// Child generation completed
		// Steady - state
		// Route is replaced in the population if child is more fit.
		int leastFitRouteid = getLeastFitRouteId();
		if (child.getFittness() <= routes[leastFitRouteid].getFittness()) {
			routes[leastFitRouteid] = child; // Replace it with more fit child
			System.out.println("Child was fit. Population updated");
		} else
			System.out.println("Child was not fit. Population not updated");

	}

	public int getLeastFitRouteId() {
		Route leastfitroute = routes[0];
		int count = -1;
		int id = -1;
		for (Route route : routes) {
			count++;
			if (route.getFittness()< leastfitroute.getFittness()) {
				leastfitroute = route;
				id = count;
			}
		}
		return id;
	}

	public boolean ifDuplicate(Route routeB, Route child, int x) {
		City cityTest = routeB.getRoute().get(x);
		for (int i = 0; i < child.getSize(); i++) {
			if (child.getRoute().get(i) != null) { // If child id not empty means city exists at index
				//if (child.getRoute().get(i).getIndex() == cityTest.getIndex()) // Compare the two dex [Better code below]
				if (child.getRoute().get(i).compareTo(cityTest) == 0) // Should return 0 if identical i.e both their index are equivalent 
					return true; // City already exist in the array
			}
		}
		return false;

	}

}