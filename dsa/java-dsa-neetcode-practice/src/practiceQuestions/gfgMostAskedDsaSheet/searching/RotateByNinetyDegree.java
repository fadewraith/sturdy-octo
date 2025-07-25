package practiceQuestions.gfgMostAskedDsaSheet.searching;

public class RotateByNinetyDegree {

    private static int[][] rotateMatrix(int[][] matrix) {


        int m = matrix.length;
        int n = matrix[0].length;
        int[][] rotatedMatrix = new int[n][m];

        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                rotatedMatrix[j][m - i - 1] = matrix[i][j];
            }
        }

        return rotatedMatrix;
    }

    private static void displayMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Test Case 1
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };

        // Function call to rotate the matrix
        matrix = rotateMatrix(matrix);

        // Print the rotated matrix
        displayMatrix(matrix);
    }
}
