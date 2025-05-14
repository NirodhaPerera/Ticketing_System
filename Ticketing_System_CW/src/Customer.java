import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Customer implements Runnable {
    private String customerID;
    private int customerRetrievalRate;

    private  TicketPool ticketPool;
    Lock lock = new ReentrantLock();
    public Customer(String customerID, int customerRetrievalRate, TicketPool ticketPool) {
        this.customerID = customerID;
        this.customerRetrievalRate = customerRetrievalRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (true){
            lock.lock();
            try {
                while (true) {  // Keep trying to get tickets
                    ticketPool.takeTicket(customerRetrievalRate,customerID);
                    Thread.sleep(1000/customerRetrievalRate );  // Control the purchase rate
                }

//            ticketPool.takeTicket();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }finally {
                lock.unlock();
            }
        }

    }
}
