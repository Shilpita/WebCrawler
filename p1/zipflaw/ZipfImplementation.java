package p1.zipflaw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ZipfImplementation {

	static HashMap<String,Integer> mapCounter=  new HashMap();
	//static TreeMap<Integer, String> sortedMap = new TreeMap();
	
	/*
	public static TreeMap<Integer, String> getSortedMap() {
		return sortedMap;
	}


	public static void setSortedMap(TreeMap<Integer, String> sortedMap) {
		ZipfImplementation.sortedMap = sortedMap;
	}
*/

	public void addToListMap(String newFilePath) throws IOException {
	int count=0;
	
	BufferedReader br = new BufferedReader(new FileReader(newFilePath));
	String line="";
	while((line=br.readLine())!=null){
		String temp[]=line.split(" ");
		for(int i=0; i<temp.length;i++){
			String key=temp[i].replaceAll("[^A-Za-z]+", "");
			key=key.trim();
			if(key==null || ("\n".equals(key)) ||("".equals(key)) || (key.isEmpty())){
		    	
	    	}
	    	else{
			
			if(mapCounter.containsKey(key)){
				count=mapCounter.get(key);
				mapCounter.put(key,count+1);
				}
				else{
					mapCounter.put(key,1);	
				}
	    	}
		}
	}
	br.close();
	
	}
	
	
	public void printHashMap(){
		 Set<String> keys = mapCounter.keySet();  //get all keys
		 for(String i: keys)
		 {
		     System.out.println(i+ "   ||||||   " +mapCounter.get(i));
		     
		 }
	}
	
	/*
	public void sortHashMapToTreeMap(){
		
		for (Map.Entry entry : mapCounter.entrySet()) {
		    sortedMap.put( Integer.parseInt( entry.getValue()+""), entry.getKey()+"");
		}
	}
	
	public void printTreeMap(){
		 Set<Integer> keys = sortedMap.keySet();  //get all keys
		 for(Integer i: keys)
		 {
		     System.out.println(i+ "   ||||||   " +sortedMap.get(i));
		     
		 }
	}
	
	*/
	
	
	
	public static Map<String, Integer> sortByValue() {
        List list = new LinkedList(mapCounter.entrySet());
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

	
	}
