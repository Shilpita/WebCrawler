/*************************************************************************************************************************
 * COEN 272 Project 1
 * Purpose : Basic Web crawler
 * Author  : Mansi and Shilpita
 * Started : 7April2016
 * 
 * **************************************************************************************************************************/


package p1.crawler;



import java.io.IOException;

public class CrawlerMainClass {
public static void main(String[] args) throws IOException, InterruptedException {
	//location of the CVS file
	String csvFile = "specification.cvs";
	
	
	
	CrawlerImplementation ci= new CrawlerImplementation();
	String input[]=ci.inputDataFromCVSFile(csvFile);
	int limit=Integer.parseInt(input[1]);
	CrawlerWrapper cw =new CrawlerWrapper(input[0],limit,input[2]);
	cw.startPartReportHtml();
	ci.crawlingfunction(cw);
	cw.endPartReportHtml();
	
}
}