/*************************************************************************************************************************
 * COEN 272 Project 1
 * Purpose : Basic Web crawler
 * Author  : Mansi and Shilpita
 * Started : 7April2016
 * 
 * **************************************************************************************************************************/

package p1.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;



//Need to check every url before its crawled... 
//to remove it from the crawling list before its crawled....
public class RobotRuleFinal 
	{
	
	    public String userAgent;
	    public String rule;

	
	    @Override public String toString() 
	    {
	        StringBuilder result = new StringBuilder();
	        String NEW_LINE = System.getProperty("line.separator");
	        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
	        result.append("   userAgent: " + this.userAgent + NEW_LINE);
	        result.append("   rule: " + this.rule + NEW_LINE);
	        result.append("}");
	        return result.toString();
	    }   
	    public  boolean robotSafe(URL url) 
		{
		    String strHost = url.getHost();

		    String strRobot = "https://" + strHost + "/robots.txt";
		    URL urlRobot;
		    try { urlRobot = new URL(strRobot);
		    } catch (MalformedURLException e) {
		        return false;
		    }

		    String strCommands;
		    try 
		    {
		        URLConnection urlConnection = urlRobot.openConnection();
		    	 InputStream inputStream = urlConnection.getInputStream();
		         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		         String line = bufferedReader.readLine();
		         strCommands="";
		         
		         
		         while (line != null) {
		           System.out.println(line);
		           line = bufferedReader.readLine();
		           strCommands+=line+"\n";
		        
		          
		         }
		         bufferedReader.close();
		    
		    } 
		    catch (IOException e) 
		    {
		        return true; // if there is no robots.txt file, it is OK to search
		    }

		    if (strCommands.contains("Disallow")) // if there are no "disallow" values, then they are not blocking anything.
		    {
		    	System.out.println("HI");
		        String[] split = strCommands.split("\n");
		        ArrayList<RobotRuleFinal> robotRules = new ArrayList<>();
		        String mostRecentUserAgent = null;
		        for (int i = 0; i < split.length; i++) 
		        {
		            String line = split[i].trim();
		            if (line.toLowerCase().startsWith("user-agent")) 
		            {
		                int start = line.indexOf(":") + 1;
		                int end   = line.length();
		                mostRecentUserAgent = line.substring(start, end).trim();
		            }
		            else if (line.startsWith("Disallow")) {
		                if (mostRecentUserAgent != null) {
		                	RobotRuleFinal r = new RobotRuleFinal();
		                    r.userAgent = mostRecentUserAgent;
		                    int start = line.indexOf(":") + 1;
		                    int end   = line.length();
		                    r.rule = line.substring(start, end).trim();
		                    robotRules.add(r);
		                }
		            }
		        }

		        for (RobotRuleFinal robotRule : robotRules)
		        {
		            String path = url.getPath();
		            System.out.println(path+""+robotRule.rule);
		            
		            
		            
		            if (robotRule.rule.length() == 0) return true; // allows everything if BLANK
		            if (robotRule.rule == "/") return false;       // allows nothing if /

		            if (robotRule.rule.length() <= path.length())
		            { 
		                String pathCompare = path.substring(0, robotRule.rule.length());
		                if (pathCompare.equals(robotRule.rule)) return false;
		            }
		        }
		    }
		    return true;
		}

	    //method to call the robot file....
	    /*public static void main(String[] args) {
	    	URL url = null;
			try {
				url = new URL("https://en.wikipedia.org");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	robotSafe(url);
		}*/
	    
	    
	}
