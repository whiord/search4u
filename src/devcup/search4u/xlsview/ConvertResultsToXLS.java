/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package devcup.search4u.xlsview;

import com.ontos.core.miner.util.ObjectPair;
import devcup.search4u.backend.SearchResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Fersy_Strak
 */
public class ConvertResultsToXLS {
    private final List <SearchResult> searchResultsList;
    private final String resultXMLPath;
    
    ConvertResultsToXLS (List <SearchResult> searchResultsList, String resultXMLPath) {
        this.searchResultsList = searchResultsList;
        this.resultXMLPath = resultXMLPath;
    }
    
    private HSSFRichTextString labeledStringToRichTextString (String labeledString, HSSFFont partFont) {
    	//create List, delete labels
    	List<ObjectPair<Integer, Integer>> boarderPairs = new ArrayList<ObjectPair<Integer, Integer>>();

    	Integer firstPosition = labeledString.indexOf("<B>"); 
    	Integer secondPosition;
    	while( firstPosition != -1) {
    		labeledString = labeledString.replaceFirst("<B>", "");
    		secondPosition = labeledString.indexOf("</B>");
    		labeledString = labeledString.replaceFirst("</B>", "");
      		boarderPairs.add(new ObjectPair<Integer, Integer>(firstPosition, secondPosition));
    		
    		firstPosition = labeledString.indexOf("<B>");
    	}
    	
    	//create richTextString, apply font
    	HSSFRichTextString richTextString = new HSSFRichTextString(labeledString);
    	
    	for(ObjectPair<Integer, Integer> boarderpair: boarderPairs) {
    		richTextString.applyFont(boarderpair.getFirst(), boarderpair.getSecond(), partFont);
    	}
    	  	
    	return richTextString;
    }
    
    public void createXLSTable() throws IOException {		
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Result sheet");
        
        //create headerRow
        Row headerRow = sheet.createRow(0);
       
        HSSFCellStyle style = workbook.createCellStyle();

        HSSFFont partFont = workbook.createFont();
        //partFont.setItalic(true);
        partFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        partFont.setUnderline(HSSFFont.U_DOUBLE);


	    HSSFFont defaultFont= workbook.createFont();
	    defaultFont.setFontHeightInPoints((short)10);
	    defaultFont.setFontName("Arial");
	    defaultFont.setColor(HSSFColor.BLACK.index);
	    defaultFont.setItalic(false);

	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short)11);
	    font.setFontName("Arial");
	    font.setColor(HSSFColor.BLUE_GREY.index);
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    font.setItalic(true);

	    //style=(HSSFCellStyle) headerRow.getRowStyle();	    
	    //style.setFillPattern(CellStyle.SOLID_FOREGROUND);	    
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_CENTER);
	    //style.setFillBackgroundColor(HSSFColor.ROYAL_BLUE.index);
	    //style.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);
	    //style.setHidden(false);
	    //style.setIndention(indent);
	    style.setFont(font);
	    
	    
        
        //create a new cell in headerRow
        Cell headerCell;
        headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Запрос");
        headerCell.setCellStyle(style);
        //sheet.autoSizeColumn(0);
        sheet.setColumnWidth(0, 7000);
        
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Путь к документу");
        headerCell.setCellStyle(style);
        //sheet.autoSizeColumn(1);
        sheet.setColumnWidth(1, 12000);
        
        /*headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Позиция в документе");
        headerCell.setCellStyle(style);
        sheet.autoSizeColumn(2);
        //sheet.setColumnWidth(2, 7000);
        */
        
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Краткое описание");
        headerCell.setCellStyle(style);
        //sheet.autoSizeColumn(3);
        sheet.setColumnWidth(2, 18000);
     
        
        //create another Rows
        int rowNum = 1;
        for(SearchResult searchResult : searchResultsList){   
            for (String key : searchResult.getRelevDocsPath()) {
            //for (String key : searchResult.getDocumentsFragments().keySet()) {
                for (ObjectPair<Integer, String> pair: searchResult.getDocumentsFragments().get(key)) {
                    
                    Row row = sheet.createRow(rowNum++);
                    
                    Cell cell;
                    cell = row.createCell(0);
                    cell.setCellValue(searchResult.getQuery());
                    //cell.setCellStyle(style);
                    
                    cell = row.createCell(1);
                    cell.setCellValue(key);
                    //cell.setCellStyle(style);
                    
                    //cell = row.createCell(2);
                    //cell.setCellValue(pair.getFirst());
                    //cell.setCellStyle(style);
                    
                    cell = row.createCell(2);
                    cell.setCellValue(labeledStringToRichTextString(pair.getSecond(), partFont));
                    //cell.setCellStyle(style);
                    
                }
            }
        }
        
        try {
            FileOutputStream out = new FileOutputStream(new File(resultXMLPath));
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    } 
}
