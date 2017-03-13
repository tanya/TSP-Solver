import java.util.*;

import java.io.*;
import java.awt.Point;

public class AStarTSP {
	
	MapNode start; 
	double[][] edgeLengths;
	MapNode[] cities;
	Map cityMap;
	double g; //cost so far
	double totalCost;
	long startTime;
	long endTime;
	long totalTime;
	PriorityQueue<MapNode> fringe = new PriorityQueue<MapNode>(); // heap for min cost
	Set<MapNode> fringeSet = new HashSet<MapNode>(); //faster lookup
	Set<MapNode> closed = new HashSet<MapNode>();
	
	public AStarTSP(Map cityMap) {
		totalCost = 0;
		totalTime = 0;
		this.cityMap = cityMap;
		cities = cityMap.cities;
		start = cities[0];
	}
	
	public void run() {

		startTime = System.currentTimeMillis();
		Date date = new Date();
		long startT = date.getTime();
		//startTime = startT;
		System.out.println("Start: "+startTime);
		Runtime runtime = Runtime.getRuntime();

		start.setG(0);
		g = start.gVal;
		start.setH(h(start) + start.gVal);
		start.setF(start.gVal + start.hVal);

		fringe.add(start);
		fringeSet.add(start);
		
		MapNode cur = start;
		
		while (!fringe.isEmpty()) {
			
			cur = fringe.poll();
			if (cur != start) {
				g += cityMap.edgeLengths[cur.parent.id][cur.id];
				//System.out.println("COST SO FAR: "+g);
			}
			
			//System.out.print("FRINGE: ");
			//printFringe();
			//System.out.println("Removed node "+cur.id+" from fringe; its fVal is "+cur.fVal+"and its gVal is "+cur.gVal);
			fringeSet.remove(cur);
			if (closed.size() == cityMap.cityNum) {
				//System.out.println("Visited all cities");
				break;
			}
			//closed.add(cur);
			cur.isVisited = true;
			//totalCost += cur.gVal;
			
			for (MapNode curPrime : cities) {
				if (curPrime.isCity == false) { //past the city list in cities, break
					break;
				}
				if (curPrime.id == cur.id) {
					continue;
				}
				if (!curPrime.isVisited) { //(!closed.contains(curPrime)) { //if curPrime not expanded yet
					if (!fringe.contains(curPrime)) { //if curPrime not generated yet
						curPrime.setG(Double.POSITIVE_INFINITY); 
						curPrime.setParent(cur);
						//curPrime.setParent(null);
					}
					
					updateVertex(cur, curPrime);					
				}	
			}
		}
		
		long totalT = 0;
		long endT = 0;
		
		if (fringe.isEmpty()) { //finished going through cities
			start.setParent(cur); //cur goes to start
			cur = start;
			endTime = System.currentTimeMillis();
			System.out.println("End: "+endTime);
			totalTime = endTime - startTime;
			solution(start);
		} else {
			System.out.println("No path found");
		}
		endT = date.getTime();
		endTime = System.currentTimeMillis();
		totalTime = endT - startT;
		System.out.println(endT-startT);
		//this.totalTime = totalTime;
		System.out.println("Total time: "+totalTime);
	}
	
	public void updateVertex (MapNode s, MapNode sPrime) {
		//System.out.println("s: "+s.x+", "+s.y+" | sPrime: "+sPrime.x+", "+sPrime.y+" id is "+sPrime.id);
		if (s.gVal + cityMap.edgeLengths[s.id][sPrime.id] < sPrime.gVal) { //basically if g was never generated
			//sPrime.setG(g(s,sPrime));
			//sPrime.prevG = sPrime.gVal; //this will be subtracted from all nodes but the one expanded
			sPrime.setG(g + cityMap.edgeLengths[s.id][sPrime.id]);
			//System.out.println("set "+sPrime.id+"'s gVal to "+ sPrime.gVal);
			sPrime.setParent(s);
			//System.out.println(s.id + " is the parent of "+sPrime.id);
			if (fringe.contains(sPrime)) { //
				fringeSet.remove(sPrime);
				fringe.remove(sPrime);
			}
			//sPrime.setF(s.gVal + h(sPrime));
			//System.out.println("Set "+sPrime.id+"'s fVal to "+sPrime.fVal);
			fringe.add(sPrime);
			fringeSet.add(sPrime);
			//System.out.println("Added "+sPrime.id+" to fringe; fVal is "+sPrime.fVal);
		} else { //g seen before, remove prevG value
			//sPrime.setG(g(s,sPrime) - sPrime.prevG);
			//sPrime.prevG = cityMap.edgeLengths[s.id][sPrime.id]; //distance from s to sPrime will be removed when the path is not taken
			sPrime.setG(g + cityMap.edgeLengths[s.id][sPrime.id]);
			//System.out.println(g + " (distance so far) + "+cityMap.edgeLengths[s.id][sPrime.id]+" (cost of path)");
			//System.out.println("set "+sPrime.id+"'s gVal to "+ sPrime.gVal);
		}
		fringe.remove(sPrime);
		sPrime.setF(sPrime.gVal + h(sPrime));
		fringe.add(sPrime);
		sPrime.setParent(s);
		//System.out.println("Set "+sPrime.id+"'s fVal to "+sPrime.fVal);
		//System.out.println(s.id + " is the parent of "+sPrime.id);
		//System.out.println("--");
	}
	public double g(MapNode n, MapNode nPrime) {
		double dist = n.gVal + cityMap.edgeLengths[n.id][nPrime.id];
		//System.out.println("Added previous gVal "+n.gVal+"to new cost, "+cityMap.edgeLengths[n.id][nPrime.id]);
		return dist;
	}
	
	public double h(MapNode n) {
		return cityMap.edgeLengths[0][n.id];
	}
	
	public void solution(MapNode current){		
		//System.out.println("SOLUTION: ");
		int n = 0;
		while(current.parent != start){
			//grid[currentPoint.x][currentPoint.y].setSolution(true);
			n++;
			totalCost += cityMap.edgeLengths[current.id][current.parent.id];
			//System.out.println(current.id);
			current = current.parent;
			
			//System.out.print(currentPoint);
		}
		
		if(current.parent == start) {
			//System.out.println(current.id);
			g += cityMap.edgeLengths[current.id][start.id];
			//totalCost += cityMap.edgeLengths[current.id][start.id];
			//System.out.println("Parent is start");
		}
		
		System.out.println("Total cost of path: "+g);
		//grid[currentPoint.x][currentPoint.y].setSolution(true);
		//grid[start.x][start.y].setSolution(true);
		//System.out.println();
	}
	
	public void printFringe() {
		for (MapNode i : fringeSet) {
			System.out.print(i.fVal + " | ");
		}
		System.out.println();
	}
}
