import java.io.File;
import java.util.Arrays;

public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double[] c1 = {6, 1, 2, 3};
        int[] e1 = {1, 2, 3, 4};
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = {2, -2, 3, 7, -9};
        int [] e2 = {1, 3, 5, 6, 7};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(3));
        if (p.hasRoot(3))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
        String filePath = "test.txt";

        File file = new File(filePath);
        Polynomial p3 = new Polynomial(file);
        System.out.println(Arrays.toString(p3.non_zero_coeff));
        Polynomial p4 = p3.add(p2);
        System.out.println(Arrays.toString(p4.non_zero_coeff));
        System.out.println(Arrays.toString(p4.exponents));

        Polynomial p5 = p4.multiply(p3);
        System.out.println(Arrays.toString(p5.non_zero_coeff));
        System.out.println(Arrays.toString(p5.exponents));
        System.out.println(p5.evaluate(1.3));
    }
}