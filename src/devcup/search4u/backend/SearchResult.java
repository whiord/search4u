package devcup.search4u.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ontos.core.miner.util.ObjectPair;
import java.util.List;


public class SearchResult {
	private String query;
	private Map<String, List<ObjectPair<Integer, String>>> fragments; // key - document path, value - pair(offset, fragment text)
	private List<ObjectPair<String, String>> documents; // key - document path, value - document highlighting text
	private List<String> relevDocsPath;
	
   public void addRelevDocsPath(String path) {
       if (relevDocsPath == null) {
           relevDocsPath = new ArrayList<String>();
       }
       relevDocsPath.add(path);
    }
    
    public List<String> getRelevDocsPath() {
        return relevDocsPath;
    }
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void addDocuments(List<ObjectPair<String, String>> docs) {
	    documents = docs;
	}
   
	public List<ObjectPair<String, String>> getDocuments() {
		return documents;
	}
	
	public void addDocumentsFragment(String path, Integer offset, String fragmentText) {
		if (fragments == null) {
			fragments = new HashMap<String, List<ObjectPair<Integer, String>>>();
		} 
		
		if (fragments.containsKey(path)) {
			ObjectPair<Integer, String> pair = new ObjectPair<Integer, String>();
			pair.setObjects(offset, fragmentText);
			fragments.get(path).add(pair);
		} else {
			List<ObjectPair<Integer, String>> value = new ArrayList<ObjectPair<Integer, String>>();
			ObjectPair<Integer, String> pair = new ObjectPair<Integer, String>();
			pair.setObjects(offset, fragmentText);
			value.add(pair);
			fragments.put(path, value);
		}
	}
	
	public Map<String, List<ObjectPair<Integer, String>>> getDocumentsFragments() {
		return fragments;
	}

}
