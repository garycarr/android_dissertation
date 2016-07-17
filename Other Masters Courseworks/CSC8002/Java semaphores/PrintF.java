/**
 * @author Gary Carr
 * 
 *         Class waits for semaphore tokens to be available before printing to
 *         the console. After printing passes a token to another class
 * 
 * */
public class PrintF extends Thread {

	private BinarySemaphore fPot;
	private BinarySemaphore gPot;
	private Semaphore hPot;

	/**
	 * Constructor takes multiple semaphore counters to determine when to print
	 * to the console.
	 * 
	 * @param fPot
	 *            Binary Semaphore that this thread will monitor for a token
	 *            before printing
	 * @param gPot
	 *            Binary Semaphore that this thread will give a token too after
	 *            printing
	 * @param hPot
	 *            Semaphore that this thread will monitor for a token before
	 *            printing
	 */
	public PrintF(BinarySemaphore fPot, BinarySemaphore gPot, Semaphore hPot) {
		this.fPot = fPot;
		this.gPot = gPot;
		this.hPot = hPot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		while (true) {
			try {
				// Thread waits for token in fPot and hPot, then takes them
				fPot.P();
				hPot.P();
				System.out.println("F");
				// Thread gives token to gPot
				gPot.V();
				Thread.sleep((long) (Math.random() * 200));
			} catch (InterruptedException e) {
				System.out.println("Print out of F interrupted");
			}
		}
	}
}