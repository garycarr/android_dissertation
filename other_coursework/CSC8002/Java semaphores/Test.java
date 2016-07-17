/**
 * @author Gary Carr
 * 
 *         Main runs a program to create semaphores and threads.
 */
public class Test {

	/**
	 * @throws InterruptedException
	 *             Interrupted exception is thrown if sleep is interrupted
	 */
	public static void main(String[] args) throws InterruptedException {

		// Starts F with a token
		BinarySemaphore fSem = new BinarySemaphore(1);

		// Starts G and H with no tokens.
		BinarySemaphore gSem = new BinarySemaphore();
		Semaphore hSem = new Semaphore();

		// Threads are ran and started
		PrintF fThread = new PrintF(fSem, gSem, hSem);
		PrintG gThread = new PrintG(fSem, gSem);
		PrintH hThread = new PrintH(hSem);
		fThread.start();
		gThread.start();
		hThread.start();

		// Allows program time to print to console
		Thread.sleep(10000);

		// Exits
		System.exit(0);
	}
}