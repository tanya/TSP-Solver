import java.util.*;

import java.io.*;
import java.awt.Point;

public class AStarTSP {
	
	MapNode start; 
	double[][] edgeLengths;
	MapNode[] cities;
	Map cityMap;
	double g;
	PriorityQueue<MapNode> fringe = new PriorityQueue<MapNode>(); // heap for min cost
	Set<MapNode> fringeSet = new HashSet<MapNode>(); //faster lookup
	Set<MapNode> closed = new HashSet<MapNode>();
	
	public AStarTSP(Map cityMap) {
		this.cityMap = cityMap;
		cities = cityMap.cities;
		start = cities[0];
	}
	
	public void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	public void run() {

		start.setG(0);
		
		start.setH(h(start) + start.gVal);
		
		fringe.add(start);
		fringeSet.add(start);
		
		MapNode cur = start;
		
		while (!fringe.isEmpty()) {
			
			cur = fringe.poll();
			System.out.print("FRINGE: ");
			printFringe();
			System.out.println("Removed node "+cur.id+" from fringe; its distance is "+cur.fVal);
			fringeSet.remove(cur);
			if (closed.size() == cityMap.cityNum) {
				System.out.println("Visited all cities");
				break;
			}
			//closed.add(cur);
			cur.isVisited = true;
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
						curPrime.setParent(null);
					}
					updateVertex(cur, curPrime);					
				}	
			}
		}
		
		if (fringe.isEmpty()) { //finished going through cities
			start.setParent(cur); //cur goes to start
			cur = start;
			solution(start);
		} else {
			System.out.println("No path found");
		}
	}
	
	public void updateVertex (MapNode s, MapNode sPrime) {
		System.out.println("s: "+s.x+", "+s.y+" | sPrime: "+sPrime.x+", "+sPrime.y+" id is "+sPrime.id);
		if (s.gVal + cityMap.edgeLengths[s.id][sPrime.id] < sPrime.gVal) { //
			//sPrime.setG(s.gVal + cityMap.edgeLengths[s.id][sPrime.id]);
			sPrime.setG(g(s,sPrime));
			System.out.println("set "+sPrime.id+"'s gVal to "+ sPrime.gVal);
			sPrime.setParent(s);
			System.out.println(s.id + " is the parent of "+sPrime.id);
			if (fringe.contains(sPrime)) {
				fringeSet.remove(sPrime);
				fringe.remove(sPrime);
			}
			//sPrime.setF(s.gVal + h(sPrime));
			//System.out.println("Set "+sPrime.id+"'s fVal to "+sPrime.fVal);
			fringe.add(sPrime);
			fringeSet.add(sPrime);
			System.out.println("Added "+sPrime.id+" to fringe; fVal is "+sPrime.fVal);
		}
		sPrime.setF(s.gVal + h(sPrime));
		sPrime.setParent(s);
		System.out.println("Set "+sPrime.id+"'s fVal to "+sPrime.fVal);
		System.out.println(s.id + " is the parent of "+sPrime.id);
		System.out.println("--");
	}
	public double g(MapNode n, MapNode nPrime) {
		double dist = n.gVal + cityMap.edgeLengths[n.id][nPrime.id];
		return dist;
	}
	
	public double h(MapNode n) {
		return cityMap.edgeLengths[0][n.id];
	}
	
	public void solution(MapNode current){		
		//System.out.print(currentPoint);
		System.out.println("SOLUTION: ");
		int n = 0;
		while(current.parent != start){
			//grid[currentPoint.x][currentPoint.y].setSolution(true);
			n++;
			System.out.println(current.id);
			current = current.parent;
			
			//System.out.print(currentPoint);
		}
		
		if(current.parent == start) {
			System.out.println(current.id);
			System.out.println("Parent is start");
		}
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
