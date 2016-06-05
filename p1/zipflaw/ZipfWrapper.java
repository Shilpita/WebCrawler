package p1.zipflaw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ZipfWrapper {

	String newFilePath="";
	 int counter=0;
	 
	 
	public String removeTagsMakeNewRepository(String filename) throws IOException {
		String newFilePath12=createTextFile(filename);
		BufferedReader br = new BufferedReader(new FileReader(filename));
	    BufferedWriter bw = new BufferedWriter(new FileWriter(newFilePath12));
	String line="";
	    while((line=br.readLine())!=null){
	    	String temp=Jsoup.parse(line).text();
	    	temp=temp.trim();
	    	if(temp==null || ("\n".equals(temp)) ||("".equals(temp)) || (temp.isEmpty())){
	    	
	    	}
	    	else{
	    	//	System.out.println("Hi     "+ temp+ "   HI ");
	    		bw.write(temp+"\n");
	    	}
	    	
	    }

	    br.close();
	    
	    bw.close();
	    
	    return newFilePath12;
	}

	
	public String createTextFile(String filename) throws IOException {
		/*
		 * File file = new File("repository\\test"+counter +".txt");
		 * file.getParentFile().mkdir(); file.createNewFile();
		 */

		// delete the files if its the first file being downloaded

		try {

		    Pattern pattern = Pattern.compile("([0-9]+)");
		//    System.out.println(filename);
		    Matcher matcher = pattern.matcher(filename);
		    int number = 0;
		    while (matcher.find()) {
		        number = Integer.parseInt(matcher.group(1));
		 //   System.out.println("number  "+ number);
		    }
		    
		    newFilePath="zipfrepository/fileD" + number + ".html";
			File file = new File(newFilePath);
			
			
			
			
			
			// deleting all the files if counter=0
			if (file.exists() && number == 1)
				deleteFolder(new File("zipfrepository"));

			// if file doesnt exists, then create it
			file.getParentFile().mkdirs();
			if (!file.exists()) {
				file.createNewFile();
			}

		//	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	//		BufferedWriter bw = new BufferedWriter(fw);
		//	bw.write(content);
		//	bw.close();

			//System.out.println("File  Path  " +newFilePath);

		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return newFilePath;
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
}