import java.io.*;

public class ConfigurationT {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public ConfigurationT(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }



    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }


    public static ConfigurationT loadFromTextFile(String txtFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(txtFile))) {
            int totalTickets = 0, ticketReleaseRate = 0, customerRetrievalRate = 0, maxTicketCapacity = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length < 2) continue;
                switch (parts[0]) {
                    case "totalTickets":
                        totalTickets = Integer.parseInt(parts[1]);
                        break;
                    case "ticketReleaseRate":
                        ticketReleaseRate = Integer.parseInt(parts[1]);
                        break;
                    case "customerRetrievalRate":
                        customerRetrievalRate = Integer.parseInt(parts[1]);
                        break;
                    case "maxTicketCapacity":
                        maxTicketCapacity = Integer.parseInt(parts[1]);
                        break;
                }
            }
            return new ConfigurationT(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
        } catch (IOException e) {
            System.out.println("Error loading configuration from text file: " + e.getMessage());
            return null;
        }
    }



    public void saveConfiguration(String txtFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFile))) {
            writer.write("totalTickets=" + totalTickets);
            writer.newLine();
            writer.write("ticketReleaseRate=" + ticketReleaseRate);
            writer.newLine();
            writer.write("customerRetrievalRate=" + customerRetrievalRate);
            writer.newLine();
            writer.write("maxTicketCapacity=" + maxTicketCapacity);
            writer.newLine();
//
        } catch (IOException e) {
            System.out.println("Error saving configuration to text file: " + e.getMessage());
        }
    }
}
