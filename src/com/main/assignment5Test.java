package com.main;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.zip.Adler32;

import org.junit.jupiter.api.Test;

public class assignment5Test {

	public void setUp() {
	CityManager ciManager = new CityManager();
		
		City city1 = new City("City A", 60,200);
		City city2 = new City("City B", 180,200);
		City city3 = new City("City C", 80,130);
		City city4 = new City("City D", 20,160);
		City city5 = new City("City E", 100,160);
		City city6 = new City("City F", 200,160);
		City city7 = new City("City G", 140,140);
		City city8 = new City("City H", 40,120);
		City city9 = new City("City I", 40,120);
		City city10 = new City("City J", 100,120);
		
	ciManager.addCity(city1);
	ciManager.addCity(city2);
	ciManager.addCity(city3);
	ciManager.addCity(city4);
	ciManager.addCity(city5);
	ciManager.addCity(city6);
	ciManager.addCity(city7);
	ciManager.addCity(city8);
	ciManager.addCity(city9);
	ciManager.addCity(city10);}
	
	
	@Test
	public void TestCityMethod() throws Exception{
		setUp();
		assertEquals("City A", CityManager.getCityManger().get(0).getName());
		assertEquals(0, CityManager.getCityManger().get(0).getIndex());
		assertEquals(60, CityManager.getCityManger().get(0).getX());
		assertEquals(200, CityManager.getCityManger().get(0).getY());

	}
	
	
	@Test
	//Mutation Rate Set to 0%
	public void TestNoMutation() {
		setUp();
		Population pop = new Population(20, 5, 0.0);
		Route childExample = new Route(true);
		Route newChild = pop.mutate(childExample, 5, 7);
		assertEquals(childExample, newChild);
	}
	
	
	@Test
//	Mutation Rate Set to 100%
	public void TestMutation() {
		setUp();
		Population pop = new Population(5, 2, 1.0);
		Route oldChild = new Route(true);
		Route newChild = pop.mutate(oldChild, 2, 5);
		assertTrue(oldChild.equals(newChild));
		assertEquals(oldChild.getRoute().get(1), newChild.getRoute().get(1));
	}
	
	@Test
//	Check Duplicated 
	public void TestDuplicated() {
		setUp();
		Population pop = new Population(5, 2, 1.0);
		Route oldChild = new Route(true);
		Route newChild = new Route(false);
		assertEquals(false,pop.ifDuplicate(oldChild,newChild,1));

	}
	
	



	
}