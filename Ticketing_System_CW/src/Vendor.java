import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Vendor implements Runnable {

    private String vendorId;
    private int ticketReleaseRate;
    private TicketPool ticketPool;

    ConfigurationT configurationT;
    int totalticket=0;



    Lock lock = new ReentrantLock();
    public Vendor(String vendrId,ConfigurationT configurationT,int ticketReleaseRate, TicketPool ticketPool) {
        this.vendorId=vendrId;
        this.configurationT =configurationT;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        System.out.println(totalticket);
        totalticket = configurationT.getTotalTickets();

        while (true) {
            lock.lock();
            try {
                while (totalticket>0){
                    ticketPool.addTicket(vendorId,ticketReleaseRate);
                    Thread.sleep(2000);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }finally {
                lock.unlock();
            }
        }


    }
}
