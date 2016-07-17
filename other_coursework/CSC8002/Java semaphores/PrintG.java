/**
 * @author Gary Carr
 * 
 *         Class waits for semaphore tokens to be available before printing to
 *         the console. After printing passes a token to another class
 * 
 * */
public class PrintG extends Thread {

	private BinarySemaphore fPot;
	private BinarySemaphore gPot;

	/**
	 * Constructor takes multiple semaphore counters to determine when to print
	 * to the console.
	 * 
	 * @param fPot
	 *            Binary Semaphore that this thread will give a token too after
	 *            printing
	 * @param gPot
	 *            Binary Semaphore that this thread will monitor for a token
	 *            before printing
	 */
	public PrintG(BinarySemaphore fPot, BinarySemaphore gPot) {
		this.fPot = fPot;
		this.gPot = gPot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		while (true) {
			try {

				// Thread waits for token in gPot
				gPot.P();
				System.out.println("G");
				// Thread gives token to fPot
				fPot.V();
				Thread.sleep((long) (Math.random() * 200));
			} catch (InterruptedException e) {
				System.out.println("Print out of G interrupted");
			}
		}
	}
}