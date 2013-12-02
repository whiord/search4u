package ru.search4u;

public interface IndexCallback {
	void writeLog(String error);
	
	void setTotalDocumentsNumber(int number);

	void setProcessedDocumentsNumber(int number);
	
}