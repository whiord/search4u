package ru.search4u;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.morphology.russian.RussianAnalyzer;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class Indexer {
	private IndexCallback callback;
	
	public Indexer (IndexCallback callback){
		this.callback = callback;
	}
	
    protected void handle_docs(List<String> docs_list, IndexWriter writer) throws IOException {
        if (callback != null) callback.setTotalDocumentsNumber(docs_list.size());
    	int procecced_files_number = 0, processed_with_errors = 0;
        
        for (String doc : docs_list) {
            File file = new File(doc);
            StringBuilder text = new StringBuilder();
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream (file), "UTF8");
                BufferedReader bufferedReader = new BufferedReader (inputStreamReader);               
                String str = "";
                while ((str = bufferedReader.readLine()) != null) {
                    text.append(str);
                    text.append("\n");
                }

            } catch (IOException ex) {
            	processed_with_errors++;
            	if (callback != null) callback.writeLog("Error in document " + doc + " : " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                procecced_files_number++;
                if (callback != null) callback.setProcessedDocumentsNumber(procecced_files_number);
            }
            
            if (text.length() > 0) {
            	Document document = new Document();
                document.add(new Field("path", doc, Field.Store.YES, Field.Index.NO));
                document.add(new Field("text", text.toString(), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
                writer.addDocument(document);
            } else {
            	if (callback != null) callback.writeLog("Empty document : " + doc);
            }
        }
        System.out.println("All documents is procecced");
    }
	
    public List<String> walkInPath( String path ) {
        File root = new File( path );
        File[] list = root.listFiles();
        List<String> docs_list = new ArrayList<String>();
        
        if (list == null) return null;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
            	List<String> tmp_docs_list = walkInPath( f.getAbsolutePath() );
                if (tmp_docs_list != null) {
                	docs_list.addAll(tmp_docs_list);
                }
            }
            else {
            	String fileName = f.getAbsolutePath();
            	if (fileName.endsWith(".txt")) {
            		docs_list.add(fileName);
            	}
            }
        }
        return docs_list;
    }
    
    private List<String> getDocsList(String docs_path) {
        if (docs_path.charAt(docs_path.length() - 1) != '/') {
        	docs_path += "/";
        }

        File file_in_path = new File(docs_path);
        if (!file_in_path.exists()) {
        	if (callback != null) callback.writeLog("Not found: " + docs_path);
            System.out.println("\nNot found: " + docs_path);
            return null;
        }

        if (!file_in_path.isDirectory()) {
        	if (callback != null) callback.writeLog("Not directory: " + docs_path);
            System.out.println("\nNot directory: " + docs_path);
            return null;
        }
        
        return walkInPath(docs_path);
    }
	
    public static void deleteDir(File file) throws IOException {
    	if (file.isDirectory()) {
    		if(file.list().length == 0){
    		   file.delete();
    		   System.out.println("Directory is deleted : "  + file.getAbsolutePath());
    		} else {
        	   String files[] = file.list();
        	   for (String temp : files) {
        	      File fileDelete = new File(file, temp);
        	      deleteDir(fileDelete);
        	   }
 
        	   if(file.list().length==0){
           	     file.delete();
        	     System.out.println("Directory is deleted : " + file.getAbsolutePath());
        	   }
    		}
    	} else {
    		file.delete();
    		System.out.println("File is deleted : " + file.getAbsolutePath());
    	}    
	}
    
    public void buildIndex(String docs_path, String index_path) throws Exception {
    	File directory = new File(index_path);
    	if (directory.exists()) {
    		deleteDir(directory);
    	}
    	
    	List<String> docs_list = getDocsList(docs_path);
    	
        RussianAnalyzer analyzer = new RussianAnalyzer();
        FSDirectory dir = FSDirectory.open(new File(index_path));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36, analyzer);
        
        IndexWriter writer = new IndexWriter(dir, config);
        handle_docs(docs_list, writer);
        writer.close();
    }
    
	public static void main(String[] args) throws Exception {
		String docs_path = "D:\\test_txt";
        String index_path = "D:\\test_index";
        System.out.println("Start indexing");
        IndexCallback callback;
        Indexer indexer = new Indexer(null);
        
        indexer.buildIndex(docs_path, index_path);
        System.out.println("Finish indexing");
	}
}