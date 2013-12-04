package devcup.search4u.backend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.morphology.russian.RussianAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.NullFragmenter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import org.apache.lucene.search.highlight.WeightedSpanTermExtractor;

import com.ontos.core.miner.util.ObjectPair;

public class Searcher {

    private RussianAnalyzer analyzer;
    private IndexSearcher searcher;
    private IndexReader reader;
    private Map<String, String> documents;   
    private SearchCallback callback;

    public Searcher(String indexDirName, SearchCallback callback) throws IOException {
        FSDirectory dir = FSDirectory.open(new File(indexDirName));
        reader = IndexReader.open(dir);
        searcher = new IndexSearcher(reader);
        analyzer = new RussianAnalyzer();
        documents = new HashMap<String, String>();
        this.callback = callback;
    }
    
    private String processQuery(String queryStr) {
    	queryStr = queryStr.replaceAll(" ", " AND ");
    	return queryStr;	
    }
   
    
    private List<ObjectPair<String, String>> formingSortedRelevDocs(Map<String, String> docs) {
    	List<ObjectPair<String, String>> docsList = new ArrayList<ObjectPair<String, String>>();
    	for (Map.Entry<String, String> entry : docs.entrySet()) {
    	    ObjectPair<String, String> pair = new ObjectPair<String, String>();
			pair.setObjects(entry.getKey(), entry.getValue());
			docsList.add(pair);
    	}
    	Collections.sort(docsList, new RelevanceComparator());
    	return docsList;
    }
    
    public List<SearchResult> search(List<String> queries, boolean isWildcardSearch) throws ParseException, IOException, InvalidTokenOffsetsException
    {	  	
        if (callback != null) callback.setTotalProgress(queries.size() + 1);
        int max_hits_number = 1000000;
        QueryParser qParser = new QueryParser(Version.LUCENE_36, "text", analyzer);
        qParser.setAllowLeadingWildcard(isWildcardSearch);
        qParser.setDefaultOperator(QueryParser.Operator.AND);
        for (String queryStr : queries) {
            String initQuery = queryStr;
            if (!isWildcardSearch) {
                queryStr = queryStr.replaceAll("[^a-zA-Z0-9а-яА-ЯёЁ\\s]", "");
                //queryStr = processQuery(queryStr);
            }
	        Query query = qParser.parse(queryStr);
	        TopDocs topDocs = searcher.search(query, max_hits_number);
	        System.out.println("Documents found: " + topDocs.scoreDocs.length);
	
	        if (topDocs.scoreDocs.length == 0) {
	            if (callback != null) callback.writeLog(LogLevel.INFO, "No documents found for query: " + initQuery);
	        	System.out.println("No documents found for query: " + initQuery);
	            continue;
	        }
	        
	        SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
	        Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));
	        highlighter.setTextFragmenter(new NullFragmenter());

	        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
	            Document d = searcher.doc(scoreDoc.doc);
	            String path = d.get("path");
	            
	            String text = (documents.containsKey(path) ? documents.get(path) : d.get("text"));
	            String hightlightText = text;
            	try {
            	    hightlightText = highlighter.getBestFragment(analyzer, "", text);
            	} catch (NoClassDefFoundError e) {
            	    if (callback != null) callback.writeLog(LogLevel.ERROR, e.getMessage());
            	}
            	documents.put(path, hightlightText);
	        }
	        if (callback != null) callback.setCurrentProgress(1);
        }
        
        List<SearchResult> results = getAllFragments(queries, isWildcardSearch);
        
        if (results.size() > 0) {
        	List<ObjectPair<String, String>> docs = formingSortedRelevDocs(documents);
        	results.get(0).addDocuments(docs);
        }
        return results;
    }
	
    private int getWordCount(String str) {
    	Pattern p = Pattern.compile(" ");
        Matcher m = p.matcher(str);
        int count = 1;
        while (m.find()){
        	count +=1;
        }
    	return count;
    }
    
    private List<SearchResult> getAllFragments(List<String> queries, boolean isWildcardSearch) throws ParseException, IOException, InvalidTokenOffsetsException
    {
    	List<SearchResult> results = new ArrayList<SearchResult>(); 	
    	
    	int max_hits_number = 1000000;
        QueryParser qParser = new QueryParser(Version.LUCENE_36, "text", analyzer);
        qParser.setAllowLeadingWildcard(isWildcardSearch);
        qParser.setDefaultOperator(QueryParser.Operator.AND);
        for (String queryStr : queries) {
        	SearchResult result = new SearchResult();
        	result.setQuery(queryStr);
        	
        	if (!isWildcardSearch) {
        	    queryStr = queryStr.replaceAll("[^a-zA-Z0-9а-яА-ЯёЁ\\s]", "");
        	    //queryStr = processQuery(queryStr);
        	}
	        Query query = qParser.parse(queryStr);
	        TopDocs topDocs = searcher.search(query, max_hits_number);
	
	        if (topDocs.scoreDocs.length == 0) {
	            continue;
	        }
	        
	        SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
	        Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));

	        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
	            Document d = searcher.doc(scoreDoc.doc);
	            String path = d.get("path");
	            String text = d.get("text");
	            
	            TokenStream tokenStream = TokenSources.getAnyTokenStream(searcher.getIndexReader(), scoreDoc.doc, "text", analyzer);
	            TextFragment[] frag = highlighter.getBestTextFragments(tokenStream, text, false, max_hits_number);
	            for (int i = 0; i < frag.length; i++) {
	            	int wordCount = getWordCount(result.getQuery());
	                if ((frag[i] != null) && (frag[i].getScore() >= wordCount)) {
	                	String fragmentText = frag[i].toString();
	                	int offset = 0;
	                	result.addRelevDocsPath(path);
	                    result.addDocumentsFragment(path, offset, fragmentText);
	                }
	            }
	        }
	        results.add(result);
        }
        if (callback != null) callback.setCurrentProgress(1);
        return results;
    }
    
	
	public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {		
		String indexDirName = "D:\\test_index";
		Searcher docSearcher = new Searcher(indexDirName, null);
		
		List<String> qStrs = new ArrayList<String>();
		//qStrs.add("ООО \\\"Рог и копытом\\\"");
		//qStrs.add("\"встреча директором\"~");
		qStrs.add("Рога и копыта");
		//qStrs.add("Группа компаний ПИК"); 
		qStrs.add("Петром Сергеевичем");
		boolean isWildcardSearch = false;
		List<SearchResult> results = docSearcher.search(qStrs, false);
				
		System.out.println("Finish search");
	}

}

class RelevanceComparator implements Comparator<ObjectPair<String, String>> {

    private int getRelev(String str) {
        Pattern p = Pattern.compile("<B>");
        Matcher m = p.matcher(str);
        int count = 1;
        while (m.find()){
            count +=1;
        }
        return count;
    }
    
    public int compare(ObjectPair<String, String> obj1, ObjectPair<String, String> obj2) {
        int relev1 = getRelev(obj1.getSecond());
        int relev2 = getRelev(obj2.getSecond());
        return relev1 > relev2 ? -1 : relev1 == relev2 ? 0 : 1;
    }
}
