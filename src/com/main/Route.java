/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author abdusamed
 */
public class Route {
    private ArrayList<City> route = new ArrayList<>();
    double fittness = 0.0; // each route begins with 0 fitness 
    
    public Route(){ // Route prefilled with cities?
        for (int i = 0; i < CityManager.getCityManger().size(); i++) {
            route.add(CityManager.getCityManger().get(i)); // Populate the route City objects
        }
    }
    
    public double getDistance(){
        double sum = 0.0;
        for (int i = 0; i < route.size(); i++) {
            if(i+1 >= route.size()){ // Check if reached the last element in the array
                break;
            }
            else{ // Elucidian distance between two cities
                sum += Math.sqrt((((route.get(i).getX()-route.get(i+1).getX())^2 +
                                   (route.get(i).getY()-route.get(i+1).getY())^2))/1.0);
                
            }
        }
        return sum;
    }
    
    public double getFitness(){ // Fitiness 1/d i.e inverse of distance
    	this.fittness = 1/getDistance(); // Cache for faster performance
        return 1/getDistance();
    }
    
    public void generateIndividualRoute(){
        // Assuming route is filled with cities -> simple shuffle to create an individual
        Collections.shuffle(route);        
    }

	public double getFittness() {
		return fittness;
	}

	public void setFittness(double fittness) {
		this.fittness = fittness;
	}
    
    
    
}
