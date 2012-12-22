package de.berlin.tuberlin.adm.algorithms;

public class Stopwatch {
	private long startTime = -1;
	private long stopTime = -1;
	private boolean running = false;

	/**
	 * This method starts the stopwatch.
	 * 
	 * @return
	 */
	public Stopwatch start() {
		startTime = System.currentTimeMillis();
		running = true;
		return this;
	}

	/**
	 * This method stops the time.
	 * 
	 * @return
	 */
	public Stopwatch stop() {
		stopTime = System.currentTimeMillis();
		running = false;
		return this;
	}

	/**
	 * This method returns the stopped time.
	 * 
	 * @return the stopped time in ms.
	 */
	public long getElapsedTime() {
		if (startTime == -1) {
			return 0;
		}
		if (running) {
			return System.currentTimeMillis() - startTime;
		} else {
			return stopTime - startTime;
		}
	}

	/**
	 * This method resets the time of the stopwatch.
	 * 
	 * @return
	 */
	public Stopwatch reset() {
		startTime = -1;
		stopTime = -1;
		running = false;
		return this;
	}
}
