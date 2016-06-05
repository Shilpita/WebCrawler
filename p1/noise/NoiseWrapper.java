package p1.noise;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class NoiseWrapper {
public  ArrayList<String> readingReportToGetLocationOfArrayListOfFileDownloaded( String fileLocation) throws IOException{
	
		
		
		ArrayList<String> arrayLReport=new ArrayList<String>();
		
		
		BufferedReader br = new BufferedReader(new FileReader(fileLocation));
		String reportLine="";
		
		while((reportLine=br.readLine())!=null){
			if(reportLine.contains("Repository file")){
			
				String addurl[]=reportLine.split("\"");
				addurl[1] = addurl[1].replace("..\\","");
				addurl[1] = addurl[1].replace("\\","/");
				///addurl[1] = addurl[1].replace("\\","\\\\");
				//System.out.println(addurl[1]);
				/*
				String addurl[]=reportLine.split("/'");
				addurl[1] = addurl[1].replace("..\\","");
				addurl[1] = addurl[1].replace("\\","\\\\");
				*/
				arrayLReport.add(addurl[1]);
			}
			
			
		}
		
		
		return arrayLReport;
		
		
	}
	
	public  void removeBodyStripableContent(String f) throws IOException{
		//read 2 files from the body tag
		//read fileandremove everythingexcept the body and </boY 
		//remove the remaining bottom
		//String f= "repository\\fileD181.html";
		//File file = new File("repository\\fileD118.html");
	//	File f=File.createTempFile(prefix, suffix, directory)
		 File tmp = File.createTempFile("tmp", "");
		BufferedReader br = new BufferedReader(new FileReader(f));
	    BufferedWriter bw = new BufferedWriter(new FileWriter(tmp));
	
	    String line="",line1="";
	    int flag =0; 
	    int flagp=0;
	    int flagendtagremove=0;
	    int flagsubstring=0;
	    
	    while((line = br.readLine()) != null)
	   {
		   line = line.trim();
	 	   if(line.contains("<body") )
	 		   flag=1;
	 	   if(line.contains("</body>"))
	 	   {  flag=0;
	 	  bw.write(line+"\n");
	 	   }
	 	   
	 	   if(flag==1)
	 	   {
	 		  flagsubstring=0;
	 		   flagendtagremove=0;
	 		 // flagp=0;
	 		   if(line.contains("</script>")||line.contains("-->")||line.contains("</style>") ||line.contains("©") )
	 			  {flagp=0;
	 			  flagendtagremove=1;
	 			 flagsubstring=0;
	 			  }
	 		   
	 		   if((line.contains("<script")||line.contains("<!--")||line.contains("<style")) && !(line.contains("</script>")||line.contains("-->")||line.contains("</style>")))
	 		   { flagp=1;flagendtagremove=1;flagsubstring=0;}
	 		  if((line.contains("<script")||line.contains("<!--")||line.contains("<style")) && (line.contains("</script>")||line.contains("-->")||line.contains("</style>")))
	 		   { flagp=0;flagendtagremove=1;flagsubstring=1;}

	        if(flagp==0){
	        	if(flagendtagremove==0 &&flagsubstring==0)
	        		 bw.write(line+"\n");
	        	if(flagendtagremove==1 && flagsubstring==0){
	        	
	        	}
	        	if(flagendtagremove==1 && flagsubstring==1){
	        		if(line.contains("</script>")){
	        		String s1[]=	line.split("</script>");
	        		if(s1.length>1)
	        			bw.write(s1[1]+"\n");
	        		
	        		
	        		}
	        				
	        	if(line.contains("-->")){
	        		String s1[]=	line.split("-->");
	        		if(s1.length>1)
	        		 bw.write(s1[1]+"\n");
	        	}
	        		
	        		if(line.contains("</style>")){
	        			String s1[]=	line.split("</style>");
	        			if(s1.length>1)
		        		 bw.write(s1[1]+"\n");	
	        		}
	        		
	        	}
	        		
	        }
	 		 
	 	   
	 	   
	 	   
	 	   }
	        
	   }
	   br.close();
	    bw.close();

	    File oldFile = new File(f);
	    if (oldFile.delete())
	        tmp.renameTo(oldFile);
	}
	
	
	

}
