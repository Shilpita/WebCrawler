/*************************************************************************************************************************
 * COEN 272 Project 1
 * Purpose : Basic Web crawler
 * Author  : Mansi and Shilpita
 * Started : 7April2016
 * 
 * **************************************************************************************************************************/


package p1.crawler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class CrawlerImplementation {
//takes the inputs from the CVS file and returns the inputs to be plugged...
	
	public  String[] inputDataFromCVSFile(String csvFile){
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String[] input=null;
		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

			        // use comma as separator
				input = line.split(cvsSplitBy);
					System.out.println("seed: "+input[0]+ "\t number:"+input[1]+"\t domain:"+input[2]);
					
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return input;
	  }
	
	
	
	//main crawling function
	public void crawlingfunction(CrawlerWrapper cw) throws IOException, InterruptedException{

		System.out.println("seed: "+cw.seedUrl+ "\t number:"+cw.numberOfSitesToBeCrawled+"\t domain:"+cw.domain);	
		
		cw.getLinks(cw.seedUrl);
		
		
		for(int i=1;i<cw.numberOfSitesToBeCrawled;i++){
			String seedNew="";
		//	cw.ListOfLinksObtainedFromCrawling
			Iterator it = cw.ListOfLinksObtainedFromCrawling.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        System.out.println(pair.getKey() + " = " + pair.getValue());
		        String s[]=(String[]) pair.getValue();
		        if(s[1].equals("0")){
		        	seedNew=s[0];
		        break;
		        }
		      //  it.remove(); // avoids a ConcurrentModificationException
		    }
			if(!(seedNew.equals("")||seedNew==null))
			{
			//delay
				//uncomment
			Thread.sleep(3000);
				System.out.println("Seed New: "+ seedNew);
				cw.getLinks(seedNew);
			}
			else
			System.out.println("No links left to crawl");
			
		}
		
	}


	
	
}