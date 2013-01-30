package de.berlin.tuberlin.adm.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunListener implements ActionListener{
	private Thread iteration;
	private boolean isRunning;
	private MainPanel panel;
	
	public RunListener(MainPanel panel){
		this.panel = panel;
		isRunning = false;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		isRunning = !isRunning;
		
		if(isRunning){
			this.iteration = new Thread(new SimplexCalculationThread(panel,panel.getInputPath().getText(), panel.getOutputPath().getText()));
			panel.getOutputButton().setEnabled(false);
			panel.getOutputPath().setEnabled(false);
			panel.getInputPath().setEnabled(false);
			panel.getInputButton().setEnabled(false);
			panel.getRun().setText("Stop calculation");
			isRunning = !isRunning;
			iteration.start();
		}
		else{
			panel.getOutputButton().setEnabled(true);
			panel.getOutputPath().setEnabled(true);
			panel.getInputPath().setEnabled(true);
			panel.getInputButton().setEnabled(true);
			panel.getRun().setText("Start calculation");
			iteration.stop();
		}

	}

}
