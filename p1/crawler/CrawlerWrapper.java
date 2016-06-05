/*************************************************************************************************************************
 * COEN 272 Project 1
 * Purpose : Basic Web crawler
 * Author  : Mansi and Shilpita
 * Started : 7April2016
 * 
 * **************************************************************************************************************************/

package p1.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.HttpURLConnection;

public class CrawlerWrapper {
	String seedUrl;
	int numberOfSitesToBeCrawled;
	String domain;
	static int counter = 1;
	int flagOutputCounts=0;
	
	//the variable is used for the  middle 
	//function to write the appropriate report in the report.html file 
	//
	
	HashMap<String, String[]> ListOfLinksObtainedFromCrawling = new HashMap();
	//for the links in the map
	//0: not visited
	//2: visited
	//1: status code issue
	
    private final static Pattern Filters= Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	
	// constructor

	public CrawlerWrapper(String seedUrl, int numberOfSitesToBeCrawled, String domain) {
		super();
		this.seedUrl = seedUrl;
		this.numberOfSitesToBeCrawled = numberOfSitesToBeCrawled;
		this.domain = domain;
	}

	// getters and setters
	public String getSeedUrl() {
		return seedUrl;
	}

	public void setSeedUrl(String seedUrl) {
		this.seedUrl = seedUrl;
	}

	public int getNumberOfSitesToBeCrawled() {
		return numberOfSitesToBeCrawled;}

	public void setNumberOfSitesToBeCrawled(int numberOfSitesToBeCrawled) {
		this.numberOfSitesToBeCrawled = numberOfSitesToBeCrawled;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	// domain verification

	public static String getDomain(String url) throws MalformedURLException {
		String cleanUrl = url.toLowerCase().trim();
		URL link = new URL(cleanUrl);
		String url_domain = link.getHost();
		System.out.println(url_domain);
		return url_domain;
	}

	
	// getting links
	public void getLinks(String url) throws IOException {
		 flagOutputCounts=0;
		System.out.println(url);
		Elements links = null;
		Document doc = null;
		int statusCode=0;
		if (getDomain(url).contains(domain) || domain == null) {
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			statusCode = connection.getResponseCode();
			
			
			
			
/*			Connection.Response response = Jsoup.connect(url)
				.userAgent(
						"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
				.timeout(100000).execute();*/
		// getting the status code
		
		String s[]={url,"2"};
		//need check...
		ListOfLinksObtainedFromCrawling.put(url, s);
		// verifying the domain url
		if (getDomain(url).contains(domain) || domain == null) {
			if (statusCode == 200) {
				URL urlr = new URL(url);
				RobotRuleFinal ro = new RobotRuleFinal();
				if(ro.robotSafe(urlr)){
				try {

				doc = Jsoup.connect(url).get();

				}

				catch (Exception e) {

					links = null;
				}

				finally {
					// Make a text file in the repository folder and add the
					// details
					// name of the text file will be counter which is static
					// so that its incremented once as the instance of the
					// program continues
					// add to the report file if counter is 1 then make from
					// scratch of counter is limit then
					// build the end of the report
				/*	String s[]={doc.baseUri(),"2"};
					//need check...
					ListOfLinksObtainedFromCrawling.put(doc.baseUri(), s);*/
					//System.out.println("doc   " + doc);
					if(doc!=null)
					 createDownloadTextFile(doc);
					
					reportDetailsAddition(doc,statusCode);
					counter++;
					links = doc.select("a[href]");
					
					if (links != null) {
						/*int i = 0;*/
						for (Element link : links) {
							/*i++;
							if (i == 20)
								break;*/
						String	linkString=link.attr("abs:href").toString().trim();
							System.out.println(linkString);
							// addtoHashSet links
							if(linkString==null || linkString.equals("") || linkString.contains(".pdf") || shouldNotVisit(linkString)){
							
							}
							else{
								addLinkToHashMap(linkString);	
							}
						}

					}
				}
			} 
				else{
					System.out.println("Url is not permitted tobe crawlered by Robot file report has been generated for the same");
				flagOutputCounts=1;
				String s1[]={url,"1"};
				//need check...
				ListOfLinksObtainedFromCrawling.put(url, s1);
				reportDetailsAddition(statusCode,url);

				}
			}
			
				
				else {
				System.out.println("received error code : " + statusCode);
				String s1[]={url,"1"};
				//need check...
				ListOfLinksObtainedFromCrawling.put(url, s1);
				reportDetailsAddition(statusCode,url);
				
				
			}
		} else {
			flagOutputCounts=2;
			String s1[]={url,"1"};
			//need check...
			ListOfLinksObtainedFromCrawling.put(url, s1);
			System.out.println("\n Incorrect domain");
			
			reportDetailsAddition(statusCode,url);
		}
		}
		else {
			flagOutputCounts=2;
			String s1[]={url,"1"};
			//need check...
			ListOfLinksObtainedFromCrawling.put(url, s1);
			System.out.println("\n Incorrect domain");
			reportDetailsAddition(statusCode,url);
		}
		
	}
	
	
	
	
	
	// TODO adding links to the HashMap of links to be crawled
	public void addLinkToHashMap(String linkString) {
		int flag=1;
		int endSlash=linkString.length();
		if((linkString.charAt(endSlash-1)+"").equals("/")){
		if(ListOfLinksObtainedFromCrawling.containsKey(linkString.substring(0, endSlash-1))){
			flag=0;
		}
			
		}
		if(!(ListOfLinksObtainedFromCrawling.containsKey(linkString)) && flag==1 ){
			String s[]={linkString,"0"};
			ListOfLinksObtainedFromCrawling.put(linkString, s);
		}
		
	}

	//adding the details of the document that has been crawled
	private void reportDetailsAddition(Document doc,int statusCode) {
		
		
		System.out.println(doc.baseUri());
		System.out.println(doc.title());
		//System.out.println(doc.);
		Elements imagesForReport = doc.select("img");
		System.out.println("image no"+imagesForReport.size());
		
		Elements linksForReport = doc.select("a[href]");
		System.out.println("outlinks no"+linksForReport.size());
		
		try (FileWriter fw = new FileWriter("report/report12.html", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {

			out.println("<!-- middle part " + counter + " -->");
			out.println("<tr>");
			out.println("<td>" + counter + "</td>");
			out.println("<td><a href=\""+doc.baseUri()+"\">"+doc.title()+"</a><br>");
			out.println("<a href=\"..\\repository\\fileD" + counter + ".html\">Repository file</a></td>");
			
			out.println("<td>"+statusCode+"</td>");
			out.println("<td>"+imagesForReport.size()+"</td>");
			out.println("<td>"+linksForReport.size()+"</td>");
			out.println(" </tr>");
			out.println(" <!-- middle part " + counter + " -->");

		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}
		
		
		
		
		
	}

	///in case of no crawling
	private void reportDetailsAddition(int statusCode,String url){
		if(statusCode!=200 && flagOutputCounts==0){
		try (FileWriter fw = new FileWriter("report/report12.html", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {

			out.println("<!-- middle part " + counter + " -->");
			out.println("<tr>");
			out.println("<td>" + counter + "</td>");
			out.println("<td><a href=\""+url+"\"></a>"+url+"</td>");
			out.println("<td>"+statusCode+"</td>");
			out.println("<td colspan=\"2\">Could not be crawled due to status code</td>");
		//	out.println("<td></td>");
			out.println(" </tr>");
			out.println(" <!-- middle part " + counter + " -->");
			counter++;
		
		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}}
		else if(flagOutputCounts==1){
			
			try (FileWriter fw = new FileWriter("report/report12.html", true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw)) {

				out.println("<!-- middle part " + counter + " -->");
				out.println("<tr>");
				out.println("<td>" + counter + "</td>");
				out.println("<td><a href=\""+url+"\">"+url+"</a></td>");
				out.println("<td>"+statusCode+"</td>");
				out.println("<td colspan=\"2\">Not permitted to crawl as per robot.txt</td>");
				//out.println("<td></td>");
				out.println(" </tr>");
				out.println(" <!-- middle part " + counter + " -->");
				counter++;
			
			} catch (IOException e) {
				// exception handling left as an exercise for the reader
			}
			
			
		}
		
		else if(flagOutputCounts==2){
			
			try (FileWriter fw = new FileWriter("report/report12.html", true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw)) {

				out.println("<!-- middle part " + counter + " -->");
				out.println("<tr>");
				out.println("<td>" + counter + "</td>");
				out.println("<td><a href=\""+url+"\">"+url+"</a></td>");
				out.println("<td>"+"invalid"+"</td>");
				out.println("<td colspan=\"2\">Incorrect domain name</td>");
				//out.println("<td></td>");
				out.println(" </tr>");
				out.println(" <!-- middle part " + counter + " -->");
				counter++;
			
			} catch (IOException e) {
				// exception handling left as an exercise for the reader
			}
			
			
		}
		
		
		
		
	}
	
	
	
	
	
	// Make a text file in the repository folder and add the details
	// name of the text file will be counter which is static
	// so that its incremented once as the instance of the program continues
	public void createDownloadTextFile(Document doc) throws IOException {
		/*
		 * File file = new File("repository\\test"+counter +".txt");
		 * file.getParentFile().mkdir(); file.createNewFile();
		 */

		// delete the files if its the first file being downloaded

		try {

			String content = doc.toString();

			File file = new File("repository/fileD" + counter + ".html");

			// deleting all the files if counter=0
			if (file.exists() && counter == 1)
				deleteFolder(new File("repository"));

			// if file doesnt exists, then create it
			if (file.getParentFile() != null) {
			   file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Done file writing");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//deletes the folder and its contents
	public static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}

	// generate the start and the end parts ofthe report
	public void startPartReportHtml() {
		try {
			StringBuilder content = new StringBuilder();
			content.append("<!-- start part -->\n");
			content.append("<!DOCTYPE html>\n");
			content.append("<html>\n");
			content.append("<head>\n");
			content.append("<style>\n");
			content.append("table, th, td {\n");
			content.append("border: 1px solid black;\n");
			content.append("border-collapse: collapse;\n");
			content.append("}\n");
			content.append("th, td {\n");
			content.append("padding: 5px;\n");
			content.append("text-align: left;\n");
			content.append("}\n");
			content.append("</style>\n");
			content.append("</head>\n");
			content.append("<body>\n");

			content.append("<table style=\"width:100%\">\n");
			content.append("<caption>Crawler Report</caption>\n");
			content.append("<tr>\n");
			content.append("<th>Sr.No.</th>\n");
			content.append("<th>Page Title</th>\n");
			content.append("<th>HTTP status code</th>\n");
			content.append("<th>number of outlinks</th>\n");
			content.append("<th>number of images</th>\n");
			content.append("</tr>\n");
			content.append("<!-- start part -->\n");

			File file = new File("report/report12.html");

			// deleting all the files ifcounter=0
			if (file.exists())
				deleteFolder(new File("report"));

			// if file doesnt exists, then create it
			if (file.getParentFile() != null) 
				file.getParentFile().mkdirs();
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content.toString());
			bw.close();

			System.out.println("Done file writing");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void endPartReportHtml() {
		try (FileWriter fw = new FileWriter("report/report12.html", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw)) {

			out.println(" <!-- end part -->");
			out.println("</table>");

			out.println("</body>");
			out.println("</html>");
			out.println("<!-- end part -->");

		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}

	}
	
	
	

	   public static boolean shouldNotVisit(String url){
	      String cleanUrl = url.toLowerCase().trim();
	      return ((Filters.matcher(cleanUrl).matches()));

	   }

}