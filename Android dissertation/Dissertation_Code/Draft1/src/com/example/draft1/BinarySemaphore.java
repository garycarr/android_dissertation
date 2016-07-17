package com.example.draft1;

/**
 * @author Gary Carr
 * 
 *         Class creates a semaphore which can add or remove tokens from the
 *         created objects
 * 
 */
class BinarySemaphore {
	protected int value;

	/**
	 * Constructor creates a semaphore with a defined number of tokens
	 * 
	 * @param initial
	 *            The number of tokens the Semaphore starts with
	 * 
	 */
	BinarySemaphore(int initial) {
		value = (initial >= 0) ? initial : 0;
	}

	/**
	 * Method removes a token from the semaphore. If no tokens are available it
	 * waits until one becomes available
	 * 
	 * @throws InterruptedException
	 *             If wait is interrupted catches exception
	 */
	synchronized void noJobsStop() throws InterruptedException {
		while (value == 0) {
			wait();
		}
		value--;
	}

	/**
	 * Method adds a token to the semaphore
	 */
	synchronized void jobNeededStart() {
		value = 1;
		notifyAll();
	}
}