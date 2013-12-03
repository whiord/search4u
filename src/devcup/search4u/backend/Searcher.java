package devcup.search4u.backend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import com.ontos.core.miner.util.ObjectPair;

public class Searcher {

    private RussianAnalyzer analyzer;
    private IndexSearcher searcher;
    private IndexReader reader;
    private Map<String, String> documents;

    public Searcher(String indexDirName) throws IOException {
        FSDirectory dir = FSDirectory.open(new File(indexDirName));
        reader = IndexReader.open(dir);
        searcher = new IndexSearcher(reader);
        analyzer = new RussianAnalyzer();
        documents = new HashMap<String, String>();
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
        return docsList;
    }
    
    public List<SearchResult> search(List<String> queries, boolean isWildcardSearch) throws ParseException, IOException, InvalidTokenOffsetsException
    {          
        int max_hits_number = 1000000;
        QueryParser qParser = new QueryParser(Version.LUCENE_36, "text", analyzer);
        qParser.setAllowLeadingWildcard(isWildcardSearch);
        for (String queryStr : queries) {
            queryStr = processQuery(queryStr);
            Query query = qParser.parse(queryStr);
            TopDocs topDocs = searcher.search(query, max_hits_number);
            System.out.println("Documents found: " + topDocs.scoreDocs.length);
    
            if (topDocs.scoreDocs.length == 0) {
                System.out.println("No documents found for query: " + queryStr);
                continue;
            }
            
            SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
            Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));
            highlighter.setTextFragmenter(new NullFragmenter());

            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document d = searcher.doc(scoreDoc.doc);
                String path = d.get("path");
                
                String text = (documents.containsKey(path) ? documents.get(path) : d.get("text"));
                String hightlightText = highlighter.getBestFragment(analyzer, "", text);
                documents.put(path, hightlightText);
            }
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
        for (String queryStr : queries) {
            SearchResult result = new SearchResult();
            result.setQuery(queryStr);
            
            queryStr = processQuery(queryStr);
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
                        result.addDocumentsFragment(path, offset, fragmentText);
                    }
                }    
            }
            results.add(result);
        }

        return results;
    }
    
    
    public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {        
        String indexDirName = "D:\\test_index";
        Searcher docSearcher = new Searcher(indexDirName);
        
        List<String> qStrs = new ArrayList<String>();
        qStrs.add("Рогов и копыт");
        qStrs.add("Группа компаний Пик"); 
        boolean isWildcardSearch = false;
        List<SearchResult> results = docSearcher.search(qStrs, false);
                
        System.out.println("Finish search");
    }

}
