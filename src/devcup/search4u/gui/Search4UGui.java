package devcup.search4u.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import devcup.search4u.gui.indexing.IndexingThread;

public class Search4UGui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String name = "Search4U";
	private final JTextField directoryField;
	private final JButton openDirButton;
	private final JButton indexButton;
	private final JTextArea queriesTextArea;
	private final JButton clearButton;
	private final JButton loadButton;
	private final JButton saveButton;
	
	
	public Search4UGui(){
		
		Container cont = this.getContentPane();
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		cont.add(mainPanel);
		
		JLabel name = new JLabel(this.name, JLabel.CENTER);
		mainPanel.add(name);
		
		JPanel indexingPanel = new JPanel();
		indexingPanel.setLayout(new BoxLayout(indexingPanel, BoxLayout.Y_AXIS));
		Border indexingBorder = BorderFactory.createTitledBorder("Предобработка");
		indexingPanel.setBorder(indexingBorder);
		//indexingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		directoryField = new JTextField();
		//directoryField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		indexingPanel.add(directoryField);
	
		openDirButton = new JButton("Выбрать...");
		openDirButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionOpenDirButton(e);
			}
		});
		indexingPanel.add(openDirButton);
		
		indexButton = new JButton("Индексировать");
		indexButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionIndexButton(e);
			}
		});
		indexingPanel.add(indexButton);
		mainPanel.add(indexingPanel);
		
		JPanel queriesPanel = new JPanel();
		queriesPanel.setLayout(new BoxLayout(queriesPanel, BoxLayout.Y_AXIS));
		queriesPanel.setBorder(BorderFactory.createTitledBorder("Составление запросов"));
		queriesTextArea = new JTextArea();
		queriesTextArea.setColumns(50);
		queriesTextArea.setRows(20);
		queriesTextArea.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		queriesPanel.add(queriesTextArea);
		
		
		clearButton = new JButton("Очистить");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionClearButton(e);
			}
		});
		queriesPanel.add(clearButton);
		
		loadButton = new JButton("Загрузить...");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionLoadButton(e);
			}
		});
		queriesPanel.add(loadButton);
		
		saveButton = new JButton("Сохранить...");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionSaveButton(e);
			}
		});
		queriesPanel.add(saveButton);
		
		mainPanel.add(queriesPanel);
		
		this.setResizable(false);
		this.setBounds(400, 400, 0, 0);
		this.pack();
	}

	protected void actionSaveButton(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	protected void actionLoadButton(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	protected void actionClearButton(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	protected void actionOpenDirButton(ActionEvent e){
		JFileChooser chooser = new JFileChooser();
		File bufDir = new File(directoryField.getText());
		if (bufDir.exists())
			chooser.setCurrentDirectory(bufDir);
		else
			chooser.setCurrentDirectory(null);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int retval = chooser.showOpenDialog(this);
		if (retval == JFileChooser.APPROVE_OPTION){
			File f = chooser.getSelectedFile();
			directoryField.setText(f.getAbsolutePath());
		}
	}
	
	protected void actionIndexButton(ActionEvent e){
		SwingUtilities.invokeLater(new IndexingThread());
	}

	public static void main(String[] args) {
		Search4UGui app = new Search4UGui();
		app.setTitle("Search4U");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
