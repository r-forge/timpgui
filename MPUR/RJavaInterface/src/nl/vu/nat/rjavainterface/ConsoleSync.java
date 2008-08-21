package nl.vu.nat.rjavainterface;

import java.util.Vector;

/**
 * ConsoleSync - notifys when commands are in the queue and send them.
 * Adapted and mofified from the JGR program written by
 * Markus Helbig / Simon Urbanek
 * 
 */

public class ConsoleSync {
//	Vector<String> msgs = new Vector<String>();
//
//	public ConsoleSync() {
//                Vector msgs = new Vector<String>();
//	}
    	Vector<String> msgs;

	public ConsoleSync() {
		msgs = new Vector<String>();
	}


	private boolean notificationArrived = false;

	/**
	 * this internal method waits until {@link #triggerNotification} is called
	 * by another thread. It is implemented by using {@link wait()} and checking
	 * {@link notificationArrived}.
	 */
	public synchronized String waitForNotification() {
		while (!notificationArrived)
			try {
				// wait();
				wait(100);
				if (RController.re != null)
					RController.re.rniIdle();
			} catch (InterruptedException e) {
			}

		String s = null;
		if (msgs.size() > 0) {
			s = msgs.elementAt(0);
			msgs.removeElementAt(0);
		}
		if (msgs.size() == 0)
			notificationArrived = false;
		return s;
	}

	/**
	 * this methods awakens {@link #waitForNotification}. It is implemented by
	 * setting {@link #notificationArrived} to <code>true</code>, setting
	 * {@link #lastNotificationMessage} to the passed message and finally
	 * calling {@link notifyAll()}.
	 */
	public synchronized void triggerNotification(String msg) {
		// System.out.println("lastmsg "+lastNotificationMessage);
		notificationArrived = true;
		msgs.addElement(msg);
		notifyAll();
	}
}
