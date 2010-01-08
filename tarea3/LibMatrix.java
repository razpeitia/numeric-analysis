/*
    Name: Ricardo Azpeitia Pimentel
    Student Number: 1344170
*/

import java.math.BigDecimal;

public class LibMatrix{
    public int rows;
    public int columns;
    public BigDecimal[][] matrix;
    
    public LibMatrix(int rows, int cols){
        this.rows = rows;
        this.columns = cols;
        matrix = new BigDecimal[rows][columns];
    }
    
    public void set(int row, int column, BigDecimal value){
        /* Sets the value in the position given */
        matrix[row][column] = value;
    }

    public BigDecimal get(int row, int column){
        /* Gets the value in the position given */
        return matrix[row][column];
    }

    public int Nrows(){
        /* Returns the number of rows */
        return rows;
    }

    public int Ncolumns(){
        /* Returns the number of columns */
        return columns;
    }

    public void mult_row(int row, BigDecimal value){
        /* Multiplys the value given in each value of the row */
        for(int j = 0; j < columns; j++)
            matrix[row][j] = matrix[row][j].multiply(value);
    }

    public void div_row(int row, BigDecimal value){
        /* Divides the value given in each value of the row */
        if(value.compareTo(new BigDecimal(0)) != 0)
            for(int j = 0; j < columns; j++)
                matrix[row][j] = matrix[row][j].divide(value, 1000, 
                                            BigDecimal.ROUND_HALF_EVEN);
    }

    public void sum_row1_to_row2(int row1, int row2, BigDecimal value){
        /* Sums the row1 to the row2 */
        for(int j=0; j<columns; j++)
            matrix[row2][j] = matrix[row2][j].add(
                                    value.multiply(matrix[row1][j]));
    }

    public int get_max(int row, int column){
        /* 
            Gets the row index where is the max number in abs
            in the column given and under the row given. 
        */
        BigDecimal max = new BigDecimal(0);
        int index = row;
        for(int i = row; i<columns; i++)
            if( matrix[i][column].abs().compareTo(max) > 0 ){
                max = matrix[i][column];
                index = i;
            }
        return index;
    }

    public void shift_rows(int row1, int row2){
        /* Shift row1 and row2 */
        BigDecimal aux;
        for(int j=0; j<columns; j++){
            aux = matrix[row1][j];
            matrix[row1][j] = matrix[row2][j];
            matrix[row2][j] = aux;
        }
    }
    

    public LibMatrix Trans(){
        /*
            Return a LibMatrix object with the matrix transpose
        */
        LibMatrix trans = new LibMatrix(columns, rows);
        for(int i = 0; i < columns; i++)
            for(int j = 0; j < rows; j++)
                trans.set(i, j, matrix[j][i]);
        return trans;
    }

    public LibMatrix Mult(LibMatrix M){
        /* 
            Will throw ArrayIndexOutOfBoundsException if they
                couldn't multiply
            Multiply the current Matrix whit the matrix M 
        */
        LibMatrix MM = new LibMatrix(rows, M.Ncolumns());
        BigDecimal sum = new BigDecimal(0);
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < M.Ncolumns(); j++){
                sum = new BigDecimal(0);
                for(int r = 0; r < columns; r++)
                    sum = sum.add(matrix[i][r].multiply(M.get(r, j)));
                MM.set(i, j, sum);
            }
        return MM;
    }

    public LibMatrix Invert(){
        /* 
            Will throw arithmetic exception if the matrix doesn't have Inv
            Return a LibMatrix object with the Matrix Invert
        */
        LibMatrix Inv = new LibMatrix(rows, columns * 2);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++)
                Inv.set(i, j, matrix[i][j]);
            for(int j = columns; j < columns*2; j++)
                if(i == j - columns)
                    Inv.set(i, j, new BigDecimal(1));
                else
                    Inv.set(i, j, new BigDecimal(0));
        }

        int temp = 0;
        for(int i = 0; i < rows; i++){
            if(Inv.get(i, i).compareTo(new BigDecimal(0)) == 0){
                temp = Inv.get_max(i, i);
                Inv.shift_rows(i, temp);
            }
            Inv.div_row(i, Inv.get(i, i));
            for (int j = i + 1; j < rows; j++)
                Inv.sum_row1_to_row2(i, j, 
                    Inv.get(j, i).multiply(new BigDecimal(-1)));
        }

        for(int i = 0; i < rows; i++)
            for(int j=i+1; j < rows; j++)
                Inv.sum_row1_to_row2(j, i, 
                    Inv.get(i, j).multiply(new BigDecimal(-1)));
        
        LibMatrix inv = new LibMatrix(rows, columns);
        for(int i = 0; i < rows; i++)
            for(int j = columns; j < columns*2; j++)
                inv.set(i, j - columns, Inv.get(i, j));
        return inv;
    }
}
