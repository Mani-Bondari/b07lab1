public class Polynomial {
    public double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[] {0};
    }

    public Polynomial(double[] coefficients){
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial other){
        int maxLen = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[maxLen];

        for(int i = 0; i < maxLen; i++){
            double thisCoeff = i < this.coefficients.length ? this.coefficients[i] : 0;
            double otherCoeff =  i < other.coefficients.length ? other.coefficients[i] : 0;
            result[i] = thisCoeff + otherCoeff;
        }
        return new Polynomial(result);
    }

    public double evaluate(double x){
        double[] coefficients = this.coefficients;
        double result = 0;

        for (int i = 0; i < coefficients.length; i++){
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double root) {
        return evaluate(root) == 0;
    }

}