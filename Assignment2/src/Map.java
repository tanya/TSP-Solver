import java.util.*;
import java.io.*;
import java.awt.Point;

public class Map {
	
	int length, width, cityNum;
	double[][] edgeLengths = new double[100][100]; //keeps track of edge lengths- mapped by node IDs
	MapNode start;
	MapNode[] cities = new MapNode[100];
			
	public Map (int length, int width, String textfile) throws Exception {
		
		this.length = length;
		this.width = width;
		//System.out.println("Added lengths and widths");
		buildEdgelengths(textfile);
		start = cities[0];
		
	}
	
	public boolean buildEdgelengths(String textfile) {
		//read from file to build edgelengths
		int x, y, cID;
		
		for (int i = 0; i < cities.length; i++) {
			try {
				cities[i] = new MapNode(0,0,0);
				cities[i].isCity = false;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		//add nodes to an array which will be traversed to calculate edge lengths
			try (BufferedReader fr = new BufferedReader(new FileReader (textfile))){
				int lineCount = 0;
				System.out.println("Found file");
				for (String line = fr.readLine(); line != null; line = fr.readLine()) {
					if (lineCount == 0) {
						//System.out.println("Line is "+line);
						cityNum = Integer.parseInt(line);
						//System.out.println("Number of cities: "+cityNum);
					} else {	
						//System.out.println(line);
						String[] ln = line.split(" ");
						cID = Integer.parseInt(ln[0]);
						//System.out.println(cID);
						x = Integer.parseInt(ln[1]);
						y = Integer.parseInt(ln[2]);
						cities[cID].setLocation(x,y);
						cities[cID].isCity = true;
						cities[cID].id = cID;
					}
					lineCount++;
				}
			} catch (IOException e) {
				System.out.println("There was an error reading the filename.");
				System.out.println(e);
				return false;
			}
			
			/*for (int i = 0; i < cityNum; i++) {
				System.out.println("City "+cities[i].id + " " + cities[i].x + ", " + cities[i].y);
			}*/
			
			for (int i = 0; i < cities.length; i++) {
				MapNode cur = cities[i];
				for (int j = i; j < cities.length; j++) {
					if (j == i) {
						edgeLengths[j][i] = 0;
						continue;
					}
					MapNode cur2 = cities[j];
					//System.out.println(i + " " + j);
					double distance = cur.calculateDistance(cur, cur2);
					cur.setH(distance);
					edgeLengths[i][j] = distance;
					//System.out.println("Edge length of "+i+" and "+j+" is "+distance);
					edgeLengths[j][i] = distance;
				}
			}
			
			/*for (int i = 0; i < cityNum; i++) {
				for (int j = 0; j < cityNum; j++) {
					System.out.print(edgeLengths[i][j]+"  ");
				}
				System.out.println();
			}*/
			
			return true;
	}
	
	

}
