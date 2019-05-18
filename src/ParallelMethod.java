import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ParallelMethod {


        private int TPointsQuantity;
        private int HPointsQuantity;
        private double x0;
        private double h;
        private double t0;
        private double tau;
        private Equation equation;

        public ParallelMethod(Equation equation) {//конструктор
            this.equation = equation;
            this.TPointsQuantity = equation.getTPointsQuantity();
            this.HPointsQuantity = equation.getHPointsQuantity();
            this.x0 = equation.getX0();
            this.h = equation.getH();
            this.t0 = equation.getT0();
            this.tau = equation.getTau();
        }

        public double[][] solve() {
            double x = x0;
            double t = t0 + tau;

            double w[][] = new double[TPointsQuantity][HPointsQuantity];
            for (int j = 0; j < HPointsQuantity; j++, x += h) {
                w[0][j] = equation.calculateFirstRow(x);
            }

            for (int i = 1; i < TPointsQuantity; ++i, t += tau) {
                w[i][0] = equation.calculateFirstColumn(t);
                AtomicInteger atom = new AtomicInteger(i);
                IntStream.range(1, equation.getHPointsQuantity()-1).parallel().forEach(j -> {
                    int m=atom.get();
                    w[m][j] = equation.calculateApproximateSolution(w[m-1][j-1],w[m-1][j],w[m-1][j+1]);
                });
                w[i][HPointsQuantity - 1] = equation.calculateLastColumn(t);
            }
            return w;

    }


}
