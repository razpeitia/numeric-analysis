import java.util.Hashtable;
import java.util.Iterator;
import javax.swing.*;
import java.math.BigDecimal;


class LibUtils{
    private String Title;
    private String ExitMessage;

    LibUtils(String Title, String ExitMessage){
        /* 
            Set the same title for every dialog-box
            and set the exit question.
        */
        this.Title = Title;
        this.ExitMessage = ExitMessage;
        
    }

    public void ToExit(){
        /* 
            If the user press 'yes' the program end
            else the program continue
        */
       int op;
       op = ConfirmDialog(ExitMessage);
       if(op == 0)
           System.exit(0);
    }

    public int ReadOption(String Options[], String Message){
        /*
            Return the option chose
            Example:
            [1,    2,   3]
            ['a', 'b', 'c']
            Choose 'a' return 1, 'b' return 2
        */
        int n;
        while (true){
            n = OptionDialog(Options, Message) + 1;
            if (n == 0)
                ToExit();
            else if(n > 0)
                break;
        }
        return n;
    }

    public int ReadInt(String Message, String ErrorMessage){
        /*
            Read an integer number
            Display ErrorMessage if the input is not a integer number.
        */
        int value;
        String buffer;
        while(true){
            try{
                buffer = InputDialog(Message);
                if(buffer == null)
                    throw new NullPointerException();
                value = Integer.parseInt(buffer);
                break;
            }
            catch (NumberFormatException ex){
                ShowError(ErrorMessage);
            }
            catch (NullPointerException ex){
                ToExit();
            }
        }
        return value;
    }

    public BigDecimal ReadBigDecimal(String Message, String ErrorMessage){
        /*
            Read a BigDecimal
            Display ErrorMessage if the format is incorrect
            Go ToExit if the user pressed ESC
        */
        String buffer;
        BigDecimal BigBuffer;
        while(true){
            try{
                buffer = InputDialog(Message);
                BigBuffer = new BigDecimal(buffer);
                break;
            }
            catch(NumberFormatException ex){
                ShowError(ErrorMessage);
            }
            catch(NullPointerException ex){
                ToExit();
            }
        }
        return BigBuffer;
    }

    public Hashtable<BigDecimal, BigDecimal> ReadData(String Message1, 
                                                      String Message2, 
                                                      String MessageError, 
                                                      String ContinueQuestion,
                                                      String AtLeast,
                                                      int grade){
        /* Read at least (grade+1)*2 numbers and return
           a Hashtable with those values.
        */
        Hashtable <BigDecimal, BigDecimal> data 
                = new Hashtable<BigDecimal, BigDecimal>();
        BigDecimal buffer1, buffer2;
        int exit = 1;
        while(exit == 1){
            buffer1 = ReadBigDecimal(Message1, MessageError);
            buffer2 = ReadBigDecimal(Message2, MessageError);
            data.put(buffer1, buffer2);
            exit = ReadYesNo(ContinueQuestion);
            if(exit != 1 && data.size() < grade + 1){
                exit = 1;
                ShowError(AtLeast);
            }
                
        }
        return data;
    }
    
    public double ReadDouble(String Message, String ErrorMessage){
        /*
            Read a float number
            Display ErrorMessage if the string is not a float number.
        */
        double value;
        while(true){
            try{
                value = Double.parseDouble(InputDialog(Message));
                break;
            }
            catch (NumberFormatException ex){
                        ShowError(ErrorMessage);
            }
            catch (NullPointerException ex){
                        ToExit();
            }
        }
        return value;
    }

    public int ReadYesNo(String Message){
        /*
            Read Yes or No
            Another case go ToExit
        */
        int buffer = -1;
        while (!(buffer == 0 || buffer == 1)){
            buffer = ConfirmDialog(Message);
            switch(buffer){
                case 0:
                    buffer = 1;
                    break;
                case 1:
                    buffer = 0;
                    break;
                default:
                    ToExit();
                    break;
            }

        }
        return buffer;
    }

    /* -------------------Message Alias----------------------- */
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

    public String InputDialog(String Message){
        /* Show a InputDialog */
        String buffer;
        buffer = JOptionPane.showInputDialog(null, Message, Title,
                                    JOptionPane.PLAIN_MESSAGE);
        return buffer;
    }

    public int ConfirmDialog(String Message){
        /* Show a Yes-No ConfirmDialog */
        int buffer;
        buffer = JOptionPane.showConfirmDialog(null, Message, Title,
                    JOptionPane.YES_NO_OPTION);
        return buffer;
    }

    public int OptionDialog(String[] Options, String Message){
        /* Show a OptionDialog */
        int buffer;
        Object[] options = Options;
        buffer = JOptionPane.showOptionDialog(null, Message, Title,
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, options, options[0]);
        return buffer;
    }
}
