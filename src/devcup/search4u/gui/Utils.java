package devcup.search4u.gui;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author whiord
 */
public class Utils {
    public static List<String> processQueriesString(String str){
        //System.out.println("In: " + str);
        List<String> list = new LinkedList<>();
        for (String s: str.split("\\n")){

            List<String> words = new LinkedList<>();

            for (String word: s.split("\\s")){
                if (!word.trim().isEmpty()){
                    words.add(word.trim());
                }
            }
            
            String query = "";
            if (words.size() != 0){
                for (int i = 0; i < words.size() -1; i++){
                    query += words.get(i) + " ";
                }
                query += words.get(words.size() - 1 );
                list.add(query);
            }
            
            
        }
        System.out.println("List: " + list);
        return list;
        
    }
}
