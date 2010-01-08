import java.util.Hashtable;
import java.util.Enumeration;
import java.math.BigDecimal;

class Main{
    public static void main(String args[]){

        LibUtils Util = new LibUtils("Minimos Cuadrados", 
                                "¿Desea Salir del programa?");
        LibMatrix Result;

        while(true){
            String[] Options = {"1", "2", "3", "4", "5"};
            int grade = Util.ReadOption(Options, 
                        "Dame el grado de la ecuacion");
            int[] eqn = new int[(grade + 1)];

            eqn[0] = 1;
            for(int i = 1; i < grade; i++)
                eqn[i] = Util.ReadYesNo("¿Desea agregar X**"+(i)+"?");
            eqn[grade] = 1;

            Hashtable <BigDecimal, BigDecimal> data;
            data = Util.ReadData("Dame un valor de x", "Dame un valor de f(x)",
                "Formato no valido\nPor favor introduzca un numero",
                 "¿Desea continuar añadiendo datos?",
                 "Introduzca al menos "+(grade + 1)+" datos", grade);
            Engine engine = new Engine(eqn, data, 7);
            try{
                engine.getConstants();
                Util.ShowMessage(engine.Result);
            }
            catch (ArithmeticException ex){
                Util.ShowError("No se pudo encontrar una correacion entre\n"+
                                "los datos y la ecuacion dada");
            }
            Util.ToExit();
        }
    }
}
