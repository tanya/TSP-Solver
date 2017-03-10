import java.util.*;

import java.io.*;
import java.awt.Point;

public class MapNode {
	
	int numNeighbors, x, y, id;
	Set edges = new HashSet<MapNode>();
	
	public MapNode(int id, int x, int y) throws Exception {
		//numNeighbors = N;
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public double calculateDistance(MapNode cur1, MapNode cur2) {
		double distance = Math.sqrt(Math.pow((cur1.x - cur2.x), 2) + Math.pow((cur1.y - cur2.y), 2));
		return distance;
		
	}

}
