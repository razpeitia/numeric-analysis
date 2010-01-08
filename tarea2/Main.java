/*
    Name: Ricardo Azpeitia Pimentel
    Student Number: 1344170
    Subject: Análisis Numérico
    Date: 10/09/2009
    University: Universidad Autonoma de Nuevo Léon
    Methods: Falsi Method and Secant Method
*/
import java.text.DecimalFormat;
class Main{
    public static void Solve(){
        double constants[];
        double root;
        int method;
        String[] methods = {"Falsa Posición", "Secante"};

        ProyectUtils utils = new ProyectUtils(
                    "Raíz de una ecuación no lineal");
        utils.SetGrade(); /* Get and set the grade of the ecuation */
        constants = utils.ReadConstants(); /* Read grade+1 constants */
        method = utils.ReadOption(methods, "¿Cual metodo desea usar?");
        if(method == 1){
            double a, b, error;
            int max;
            FalsiMethod F = new FalsiMethod(utils.GetGrade(), constants);

            utils.ShowMessage("A continuación se le pedira un\n "+
                    "intervalo de la forma [a, b], donde\n "+
                    "se encuentre una raiz de la ecuacion");

            a = utils.ReadDouble("Introduzca el punto a", 
                "Entrada no valida, por favor introduzca un número");

            b = utils.ReadDouble("Introduzca el punto b",
                "Entrada no valida, por favor introduzca un número");

            error = utils.ReadError("Introduzca un "+
                            "margen de error entre 0 y 1",
                "Entrada no valida, por favor introduzca un número");

            max = utils.ReadMax("Introduzca el número maximo"+
                " de iteraciones para encontrar la solución",
                "Entrada no valida, por favor introduzca un número");

            root = F.Solve(a, b, error, max);

            if(F.GetFail() == 1) /* If the method not found a root */
                utils.ShowError("No se encontro la raíz de la "+
                            "ecuación\nRazones:\n"+
                            "1.- No existe la raíz de la "+
                                "ecuación en el intervalo dado.\n"+
                            "2.- La tolerancia es tan pequeña que "+
                                "no se logro el resultado deseado "+
                                "en el número indicado de ciclos\n"+
                            "3.- La ecuación no tiene raíz en los"+
                                " reales\n\n"+
                            "Si tiene duda acerca de los intevalos "+
                            "recomendamos usar el metodo secante");
            else
                utils.ShowMessage("La raiz de la ecuación es: "+root);
        }
        else{
            double a, b, error;
            int max;
            SecantMethod S = new SecantMethod(utils.GetGrade(), 
                                                constants);
            utils.ShowMessage("A continuación se le pedira un\n"+
                    "intervalo de la forma [a, b]");

            a = utils.ReadDouble("Introduzca el punto a", 
                "Entrada no valida, por favor introduzca un número");

            b = utils.ReadDouble("Introduzca el punto b",
                "Entrada no valida, por favor introduzca un número");

            error = utils.ReadError("Introduzca un "+
                            "margen de error entre 0 y 1",
                "Entrada no valida, por favor introduzca un número");

            max = utils.ReadMax("Introduzca el número maximo"+
                " de iteraciones para encontrar la solución",
                "Entrada no valida, por favor introduzca un número");

            root = S.Solve(a, b, error, max);
            DecimalFormat formater = new DecimalFormat(
                                "#######.##########");
            
            if(S.GetFail() == 1)
                utils.ShowError("No se encontro la raíz de la "+
                            "ecuación\nRazones:\n"+
                            "1.- No existe la raíz de la "+
                                "ecuación en el intervalo dado.\n"+
                            "2.- La tolerancia es tan pequeña que "+
                                "no se logro el resultado deseado "+
                                "en el número indicado de ciclos\n"+
                            "3.- La ecuación no tiene raíz en los"+
                                " reales");
            else
                utils.ShowMessage("La raiz de la ecuación es: "
                    +formater.format (root));

        }
        utils.ToExit();
    }

    public static void main(String argv[]){
        while(true){
            Solve();
        }
    }
}
/*
    Code written by Ricardo Azpeitia Pimentel
*/
