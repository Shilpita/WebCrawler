package p1.noise;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;



public class NoiseImplementation {
//final int threshold = 8;
	
	static HashMap<String, Integer> ListOfLinesFromRepositoryFiles = new HashMap();
	
	static String f1 = "fileD";
	///static String f2 = "fileD5.html";
	
	
	// Each line is compared to the hashmap keys and if present increment the hashmap value
	
	public void populateHash(String f) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line ="";
		 while((line = br.readLine()) != null)
		   { 
			 line = line.trim();
			 if(ListOfLinesFromRepositoryFiles.containsKey(line)){
				    ListOfLinesFromRepositoryFiles.put(line,ListOfLinesFromRepositoryFiles.get(line)+1);
			 }
			 else{
				    ListOfLinesFromRepositoryFiles.put(line,1);
				 
			 }
		   }
		 
		 /*Set<String> keys = ListOfLinesFromRepositoryFiles.keySet();  //get all keys
		 for(String i: keys)
		 {
		     System.out.println(i+ "    " +ListOfLinesFromRepositoryFiles.get(i));
		 }*/
		
	}
	
	public void removeNoise(String f, int threshold) throws IOException{
		 File tmp1 = File.createTempFile("tmp", "");
			BufferedReader br = new BufferedReader(new FileReader(f));
		    BufferedWriter bw = new BufferedWriter(new FileWriter(tmp1));
		
		    String line="",line1="";
		    int flag =0; 
		    int flagp=0;
		    int flagendtagremove=0;
		    int flagsubstring=0;
		    
		    while((line = br.readLine()) != null)
			   {
				   line = line.trim();
				   if(ListOfLinesFromRepositoryFiles.containsKey(line) && ListOfLinesFromRepositoryFiles.get(line) > threshold){
					   
				   }else{
					   bw.write(line+"\n");
				   }
				     
			   }
		    br.close();
		    bw.close();

		    File oldFile = new File(f);
		    if (oldFile.delete())
		        tmp1.renameTo(oldFile);
	}
	
	public void printHashMap(){
		 Set<String> keys = ListOfLinesFromRepositoryFiles.keySet();  //get all keys
		 for(String i: keys)
		 {
		     System.out.println(i+ "   ||||||   " +ListOfLinesFromRepositoryFiles.get(i));
		     
		 }
	}
	
	
}
		


















/*
			 int flag =0;
			 Iterator it = ListOfLinesFromRepositoryFiles.entrySet().iterator();
			 while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			        String s=(String) pair.getKey();
			        Integer i = (Integer) pair.getValue();
			        /*if(s.equals(line)){
			        	pair.setValue(i+1);
			        	flag =1;
			        	break;
			        	
			        if(s.contains(line))
			        }
			    }
			  if(flag==1)
				  ListOfLinesFromRepositoryFiles.put(line, 1);
			  
		   }
	}

	 
	
}
*/