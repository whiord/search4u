
/*
 * Search4uGUI.java
 *
 * Created on Dec 2, 2013, 4:11:44 PM
 */
/**
 *
 * @author whiord
 */
package devcup.search4u.gui;

import com.ontos.core.miner.util.ObjectPair;
import devcup.search4u.backend.IndexCallback;
import devcup.search4u.backend.Indexer;
import devcup.search4u.backend.SearchCallback;
import devcup.search4u.backend.SearchResult;
import devcup.search4u.backend.Searcher;
import devcup.search4u.common.LogLevel;
import devcup.search4u.xlsview.ConvertResultsToXLS;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;

public class Search4uGUI extends javax.swing.JFrame implements IndexCallback, SearchCallback{
    
    /** Creates new form NewApplication */
    public Search4uGUI() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        docDirectoryField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        indexButton = new javax.swing.JButton();
        indexProgressBar = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        clearQueriesButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        queriesPane = new javax.swing.JTextPane();
        loadQueriesButton = new javax.swing.JButton();
        saveQueriesButton = new javax.swing.JButton();
        openJournalButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        searchButton = new javax.swing.JButton();
        searchProgressBar = new javax.swing.JProgressBar();
        searchSummaryLabel = new javax.swing.JLabel();
        wildcardsCheckBox = new javax.swing.JCheckBox();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("search4u");
        setLocationByPlatform(true);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
        setName("search4u"); // NOI18N
        setResizable(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("search4u");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Предобработка документов"));

        docDirectoryField.setToolTipText("Путь к документам");

        jButton1.setText("Выбрать...");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        indexButton.setText("Обработать");
        indexButton.setEnabled(false);
        indexButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                indexButtonMouseClicked(evt);
            }
        });

        indexProgressBar.setStringPainted(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(docDirectoryField)
                    .addComponent(indexProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(indexButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(docDirectoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(indexProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(indexButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Составление запросов"));

        clearQueriesButton.setText("Очистить");
        clearQueriesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearQueriesButtonMouseClicked(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        queriesPane.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane1.setViewportView(queriesPane);

        loadQueriesButton.setText("Загрузить...");
        loadQueriesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadQueriesButtonMouseClicked(evt);
            }
        });

        saveQueriesButton.setText("Сохранить...");
        saveQueriesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveQueriesButtonMouseClicked(evt);
            }
        });

        openJournalButton.setText("Журнал");
        openJournalButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openJournalButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(loadQueriesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveQueriesButton, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                        .addComponent(clearQueriesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(openJournalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(clearQueriesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadQueriesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveQueriesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                        .addComponent(openJournalButton))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Поиск по документам"));

        searchButton.setEnabled(false);
        searchButton.setLabel("Найти");
        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchButtonMouseClicked(evt);
            }
        });

        searchProgressBar.setStringPainted(true);

        searchSummaryLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        searchSummaryLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        wildcardsCheckBox.setText("Расширенный поиск");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wildcardsCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchSummaryLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchSummaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wildcardsCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addContainerGap())
        );

        fileMenu.setMnemonic('f');
        fileMenu.setText("Файл");

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Выход");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
}//GEN-LAST:event_exitMenuItemActionPerformed

private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
    JFileChooser chooser = new JFileChooser();
    File bufDir = new File(docDirectoryField.getText());
    if (bufDir.exists())
            chooser.setCurrentDirectory(bufDir.getParentFile());
    else
            chooser.setCurrentDirectory(null);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int retval = chooser.showOpenDialog(this);
    if (retval == JFileChooser.APPROVE_OPTION){
            File f = chooser.getSelectedFile();
            docDirectoryField.setText(f.getAbsolutePath());
            logWork(LogLevel.INFO, "Selected directory: " + f.getAbsolutePath());
    }
}//GEN-LAST:event_jButton1MouseClicked

private void indexButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_indexButtonMouseClicked
    if (!indexButton.isEnabled()){
        return;
    }
    indexProgressBar.setValue(0);
    indexProgressBar.setMinimum(0);
    final IndexCallback handler = this;
    final File docPath = new File(docDirectoryField.getText());
    if (! docPath.exists() || ! docPath.isDirectory()) {
        logWork(LogLevel.ERROR, "Document path doesn't exist or isn't directory");
        return;
    }
    docDirectoryField.setEnabled(false);
    jButton1.setEnabled(false);
    indexButton.setEnabled(false);
    jPanel1.setEnabled(false);
    
    SwingWorker backend = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Indexer indexer = new Indexer(handler);
                String index = "_search4u_indexies/" + docPath.getName()+"_index";
                        
                indexer.buildIndex(docPath.getAbsolutePath(), index);
                return null;
            }
            
            @Override
            protected void done(){
                handler.processingDone();
            }
    };
    backend.execute();
}//GEN-LAST:event_indexButtonMouseClicked

    private void openJournalButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openJournalButtonMouseClicked
        JournalFrame.getInstance().pack();
        JournalFrame.getInstance().setVisible(true);
    }//GEN-LAST:event_openJournalButtonMouseClicked

    private void searchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchButtonMouseClicked
        if (!searchButton.isEnabled()){
            return;
        }
      
        searchProgressBar.setValue(0);
        searchProgressBar.setMinimum(0);
        final SearchCallback handler = this;
        final File docPath = new File(docDirectoryField.getText());
        
        if (! docPath.exists() || ! docPath.isDirectory())  {
            logWork(LogLevel.ERROR, "Document path doesn't exists or isn't directory");
            return;
        }
        
        final List<String> queries = Utils.processQueriesString(queriesPane.getText());
        if (queries.isEmpty()){
            logWork(LogLevel.ERROR, "No queries entered");
            return;
        }  
        final boolean useWildcards = wildcardsCheckBox.isSelected();
        
        
        searchButton.setEnabled(false);
        jPanel3.setEnabled(false);
        searchSummaryLabel.setText(null);

        
        SwingWorker backend = new SwingWorker<List<SearchResult>, Void>() {
                @Override
                protected List<SearchResult> doInBackground() throws Exception {
                    String index = "_search4u_indexies/" + docPath.getName()+"_index";
                    Searcher searcher = new Searcher(index, handler);
                   
                    return searcher.search(queries, useWildcards);
                }

                @Override
                protected void done(){
                    try {
                        handler.processingDone(this.get());
                    } catch (InterruptedException ex) {
                        handler.writeLog(LogLevel.ERROR, "Search thread was interrupted");
                    } catch (ExecutionException ex) {
                        handler.writeLog(LogLevel.ERROR, "Something wrong hapened in search thread: " + ex.getCause());
                        ex.printStackTrace();
                    }
                }
        };
              
        backend.execute();
    }//GEN-LAST:event_searchButtonMouseClicked

    private void saveQueriesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveQueriesButtonMouseClicked
        JFileChooser chooser = new JFileChooser();
        File bufDir = new File(docDirectoryField.getText());
        if (bufDir.exists())
            chooser.setCurrentDirectory(bufDir.getParentFile());
        else
            chooser.setCurrentDirectory(null);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);

        int retval = chooser.showSaveDialog(this);
        if (retval == JFileChooser.APPROVE_OPTION){
            File f = chooser.getSelectedFile();
            if (!f.getAbsolutePath().endsWith(".txt")){
                f = new File(f.getAbsolutePath() + ".txt");
            }
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                writer.write(queriesPane.getText());
                writer.close();
                logWork(LogLevel.INFO, "Queries was saved into: " + f.getAbsolutePath());
            } catch (IOException ex){
                logWork(LogLevel.ERROR, "Error while writing");

            }
        }
    }//GEN-LAST:event_saveQueriesButtonMouseClicked

    private void loadQueriesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loadQueriesButtonMouseClicked
        JFileChooser chooser = new JFileChooser();
        File bufDir = new File(docDirectoryField.getText());
        if (bufDir.exists())
            chooser.setCurrentDirectory(bufDir.getParentFile());
        else
            chooser.setCurrentDirectory(null);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");
        chooser.setFileFilter(filter);

        int retval = chooser.showOpenDialog(this);
        if (retval == JFileChooser.APPROVE_OPTION){
            File f = chooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(f));
                clearQueriesButtonMouseClicked(null);
                String query = "";
                while (reader.ready()){
                    query += reader.readLine();
                    query += "\n";
                }
                queriesPane.setText(query);
                logWork(LogLevel.INFO, "Queries was read from file: " + f.getAbsolutePath());
            }
            catch (FileNotFoundException ex){
                logWork(LogLevel.ERROR, "File with queries not found");
            }
            catch (IOException ex){
                logWork(LogLevel.ERROR, "File reading error");
            }
        }
    }//GEN-LAST:event_loadQueriesButtonMouseClicked

    private void clearQueriesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearQueriesButtonMouseClicked
        queriesPane.setText(null);
        logWork(LogLevel.INFO, "Queries pane cleared");
    }//GEN-LAST:event_clearQueriesButtonMouseClicked

    private void processSearchResults(List<SearchResult> res) {
        searchButton.setEnabled(true);
        jPanel3.setEnabled(true);
        
        initSearchSummaryLabel(res);
        PreviewFrame.disposeIfExists();
        if (res.size() > 0){
             PreviewFrame preview = PreviewFrame.getInstance(res);
             preview.setVisible(true);
        }
        
        
    }
    
    private void initSearchSummaryLabel(List<SearchResult> res){
        String summary = "";
        int total_fragments = 0;
        for (SearchResult s: res){
            for (List<ObjectPair<Integer, String>> l :  s.getDocumentsFragments().values() ){
                total_fragments += l.size();
            }
        }
        
        int total_documents = 0;
        if (res.size() > 0 ){
            total_documents = res.get(0).getDocuments().size();
        }
        summary += String.format("<html> <body> Документов: <span style=\"color:#F25607;\">%d</span> <br> Совпадений: <span style=\"color:#0A65F7;\">%d</span>  </body> </html>", total_documents, total_fragments);
        
        searchSummaryLabel.setText(summary);
        logWork(LogLevel.INFO, String.format("Found: %d fragments in %d documents", total_fragments, total_documents));
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final Search4uGUI app = new Search4uGUI();
                app.docDirectoryField.getDocument().addDocumentListener(new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent arg0) {
                        try {
                            File docPath = new File(arg0.getDocument().getText(0, arg0.getDocument().getLength()));
                            app.indexButton.setEnabled(docPath.exists() && docPath.isDirectory());
                            app.logWork(LogLevel.DEBUG, "Doc path changed, valid: " + (docPath.exists() && docPath.isDirectory()));
                            //System.out.println("Doc path changed, valid: " + new Boolean(docPath.exists() && docPath.isDirectory()));
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent arg0) {
                        try {
                            File docPath = new File(arg0.getDocument().getText(0, arg0.getDocument().getLength()));
                            app.indexButton.setEnabled(docPath.exists() && docPath.isDirectory());
                            app.logWork(LogLevel.DEBUG, "Doc path changed, valid: " + (docPath.exists() && docPath.isDirectory()));
                            //System.out.println("Doc path changed, valid: " + new Boolean(docPath.exists() && docPath.isDirectory()));
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void changedUpdate(DocumentEvent arg0) {
                        try {
                            File docPath = new File(arg0.getDocument().getText(0, arg0.getDocument().getLength()));
                            app.indexButton.setEnabled(docPath.exists() && docPath.isDirectory());
                            
                            app.logWork(LogLevel.DEBUG, "Doc path changed, valid: " + (docPath.exists() && docPath.isDirectory()));
                            //System.out.println("Doc path changed, valid: " + new Boolean(docPath.exists() && docPath.isDirectory()));
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                Dimension scr_d = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension app_d = app.getSize();
                int disposal_x = (scr_d.width - app_d.width)/2,
                    disposal_y = (scr_d.height - app_d.height)/2;
                app.setLocation(disposal_x, disposal_y);
                app.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton clearQueriesButton;
    javax.swing.JTextField docDirectoryField;
    javax.swing.JMenuItem exitMenuItem;
    javax.swing.JMenu fileMenu;
    javax.swing.JButton indexButton;
    javax.swing.JProgressBar indexProgressBar;
    javax.swing.JButton jButton1;
    javax.swing.JLabel jLabel1;
    javax.swing.JPanel jPanel1;
    javax.swing.JPanel jPanel2;
    javax.swing.JPanel jPanel3;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JButton loadQueriesButton;
    javax.swing.JMenuBar menuBar;
    javax.swing.JButton openJournalButton;
    javax.swing.JTextPane queriesPane;
    javax.swing.JButton saveQueriesButton;
    javax.swing.JButton searchButton;
    javax.swing.JProgressBar searchProgressBar;
    javax.swing.JLabel searchSummaryLabel;
    javax.swing.JCheckBox wildcardsCheckBox;
    // End of variables declaration//GEN-END:variables

    protected void logWork(LogLevel level, String message){
        JournalFrame.getInstance().logWork(level, message);
    }
    
    @Override
    public void writeLog(final LogLevel level, final String message) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
               logWork(level, message);
            }
        });
        //System.out.println(error);
    }

    @Override
    public void setTotalDocumentsNumber(final int number) {
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                logWork(LogLevel.INFO, "Document number for indexing: " + number);
                indexProgressBar.setMaximum(number);
            }
        });
    }

    @Override
    public void setProcessedDocumentsNumber(final int number) {
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                indexProgressBar.setValue(number);
            }
        });
    }

    @Override
    public void processingDone() {
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                logWork(LogLevel.INFO, "Indexing done! ");
                docDirectoryField.setEnabled(true);
                jButton1.setEnabled(true);
                indexButton.setEnabled(true);
                jPanel1.setEnabled(true);
                searchButton.setEnabled(true);
            }
        });
    }

    @Override
    public void setTotalProgress(final int number) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                logWork(LogLevel.INFO, "Total amount of work in search: " + number);
                searchProgressBar.setMaximum(number);
            }
            
        });
        
        
    }

    @Override
    public void setCurrentProgress(final int number) {
        EventQueue.invokeLater(new Runnable(){
            
            @Override
            public void run() {   
                searchProgressBar.setValue(number);
            }
        });
    }

    @Override
    public void processingDone(final List<SearchResult> res) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                logWork(LogLevel.INFO, "Searching done!");
                processSearchResults(res);
            }   
        });
    }
}
