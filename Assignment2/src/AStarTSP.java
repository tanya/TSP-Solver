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
		try {
			start.setG(0);
		} catch (Exception e) {
			System.out.println("Could not set g value.");
		}
		
		start.setH(h(start) + start.gVal);
		
		fringe.add(start);
		fringeSet.add(start);
		
		MapNode cur = start;
		
		while (!fringe.isEmpty()) {
			cur = fringe.poll();
			fringeSet.remove(cur);
			if (closed.size() == cityMap.cityNum) {
				System.out.println("Visited all cities");
				break;
			}
			closed.add(cur);
			for (MapNode curPrime : cities) {
				if (curPrime.isCity == false) { //past the city list in cities, break
					break;
				}
				if (curPrime == cur) {
					continue;
				}
				if (!closed.contains(curPrime)) {
					if (!fringe.contains(curPrime)) {
						curPrime.setG(Double.POSITIVE_INFINITY);
						curPrime.setParent(null);
					}
					updateVertex(cur, curPrime);
					
				}	
			}
		}
		
		if (fringe.isEmpty()) { //finished going through cities
			//update vertex
			start.setParent(cur); //cur goes to start
			cur = start;
			solution(start);
		} else {
			System.out.println("No path found");
		}
	}
	
	public void updateVertex (MapNode s, MapNode sPrime) {
		System.out.println("s: "+s.x+", "+s.y+" | sPrime: "+sPrime.x+", "+sPrime.y);
		if (s.gVal + cityMap.edgeLengths[s.id][sPrime.id] < sPrime.gVal) {
			sPrime.setG(s.gVal + cityMap.edgeLengths[s.id][sPrime.id]);
			sPrime.setParent(s);
			if (fringe.contains(sPrime)) {
				fringeSet.remove(sPrime);
				fringe.remove(sPrime);
			}
			sPrime.setF(s.gVal + h(s));
			fringe.add(sPrime);
			fringeSet.add(sPrime);
		}
		System.out.println("Updated vertex");
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
		while(current.parent != start){
			//grid[currentPoint.x][currentPoint.y].setSolution(true);
			System.out.println(current.id);
			current = current.parent;
			
			//System.out.print(currentPoint);
		}
		
		if(current.parent == start) {
			System.out.println("Parent is start");
		}
		//grid[currentPoint.x][currentPoint.y].setSolution(true);
		//grid[start.x][start.y].setSolution(true);
		//System.out.println();
	}
}
