
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class Polynomial {
    public double[] non_zero_coeff;
    public int[] exponents;

    public Polynomial() {
        this.non_zero_coeff = new double[] {0};
        this.exponents = new int[] {0};
    }

    public Polynomial(double[] coefficients, int[] exponents){
        this.non_zero_coeff = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file){

        String polynomial = "";
        try(Scanner scanner = new Scanner(file)){
            polynomial = scanner.nextLine();
        }catch (FileNotFoundException e){
            System.err.println("File not found: " + e.getMessage());
        }

        if(polynomial.isEmpty()){
            return;
        }
        //place whitespaces before + and - in the string
        String norm_poly = polynomial.replaceAll("(?<=\\d)([+-])", " $1");

        String[] split_poly = norm_poly.split(" ");

        double[] coeff = new double[split_poly.length];
        int[] exp = new int[split_poly.length];
        double coefficient;
        int exponent;
        for(int count = 0; count < split_poly.length; count++){
            String token = split_poly[count];


            if(token.contains("x")){
                String[] parts = token.split("x");
                coefficient = (parts[0].isEmpty() || parts[0].equals("+")) ? 1 : (parts[0].equals("-") ? -1 : Double.parseDouble(parts[0]));
                exponent = (parts.length > 1 && !parts[1].isEmpty()) ? Integer.parseInt(parts[1]) : 1;
            }else {
                coefficient = Integer.parseInt(token);
                exponent = 0;
            }
            coeff[count] = coefficient;
            exp[count] = exponent;
        }
        this.non_zero_coeff = coeff;
        this.exponents = exp;

    }

    private int findMaxExponent() {
        int maxExp = 0;
        for (int exp : this.exponents) {
            if (exp > maxExp) maxExp = exp;
        }
        return maxExp;
    }

    // Method to find the maximum exponent between two polynomials
    private int findMaxExponent(Polynomial other) {
        return Math.max(this.findMaxExponent(), other.findMaxExponent());
    }


    public Polynomial add(Polynomial other) {
        int maxExp = findMaxExponent(other);

        double[] resultCoeff = new double[maxExp + 1];

        // Add coefficients from this polynomial
        for (int i = 0; i < this.exponents.length; i++) {
            resultCoeff[this.exponents[i]] += this.non_zero_coeff[i];
        }

        // Add coefficients from other polynomial
        for (int i = 0; i < other.exponents.length; i++) {
            resultCoeff[other.exponents[i]] += other.non_zero_coeff[i];
        }

        // Filter non-zero coefficients and their exponents to new arrays
        int nonZeroCount = 0;
        for (double coeff : resultCoeff) {
            if (coeff != 0) nonZeroCount++;
        }
        double[] nonZeroCoeff = new double[nonZeroCount];
        int[] resExp = new int[nonZeroCount];

        int index = 0;
        for (int i = 0; i < resultCoeff.length; i++) {
            if (resultCoeff[i] != 0) {
                nonZeroCoeff[index] = resultCoeff[i];
                resExp[index] = i;
                index++;
            }
        }

        // Return new Polynomial object
        return new Polynomial(nonZeroCoeff, resExp);
    }



    public double evaluate(double x){
        double[] non_zero_coeff = this.non_zero_coeff;
        double result = 0;

        for (int i = 0; i < non_zero_coeff.length; i++){
            result += non_zero_coeff[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public Polynomial multiply(Polynomial other) {
        int maxExp = this.findMaxExponent() + other.findMaxExponent();
        double[] resultCoeff = new double[maxExp + 1];

        // Multiply each term in this polynomial by each term in the other polynomial
        for (int i = 0; i < this.non_zero_coeff.length; i++) {
            for (int j = 0; j < other.non_zero_coeff.length; j++) {
                int resultantExponent = this.exponents[i] + other.exponents[j];
                double resultantCoefficient = this.non_zero_coeff[i] * other.non_zero_coeff[j];
                resultCoeff[resultantExponent] += resultantCoefficient;
            }
        }

        // Filter non-zero coefficients and their corresponding exponents
        int nonZeroCount = 0;
        for (double coeff : resultCoeff) {
            if (coeff != 0) nonZeroCount++;
        }
        double[] nonZeroCoeff = new double[nonZeroCount];
        int[] resExp = new int[nonZeroCount];

        int index = 0;
        for (int i = 0; i < resultCoeff.length; i++) {
            if (resultCoeff[i] != 0) {
                nonZeroCoeff[index] = resultCoeff[i];
                resExp[index] = i;
                index++;
            }
        }

        // Return new Polynomial object with non-zero coefficients
        return new Polynomial(nonZeroCoeff, resExp);
    }


    public boolean hasRoot(double root) {
        return evaluate(root) == 0;
    }

    public void saveToFile(String fileName){
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < this.non_zero_coeff.length; i++){
            res.append(this.non_zero_coeff[i]).append("x").append(this.exponents[i]);
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            writer.write(res.toString());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}