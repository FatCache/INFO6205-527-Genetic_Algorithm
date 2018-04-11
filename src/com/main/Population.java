/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import java.util.Random;

/**
 *
 * @author abdusamed
 */
public class Population { // Composed of many routes
    Route[] routes;
    int tournamentSize; // Assume tournamentSize = 5;
    
    public Population(int size, int tournamentSize){ // Say we want to have 50 routes
    	this.tournamentSize = tournamentSize;
        routes = new Route[size]; // Initialize the list of 50 size
        for (int i = 0; i < routes.length; i++) {
            Route route = new Route();
            route.generateIndividualRoute();
            routes[i] = route;
        }
    }
    
    // Get fittest, a better way could be to use priority queue i.e most fit at the top of the rack ...
    public Route getFittestRoute() {
    	// Tournament Selection
    	Route routeFittest = new Route(); // Has fitness = 0.0 as an initializer
    	Random rnd = new Random();
    	Route[] tournamentList = new Route[tournamentSize];
    	for (int i = 0; i < tournamentSize; i++) {
    		tournamentList[i] = routes[rnd.nextInt(routes.length)]; // Randomly pick routes from entire length
		}
    	
    	// Tournament Time!
    	for(Route route:tournamentList) {
    		if(route.getFittness() >= routeFittest.getFitness()) routeFittest = route;
    	}
    	
    	return routeFittest;
    	
    }
    
    
    
   
    
    
}