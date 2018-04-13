/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import java.util.ArrayList;

/**
 *
 * @author abdusamed
 */
public class CityManager {
    // Holds all the cities
    private static ArrayList<City> cityManger = new ArrayList<>();
    
    public CityManager(){
        
    }
    
    public void addCity(City city){
        cityManger.add(city);
    }

    public static ArrayList<City> getCityManger() {
        return cityManger;
    }
    
    public void show() {
    	System.out.print("[");
    	for(City city:cityManger) {
    		System.out.print(city.getIndex()+ ",");
    	}
    	System.out.print("]");
    	System.out.println("");
    }
}
