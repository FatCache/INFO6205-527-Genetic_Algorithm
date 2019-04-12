/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

// Collection of generated routes
public class Population {
        
	double mutatePercentage;
	Route[] routes;
	Route mostFitRoute;
	Random rnd = new Random();
	int tournamentSize;

	/**
	 * Initialization method
	 * 
	 * @param size
	 *            Parameter to set the size of the population i.e number of solution
	 *            in the space
	 * @param tournamentSize
	 *            Individual selection for breed is carried out via tournament
	 *            selection.
	 * @param mP
	 *            We set the mutation type as a double value. 0.015 corresponds to
	 *            1.5%.
	 * 
	 */
	public Population(int size, int tournamentSize, double mP) {
		this.tournamentSize = tournamentSize;
		this.mutatePercentage = mP;
                
                List<CompletableFuture> futures = new ArrayList();
                
		routes = new Route[size]; // Initialize the list of 50 size
		for (int i = 0; i < routes.length; i++) {
                    
			Route route = new Route(true);
			route.generateIndividualRoute();

			routes[i] = route;
		}              

	}

	/**
	 * Tournament Selection Method
	 * 
	 * @return Returns the most fit from randomly picked five routes from the
	 *         population
	 */
	public Route getFittestRouteTournament() {
		// Tournament Selection
		Route routeFittest = new Route(false); // Create empty Object

		Random random = new Random();
		Route[] tournamentList = new Route[tournamentSize];
		// Populate tournamentList with routes
		for (int i = 0; i < tournamentSize; i++) { 
			// Randomly pick routes from the entire population
			tournamentList[i] = routes[random.nextInt(routes.length)]; 
		}

		// Tournament Procedure
		for (Route route : tournamentList) {
			if (routeFittest.getFittness() == 0.0)
				routeFittest = route;
			else {
				if (route.getFittness() <= routeFittest.getFittness())
					routeFittest = route;
			}
		}

		return routeFittest;

	}

	public void nextGen() {
		show();
		refreshFitness();

		// Parent 1
		Route routeA = new Route(false); 
		Route dummuyRouteA = getFittestRouteTournament();
		for (int i = 0; i < routeA.getSize(); i++) {
			routeA.getRoute().set(i, dummuyRouteA.getRoute().get(i));}
		routeA.sortRoute();
		routeA.setFittness();

		// Parent 2
		Route dummuyRouteB = getFittestRouteTournament();
		Route routeB = new Route(false); 
		for (int i = 0; i < routeB.getSize(); i++) {
			routeB.getRoute().set(i, dummuyRouteB.getRoute().get(i));}
		routeB.setFittness();


		System.out.print("Parent A - ");
		routeA.show();
		System.out.print("" + routeA.getFittness());
		System.out.println("");
		System.out.print("Parent B - ");
		routeB.show();
		System.out.print("" + routeB.getFittness());
		System.out.println("");
		
		// Child Creation
		Route child = new Route(false);
		
		int start, end = 0;
		while (true) { // Start < End index always for mutation & cross over
			start = rnd.nextInt(routeA.getSize());
			end = rnd.nextInt(routeA.getSize());
			if (start < end) {
				break;
			}
		}
		System.out.println("START:" + start + ":END:" + end);
		
		// Get genes from Parent A and push it into the Child
		for (int i = start; i <= end; i++) {
			child.getRoute().set(i, routeA.getRoute().get(i));
		}
		
		// Get genes from Parent B and push it into the Child making sure all elements are unique
		int nullCounter = 0;
		for (int i = 0; i < routeB.getRoute().size(); i++) {
			if (!ifDuplicate(routeB, child, i)) {
				for (; nullCounter < routeB.getRoute().size() + 1; nullCounter++) { // Find a null location
					if (child.getRoute().get(nullCounter) == null) {
						child.getRoute().set(nullCounter, routeB.getRoute().get(i));
						break;}}}}

		
		// Mutate Child
		child = mutate(child, start, end);
		child.setFittness();

		// Child Process Complete
		System.out.print("Child Gen     -> ");
		child.show();
		System.out.print(":" + child.getFittness());
		System.out.println("");
		
		// Print Most Fit Route in the Population for illustraive purposes
		getMostFitRoute();
		
		// Compare Child with LEAST fit route in the population
		int leastFitRouteid = getLeastFitRouteId();
		if (child.getFittness() < routes[leastFitRouteid].getFittness()) {
			routes[leastFitRouteid] = child; // Child replaces least fit route
		} else {
			System.out.println("Child is Not Fit Enough"); 
		}
		 
	}
	
	/**
	 * Mutation Procedure
	 * @param child
	 * @param start
	 * @param end
	 * @return
	 * Start & End point are preselected for test case reasons only.
	 */
	public Route mutate(Route child, int start, int end) {
		double toMutateorNotTo = rnd.nextFloat();
		if (toMutateorNotTo < mutatePercentage) {
			child.show();
			child.setFittness();
			System.out.print(":" + child.getFittness());

			Collections.swap(child.getRoute(), start, end);
			System.out.println("Mutation Has Occured");
			child.show();
			System.out.println("");
		}
		return child;
	}

	public int getLeastFitRouteId() {
                // Initializing the routes
		Route leastfitroute = routes[0]; 
		int count = -1;
		int id = 0;
		for (Route route : routes) {
			count++;
			if (route.getFittness() > leastfitroute.getFittness()) {
				leastfitroute = route;
				id = count;
			}
		}
		return id;
	}

	public boolean ifDuplicate(Route routeB, Route child, int x) {
		City cityTest = routeB.getRoute().get(x);
		for (int i = 0; i < routeB.getSize(); i++) {
                        // If child index not empty means city exists at index
			if (child.getRoute().get(i) != null) { 
				if (child.getRoute().get(i).compareTo(cityTest) == 0) // Returns 0 if identical
					return true;}}
		return false;

	}

	public void show() {
		for (Route route : routes) {

			route.show();
			System.out.print(route.getFittness());
			System.out.println("");
		}
	}

	public void getMostFitRoute() { 
		Route mostFitRoute = routes[0]; 
		for (Route route : routes) {
			if (route.getFittness() < mostFitRoute.getFittness()) {
				mostFitRoute = route;
			}
		}
		
		System.out.print("Fittest Route -> ");
		mostFitRoute.show();
		System.out.print(mostFitRoute.getFittness());
		System.out.println("");
	}

	public void refreshFitness() {
		for (Route route : routes) {
			route.setFittness();
		}
	}

}