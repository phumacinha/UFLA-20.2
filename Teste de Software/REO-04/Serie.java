import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class Serie {

    private static Scanner read;

    public static void main(String[] args) {

        float start, ending, step;

        read = new Scanner(System.in);

        System.out.println("Digite o numero de incio da sequncia: ");
        while (!read.hasNextFloat()) {
            System.out.println("A entrada deve ser um numero. Tente novamente:");
            read.next();
        }
        start = read.nextFloat();

        System.out.println("Digite o numero final da sequncia: ");
        while (!read.hasNextFloat()) {
            System.out.println("A entrada deve ser um nmero. Tente novamente: ");
            read.next();
        }
        ending = read.nextFloat();

        System.out.println("Digite o numero de stepsize: ");
        step = read.nextFloat();

        if (step == 0) {
            step = 1;
        }

        compute_serie(start, ending, step);

    }

    public static ArrayList<Double> compute_serie(float start, float ending, float step) {
        
        double item, value;
        ArrayList<Double> results = new ArrayList<Double>();
        
        double range = Math.floor(Math.abs(ending - start)/step);

        if (start > ending) {

            for (item = start; item >= 0; item--) {
                
                value = ending + step * item;
                results.add(value);

            }
        } else if (start <= ending){
            for (item = 0; item <= range; item++) {

                value = start + step * item;
                results.add(value);
            }

        }
        
        for(Double r : results) {
            
            System.out.print("Contagem: " + r + ";\n");
        }
        
        return results;
    }

}
