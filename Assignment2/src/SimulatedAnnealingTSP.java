import java.util.*;
import java.io.*;
import java.awt.Point;

public class SimulatedAnnealingTSP {
	
	Map nodeMap;
	SANode initialState;
	SANode sPrime;
	SANode currentState;
	SANode bestSolution; //overall best
	SANode currentBest; //best for the current "set" in the loop
	double coolingConstant;
	int numberOfCities; 
	long startTime;
	long endTime;
	double T_init; //temperature at beginning
	double temp; //temp_k at the kth instance of accepting a new solution state 
	double Beta; //between 0 and 1
	double bestPath;
	int nodesGenerated;
	
	public SimulatedAnnealingTSP(Map m, double t, double B) {
		nodesGenerated = 0;
		T_init = t;
		temp = t;
		Beta = B;
		nodeMap = m;
		numberOfCities = m.cityNum;
		initialState = new SANode(m);
		nodesGenerated++;
		currentState = initialState;
		bestSolution = initialState;
		currentBest = initialState;
		//sPrime = initialState.randomSwitch();
		//System.out.println("AP: "+acceptanceProbability(initialState,sPrime));
	}
	
	public boolean run() {
		startTime = System.currentTimeMillis();
		double upperLimit = 120000; //factorial(numberOfCities); //number of loops allowed before exit
		double bestCost = 0;
		double curBestCost = 0;
		try {
			bestCost = bestSolution.calculateCost();
			System.out.println("initial cost: "+bestCost);
			curBestCost = bestCost;
			currentBest = bestSolution;
		} catch (Exception e) {
			
		}
		
		for (int i = 0; i < upperLimit; i++) {
			sPrime = currentState.randomSwitch();
			nodesGenerated++;
			acceptanceProbability();
			if (i % 40000 == 0 && i != 0) { //check if bestCost has changed 
				try {
					bestCost = bestSolution.calculateCost();
					curBestCost = currentBest.calculateCost();
					System.out.println("Best cost at i = "+i+": "+curBestCost);
				} catch (Exception e) {
					
				}
				if (curBestCost == bestCost) {
					System.out.println("Best cost hasn't changed");
					break;
				} else if (bestCost < curBestCost){ //overall best cost is better than the one in the current "set"
					currentBest = bestSolution;
					currentState = bestSolution; //go back to the best solution so far
					break; //we've probably found the overall best at this point, so end 
					//curBestCost = bestCost; // set new curBestCost
				} else { //current best cost is better than the overall
					bestSolution = currentBest;
				}
			}
		}
		
		endTime = System.currentTimeMillis();
		
		try {
			bestCost = bestSolution.calculateCost();
			bestPath = bestCost;
			curBestCost = bestCost;
		} catch (Exception e) {
			
		}
		System.out.println("Best path found, cost "+bestCost+": ");
		for (int i = 0; i < numberOfCities; i++) {
			System.out.println(bestSolution.pathArray[i].id);
		}
		return true;
	}
	
	public double acceptanceProbability() {
		double difference = 0;
		Random r = new Random();
		int changeState = 0;
		double probability = 0;
		
		try {
			difference = (sPrime.calculateCost() - currentState.calculateCost()) / currentState.calculateCost(); //change in cost
			//System.out.println("Difference is "+difference);
		} catch (Exception e) {
			System.out.println("Could not calculate change in cost");
			System.out.println(e);
		}
		//System.out.println("Difference :"+difference);
		if (difference > 0) { //sPrime is higher than s
			//System.out.println("Difference is greater than 0");
			difference += -1;
			probability = Math.pow(Math.E, (difference/temp));
			//System.out.println("Probability is "+probability);
			changeState = r.nextInt(101);
			//System.out.println("ChangeState is "+changeState);
			if (changeState/100 <= probability) { //update s to s' if probability matches, in the hopes of finding a better "best" 
				currentState = sPrime;
				temp *= Beta; //updating temp
				//System.out.println("Moving to sPrime, updating temp to "+temp);
			}
			return Math.pow(Math.E, ((-1 * difference)/temp));
		} else { //sPrime is smaller than s
			//System.out.println("Difference is <= 0");
			currentState = sPrime;
			currentBest = sPrime;
			return 1;
		}
	}
	
	public double factorial(int n) {
		if (n == 1) {
			return 1;
		} else {
			return n * factorial(n-1);
		}
	}
	
}
