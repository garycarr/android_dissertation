/**
 * @author Gary Carr
 * 
 *         Class creates a semaphore which can add or remove tokens from the
 *         created objects
 * 
 */
public class Semaphore {
	protected int value;

	/**
	 * Constructor creates an empty semaphore
	 */
	public Semaphore() {
		value = 0;
	}

	/**
	 * Constructor creates a semaphore with a defined number of tokens
	 * 
	 * @param initial
	 *            The number of tokens the Semaphore starts with
	 * 
	 */
	public Semaphore(int initial) {
		value = (initial >= 0) ? initial : 0;
	}

	/**
	 * Method removes a token from the semaphore. If no tokens are available it
	 * waits until one becomes available
	 * 
	 * @throws InterruptedException
	 *             If wait is interrupted catches exception
	 */
	public synchronized void P() throws InterruptedException {
		while (value == 0) {
			wait();
		}
		value--;
	}

	/**
	 * Method adds a token to the semaphore
	 */
	public synchronized void V() {
		value++;
		notify();
	}
}