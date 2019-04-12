/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.tsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author abdusamed
 */
public class TSPChromosome implements Comparable<TSPChromosome> {

    private Random random = new Random();
    private int[] phenotypeList;
    private String[] genotypeList;
    private final int chromosomeLength;
    private double weight;
    private double[][] map;

    // Generate a random chromosome list randomly
    public TSPChromosome(int chromosomeLength, Random random, double[][] givenMap) {
        this.chromosomeLength = chromosomeLength;
        this.phenotypeList = new int[chromosomeLength];
        this.genotypeList = new String[chromosomeLength];
        this.map = givenMap.clone();

        // Generate the random gene list using arraylist
        ArrayList<Integer> tempList = new ArrayList<>();
        int counter = 0;
        while (counter < chromosomeLength) {
            int randomCity = Math.abs(random.nextInt() % chromosomeLength);
            if (!tempList.contains(randomCity)) {
                tempList.add(randomCity);
                counter++;
            }
        }

        // Copy the temp list to the chromosome list
        for (int i = 0; i < chromosomeLength; i++) {
            phenotypeList[i] = tempList.get(i);
            genotypeList[i] = phenotypeToGenotype(phenotypeList[i]);
        }

        // Initialize
        initialize();
    }

    // Set the chromosome list to the given list
    public TSPChromosome(int[] givenChromosomeList, double[][] givenMap) {
        this.chromosomeLength = givenChromosomeList.length;
        this.phenotypeList = givenChromosomeList.clone();
        this.genotypeList = new String[chromosomeLength];
        this.map = givenMap.clone();
        for (int i = 0; i < chromosomeLength; i++) {
            genotypeList[i] = phenotypeToGenotype(phenotypeList[i]);
        }

        // Initialize
        initialize();
    }

    public int getChromosomeLength() {
        return chromosomeLength;
    }

    public int[] getPhenotypeList() {
        return phenotypeList;
    }

    public String[] getGenoTypeList() {
        return genotypeList;
    }

    public double[][] getMap() {
        return map;
    }

    // Phenotype List to Genotype List
    private String phenotypeToGenotype(int phenotype) {
        if (phenotype >= 256) {
            throw new IllegalArgumentException();
        }
        int num1 = phenotype / 64;
        int num2 = (phenotype - num1 * 64) / 16;
        int num3 = (phenotype - num1 * 64 - num2 * 16) / 4;
        int num4 = phenotype - num1 * 64 - num2 * 16 - num3 * 4;
        return toGene(num1) + toGene(num2) + toGene(num3) + toGene(num4);
    }

    private String toGene(int pheno) {
        switch (pheno) {
            case 0:
                return "A";
            case 1:
                return "G";
            case 2:
                return "C";
            case 3:
                return "T";
            default:
                throw new IllegalArgumentException();
        }
    }

    // Genotype List to Phenotype List
    private int genotypeToPhenotype(String phenotype) {
        char[] phenotypeCharList = phenotype.toCharArray();
        return toPheno(phenotypeCharList[0]) * 64 + toPheno(phenotypeCharList[1]) * 16
                + toPheno(phenotypeCharList[2]) * 4 + toPheno(phenotypeCharList[3]);
    }

    private int toPheno(char gene) {
        switch (gene) {
            case 'A':
                return 0;
            case 'G':
                return 1;
            case 'C':
                return 2;
            case 'T':
                return 3;
            default:
                throw new IllegalArgumentException();
        }
    }

    // Get the weight
    public double getWeight() {
        return this.weight;
    }

    // Initialize the parameters:weight
    public void initialize() {
        this.weight = calWeight();
    }

    // Calculate the whole weight of this chromosome list
    public double calWeight() {
        double totalWeight = 0;
        for (int i = 0; i < chromosomeLength - 1; i++) {
            totalWeight += map[phenotypeList[i]][phenotypeList[i + 1]];
        }
        totalWeight += map[phenotypeList[chromosomeLength - 1]][phenotypeList[0]];
        return totalWeight;
    }

    // Cross over with another chromosome list to create a new offspring
    public TSPChromosome crossOver(TSPChromosome that) {
        // Offspring data structure
        int[] newPhenotypeList = new int[chromosomeLength];
        String[] newGenotypeList = new String[chromosomeLength];

        // Generate random location from 1 to chromosomeLenght -1
        int randomLocation = Math.abs(random.nextInt() % (that.chromosomeLength - 1)) + 1;

        // Place the phenotypes from father into child
        for (int i = 0, j = randomLocation; j < chromosomeLength; i++, j++) {
            newGenotypeList[i] = genotypeList[j];
        }

        // Fill in remaining genes spots in the new chromosome by retrieving genes from that
        /* Remove duplicate cities
                    j->
         PARENT P1 [A,B,C,D,E]
         CHILD  C1 [_,_,Q,R,S]   
                    i
         */
        for (int i = chromosomeLength - randomLocation, j = 0; j < chromosomeLength;) {
            newGenotypeList[i] = that.genotypeList[j++];

            int k;
            for (k = 0; k < chromosomeLength - randomLocation; k++) {
                if (newGenotypeList[i].equals(newGenotypeList[k])) {
                    break;
                }
            }
            // If true, gene does not exist in the chromosome, hence, it is accepted
            if (k == chromosomeLength - randomLocation) {
                j++;
            }
        }

        for (int i = 0; i < chromosomeLength; i++) {
            newPhenotypeList[i] = genotypeToPhenotype(newGenotypeList[i]);
        }
        return new TSPChromosome(newPhenotypeList, this.map);

    }

    // Mutate on itself by switching random location gene on itself
    public void mutation() {
        int randomLocation1, randomLocation2;
        String temp;

        randomLocation1 = Math.abs(random.nextInt() % chromosomeLength);
        randomLocation2 = Math.abs(random.nextInt() % chromosomeLength);

        while (randomLocation1 == randomLocation2) {
            randomLocation2 = Math.abs(random.nextInt() % (chromosomeLength - 1)) + 1;
        }

        // Exchange the gene located in the random locations
        temp = genotypeList[randomLocation1];
        genotypeList[randomLocation1] = genotypeList[randomLocation2];
        genotypeList[randomLocation2] = temp;

        // Refresh the phenotype list
        for (int i = 0; i < chromosomeLength; i++) {
            phenotypeList[i] = genotypeToPhenotype(genotypeList[i]);
        }
    }

    public boolean isValidateChromosome() {
        Map<Integer, Integer> validateMap = new HashMap<>();
        for (int i = 0; i < chromosomeLength; i++) {
            if (null == validateMap.get(phenotypeList[i])) {
                validateMap.put(phenotypeList[i], 0);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(TSPChromosome that) {
        return Double.compare(weight, that.weight);
    }

    // Main method is for testing
    public static void main(String[] args) throws Exception {

        TSPGenerateAlgorithm tspGenerateAlgorithm = new TSPGenerateAlgorithm(10, "input/data.txt");
        TSPChromosome tspChromosome = new TSPChromosome(tspGenerateAlgorithm.cityNum, new Random(),
                tspGenerateAlgorithm.distanceMap);
    }

}
