import java.util.Arrays;
import java.io.IOException;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
/*
        double vals[][] = simulator.Simulate(7000, 1000, 0.5, new double[]{0.99, 0.99, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.7}, 9, 3, false,1.0/3.0, false);
        System.out.println(Arrays.toString(vals[0]));
        System.out.println(Arrays.toString(vals[1]));

        System.out.println("------------------------------------------------------");

        double vals2[][] = simulator.Simulate(7000, 1000, 0.5, new double[]{0.99, 0.99, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.7}, 9, 3, false,1.0/3.0, true);
        System.out.println(Arrays.toString(vals2[0]));
        System.out.println(Arrays.toString(vals2[1]));

*/


        // Run Traffic Experiment
        // =======================================================================================
        
        // Make Traffic Experiment Folder for the Data

        File f = new File("./Traffic Experiment");
        f.mkdirs();

        for (int i = 2 ; i < 7 ; i++){
            simulator.Simulate(50000, 5000, 0.5, new double[]{0.99, 0.99, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.7}, 9, -1, 2, false,1.0/i, false, 0.5);

            try{ simulator.serverDataToCSV("./Traffic Experiment/server_" + i);} 
            catch(IOException e){ System.out.println("IOException"); }

            try{ simulator.jobDataToCSV("./Traffic Experiment/jobs_" + i);} 
            catch(IOException e){ System.out.println("IOException"); }

            System.out.print(" . ");
        } 
        System.out.println();  
        System.out.println("Traffic Experiment Done");
        // =======================================================================================


        // Run Meal Rating Experiment 
        // =======================================================================================
        
        // Make Meal Rating Experiment Folder for the Data
        f = new File("./Meal Rating Experiment");
        f.mkdirs();

        for (int i = 1 ; i <= 8 ; i++){
            simulator.Simulate(50000, 5000, 0.5, new double[]{0.99, 0.99, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.7}, 9, i, 2, false,1.0/3.0, false, 0.5);

            try{ simulator.serverDataToCSV("./Meal Rating Experiment/server_" + i);} 
            catch(IOException e){ System.out.println("IOException"); }

            try{ simulator.jobDataToCSV("./Meal Rating Experiment/jobs_" + i);} 
            catch(IOException e){ System.out.println("IOException"); }

            System.out.print(" . ");
        } 
        System.out.println();  
        System.out.println("Meal Rating Experiment Done");
        // =======================================================================================

        // Run Shock Experiment
        // =======================================================================================
        
        // Make Traffic Experiment Folder for the Data
        f = new File("./Shock Experiment");
        f.mkdirs();

        // Shock & Random Preferences
        simulator.Simulate(50000, 5000, 0.5, new double[]{0.99, 0.99, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.7}, 9, -1, 2,true,1.0/3.0, false, 0.5);
        try{ simulator.serverDataToCSV("./Shock Experiment/server_shock_r");} 
        catch(IOException e){ System.out.println("IOException"); }
        try{ simulator.jobDataToCSV("./Shock Experiment/jobs_shock_r");} 
        catch(IOException e){ System.out.println("IOException"); }

        // Shock & Great Meal
        simulator.Simulate(50000, 5000, 0.5, new double[]{0.99, 0.99, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.7}, 9, 8, 2,true,1.0/3.0, false, 0.5);
        try{ simulator.serverDataToCSV("./Shock Experiment/server_shock_g");} 
        catch(IOException e){ System.out.println("IOException"); }
        try{ simulator.jobDataToCSV("./Shock Experiment/jobs_shock_g");} 
        catch(IOException e){ System.out.println("IOException"); }

        // Shock & Bad Meal
        simulator.Simulate(50000, 5000, 0.5, new double[]{0.99, 0.99, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.7}, 9, 3, 2,true,1.0/3.0, false, 0.5);
        try{ simulator.serverDataToCSV("./Shock Experiment/server_shock_b");}
        catch(IOException e){ System.out.println("IOException"); }
        try{ simulator.jobDataToCSV("./Shock Experiment/jobs_shock_b");} 
        catch(IOException e){ System.out.println("IOException"); }
        
        System.out.println("Shock Experiment Done");
        // =======================================================================================


        // ====================================SPLITTING UP GROUPS EXPERIMENT (NOT RAN YET)===================================================

        // Run Traffic Experiment
        // =======================================================================================

        // Make Traffic Experiment Folder for the Data
        f = new File("./Traffic Experiment Groups");
        f.mkdirs();

        for (int i = 2 ; i < 7 ; i++){
            simulator.Simulate(50000, 5000, 0.5, new double[]{0.99, 0.99, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.7}, 9, -1, 2, false,1.0/i, true, 0.5);

            try{ simulator.serverDataToCSV("./Traffic Experiment Groups/server_" + i);}
            catch(IOException e){ System.out.println("IOException"); }

            try{ simulator.jobDataToCSV("./Traffic Experiment Groups/jobs_" + i);}
            catch(IOException e){ System.out.println("IOException"); }

            System.out.print(" . ");
        }
        System.out.println();
        System.out.println("Traffic Experiment Done");
        // =======================================================================================


        // Run Meal Rating Experiment
        // =======================================================================================

        // Make Meal Rating Experiment Folder for the Data
        f = new File("./Meal Rating Experiment Groups");
        f.mkdirs();

        for (int i = 1 ; i <= 8 ; i++){
            simulator.Simulate(50000, 5000, 0.5, new double[]{0.99, 0.99, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.7}, 9, i, 2, false, 1.0/3.0, true, 0.5);

            try{ simulator.serverDataToCSV("./Meal Rating Experiment Groups/server_" + i);}
            catch(IOException e){ System.out.println("IOException"); }

            try{ simulator.jobDataToCSV("./Meal Rating Experiment Groups/jobs_" + i);}
            catch(IOException e){ System.out.println("IOException"); }

            System.out.print(" . ");
        }
        System.out.println();
        System.out.println("Meal Rating Experiment Done");
        // =======================================================================================

        // Run Shock Experiment
        // =======================================================================================

        // Make Traffic Experiment Folder for the Data

        f = new File("./Shock Experiment Groups");
        f.mkdirs();

        // Shock & Random Preferences
        simulator.Simulate(50000, 5000, 0.5, new double[]{0.99, 0.99, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.7}, 9, -1, 2, true, 1.0/3.0, true, 0.5);
        try{ simulator.serverDataToCSV("./Shock Experiment Groups/server_shock_r");}
        catch(IOException e){ System.out.println("IOException"); }
        try{ simulator.jobDataToCSV("./Shock Experiment Groups/jobs_shock_r");}
        catch(IOException e){ System.out.println("IOException"); }

        System.out.println("Shock Experiment Done");
        // =======================================================================================

        // Make Side Rating Experiment Folder for the Data
        f = new File("./Side Rating Experiment");
        f.mkdirs();

        for (int i = 1 ; i <= 7 ; i++){
            simulator.Simulate(50000, 5000, 0.5, new double[]{0.99, 0.99, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.7}, 9, 5, i, false, 1.0/3.0, false, 0.33);

            try{ simulator.serverDataToCSV("./Side Rating Experiment/server_" + i);}
            catch(IOException e){ System.out.println("IOException"); }

            try{ simulator.jobDataToCSV("./Side Rating Experiment/jobs_" + i);}
            catch(IOException e){ System.out.println("IOException"); }

            System.out.print(" . ");
        }
        System.out.println();
        System.out.println("Side Rating Experiment Done");

        // =======================================================================================

        // Make Side Rating Experiment Folder for the Data
        f = new File("./Portion Size Experiment");
        f.mkdirs();

        for (int i = 1 ; i <= 20 ; i++){
            simulator.Simulate(50000, 5000, 0.5, new double[]{0.99, 0.99, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.7}, 9, -1, 2, false, 1.0/3.0, false, 0.4+i*0.01);

            try{ simulator.serverDataToCSV("./Portion Size Experiment/server_" + i);}
            catch(IOException e){ System.out.println("IOException"); }

            try{ simulator.jobDataToCSV("./Portion Size Experiment/jobs_" + i);}
            catch(IOException e){ System.out.println("IOException"); }

            System.out.print(" . ");
        }
        System.out.println();
        System.out.println("Portion Size Experiment Done");

    }
}
