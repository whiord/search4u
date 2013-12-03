/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package devcup.search4u.backend;

import devcup.search4u.common.LogLevel;
import java.util.List;

/**
 *
 * @author whiord
 */
public interface SearchCallback {
    void writeLog(LogLevel level, String message);
	
	void setTotalProgress(int number);

	void setCurrentProgress(int number);
    
    void processingDone(List<SearchResult> res);
}
