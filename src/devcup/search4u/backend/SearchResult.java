package devcup.search4u.backend;

import java.util.HashMap;
import java.util.Map;

import com.ontos.core.miner.util.ObjectPair;
import java.util.List;


public class SearchResult {
	private String query;
	private List<ObjectPair<String, String> > documents; // key - document path, value - document highlighting text
	private Map<String, List< ObjectPair<Integer, String> > > fragments; // key - document path, value - pair(offset, fragment text)
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void addDocumentsText(String path, String text) {
		if (documents == null) {
			documents = new HashMap<String, String>();
		}
		documents.put(path, text);
	}
	
	public List<ObjectPair<String, String> > getDocumentsText() {
		return documents;
	}
	
	public void addDocumentsFragment(String path, Integer offset, String fragmentText) {
		if (fragments == null) {
			fragments = new HashMap<String, List< ObjectPair<Integer, String> > >();
		}
		ObjectPair<Integer, String> value = new ObjectPair<Integer, String>();
		value.setObjects(offset, fragmentText);
		fragments.put(path, value);
	}
	
	public Map<String, List<ObjectPair<Integer, String>>> getDocumentsFragments() {
		return fragments;
	}

}