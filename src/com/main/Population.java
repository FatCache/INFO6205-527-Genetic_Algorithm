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
	double mutatePercentage;
	Route[] routes;
	Route mostFitRoute;
	Random rnd = new Random();
	int tournamentSize;

	public Population(int size, int tournamentSize, double mP) { // Say we want to have 50 routes
		this.tournamentSize = tournamentSize;
		this.mutatePercentage = mP;
		routes = new Route[size]; // Initialize the list of 50 size
		for (int i = 0; i < routes.length; i++) {
			Route route = new Route(true);
			route.generateIndividualRoute();
			
			routes[i] = route;
		}

	}

	// Get most fit, a better way could be to use priority queue i.e most fit at the
	// top of the rack ...
	public Route getFittestRouteTournament() {
		// Tournament Selection
		Route routeFittest = new Route(false); // Has fitness = 0.0 as an initializer

		Random rnd = new Random();
		Route[] tournamentList = new Route[tournamentSize];
		for (int i = 0; i < tournamentSize; i++) { // Populate tournamentList with routes
			tournamentList[i] = routes[rnd.nextInt(routes.length)]; // Randomly pick routes from the entire population
		}

		// Tournament Time!
		for (Route route : tournamentList) {
			//System.out.println(route.getFittness());
			if (routeFittest.getFittness() == 0.0)
				routeFittest = route;
			else {
				if (route.getFittness() <= routeFittest.getFittness())
					routeFittest = route; // We have a new fit!
			}
		}
		
		return routeFittest;
		
	}

	public void nextGen() throws CloneNotSupportedException {
		show();
		refreshFitness();
		
		
		Route routeA = new Route(false); // Parent 1
		Route dummuyRouteA = getFittestRouteTournament();
		for(int i = 0; i < routeA.getSize();i++) {
			routeA.getRoute().set(i, dummuyRouteA.getRoute().get(i));
		}
//		routeA = getFittestRouteTournament().clone();
		System.out.print("Parent A Before Sort - ");
		routeA.show(); System.out.print(""+routeA.getFittness());
		System.out.println("");
		Route dummuyRouteB = getFittestRouteTournament();
		Route routeB = new Route(false);//Parent 2
		for(int i = 0; i < routeB.getSize();i++) {
			routeB.getRoute().set(i, dummuyRouteB.getRoute().get(i));
		}
		
		routeB.setFittness();
//		routeB = getFittestRouteTournament().clone(); // Parent 2
		routeA.sortRoute(routeA);
		routeA.setFittness();
		System.out.print("Parent A - ");
		routeA.show(); System.out.print(""+routeA.getFittness());
		System.out.println("");
		System.out.print("Parent B - ");
		routeB.show(); System.out.print(""+routeB.getFittness());
		System.out.println("");
		int start, end = 0;
		Route child = new Route(false); // Need nulled filled arrsay

		// Crossover Process 1) Sort 2) Pick remaining values
		// Sort
//		System.out.println("Route A Before:");
//		routeA.show();
//		
//		System.out.println("Route A After:");
		
//		routeA.show();
//		System.out.println("");

		while (true) { // Start should be less than end
			start = rnd.nextInt(routeA.getSize());
			end = rnd.nextInt(routeA.getSize());
			if (start < end) {
				break;
			}
		}
		System.out.println("START:"+start+":END:"+end);

		for (int i = start; i <= end; i++) {
			child.getRoute().set(i, routeA.getRoute().get(i));
		}

		// Fill null values with cities from second parent
		int nullCounter = 0;
		for (int i = 0; i < routeB.getRoute().size(); i++) {
			if (!ifDuplicate(routeB, child, i)) {
				for (; nullCounter < routeB.getRoute().size() + 1; nullCounter++) { // Find a null location
					if (child.getRoute().get(nullCounter) == null) {
						child.getRoute().set(nullCounter, routeB.getRoute().get(i));
						break;
					}
				}
			}
		}

		// New Child Populated with cross over. Time to mutate

		// Child generation completed
		// Steady - state

		// Child is put back into the population IF and ONLY IF it's more fit from least
		// fit route in pop
		child = mutate(child, start, end);
		int leastFitRouteid = getLeastFitRouteId();
		// show();
		// System.out.println("Location Index -> " + leastFitRouteid);

		child.setFittness();

		// System.out.println("");
//		System.out.print("Child must not be perfect! - -> ");
		System.out.print("Child Gen     -> ");
		child.show();
		System.out.print(":" + child.getFittness());
		System.out.println("");
		getMostFitRoute();

		// System.out.println("Least Fit Fitness -> " +
		// routes[leastFitRouteid].getFittness());
		// System.out.print("Least - -> ");
		// routes[leastFitRouteid].show();

		if (child.getFittness() < routes[leastFitRouteid].getFittness()) {
			routes[leastFitRouteid] = child;
			//System.out.println("Child was more fit. Population updated");
		}else {
			System.out.println("Nope");
		}
		// System.out.println("After replacement");
		// show();

//		System.out.println("End of Gen");
		// else {
		// System.out.println("Child was not fit. Population not updated");
		// }


	}
	
	public Route mutate(Route child, int start, int end) {
		double toMutateorNotTo = rnd.nextFloat();
		if (toMutateorNotTo < mutatePercentage) {
			child.show();
			child.setFittness();
			System.out.print(":"+child.getFittness());
//			start = rnd.nextInt(child.getSize());
//			end = rnd.nextInt(child.getSize());
			Collections.swap(child.getRoute(), start, end);
			System.out.println("Mutate Occured");
			child.show();
			System.out.println("");
		}
		return child;
	}

	public int getLeastFitRouteId() throws CloneNotSupportedException {
		Route leastfitroute = routes[0]; // Initializing the routes
		int count = -1;
		int id = 0;
		for (Route route : routes) {
			count++;
			if (route.getFittness() > leastfitroute.getFittness()) {
				leastfitroute = route.clone();
				id = count;
			}
		}
		// System.out.println("Least fit in pop -> " + leastfitroute.getFittness());
		// System.out.println(id);
		return id;
	}

	public boolean ifDuplicate(Route routeB, Route child, int x) {
		City cityTest = routeB.getRoute().get(x);
		for (int i = 0; i < routeB.getSize(); i++) {
			if (child.getRoute().get(i) != null) { // If child id not empty means city exists at index
				// if (child.getRoute().get(i).getIndex() == cityTest.getIndex()) // Compare the
				// two dex [Better code below]
				if (child.getRoute().get(i).compareTo(cityTest) == 0) // Should return 0 if identical i.e both their
					// index are equivalent
					return true; // City already exist in the array
			}
		}
		return false;

	}

	public void show() {
		for (Route route : routes) {

			route.show();
			System.out.print(route.getFittness());
			System.out.println("");
		}
	}

	public void getMostFitRoute() throws CloneNotSupportedException { // Useless ... should return the best!
		Route mostFitRoute = routes[0].clone(); // Initializing the routes
		for (Route route : routes) {
//			System.out.println("Compare route with most" + route.getFittness() + ":" + mostFitRoute.getFittness());

			if (route.getFittness() < mostFitRoute.getFittness()) {
				mostFitRoute = route.clone();
			}
		}
		System.out.print("Fittest Route -> ");
		mostFitRoute.show();
		System.out.print(mostFitRoute.getFittness());
		System.out.println("");
	}
	
	public void refreshFitness() {
		for(Route route:routes) {
			route.setFittness();
		}
	}

}