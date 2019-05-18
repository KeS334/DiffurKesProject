import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ParallelMethod {


        private int TPointsQuantity;//колиество точек по времени
        private int HPointsQuantity;//по х
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

        public double[][] solve() {//подсчёт
            double t = t0 + tau;
            double x = x0;
            double w[][] = new double[TPointsQuantity][HPointsQuantity];
            for (int j = 0; j < HPointsQuantity; j++, x += h) {//пробежка по х
                w[0][j] = equation.calculateBottomBorder(x);//считаем самую нижнюю черту,т=0
            }

            for (int i = 1; i < TPointsQuantity; ++i, t += tau) {
                w[i][0] = equation.calculateLeftBorder(t);//для х=0 левую границу
                AtomicInteger ai = new AtomicInteger(i);
                IntStream.range(1, equation.getHPointsQuantity()-1).parallel().forEach(j -> {//розбиваем парарлельно и для каждого j пробегаем
                    int m=ai.get();//создаём атомарную переменную
                    w[m][j] = equation.calculateApproximateSolution(w[m-1][j-1],w[m-1][j],w[m-1][j+1]);//считам приблизительное решения
                });
                w[i][HPointsQuantity - 1] = equation.calculateRightBorder(t);//для правой
            }
            return w;

    }


}
