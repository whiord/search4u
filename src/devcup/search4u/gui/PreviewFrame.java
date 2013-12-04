/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package devcup.search4u.gui;

import com.ontos.core.miner.util.ObjectPair;
import devcup.search4u.backend.SearchResult;
import devcup.search4u.common.LogLevel;
import devcup.search4u.xlsview.ConvertResultsToXLS;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.HTMLDocument;

/**
 *
 * @author whiord
 */
public class PreviewFrame extends javax.swing.JFrame {
    private static PreviewFrame instance;
    private static List<SearchResult> searchResultList_origin;
    private static List<ObjectPair<String,String>> searchResultList;
    public static PreviewFrame getInstance(List<SearchResult> origin){
        if (instance == null){
            instance = new PreviewFrame();
            instance.initPreviewFrame(origin);
        }
        return instance;
    }
    
    public static void disposeIfExists(){
        if (instance != null){
            instance.dispose();
            instance = null;
        }
    }
    
    /**
     * Creates new form PreviewFrame
     */
    private PreviewFrame() {
        initComponents();
        
    }

    private void initPreviewFrame(List<SearchResult> res){
        if (res.size() > 0){
            //showResultsButton.setEnabled(true);
            searchResultList = res.get(0).getDocuments();
            searchResultList_origin = res;
            String[] names = new String[searchResultList.size()];
            for (int i = 0; i < searchResultList.size(); i++){
                names[i] = new File(searchResultList.get(i).getFirst()).getName();
            }
            resultList.setListData(names);
            resultList.setSelectedIndex(0);
            //showResultsButtonMouseClicked(null);
        }
        else{
            //showResultsButton.setEnabled(false);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closePreviewButton = new javax.swing.JButton();
        exportXLSButton = new javax.swing.JButton();
        openDestinationButton = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        documentPreviewPathField = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        previewPane = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultList = new javax.swing.JList();
        openDestinationFileButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Предпросмотр");

        closePreviewButton.setLabel("Закрыть");
        closePreviewButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closePreviewButtonMouseClicked(evt);
            }
        });

        exportXLSButton.setText("Экспорт в XLS");
        exportXLSButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportXLSButtonMouseClicked(evt);
            }
        });

        openDestinationButton.setLabel("Открыть расположение");
        openDestinationButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openDestinationButtonMouseClicked(evt);
            }
        });

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setDividerSize(8);

        documentPreviewPathField.setEditable(false);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setToolTipText("");
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        previewPane.setEditable(false);
        previewPane.setContentType("text/html"); // NOI18N
        jScrollPane3.setViewportView(previewPane);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(documentPreviewPathField, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(documentPreviewPathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel4);

        resultList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        resultList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                resultListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(resultList);

        jSplitPane1.setLeftComponent(jScrollPane2);

        openDestinationFileButton.setText("Открыть файл");
        openDestinationFileButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openDestinationFileButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(closePreviewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exportXLSButton, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(openDestinationFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openDestinationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closePreviewButton)
                    .addComponent(exportXLSButton)
                    .addComponent(openDestinationButton)
                    .addComponent(openDestinationFileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closePreviewButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closePreviewButtonMouseClicked
        this.dispose();
        //setVisible(false);
    }//GEN-LAST:event_closePreviewButtonMouseClicked

    private void exportXLSButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportXLSButtonMouseClicked
        JFileChooser chooser = new JFileChooser();
        /*
        File bufDir = new File(docDirectoryField.getText());
        if (bufDir.exists())
            chooser.setCurrentDirectory(bufDir.getParentFile());
        else
            chooser.setCurrentDirectory(null);
        */
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XLS files", "xls");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);

        int retval = chooser.showSaveDialog(this);
        if (retval == JFileChooser.APPROVE_OPTION){
            File f = chooser.getSelectedFile();
            if (!f.getAbsolutePath().endsWith(".xls")){
                f = new File(f.getAbsolutePath() + ".xls");
            }
            ConvertResultsToXLS xlsConverter = new ConvertResultsToXLS(searchResultList_origin, f.getAbsolutePath());
            try {
                xlsConverter.createXLSTable();
                Desktop.getDesktop().open(f);
            } catch (IOException ex) {
                logWork(LogLevel.ERROR, "Can't save to *.xls: " + f.getAbsolutePath());
            }
        }
    }//GEN-LAST:event_exportXLSButtonMouseClicked

    private void openDestinationButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openDestinationButtonMouseClicked
        try {
            Desktop.getDesktop().open((new File(documentPreviewPathField.getText())).getParentFile());
        } catch (IOException ex) {
            logWork(LogLevel.ERROR, "Can't open directory");
        }
    }//GEN-LAST:event_openDestinationButtonMouseClicked

    private void resultListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_resultListValueChanged
        int sel = resultList.getSelectedIndex();
        if (sel == -1){
            return;
        }
        ObjectPair<String, String> selectedDoc = new ObjectPair<>(searchResultList.get(sel));

        String labelText = String.format("%s", selectedDoc.getFirst());
        documentPreviewPathField.setText(labelText);
        documentPreviewPathField.setCaretPosition(0);
        HTMLDocument doc = (HTMLDocument) previewPane.getDocument();
        String style = "<style type=\"text/css\"> B{ background-color: #EAFF5E } body{ font-family: monospace;} </style>";
        //HTMLEditorKit editor = new HTMLEditorKit();
        //editor.
        String text= selectedDoc.getSecond();
        text = text.replaceAll("\\n", "<br>");
        String html = String.format("<html> <head> %s </head> <body> %s </body> </html>", style, text);
        previewPane.setText(html);
        previewPane.setCaretPosition(0);
    }//GEN-LAST:event_resultListValueChanged

    private void openDestinationFileButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openDestinationFileButtonMouseClicked
        try {
            Desktop.getDesktop().open(new File(documentPreviewPathField.getText()));
        } catch (IOException ex) {
            logWork(LogLevel.ERROR, "Can't open file");
        }
    }//GEN-LAST:event_openDestinationFileButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PreviewFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closePreviewButton;
    private javax.swing.JTextField documentPreviewPathField;
    private javax.swing.JButton exportXLSButton;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JButton openDestinationButton;
    private javax.swing.JButton openDestinationFileButton;
    private javax.swing.JTextPane previewPane;
    private javax.swing.JList resultList;
    // End of variables declaration//GEN-END:variables

    protected void logWork(LogLevel level, String message) {
        JournalFrame.getInstance().logWork(level, message);
    }

    
}