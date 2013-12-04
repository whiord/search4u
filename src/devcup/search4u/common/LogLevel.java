/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package devcup.search4u.common;

/**
 *
 * @author whiord
 */
public enum LogLevel {
    INFO, DEBUG, ERROR;
    
    public String decorateHTML(String msg){
        String buf = msg.replaceAll("\\n", "<br>");
        return "<div class=\"" + this.toString().toLowerCase() + "\">" + this.toString().toUpperCase() + ": " + buf + "</div>";
    }
}
