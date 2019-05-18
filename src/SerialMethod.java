public class SerialMethod {


        private int TPointsQuantity;
        private int HPointsQuantity;
        private double x0;
        private double h;
        private double t0;
        private double tau;
        private Equation equation;

        public SerialMethod(Equation equation) {
            this.equation = equation;
            this.TPointsQuantity = equation.getTPointsQuantity();
            this.HPointsQuantity = equation.getHPointsQuantity();
            this.x0 = equation.getX0();
            this.h = equation.getH();
            this.t0 = equation.getT0();
            this.tau = equation.getTau();
        }

        public double[][] solve() {
            double t = t0 + tau;
            double x = x0;
            double w[][] = new double[TPointsQuantity][HPointsQuantity];
            for (int j = 0; j < HPointsQuantity; j++, x += h) {
                w[0][j] = equation.calculateFirstRow(x);
            }

            for (int i = 1; i < TPointsQuantity; ++i, t += tau) {
                w[i][0] = equation.calculateFirstColumn(t);
                for (int j = 1; j < HPointsQuantity - 1; j++) {
                    w[i][j] = equation.calculateApproximateSolution(w[i-1][j-1],w[i-1][j],w[i-1][j+1]);
                }
                w[i][HPointsQuantity - 1] = equation.calculateLastColumn(t);
            }
            return w;
        }



}
