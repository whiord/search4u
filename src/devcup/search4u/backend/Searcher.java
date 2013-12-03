package ru.search4u;

import java.io.File;
import java.io.IOException;

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
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Searcher {

    private RussianAnalyzer analyzer;
    private IndexSearcher searcher;
    private IndexReader reader;

    public Searcher(String indexDirName) throws IOException {
        FSDirectory dir = FSDirectory.open(new File(indexDirName));
        reader = IndexReader.open(dir);
        searcher = new IndexSearcher(reader);
        analyzer = new RussianAnalyzer();
    }
    
    public SearchResult search(final String queryStr) throws ParseException, IOException, InvalidTokenOffsetsException
    {
        SearchResult result = new SearchResult();
    	
    	int max_hits_number = 1000000;

        System.out.println("Query: "+queryStr+"; max_hits_number: "+max_hits_number);
        Query query = new QueryParser(Version.LUCENE_36, "text", analyzer).parse(queryStr);
        TopDocs topDocs = searcher.search(query, max_hits_number); //TODO: try SpanTermQuery - maybe it will be faster than searcher.doc() and reader.getTermFreqVector()
        System.out.println("Documents found: " + topDocs.scoreDocs.length);

        if (topDocs.scoreDocs.length == 0) {
        	System.out.println("No documents found for query: " + queryStr);
            return null;
        }

        int procecced_hits = 0;
        
        SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
        Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));
        
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {        	
        	
            Document d = searcher.doc(scoreDoc.doc);
            String path = d.get("path");
            String text = d.get("text");
                        
            TokenStream tokenStream = TokenSources.getAnyTokenStream(searcher.getIndexReader(), scoreDoc.doc, "text", analyzer);
            TextFragment[] frag = highlighter.getBestTextFragments(tokenStream, text, false, 10);
            for (int j = 0; j < frag.length; j++) {
                if ((frag[j] != null) && (frag[j].getScore() > 0)) {
                    System.out.println((frag[j].toString()));
                }
            }            
        }

        if (procecced_hits == 0){
        	System.out.println("No good sentence found for query: " + queryStr);
            return null;
        }

        return result;
    }
	
	
	public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {		
		String indexDirName = "D:\\test_index";
		Searcher docSearcher = new Searcher(indexDirName);
		
		String queryStr1 = "Рогов и Копыт";
		SearchResult result1 =  docSearcher.search(queryStr1);
		
		String queryStr2 = "Группа компаний ПИК";
		SearchResult result2 =  docSearcher.search(queryStr2);
				
		System.out.println("Finish search");
	}

}