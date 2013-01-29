package de.berlin.tuberlin.adm.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class PathListener implements ActionListener{
	private MainPanel panel;
	private boolean input;
	
	public PathListener(MainPanel panel, boolean input){
		this.panel = panel;
		this.input = input;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(input){
			JFileChooser in = new JFileChooser();
			in.setFileFilter(new FileFilter() {
				public boolean accept(File f) {
					return f.isDirectory() || f.isFile();
				}

				public String getDescription() {
					return "Text";
				}
			}); 
			in.setVisible(true);
			
			int state = in.showSaveDialog(null);
			if (state == JFileChooser.APPROVE_OPTION) {
				File file = in.getSelectedFile();
				panel.getInputPath().setText(file.getPath());
			}
			in.disable();
		}
		else{
			JFileChooser output = new JFileChooser();
			output.setDialogType(JFileChooser.SAVE_DIALOG);
			output.setApproveButtonText("Speichern");
			output.setApproveButtonToolTipText("Save");
			output.setFileFilter(new FileFilter() {
				public boolean accept(File f) {
					return f.isDirectory();
				}

				public String getDescription() {
					return "Text";
				}
			});
			int state = output.showSaveDialog(null);
			if (state == JFileChooser.APPROVE_OPTION) {
				File file = output.getSelectedFile();
				panel.getOutputPath().setText(file.getPath());
			}
			output.disable();
		}
	}
	

}
