import java.util.*;

import java.io.*;
import java.awt.Point;

public class MapNode implements Comparable<MapNode>{
	
	int numNeighbors, x, y, id;
	double fVal, gVal, hVal;
	boolean isCity, isVisited;
	//Set successors = new HashSet<MapNode>();
	//Set edges = new HashSet<MapEdge>();
	MapNode parent;
	
	public MapNode(int id, int x, int y) throws Exception {
		//numNeighbors = N;
		this.id = id;
		this.isVisited = false;
		this.x = x;
		this.y = y;
	}
	
	public double calculateDistance(MapNode cur1, MapNode cur2) {
		double distance = Math.sqrt(Math.pow((cur1.x - cur2.x), 2) + Math.pow((cur1.y - cur2.y), 2));
		return distance;
		
	}
	
	public boolean setLocation(int x, int y) {
		try {
			this.x = x;
			this.y = y;
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Could not set location");
			return false;
		}
		return true;
	}
	
	public boolean setG(double g) {
		try {
			this.gVal = g;
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Could not set g value");
			return false;
		}
		return true;
	}
	
	public boolean setParent(MapNode parent) {
		try {
			this.parent = parent;
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Could not set parent");
			return false;
		}
		return true;
	}
	
	public boolean setF(double f) {
		try {
			this.fVal = f;
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Could not set f value");
			return false;
		}
		return true;
	}
	
	public boolean setH(double h) {
		try {
			this.hVal = h;
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Could not set h value");
			return false;
		}
		return true;
	}
	
	@Override
	public int compareTo(MapNode o){
		if (fVal > o.fVal)
			return 1;
		else if (fVal < o.fVal)
			return -1;
		else
			return 0;
	}
}
