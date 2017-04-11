    package stack43323164;

    import java.lang.management.ManagementFactory;
    import java.lang.management.ThreadMXBean;
    import java.util.List;

    import java.lang.management.ThreadInfo;

    public class HowToDemonstrateDeadlock {

    	private static List<ThreadInfo> findDeadlocks() {
    		ThreadMXBean tmxb = ManagementFactory.getThreadMXBean();
    		long[] result = tmxb.findDeadlockedThreads();
    		if (result == null)
    			return java.util.Collections.emptyList();
    		return java.util.Arrays.asList(tmxb.getThreadInfo(result, 2));
    	}

    	public static void main(String[] args) {

    		final StringBuilder sb1 = new StringBuilder();
    		final StringBuilder sb2 = new StringBuilder();
    		
    		long monitorDelay=1000L;
    		long threadOneDelay=100L;
    		long threadTwoDelay=100L;

    		new Thread() {
    			public void run() {
    				try {
    					while (true) {
    						synchronized (sb1) {
    							sb1.append("A");
    							System.out.println("Thread 1 has sync sb1");
    							System.out.println("Waiting for thread 1 to sync sb2");
    							synchronized (sb2) {
    								sb2.append("B");
    								System.out.println(sb1.toString());
    								System.out.println(sb2.toString());
    							}
    						}
    						Thread.sleep(threadOneDelay);
    					}
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		}.start();

    		new Thread() {
    			public void run() {
    				try {
    					while (true) {
    						synchronized (sb2) {
    							System.out.println("Thread 2 has sync sb2");
    							sb2.append("A");
    							System.out.println("Waiting for thread 2 to sync sb1");
    							synchronized (sb1) {
    								sb1.append("B");
    								System.out.println("second thread: " + sb1.toString());
    								System.out.println("second thread: " + sb2.toString());
    							}
    						}
    						Thread.sleep(threadTwoDelay);
    					}
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		}.start();

    		new Thread() {
    			public void run() {
    				try {
    					while (true) {
    						List<ThreadInfo> deadlocks = findDeadlocks();
    						if (!deadlocks.isEmpty()) {
    							for (ThreadInfo i : deadlocks) {
    								System.out.println("Deadlock detected on thread " + i.getThreadId() + "\n" + i);
    							}
    							//Not a chance to solve the situation - boom
    							System.exit(0);
    						} else {
    							System.out.println("No deadlock so far.");
    						}
    						Thread.sleep(monitorDelay);
    					}
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		}.start();
    	}
    }
