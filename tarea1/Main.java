import javax.swing.*;
import java.text.DecimalFormat;

public class Main{
    public static Matriz m;    

    static int incognitas(){
        int n = 0;
        while (n == 0){
        Object[] options = { "1", "2", "3", "4", "5",
                         "6", "7", "8", "9", "10"};

        n = JOptionPane.showOptionDialog(null, 
        "Cuantas incognitas tiene el sistema?", 
        "Sistema de Ecuaciones Lineales",
        JOptionPane.DEFAULT_OPTION, 
        JOptionPane.INFORMATION_MESSAGE,
        null, options, options[0]) + 1;
        if (n == 0)
            desea_salir();
        }
        return n;
    }

    static int metodos(){
        int metodo = 0;
        while (metodo == 0){
        Object[] moptions = { "Gauss-Jordan", "Pivote Maximo"};
        metodo = JOptionPane.showOptionDialog(null, 
        "¿Que metodo desea usar?",
        "Sistema de Ecuaciones Lineales",
        JOptionPane.DEFAULT_OPTION, 
        JOptionPane.INFORMATION_MESSAGE,
        null, moptions, moptions[0]) + 1;
        if (metodo == 0)
            desea_salir();
        }
        return metodo;
    }

    static int leer(int n){
        m = new Matriz(n);
        for(int i=0; i<n; i++)
            for(int j=0; j<n+1; j++){
                while (true){
                    try{
                        double value;
                        String message;
                        if (j != n)
                            message = (i+1)+"° Ecuacion: X"+(j+1)+" =";
                        else 
                            message = "Resultado de la "+(i+1)+"° ecuacion";
                        value = Double.parseDouble(JOptionPane.showInputDialog(message));
                        m.set(i,j,value);
                        break;
                    }
                    catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Introduzca un numero por favor", "Alerta", JOptionPane.ERROR_MESSAGE);     
                    }
                    catch (NullPointerException ex){
                        desea_salir();
                    }
                }
            }
        return 0;
    }

    static int desea_salir(){
       int op;
       while (true){
           op = JOptionPane.showConfirmDialog(null, "¿Desea salir del programa?", "Sistema de ecuaciones lineales",JOptionPane.YES_NO_OPTION);
           if( op != -1)
               break;
       }
       if(op == 0)
           System.exit(0);
       return 0;
    }
    
    static int imprimir(int n, int metodo){
        String vars = "";
        DecimalFormat formateador = new DecimalFormat("####.#####");
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if( j != 0 && m.get(i,j) >= 0)
                    vars += "+";
                vars += " "+formateador.format(m.get(i,j))+"*X"+(i+1)+" ";
            }
            vars += "= "+formateador.format(m.get(i, n))+"\n";
        }

        vars += "\nLos resultados son:\n\n";
        
        int temp;
        if (metodo == 1)
            temp = m.resolver_GaussJordan();
        else
            temp = m.resolver_PivoteMaximo();

        for(int i=0; i<n; i++)
            vars += "X"+(i+1)+" = "+m.get(i, n)+"\n";

        if(temp == 1){
            vars = "El sistema tiene ecuaciones repetidas D:";
            JOptionPane.showMessageDialog(null, vars, "Sistema de ecuaciones", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        else
            JOptionPane.showMessageDialog(null, vars, "Sistema de ecuaciones", JOptionPane.INFORMATION_MESSAGE);
        return 0;
    }

    public static void main(String args[]){
        int salir = 0, n, metodo;
        while(salir == 0){

            n = incognitas();
            JOptionPane.showMessageDialog(null, "Usted ha elegido "+n+" Ecuacione(s)", "Sistema de ecuaciones", JOptionPane.INFORMATION_MESSAGE);

            metodo = metodos();

            leer(n);
            imprimir(n, metodo);
            salir = desea_salir();
        }//while
    }
}
