/**
 * @author Gary Carr
 * 
 *         Class prints a line to the console and then adds a token to the
 *         semaphore
 * */
public class PrintH extends Thread {

	private Semaphore hPot;

	/**
	 * Constructor takes a semaphore to add a token too after printing
	 * 
	 * @param hPot
	 *            Semaphore added to token after printing to console
	 */
	public PrintH(Semaphore hPot) {
		this.hPot = hPot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		while (true) {
			// Line is printed then token added to semaphore
			System.out.println("H");
			hPot.V();
			try {
				Thread.sleep((long) (Math.random() * 200));
			} catch (InterruptedException e) {
				System.out.println("Print out of H interrupted");
			}
		}
	}
}