import java.util.Hashtable;
import java.util.Enumeration;
import java.math.BigDecimal;

class Main{
    public static void main(String args[]){
        LibUtils Util = new LibUtils("Interpolacion Ascendente", 
                                "¿Desea Salir del Programa?");
        Hashtable <BigDecimal, BigDecimal> data;
        BigDecimal value;
        
        while(true){
            data = Util.ReadData("Dame un valor de x", "Dame un valor de f(x)",
                "Formato no valido", "¿Desea continuar añadiendo datos?",
                "Debe añadir al menos 2 datos", 2);
            value = Util.ReadBigDecimal("Dame el valor a sustituir", 
                            "Por favor introduce un numero");
            Engine engine = new Engine(data, value);
            while(true){
                Util.ShowMessage("El resultado es:\nF(x="+
                    engine.getValue().setScale(7)+") = "+engine.getResult());
                if (Util.ReadYesNo("Desea evaluar en otro valor") == 0)
                    break;
                value = Util.ReadBigDecimal("Dame el valor a sustituir", 
                            "Por favor introduce un numero");
                engine.setValue(value);
            }
            Util.ToExit();
        }
    }
}
