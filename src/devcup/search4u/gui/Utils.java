package devcup.search4u.gui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author whiord
 */
public class Utils {
    public static List<String> processQueriesString(String str){
        List<String> list = new LinkedList<>();
        for (String s: str.split("\n")){
            
            if (!s.trim().isEmpty()){
                String query = "";
                for (String word: s.split("\\w")){
                    if (!word.trim().isEmpty()){
                        query += word.trim();
                    }
                }
                list.add(query);
            }
        }
        return list;
        
    }
}
