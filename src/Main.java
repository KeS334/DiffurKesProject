public class Main {

    public static void main(String[] args) {
        Equation equation = new Equation();
        long startTimeSerial = System.nanoTime();
        double[][] serialMethod = new SerialMethod(equation).solve();
        long endTimeSerial = System.nanoTime();
        long executeTimeSerial = endTimeSerial - startTimeSerial;

        long startTimeParallel = System.nanoTime();
        double[][] parallelMethod = new ParallelMethod(equation).solve();
        long endTimeParallel = System.nanoTime();
        long executeTimeParallel = endTimeParallel - startTimeParallel;

        printResult(executeTimeSerial, executeTimeParallel, equation, serialMethod, parallelMethod, calculateExactResult(equation));
    }

    private static double[][] calculateExactResult(Equation equation) {
        double trueMatrix[][] = new double[equation.getTPointsQuantity()][equation.getHPointsQuantity()];
        double t = equation.getT0();
        for (int i = 0; i < equation.getTPointsQuantity(); i++) {
            double x = equation.getX0();
            for (int j = 0; j < equation.getHPointsQuantity(); j++) {
                trueMatrix[i][j] = equation.calculateTrueSolution(x, t);
                x += equation.getH();
            }
            t += equation.getTau();
        }
        return trueMatrix;
    }

    private static void printResult(long executeTimeSerial, long executeTimeParallel,
        Equation equation, double[][] serialMethod, double[][] parallelMethod, double[][] trueMatrix) {

        System.out.println("\nSerial Solution:");
        equation.printMatrix(serialMethod);

        System.out.println("\nParallel Result:");
        equation.printMatrix(parallelMethod);

        System.out.println("\nExact Result:");
        equation.printMatrix(trueMatrix);

        System.out.println();
        System.out.println("Serial Time: " + executeTimeSerial + " ns");
        System.out.println("Parallel Time: " + executeTimeParallel + " ns");
        System.out.println();

        Errors error = new Errors();
        System.out.println("Average absolute error: " + error.calculateError(equation, serialMethod, trueMatrix, 1));
        System.out.println("Max absolute error: " + error.calculateError(equation, serialMethod, trueMatrix, 2));
        System.out.println("Average relative error: " + error.calculateError(equation, serialMethod, trueMatrix, 3));
        System.out.println("Max relative error: " + error.calculateError(equation, serialMethod, trueMatrix, 4));
    }





}
