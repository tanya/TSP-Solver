import java.util.*;

import java.io.*;
import java.awt.Point;

public class TravelingSalesman {
	
	static int fileNo = 0;
	
	public static void main (String[] args) {
		String s = "";
		Scanner scanner = new Scanner(System.in);
		System.out.println("Generate city files? (y/n): ");
		
		s = scanner.nextLine();
		System.out.println(s);
		//fileGen(4,10);
		
		if (s.equals("y")) {
			System.out.println("Hello?");
			fileGen(25,10);
			fileGen(25,25);
			fileGen(25,50);
			fileGen(25,100);
		}
		
	}

	public static void fileGen (int fileNum, int cityNum) {
		//25 files for 10, 25, 50, and 100 cities
		int cityId = 0;
		
		Random r = new Random();
		
		for(int i = 0; i < fileNum; i++) {
			try{
			    PrintWriter writer = new PrintWriter(fileNo+".txt", "UTF-8");
			    for(int j = 0; j < cityNum; j++) {
			    	int x = r.nextInt(101);
					int y = r.nextInt(101);
				    writer.println(cityId+" "+x+" "+y);
				    cityId++;
			    }
			    fileNo++;
			    cityId = 0;
			    writer.close();
			} catch (IOException e) {
				System.out.println("Could not print to file.");
			   // do something
			}
		}
		
		System.out.println("Success");
	}
}
