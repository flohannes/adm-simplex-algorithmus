package de.berlin.tuberlin.adm.view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainPanel extends JPanel{
	
	private JTextField inputPath;
	private JTextField outputPath;
	private JButton inputButton;
	private JButton outputButton;
	private JButton run;
	private JLabel ergebnisse;

	public MainPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel input = new JPanel();
		inputPath = new JTextField();
		inputPath.setPreferredSize(new Dimension(200, 30));
		inputButton = new JButton("Input Path");
		inputButton.addActionListener(new PathListener(this, true));
		
		JPanel output = new JPanel();
		outputPath = new JTextField();
		outputPath.setPreferredSize(new Dimension(200, 30));
		outputButton = new JButton("Output Path");
		outputButton.addActionListener(new PathListener(this, false));
		
		run = new JButton("Start calculation");
		run.addActionListener(new RunListener(this));
		ergebnisse = new JLabel("");
		ergebnisse.setPreferredSize(new Dimension(300,200));
		
		input.add(inputPath);
		input.add(inputButton);
		output.add(outputPath);
		output.add(outputButton);
		this.add(input);
		this.add(output);
		this.add(run);
		this.add(ergebnisse);
	}

	public JButton getInputButton() {
		return inputButton;
	}

	public void setInputButton(JButton inputButton) {
		this.inputButton = inputButton;
	}

	public JButton getOutputButton() {
		return outputButton;
	}

	public void setOutputButton(JButton outputButton) {
		this.outputButton = outputButton;
	}

	public JButton getRun() {
		return run;
	}

	public void setRun(JButton run) {
		this.run = run;
	}

	public JTextField getInputPath() {
		return inputPath;
	}

	public void setInputPath(JTextField inputPath) {
		this.inputPath = inputPath;
	}

	public JTextField getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(JTextField outputPath) {
		this.outputPath = outputPath;
	}

	public JLabel getErgebnisse() {
		return ergebnisse;
	}

	public void setErgebnisse(JLabel ergebnisse) {
		this.ergebnisse = ergebnisse;
	}
	
}
