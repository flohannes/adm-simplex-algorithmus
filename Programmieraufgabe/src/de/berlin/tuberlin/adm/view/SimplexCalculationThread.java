package de.berlin.tuberlin.adm.view;

import java.io.IOException;

import de.berlin.tuberlin.adm.algorithms.SimplexAlgorithm;
import de.berlin.tuberlin.adm.input.Input;
import de.berlin.tuberlin.adm.output.Output;

public class SimplexCalculationThread implements Runnable{

	private String input;
	private String output;
	private MainPanel panel;
	
	public SimplexCalculationThread(MainPanel p, String input, String output){
		this.input = input;
		this.output = output;
		this.panel =p;
	}
	
	@Override
	public void run() {
		Input r;
		try {
			r = new Input(this.input);
			SimplexAlgorithm sim = new SimplexAlgorithm(r.getGraph());
			sim.startOptimierung();
			Output output = new Output(this.output, sim.getGraph(), sim.calculateObjective());
			this.panel.getErgebnisse().setText("<html>Dauer: " + sim.getStopwatch().getElapsedTime() + "<br> Augmentierungsschritte: " + sim.getAugSchritte() + "<br> Kosten insgesamt: "+ sim.calculateObjective()+"</html>");
			panel.getOutputButton().setEnabled(true);
			panel.getOutputPath().setEnabled(true);
			panel.getInputPath().setEnabled(true);
			panel.getInputButton().setEnabled(true);
			panel.getRun().setText("Start calculation");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
