import java.util.*;
import java.io.*;
import java.awt.Point;

public class SANode {
	//nodes used for simulated annealing.
	//each node will represent an entire graph
	double cost;
	//List<MapNode> path = new LinkedList<MapNode>();
	List<MapNode> path;
	MapNode[] pathArray;
	MapNode[] cityList;
	Map nodeMap; //contains list of all nodes
	
	//algorithm: randomly generate a node in the beginning
	//randomly switch two nodes within the path and recalculate the path
	
		public SANode(Map m) {
			nodeMap = m;
			pathArray = new MapNode[m.cityNum];
			cityList = m.cities;
			try {
				buildRandomPath();
			} catch (Exception e) {
				System.out.println("Couldn't build path");
				System.out.println(e);
			}
			try {
				cost = calculateCost();
			} catch (Exception e) {
				System.out.println("Couldn't calculate cost");
				System.out.println(e);
			}
			
		}
		
		public SANode(Map m, MapNode[] path) {
			nodeMap = m;
			pathArray = path;
		}
		
		public double calculateCost() throws Exception {
			
			if (pathArray == null) {
				return 0;
			}
			
			double pathCost = 0;
			int i = 0;
			MapNode cur = pathArray[i];
			MapNode curNext = pathArray[i+1];
			
			if (pathArray.length <= 1) {
				return 0;
			}
			else if (pathArray.length == 2) {
				return nodeMap.edgeLengths[cur.id][curNext.id];
			}
			
			while (i + 1 < pathArray.length) {
				pathCost += nodeMap.edgeLengths[cur.id][curNext.id];
				//System.out.println("pathCost +="+nodeMap.edgeLengths[cur.id][curNext.id]);
				i++;
				cur = pathArray[i];
				if(i + 1 >= pathArray.length) break;
				curNext = pathArray[i+1];
			}
			
			pathCost += nodeMap.edgeLengths[0][cur.id]; //cost of going back to start
			//System.out.println("pathCost +="+nodeMap.edgeLengths[cur.id][0]);
			//System.out.println("This path's cost is "+pathCost);
			return pathCost;
		}
		
		public boolean buildRandomPath() throws Exception{ //call this to randomly generate a path (the first time) 
			
			path = new ArrayList<MapNode>(Arrays.asList(Arrays.copyOfRange(cityList, 1, nodeMap.cityNum))); //path is initially the cities in order, excluding the start
			
			//System.out.println("Original path (excluding start)");
			/*for (int i = 0; i < path.size(); i++) {
				System.out.print(path.get(i).id + " | ");
			}*/
			//System.out.println();
			
			Collections.shuffle(path); //shuffle the path
			MapNode start = cityList[0]; //get start
			
			//System.out.println("Shuffled path and got start");
			
			path.add(0,start); //add start back into the path
			
			//System.out.println("added start back to path");
			
			pathArray = path.toArray(pathArray); //pathArray is the path as an array (useful for switching)
			
			//System.out.println("Path generated: ");
			/*for (int i = 0; i < path.size(); i++) {
				System.out.print(path.get(i).id + " | ");
			}*/
			//System.out.println();
			//System.out.println("Array generated: ");
			/*for (int i = 0; i < pathArray.length; i++) {
				System.out.print(pathArray[i].id + " | ");
			}*/
			//System.out.println();
			
			//randomly select indices in the cityList and add them to pathArray; use path to check for presence in the pathArray
			return true;
		}
		
		public SANode randomSwitch() { //call this to switch two indices in the path
			//switch two indices in pathArray
			//set path to pathArray
			
			Random r = new Random();
			
			MapNode[] newPath = pathArray.clone();
			
			int i = r.nextInt(nodeMap.cityNum);
			while (i == 0) { //shouldn't switch start
				i = r.nextInt(nodeMap.cityNum);
			}
			
			int j = r.nextInt(nodeMap.cityNum);
			while (j == 0 || j == i) { //shouldn't switch start
				j = r.nextInt(nodeMap.cityNum);
			}
			
			//System.out.println("Switching "+pathArray[i].id+" and "+pathArray[j].id);
			
			MapNode temp = newPath[i];
			newPath[i] = newPath[j];
			newPath[j] = temp;
			
			//System.out.println("After the switch: ");
			/*for (int k = 0; k < newPath.length; k++) {
				System.out.print(newPath[k].id + " | ");
			}*/
			//System.out.println("---");
			
			return new SANode(nodeMap, newPath);
		}
}
