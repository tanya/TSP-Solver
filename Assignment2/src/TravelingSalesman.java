import java.util.*;

import java.io.*;
import java.awt.Point;

public class TravelingSalesman {
	
	static int fileNo = 0;
	
	public static void main (String[] args) {
		String s = "";
		int len, wid;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Generate city files? (y/n): ");
		
		s = scanner.nextLine();
		
		/*if (s == "y") {
			fileGen(25,10);
			fileGen(25,25);
			fileGen(25,50);
			fileGen(25,100);
		}*/
		fileGen(0,10);
				
		//try {
			System.out.println("Enter a filename");
			String file = scanner.nextLine();
			Map cityMap = new Map(101, 101, file);
		//} catch (Exception e){
			System.out.println("Error building map");
		//}
		
		
		
	}

	public static void fileGen (int fileNum, int cityNum) {
		//25 files for 10, 25, 50, and 100 cities
		int cityId = 0;
		
		Random r = new Random();
		
		for(int i = 0; i < fileNum; i++) {
			try{
				File file = new File("/Users/tanya/Programming/Classes/AI_Assignment2/Assignment2/src/"+fileNo+".txt");
				System.out.println("Created file");
				file.getParentFile().mkdirs();
			    PrintWriter writer = new PrintWriter(file);
			    System.out.println("created writer?");
			    writer.println(cityNum);
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
