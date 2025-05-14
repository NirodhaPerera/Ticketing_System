import java.io.Console;
import java.util.*;
import java.util.logging.Logger;

public class TicketPool {

    ConfigurationT configurationT;
    private final  int maxTicketCapacity;

    private final List<Integer> tickets;
    int totalTickets=0;
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());

    public TicketPool(ConfigurationT configurationT,int maxTicketCapacity) {
        this.configurationT=configurationT;
        this.maxTicketCapacity = maxTicketCapacity;
        this.tickets=Collections.synchronizedList(new ArrayList<>());
        this.totalTickets = configurationT.getTotalTickets();
    }



    public synchronized void addTicket(String vendorId,int ticketToAdd) throws InterruptedException {
        if (totalTickets<=0){
            /*logger.warning(vendorId+" No More Tickets Have To Release");*/
            System.out.println(vendorId+" No More Tickets Have To Release");
            return;
        }


        if (tickets.size()+ticketToAdd>=maxTicketCapacity){
            System.out.println(vendorId + " Wait Until Space Become Available");
           /* logger.info(vendorId + " Wait Until Space Become Available");*/
            wait();
        }else {
            for (int i = 0; i <ticketToAdd; i++) {
                tickets.add(i);
                totalTickets--;
            }
           /* logger.info(vendorId+" "+ticketToAdd + " Ticket Released ");
            logger.info("Total Ticket :" + tickets.size());*/
            /*logger.info("Remaining Total Tickets: " + totalTickets);*/
            System.out.println(vendorId+" "+ticketToAdd + " Ticket Released ");
            System.out.println("Total Ticket :" + tickets.size());

            notifyAll();
        }

    }

    public synchronized void takeTicket(int removeTickets,String customerId) throws InterruptedException {
            while (tickets.isEmpty()) {
                if (totalTickets<=0){
                   /* logger.warning(customerId+" All The Tickets Are Sold Out");*/
                    System.out.println(customerId+" All The Tickets Are Sold Out");
                    return;
                }
               /* logger.info(customerId +" Wait Until a Ticket Becomes Available");*/
                System.out.println(customerId +" Wait Until a Ticket Becomes Available");
                wait();
            }

            for (int i = 0; i <removeTickets; i++) {
                tickets.remove(0);
            }
            /*logger.info(customerId +" Ticket purchased Successfully");
            logger.info("Total Ticket :" + tickets.size());
            logger.info("Remaining Total Tickets: " + tickets.size());*/
            System.out.println(customerId +" Ticket purchased Successfully");
            System.out.println("Remaining Tickets "+tickets.size());
            configurationT.setTotalTickets(totalTickets);
            configurationT.saveConfiguration("ConfigPackege.txt");

            notifyAll();

    }
}
