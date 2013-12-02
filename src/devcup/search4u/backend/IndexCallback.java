package devcup.search4u.backend;

public interface IndexCallback {
	void writeLog(String error);
	
	void setTotalDocumentsNumber(int number);

	void setProcessedDocumentsNumber(int number);
    
    void processingDone();
	
}