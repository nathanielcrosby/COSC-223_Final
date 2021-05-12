import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        double vals[][] = simulator.Simulate(1000000, 2000, 0.4, new double[]{0.6, 0.6, 0.4, 0.4, 0.4, 0.4, 0.4, 0.4, 0.4}, 9, 3);
        System.out.println(Arrays.toString(vals[0]));
        System.out.println(Arrays.toString(vals[1]));
    }
}
