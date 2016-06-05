package p1.zipflaw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
//import org.knowm.xchart.demo.XChartDemo;
import p1.noise.NoiseWrapper;



public class ZipfMain {
	static double sum=0;
public static void main(String[] args) throws IOException {

	NoiseWrapper nw= new NoiseWrapper();
	ZipfWrapper zw = new ZipfWrapper();
	ZipfImplementation zi= new ZipfImplementation();
	ArrayList<String> rArrayList=nw.readingReportToGetLocationOfArrayListOfFileDownloaded("report/report12.html");

	for(String filename:rArrayList){
		String newFilePath=zw.removeTagsMakeNewRepository(filename);	
		zi.addToListMap(newFilePath);
	}
	
	//zi.printHashMap();
		
	   Map<String,Integer> sortedMap = zi.sortByValue();
	   for(int i: sortedMap.values()){
		   sum+=i;
	   }
	  // System.out.println(sum);
	   
	   Set<String> keys = sortedMap.keySet();  //get all keys
	
	   
	   
	   int rank=1;
	   final XYSeries zipfSeries = new XYSeries( "Zipf set" ); 
	   final XYSeries zipfSeries2= new XYSeries("Zipf set Word frequency to rank");
	   for(String i: keys)
		 {
		  // System.out.println(i+ "   ||||||   " +sortedMap.get(i));
		   double numerator=sortedMap.get(i);
		   double ans=numerator/sum ;
		  // System.out.println(ans);
		     zipfSeries.add( rank,ans );
		  //   zipfSeries.add(numerator, rank);
		     rank++;
		 }
	   for(String i: keys)
		 {
		   System.out.println(i+ "   ||||||   " +sortedMap.get(i));
		   break;
		 }
	   
	   
	   
	   
	             
	               
	      final XYSeriesCollection dataset = new XYSeriesCollection( );          
	      dataset.addSeries( zipfSeries ); 
	   
	     // final XYSeriesCollection dataset1 = new XYSeriesCollection( );          
	    //  dataset.addSeries( zipfSeries2 ); 
	      
	      
	      
	   
	      XYLineChart_AWT chart = new XYLineChart_AWT("Zipf Law", "Zipf Law",(XYDataset)dataset);
	      chart.pack( );          
	      RefineryUtilities.centerFrameOnScreen( chart );          
	      chart.setVisible( true ); 
	   /*
	      XYLineChart_AWT chart1 = new XYLineChart_AWT("Zipf Law", "Zipf Law",(XYDataset)dataset1);
	      chart.pack( );          
	      RefineryUtilities.centerFrameOnScreen( chart1 );          
	      chart.setVisible( true ); 
	   
	 */
	   
	
	/*zi.sortHashMapToTreeMap();
	zi.printTreeMap();*/
	
	
	/*NavigableMap<Integer, String> tree1= ZiipfImplementation.getSortedMap().descendingMap();

System.out.println(tree1);*/
	
	
}
}
