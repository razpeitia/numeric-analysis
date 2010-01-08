import java.math.BigDecimal;

class Main{
    public static void main(String args[]){
        /* New object for the useful LibUtils class */
        LibUtils Util = new LibUtils("Metodo Romberg", 
                                "¿Desea Salir del programa?");
        int Max = 5, n; //The largest equation grade and the subareas number
        int precision = 7; //The presicion to show
        BigDecimal lrange; //Left limit
        BigDecimal rrange; //Right limi
        /* Array of options */
        String[] Options  = new String[Max];
        for(int i = 0; i < Max; i++)
            Options[i] = ""+(i + 1);
        
        while(true){
            /* Read the grade of the equation from 1 to 5 */
            int grade = Util.ReadOption(Options, 
                        "Seleciona grado de la ecuacion");
            /* Create a BuiDecimal array and read "grade+1"  values,
               the item grade, couldn't be 0.
            */
            BigDecimal[] eqn  = new BigDecimal[(grade + 1)];
            for(int i = 0; i < grade; i++)
                eqn[i] = Util.ReadBigDecimal(
                    "Dame el coeficiente de X**"+i,
                    "Formato no Valido\nIntroduzca un numero por favor");

            while(true){
                eqn[grade] = Util.ReadBigDecimal(
                    "Dame el coeficiente de X**"+grade,
                    "Formato no Valido\nIntroduzca un numero por favor");
                if(eqn[grade].compareTo( new BigDecimal(0) ) == 0)
                    Util.ShowError("Este coeficiente no puede ser 0");
                else
                    break;
            }
            
            /* Read  the left and the rigth limit and send a error message if
               the left limit is greater than the rigth limit.
            */
            while(true){
                lrange = Util.ReadBigDecimal("Dame el limite izquierdo", 
                    "Formato no Valido\nIntroduzca un numero por favor");
                rrange = Util.ReadBigDecimal("Dame el limite derecho", 
                    "Formato no Valido\nIntroduzca un numero por favor");
                if(lrange.compareTo(rrange) == 0)
                    Util.ShowError("Los limites no pueden ser iguales");
                else if(lrange.compareTo(rrange) == 1)
                    Util.ShowError("El limite izquierdo no puede ser mayor"+
                                  " que el derecho");
                else
                    break;
            }

            /* Read the number of subareas, it most be greater than zero. */
            while(true){
                n = Util.ReadInt("Introduzca el numero de subareas a sumar", 
                        "Formato no Valido\nIntroduzca un numero por favor");
                if( n <= 0 )
                    Util.ShowError("El numero de subareas debe ser "+
                                   "mayor o igual a 1");
                else
                    break;
            }

            /* Create a engine objecto from the class Engine, 
               which manage the method */ 
            Engine engine = new Engine(eqn, lrange, rrange, n);

            /* Fancy string format */
            String result = "";
            result += "Ecuacion:\n   ";
            for(int i = 0; i < eqn.length; i++){
                int temp = eqn[i].compareTo(new BigDecimal(0));
                if(temp == 0)
                    continue;
                else
                    if(temp == -1)
                        result += " - ";
                    else
                        result += " + ";
                result += eqn[i].abs().setScale(3, BigDecimal.ROUND_HALF_EVEN);
                if(i != 0)
                    if(i == 1)
                        result += "X";
                    else
                        result += "X**"+i;
            }
            result += "\n";
            result += "Intervalo:\n    "+lrange.setScale(2, 
                                BigDecimal.ROUND_HALF_EVEN)+" : "+
                                rrange.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            result += "\n";
            result += "Resultado:\n    "+engine.Romber(precision);
            result += "\n";

            /* Show the equation, the range and the result */
            Util.ShowMessage(result);
            /* Ask if want to exit */
            Util.ToExit();
        }
    }
}
