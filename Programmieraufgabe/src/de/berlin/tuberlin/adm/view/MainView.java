package de.berlin.tuberlin.adm.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainView extends JFrame{
	
	public MainView(){
		
		MainPanel panel = new MainPanel();
		
		this.add(panel);
	}	
	
	public static void main(String[] args){
		MainView view = new MainView();
		view.setTitle("Netzwerk Simplex Algorithmus");
		view.setSize(400, 300);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		 
		// Determine the new location of the window
		int w = view.getSize().width;
		int h = view.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		 
		// Move the window
		view.setLocation(x, y);
		
		
		view.setVisible(true);
	}

}
