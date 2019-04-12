/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;
/**
 *
 * @author abdusamed
 * 
 */
public class City implements Comparable<City> {
	final private int index;
	private String name = null;
	final private int x;
	final private int y;
        
        private static int count = 0;

        @Deprecated
	public City(String name, int x, int y) {
		index = count++; // Counter to increment for each initialization of City
		this.name = name;
		this.x = x;
		this.y = y;
	}
        
        // City no name to submit coordinates without name @param name
        public City(final int x, final int y) {
            this.index = count++;
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

	public int getY() {
		return y;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public int compareTo(City o) {
		return (this.getIndex() - o.getIndex());
	}
        
        @Override
        public String toString(){
            return ("[" + this.x + "," + this.y + "]");
        }

}
