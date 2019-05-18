public class Equation {

    private final double x0 = 0;
    private final double x1 = 1;
    private final double h = 1.0/3;
    private final double t0 = 0;
    private final double t1 = 1;
    private final double tau = 1.0/5;
    private final double a = 0.2;
    private final double b = 0.005;
    private final double c1 = 1.0;
    private final double alt = 0.005;

    public double getX0() {
        return x0;
    }

    public double getX1() {
        return x1;
    }

    public double getH() {
        return h;
    }

    public double getT0() {
        return t0;
    }

    public double getT1() {
        return t1;
    }

    public double getTau() {
        return tau;
    }

    public int getTPointsQuantity() {
        return (int) Math.ceil((t1 - t0) / tau) + 1;
    }

    public int getHPointsQuantity() {
        return (int) Math.ceil((x1 - x0) / h) + 1;
    }

    public double calculateTrueSolution(double x, double t) {
        return  (1/Math.pow(c1*Math.exp(alt/a/-2*(x+alt*t))-2*b/(3*alt),2));

    }

    public double calculateFirstRow(double x) {
        double t = 0;
        return  (1/Math.pow(c1*Math.exp(alt/a/-2*(x+alt*t))-2*b/(3*alt),2));
    }

    public double calculateFirstColumn(double t) {
        double x = 0;
        return  (1/Math.pow(c1*Math.exp(alt/a/-2*(x+alt*t))-2*b/(3*alt),2));
    }

    public double calculateLastColumn(double t) {
        double x = 1;
        return  (1/Math.pow(c1*Math.exp(alt/a/-2*(x+alt*t))-2*b/(3*alt),2));
    }

    public double calculateApproximateSolution(double wLeft, double wCurrent , double wRight) {
        return (wCurrent + (a*(wRight -2* wCurrent + wLeft)/Math.pow(h,2) + Math.sqrt(wCurrent)*(wRight - wLeft)*b/2*h)*tau);
    }

    public void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                System.out.print(String.format("%.7f\t", matrix[i][j]));
            }
            System.out.println();
        }
    }
}
