package p1.noise;


import java.util.*;
import java.io.IOException;


public class NoiseMain {

	
	
	public static void main(String[] args) throws IOException {
		/*String filename="repository\\fileD1.html";*/
		NoiseWrapper nw= new NoiseWrapper();
		ArrayList<String> rArrayList=nw.readingReportToGetLocationOfArrayListOfFileDownloaded("report/report12.html");
		
		//System.out.println(Arrays.toString(rArrayList.toArray()));
		
		//NoiseWrapper nw= new NoiseWrapper();
		NoiseImplementation ni = new NoiseImplementation();
		/*nw.removeBodyStripableContent(filename);	
		ni.populateHash(filename);*/
		for(String filename:rArrayList){
		//	System.out.println(filename);
		nw.removeBodyStripableContent(filename);	
		ni.populateHash(filename);
		}
		int threshold = 0;
		if (rArrayList.size() >= 100) 
			 threshold = 10;
		else
			 threshold= (rArrayList.size()-1)/10;
		System.out.println("Threahold:   "+ threshold);
		for(String filename:rArrayList){
			ni.removeNoise(filename, threshold);
		}
		
		ni.printHashMap();
		
		
		

	}
}
		
		
		
		
	