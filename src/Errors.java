public class Errors {

    public static double calculateError(Equation equation, double[][] serialMethod, double[][] trueMatrix, int flag) {
        double[][] errorMatrix = new double[equation.getTPointsQuantity()][equation.getHPointsQuantity()];
        double error = 0.0;

        switch (flag){
            case 1:
                for (int i = 0; i < equation.getTPointsQuantity(); i++) {
                    for (int j = 0; j < equation.getHPointsQuantity(); j++) {
                        errorMatrix[i][j] = Math.abs(serialMethod[i][j] - trueMatrix[i][j]);
                        error += errorMatrix[i][j];
                    }
                }
                error = error / (equation.getTPointsQuantity() * equation.getHPointsQuantity());
                break;
            case 2:
                for (int i = 0; i < equation.getTPointsQuantity(); i++) {
                    for (int j = 0; j < equation.getHPointsQuantity(); j++) {
                        errorMatrix[i][j] = Math.abs(serialMethod[i][j] - trueMatrix[i][j]);
                        if (error < errorMatrix[i][j]) {
                            error = errorMatrix[i][j];
                        }
                    }
                }
                break;
            case 3:
                for (int i = 0; i < equation.getTPointsQuantity(); i++) {
                    for (int j = 0; j < equation.getHPointsQuantity(); j++) {
                        errorMatrix[i][j] = 100 * (Math.abs(serialMethod[i][j] - trueMatrix[i][j])) / trueMatrix[i][j];
                        error += errorMatrix[i][j];
                    }
                }
                error = error / (equation.getTPointsQuantity() * equation.getHPointsQuantity());
                break;
            case 4:
                for (int i = 0; i < equation.getTPointsQuantity(); i++) {
                    for (int j = 0; j < equation.getHPointsQuantity(); j++) {
                        errorMatrix[i][j] = 100 * (Math.abs(serialMethod[i][j] - trueMatrix[i][j])) / trueMatrix[i][j];
                        if (error < errorMatrix[i][j]) {
                            error = errorMatrix[i][j];
                        }
                    }
                }
                break;
        }
        return error;
    }

}
