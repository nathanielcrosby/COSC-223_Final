import java.util.Arrays;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        double vals[][] = simulator.Simulate(1000000, 2000, 0.4, new double[]{0.6, 0.6, 0.4, 0.4, 0.4, 0.4, 0.4, 0.4, 0.4}, 9, 3);
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
