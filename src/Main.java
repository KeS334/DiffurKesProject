public class Main {

    public static void main(String[] args) {
        System.out.println("ПОСЛЕДОВАТЕЛЬНОЕ РЕШЕНИЕ:");
        Equation equation = new Equation();//новое диф уровнения
        long startTimeSerial = System.nanoTime();
        double[][] serialMethod = new SerialMethod(equation).solve();//создаём обьект сериал солв для вызова метода солв и возращает матрицу
        long endTimeSerial = System.nanoTime();
        long executeTimeSerial = endTimeSerial - startTimeSerial;
        System.out.println("Приближённый результат:");
//        equation.printMatrix(serialMethod);

        System.out.println();
        System.out.println("\nПАРАЛЛЕЛЬНОЕ РЕШЕНИЕ:");
        long startTimeParallel = System.nanoTime();
        double[][] parallelMethod = new ParallelMethod(equation).solve();
        long endTimeParallel = System.nanoTime();
        long executeTimeParallel = endTimeParallel - startTimeParallel;
//        equation.printMatrix(parallelMethod);
        System.out.println("\nВремя выполнения параллельного решения: " + executeTimeParallel + " нс");

        printResult(executeTimeSerial, equation, serialMethod, calculateExactResult(equation));
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
        System.out.println("\nТочный резульат:");
//        equation.printMatrix(trueMatrix);

        return trueMatrix;
    }

    private static void printResult(long executeTimeSerial, Equation equation, double[][] serialMethod, double[][] trueMatrix) {
        System.out.println("\nВремя выполнения последовательного решения: " + executeTimeSerial + " нс");
        System.out.println("Средняя абсолютная погрешность: " + calculateError(equation, serialMethod, trueMatrix, 1));
        System.out.println("Максимальная абсолютная погрешность: " + calculateError(equation, serialMethod, trueMatrix, 2));
        System.out.println("Средняя относительная погрешность: " + calculateError(equation, serialMethod, trueMatrix, 3));
        System.out.println("Максимальная относительная погрешность: " + calculateError(equation, serialMethod, trueMatrix, 4));
    }



    private static double calculateError(Equation equation, double[][] serialMethod, double[][] trueMatrix, int flag) {
        double[][] errorMatrix = new double[equation.getTPointsQuantity()][equation.getHPointsQuantity()];
        double error = 0.0;

        if (flag == 1 || flag == 2) {
            for (int i = 0; i < equation.getTPointsQuantity(); i++) {
                for (int j = 0; j < equation.getHPointsQuantity(); j++) {
                    errorMatrix[i][j] = Math.abs(serialMethod[i][j] - trueMatrix[i][j]);
                }
            }

            if (flag == 1) {
                for (int i = 0; i < equation.getTPointsQuantity(); i++) {
                    for (int j = 0; j < equation.getHPointsQuantity(); j++) {
                        error += errorMatrix[i][j];
                    }
                }

                error = error / (equation.getTPointsQuantity() * equation.getHPointsQuantity());
            } else if (flag == 2) {
                error = errorMatrix[0][0];
                for (int i = 0; i < equation.getTPointsQuantity(); i++) {
                    for (int j = 0; j < equation.getHPointsQuantity(); j++) {
                        if (error < errorMatrix[i][j]) {
                            error = errorMatrix[i][j];
                        }
                    }
                }
            }
        } else if (flag == 3 || flag == 4) {
            for (int i = 0; i < equation.getTPointsQuantity(); i++) {
                for (int j = 0; j < equation.getHPointsQuantity(); j++) {
                    errorMatrix[i][j] = 100 * (Math.abs(serialMethod[i][j] - trueMatrix[i][j])) / trueMatrix[i][j];
                }
            }

            if (flag == 3) {
                for (int i = 0; i < equation.getTPointsQuantity(); i++) {
                    for (int j = 0; j < equation.getHPointsQuantity(); j++) {
                        error += errorMatrix[i][j];
                    }
                }

                error = error / (equation.getTPointsQuantity() * equation.getHPointsQuantity());
            } else if (flag == 4) {
                error = errorMatrix[0][0];
                for (int i = 0; i < equation.getTPointsQuantity(); i++) {
                    for (int j = 0; j < equation.getHPointsQuantity(); j++) {
                        if (error < errorMatrix[i][j]) {
                            error = errorMatrix[i][j];
                        }
                    }
                }
            }
        }
        return error;
    }

}
