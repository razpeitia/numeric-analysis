/*
    Name: Ricardo Azpeitia Pimentel
    Student Number: 1344170
    Subject: Análisis Numérico
    Date: 10/09/2009
    University: Universidad Autonoma de Nuevo Léon
    Methods: Falsi Method and Secant Method
*/
import javax.swing.*;
class ProyectUtils{
    /*
        Common title for all MessagesDialogs
        Grade of the equation
        Constants of the equation
    */
    private String Title;
    private int Grade;
    private int[] Constants;

    ProyectUtils(String Title){
        this.Title = Title;
    }

    /* Code Only for this project */
    public void SetGrade(){
        String[] op = {"2", "3", "4", "5"};
        Grade = ReadOption(op, "Seleccione el grado de la ecuación")+1;
    }

    public int GetGrade(){
        return Grade;
    }

    public double[] ReadConstants(){
        /*
            Read an array of size grade + 1 of double numbers
        */
        double[] constants = new double[(Grade + 1)];
        String message;
        for(int i=0; i < (Grade + 1); i++){
            while(true){
                constants[i] = ReadDouble(
                    "Escriba el valor del coeficiente de X^"+(Grade-i),
                    "Entrada no valida por favor introduzca un numero");
                if(i == 0 && constants[i] == 0)
                    ShowError("El primer coeficiente debe ser "+
                            "diferente de cero para que la ecuación"+
                            " sea de grado "+Grade);
                else
                    break;
            }
        }
        return constants;
    }

    public double ReadError(String Message, String MessageError){
        /*
            Read a float number, show a MessageError if the
            number is not in the range [0, 1]
        */
        double error;
        while(true){
            error = ReadDouble(Message, MessageError);
            if(!(error >= 0 && error <= 1))
                ShowError("La tolerancia debe ser entre 0 y 1");
            else
                return error;
        }
    }

    public int ReadMax(String Message, String MessageError){
        /* 
           Read a inter, show a MessageError if the number
           is not upper to 0
        */
        int max;
        while(true){
            max = ReadInt(Message, MessageError);
            if(!(max > 0))
                ShowError("El numero máximo de iteraciones debe ser "+
                            "mayor a cero");
            else
                return max;
        }
    }
    /* Code Multi-Purpose */
    public void ToExit(){
        /* 
            If the user press 'yes' the program end
            if press 'no' the program continue
            For others cases the program continue
        */
       int op;
       while (true){
           op = JOptionPane.showConfirmDialog(null, 
                    "¿Desea salir del programa?", Title,
                    JOptionPane.YES_NO_OPTION);
           if( op != -1)
               break;
       }
       if(op == 0)
           System.exit(0);
    }

    public int ReadOption(String Options[], String Message){
        /*
            Choise a option from Options[]
        */
        int n = 0;
        while (n == 0){
            Object[] options = Options;
            n = JOptionPane.showOptionDialog(null,
            Message, Title,
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, options, options[0]) + 1;
            if (n == 0)
                ToExit();
        }
        return n;
    }

    public int ReadInt(String Message, String ErrorMessage){
        /*
            Read an integer number, display ErrorMessage if
            the string is not a integer number.
        */
        int value;
        String buffer;
        while(true){
            try{
                buffer = JOptionPane.showInputDialog(null, Message, 
                                Title, JOptionPane.PLAIN_MESSAGE);
                if(buffer == null)
                    throw new NullPointerException();
                value = Integer.parseInt(buffer);
                break;
            }
            catch (NumberFormatException ex){
                 JOptionPane.showMessageDialog(null,
                     ErrorMessage, Title, JOptionPane.ERROR_MESSAGE);
            }
            catch (NullPointerException ex){
                ToExit();
            }
        }
        return value;
    }

    public double ReadDouble(String Message, String ErrorMessage){
        /*
            Read a float number, display ErrorMessage if the
            string is not a float number.
        */
        double value;
        while(true){
            try{
                value = Double.parseDouble(
                    JOptionPane.showInputDialog(null, Message, 
                                Title, JOptionPane.PLAIN_MESSAGE));
                break;
            }
            catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null,
                                ErrorMessage,
                                Title, JOptionPane.ERROR_MESSAGE
                        );
            }
            catch (NullPointerException ex){
                        ToExit();
            }
        }
        return value;
    }
    
    public void ShowError(String ErrorMessage){
        /* Show a MessageBoxError */
        JOptionPane.showMessageDialog(null, ErrorMessage, Title, 
                        JOptionPane.ERROR_MESSAGE);
    }

    public void ShowMessage(String Message){
        /* Show a MessageBox */
        JOptionPane.showMessageDialog(null, Message, Title, 
                        JOptionPane.INFORMATION_MESSAGE);
    }
}

/*
    Code written by Ricardo Azpeitia Pimentel
*/
