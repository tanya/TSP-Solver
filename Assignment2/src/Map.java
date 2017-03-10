import java.util.*;

import java.io.*;
import java.awt.Point;

public class Map {
	
	int length, width, cityNum;
	double[][] edgeLengths = new double[length][width]; //keeps track of edge lengths- mapped by node IDs
	MapNode[] cities = new MapNode[100];
			
	public Map (int length, int width, String textfile) { //throws Exception {
		
		this.length = length;
		this.width = width;
		
		System.out.println("Added lengths and widths");
		buildEdgelengths(textfile);
		
	}
	
	public boolean buildEdgelengths(String textfile) {
		//read from file to build edgelengths
		int x, y, cID;
		//add nodes to an array which will be traversed to calculate edge lengths
			try (BufferedReader fr = new BufferedReader(new FileReader (textfile))){
				int lineCount = 0;
				System.out.println("Found file");
				for (String line = fr.readLine(); line != null; line = fr.readLine()) {
					if (lineCount == 0) {
						System.out.println("Line is "+line);
						cityNum = Integer.parseInt(line);
						System.out.println("Number of cities: "+cityNum);
					} else {							
						String[] ln = line.split(" ");
						cID = Integer.parseInt(ln[0]);
						x = Integer.parseInt(ln[1]);
						y = Integer.parseInt(ln[2]);
						try {
							cities[cID] = new MapNode(cID, x, y);
						} catch (Exception e) {
							System.out.println("Could not create MapNode with ID = "+cID);
						}						
					}
					lineCount++;
				}
			} catch (IOException e) {
				System.out.println("There was an error reading the filename.");
				return false;
			}
			
			for (int i = 0; i < cityNum; i++) {
				MapNode cur = cities[i];
				edgeLengths[i][i] = 0;
				for (int j = i + 1; j < cityNum; j++) {
					MapNode cur2 = cities[j];
					double distance = cur.calculateDistance(cur, cur2);
					edgeLengths[i][j] = distance;
					edgeLengths[j][i] = distance;
				}
			}
			
			for (int i = 0; i < cityNum; i++) {
				for (int j = 0; j < cityNum; j++) {
					System.out.print(edgeLengths[i][j] + " ");
				}
				System.out.println();
			}
			
			return true;
	}
	
	

}
