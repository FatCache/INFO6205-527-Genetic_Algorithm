/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import java.util.ArrayList;

/**
 * A chromosome [a travel route] composed of genotypes[cities]
 * @author abdusamed
 */
public class CityManager {

    // A collection of genotypes
    private static final ArrayList<City> cityManger = new ArrayList<>();
    
    public CityManager() {}
    
    public void addCity(City city){
        cityManger.add(city);
    }

    public static ArrayList<City> getCityManger() {
        return cityManger;
    }
    
    public void show() {
    	System.out.print("[");
        cityManger.forEach((city) -> {
            System.out.print(city.getIndex()+ ",");
        });
    	System.out.print("]");
    	System.out.println("");
    }
}
