package devcup.search4u.backend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

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

import com.ontos.core.miner.util.ObjectPair;

public class Searcher {

//	    public static final int MAX_STEP = 450;
    private RussianAnalyzer analyzer;
    private IndexSearcher searcher;
    private IndexReader reader;

    public Searcher(String indexDirName) throws IOException {
        FSDirectory dir = FSDirectory.open(new File(indexDirName));
        reader = IndexReader.open(dir);
        searcher = new IndexSearcher(reader);
        analyzer = new RussianAnalyzer();
    }

    private static boolean isBigRussianLetter(char letter) {
        return letter >= '�' && letter <= '�' || letter == '�';
    }

    private static boolean isSentenceDelim(char c){
        return c=='.' || c=='!' || c=='?' || c=='�';
    }

    private static boolean isStartSentence(String s, int pos)
    {
        return isBigRussianLetter(s.charAt(pos)) &&
                ( pos == 0 || pos > 0 && s.charAt(pos-1) == '\n' ||
                  pos > 1 && s.charAt(pos-1) == ' ' && isSentenceDelim(s.charAt(pos - 2)) );
    }

    private String GetSentence(String input_hit, int offset)
    {
        int start_sentence_pos, end_sentence_pos;
        start_sentence_pos = end_sentence_pos = offset;

        //get start position of sencence
        while (start_sentence_pos > 0 && !isStartSentence(input_hit, start_sentence_pos))
            start_sentence_pos--;

        //get end position of sencence
        while (end_sentence_pos < input_hit.length() && !isSentenceDelim(input_hit.charAt(end_sentence_pos)) )
            end_sentence_pos++;

        if (end_sentence_pos != input_hit.length())
            end_sentence_pos++;

        char[] chars = input_hit.substring(start_sentence_pos, end_sentence_pos).toCharArray();
        return new String(chars);
    }


    private static boolean IsGoodSentence(String sentence) {
        return isBigRussianLetter(sentence.charAt(0)) && isSentenceDelim(sentence.charAt(sentence.length()-1));
    }

    @Override
    protected void finalize() throws Throwable {
        searcher.close();
    }
    
    public Collection<ObjectPair<String, String>> search(final String query_str, Integer max_hits_number) throws ParseException, IOException, InvalidTokenOffsetsException
    {
        if (max_hits_number == -1)
            max_hits_number = 1000000;

        System.out.println("Query: "+query_str+"; max_hits_number: "+max_hits_number);
        Query query = new QueryParser(Version.LUCENE_36, "text", analyzer).parse(query_str);
        TopDocs topDocs = searcher.search(query, max_hits_number); //TODO: try SpanTermQuery - maybe it will be faster than searcher.doc() and reader.getTermFreqVector()
        System.out.println("Documents found: " + topDocs.scoreDocs.length);

        if (topDocs.scoreDocs.length == 0) {
        	System.out.println("No documents found for query: " + query_str);
            return null;
        }

        Collection<ObjectPair<String, String>> result = new ArrayList<ObjectPair<String, String>>();
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
        	System.out.println("No good sentence found for query: " + query_str);
            return null;
        }

        return result;
    }
	
	
	public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
		/*
		String indexDirName = args[0];
		String resultFileName = args[1];
		String queryStr = args[2];
		Integer maxHitsNumber = Integer.valueOf(args[3]);
		*/
		
		String indexDirName = "D:\\test_index";
		String resultFileName = "D:\\search_results.txt";
		String queryStr = "����� � �����";
		Integer maxHitsNumber = Integer.valueOf(50);
		
		Searcher docSearcher = new Searcher(indexDirName);
		ArrayList<ObjectPair<String, String>> resultSentences = 
				(ArrayList<ObjectPair<String, String>>) docSearcher.search(queryStr, maxHitsNumber);
		
		try {
			PrintWriter out = new PrintWriter(new FileWriter(resultFileName));
			out.println(queryStr);
	        for (ObjectPair<String, String> entry : resultSentences) { 
	        	out.println(entry.getFirst());
	        }
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Finish search");

	}

}