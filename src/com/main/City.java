/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import java.util.Comparator;

/**
 *
 * @author abdusamed
 */
public class City implements Comparable<City>{
    private static int count = 0;
    private int index;
	private String name;
    private int x;
    private int y;
    
    public City (String name, int x, int y){
        index = count++; // Counter to increment one by each initialization
    	this.name=name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



	public int getIndex() {
		return index;
	}

	@Override
	public int compareTo(City o) {
		return (this.getIndex() - o.getIndex());
	}
	
	

	
    
    
    
}
