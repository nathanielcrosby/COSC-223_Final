import java.util.Arrays;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        double vals[][] = simulator.Simulate(7000, 1000, 0.5, new double[]{0.99, 0.99, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.7}, 9, 3, true);
        System.out.println(Arrays.toString(vals[0]));
        System.out.println(Arrays.toString(vals[1]));

        try{ simulator.serverDataToCSV("./server");} 
        catch(IOException e){ System.out.println("IOException"); }
        System.out.println("Downloaded server data as ./server.csv");

        try{ simulator.jobDataToCSV("./jobs");} 
        catch(IOException e){ System.out.println("IOException"); }
        System.out.println("Downloaded job data as ./jobs.csv");
    }
}
