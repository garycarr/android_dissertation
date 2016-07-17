/**
 * @author Gary Carr
 * 
 *         Class is an extension of Semaphore. Creates a Binary Semaphore that
 *         only allows one or zero tokens.
 * 
 * */
public class BinarySemaphore extends Semaphore {

	/**
	 * Creates a binary semaphore without a token
	 */
	public BinarySemaphore() {
		super();
	}

	/**
	 * Creates a binary semaphore with 1 or 0 tokens
	 * 
	 * @param initial
	 *            The number of tokens requested by the user. Will be set to
	 *            either zero or one
	 */
	public BinarySemaphore(int initial) {
		super((initial >= 1) ? 1 : 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Semaphore#V()
	 */
	public synchronized void V() {
		value = 1;
		notify();
	}
}