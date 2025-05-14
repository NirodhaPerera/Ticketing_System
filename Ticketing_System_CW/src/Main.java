import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

//TIP To <b>Run</b>
// code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static int validation( String text,int min, int max){
        Scanner  scanner = new Scanner(System.in);

        int temp;
        while (true){
            try {
                System.out.println("Enter " + text +" :");
                temp = scanner.nextInt();
                if (temp<=0){
                    System.out.println("Invalid input: "+text+ " must be a Positive Integer");
                } else if (! (temp>=min && temp <=max)) {
                    System.out.println("Invalid input: "+text+ " must be "+ min + " and "+ max+" .");

                } else{
                    break;
                }
            }catch (Exception e){
                System.out.println("Error..: Please Enter valid Integer");
                scanner.next();
            }
        }
        return temp;
    }


    public static void main(String[] args) {

        Scanner  scanner = new Scanner(System.in);

        while (true){
            System.out.println("Enter Command  (Start/Stop) :");
            String command = scanner.next().trim().toLowerCase();

            if (command.equals("start")){

                System.out.println("Do you load Previous Configuration Parameters ?? (_Yes_or_No_)");
                String command1 = scanner.next().trim().toLowerCase();
                ConfigurationT configObj = ConfigurationT.loadFromTextFile("configPackege.txt");


                if (command1.equals("yes")){

                    System.out.println("Data Loading from Text File :");
                    System.out.println("Total Number of Tickets :"+ configObj.getTotalTickets());
                    System.out.println("Tickets Release Rate :"+ configObj.getTicketReleaseRate());
                    System.out.println("Customer Retrieval Rate :"+ configObj.getCustomerRetrievalRate());
                    System.out.println("Maximum Ticket Capacity :"+ configObj.getMaxTicketCapacity());



                    TicketPool ticketPool = new TicketPool(configObj,configObj.getMaxTicketCapacity());

                    List<Thread> vendorThreads = new ArrayList<>();
                    List<Thread> customerThreads = new ArrayList<>();


                    for (int i = 0; i < 5; i++) {
                        Thread vendorThread = new Thread(new Vendor("Vendor"+(i+1),configObj,configObj.getTicketReleaseRate(),ticketPool));
                        vendorThreads.add(vendorThread);
                        Thread customerThread = new Thread(new Customer("Customer"+(i+1), configObj.getCustomerRetrievalRate(),ticketPool));
                        customerThreads.add(customerThread);
                    }

                    vendorThreads.forEach(Thread::start);
                    customerThreads.forEach(Thread::start);


                }else if (command1.equals("n" +
                        "o")){
                    int totalTickets = validation("Total Number of Tickets",1,5000);
                    int ticketReleaseRate = validation("Tickets Release Rate (1-60)",1,60);
                    int customerRetrievalRate = validation("Customer Retrieval Rate (1-60)",1,60);
                    int maxTicketCapacity = validation("Maximum Ticket Capacity",1,100);
                    ConfigurationT config = new ConfigurationT(totalTickets,ticketReleaseRate,customerRetrievalRate,maxTicketCapacity);
                    config.saveConfiguration("ConfigPackege.txt");
                    System.out.println();

                    System.out.println("Total Number of Tickets :"+ totalTickets);
                    System.out.println("Tickets Release Rate :"+ ticketReleaseRate);
                    System.out.println("Customer Retrieval Rate :"+ customerRetrievalRate);
                    System.out.println("Maximum Ticket Capacity :"+ maxTicketCapacity);


                    TicketPool ticketPool = new TicketPool(config,maxTicketCapacity);

                    List<Thread> vendorThreads = new ArrayList<>();
                    List<Thread> customerThreads = new ArrayList<>();


                    for (int i = 0; i < 5; i++) {
                        Thread vendorThread = new Thread(new Vendor("Vendor"+(i+1),config,ticketReleaseRate,ticketPool));
                        vendorThreads.add(vendorThread);
                        Thread customerThread = new Thread(new Customer("customer"+(i+1), customerRetrievalRate,ticketPool));
                        customerThreads.add(customerThread);
                    }

                    vendorThreads.forEach(Thread::start);
                    customerThreads.forEach(Thread::start);
                }else{
                    System.out.println("Invalid Try Again...");
                }


            }else if("stop".equals(command)){
                    logger.info("Stopped The Ticketing System  ");
                    break;

            }else{
                System.out.println("Invalid Command. Try Again");
            }

        }






    }
}