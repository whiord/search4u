package devcup.search4u.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Search4UGui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String name = "Search4U";
	private final JTextField directory;
	private final JButton openDir;
	
	public Search4UGui(){
		Container cont = this.getContentPane();
		BoxLayout layout =  new BoxLayout(cont, BoxLayout.Y_AXIS);
		cont.setLayout(layout);
		
		JLabel name = new JLabel(this.name, JLabel.CENTER);
		cont.add(name);
		directory = new JTextField();
		cont.add(directory);
		openDir = new JButton("Выбрать...");
		openDir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionOpenDir(e);
			}
		});
		cont.add(openDir);
		this.setBounds(400, 400, 0, 0);
		this.pack();
	}

	public static void main(String[] args) {
		Search4UGui app = new Search4UGui();
		app.setTitle("Search4U");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
	
	public void actionOpenDir(ActionEvent e){
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int retval = chooser.showOpenDialog(this);
		if (retval == JFileChooser.APPROVE_OPTION){
			File f = chooser.getSelectedFile();
			directory.setText(f.getAbsolutePath());
		}
	}

}
