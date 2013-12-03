package devcup.search4u.backend;

import devcup.search4u.common.LogLevel;

public interface IndexCallback {
	void writeLog(LogLevel level, String message);
	
	void setTotalDocumentsNumber(int number);

	void setProcessedDocumentsNumber(int number);
    
    void processingDone();
	
}