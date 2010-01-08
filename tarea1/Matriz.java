import java.text.DecimalFormat;

/*
    Metodo Gauss-Jordan para sistema de ecuaciones lineles
    Hecho por: Ricardo Azpeitia Pimentel
    Matricula: 1344170
*/
public class Matriz{
    private int alto;
    private int ancho;
    private double matriz[][];
    
    public Matriz(int orden){
        alto = orden;
        ancho = orden +1;
        matriz = new double[alto][ancho];
    }
    
    private double abs(double value){
        /* Funcion propia de la clase para obtener el valor absoluto */
        //Me dio hueva importar math
        return (value>0)?value:-value;
    }

    public void set(int fila, int columna, double value){
        /* Pone el valor en la fila y columna dada */
        matriz[fila][columna] = value;
    }

    public double get(int fila, int columna){
        /* Obtiene el valor de la fila y columna dada */
        return matriz[fila][columna];
    }

    public int get_alto(){
        /* Regresa el alto de la matriz */
        return alto;
    }

    public int get_ancho(){
        /* Regresa el ancho de la matriz */
        return ancho;
    }

    private void mult_fila(int fila, double value){
        /* Multiplica cada miembro de la fila dada con el valor dado */
        for(int j=0; j<ancho; j++)
            matriz[fila][j] *= value;
    }

    private void div_fila(int fila, double value){
        /* Divide cada miembro de la fila dada con el valor dado */
        if(value != 0)
            for(int j=0; j<ancho; j++)
                matriz[fila][j] /= value;
    }

    private void suma_fila1_a_fila2(int fila1, int fila2, double value){
        /* Suma la fila1 por un valor a la fila2 */
        for(int j=0; j<ancho; j++)
            matriz[fila2][j] += value*matriz[fila1][j];
    }

    private int get_max(int fila, int columna){
        /* Regresa la fila donde se encuentra el mayor por debajo 
           de la fila en la columna */
        double max = 0;
        int index = fila;
        for(int i=fila; i<alto; i++)
            if( abs(matriz[i][columna]) > max ){
                max = matriz[i][columna];
                index = i;
            }
        return index;
    }

    private void cambiar_filas(int fila1, int fila2){
        /* Cambia la fila1 por la fila2 de la matriz */
        double aux;
        for(int j=0; j<ancho; j++){
            aux = matriz[fila1][j];
            matriz[fila1][j] = matriz[fila2][j];
            matriz[fila2][j] = aux;
        }
    }
    
    public int resolver_GaussJordan(){
        int temp = 0;
        for(int i=0; i<alto; i++){
            if(matriz[i][i] == 0){ //Si la diagonal es cero
                temp = get_max(i,i);
                if(temp == i) //Si el mayor es el 0 en valor absoluto
                    return 1; //No puedo seguir con esta ecuacion
                cambiar_filas(i, temp);//Si no cambiamos filas
            }
            div_fila(i, matriz[i][i]); //Hace uno la diagonal pos i,i
            for (int j=i+1; j < alto; j++) //Hace ceros, debajo de el
                suma_fila1_a_fila2(i, j, -matriz[j][i]);
        }

        for(int i=0; i<alto; i++) //Saco los valores de X1, X2, ... Xn
            for(int j=i+1; j<alto; j++)
                suma_fila1_a_fila2(j, i, -matriz[i][j]);
        return 0; //Termine como debe de ser
    }

    public int resolver_PivoteMaximo(){
        // Lo mismo que el Metodo anterior
        //Solo que siempre cambio la fila por el mayor en valor absoluto
        int flag;
        for(int i=0; i<alto; i++){
            flag = get_max(i,i);
            if(matriz[flag][i] == 0 && flag == i)
                return 1;
            if (flag !=  i)
                cambiar_filas(i, flag);
            div_fila(i, matriz[i][i]); //Hace uno la pos i,i
            for (int j=i+1; j < alto; j++) //Hace ceros, debajo de el
                suma_fila1_a_fila2(i, j, -matriz[j][i]);
        }

        for(int i=0; i<alto; i++)
            for(int j=i+1; j<alto; j++)
                suma_fila1_a_fila2(j, i, -matriz[i][j]);
        return 0;
    }
}
