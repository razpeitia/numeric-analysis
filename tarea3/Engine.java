import java.util.Hashtable;
import java.util.Enumeration;
import java.math.BigDecimal;

class Engine{

    public int[] equation;
    public Hashtable<BigDecimal, BigDecimal> data;
    public int scale;
    public String Result = "";

    Engine(int[] equation, Hashtable<BigDecimal, BigDecimal> data, int scale){
        /*
            scale     -> integer with the final precision.
            data      -> Hashtable of BigDecimal
            equation  -> [1, 0, 0, 1] == a + 0x + 0x**2 + bx**3
        */
        this.scale = scale;
        this.data = data;
        this.equation = equation;
    }
    
    public LibMatrix getConstants(){
        /*
            Return a LibMatrix object with the values of the constans
        */
        LibMatrix X;
        LibMatrix Xt;
        LibMatrix Y;
        LibMatrix B;
        int cols = Count(1), rows = data.size();
        int[] grades = Grades();

        X = new LibMatrix(rows, cols);
        BigDecimal temp;
        Enumeration <BigDecimal> e = data.keys();
        for(int i = 0; i < rows; i++){
                temp = e.nextElement();
            for(int j = 0 ; j < cols; j++){
                X.set(i, j, temp.pow(grades[j]));
            }
        }
        Xt = X.Trans();
        Y = getY();
        B = Xt.Mult(X).Invert().Mult(Xt.Mult(Y));
        String pow;
        for(int i = 0; i < grades.length; i++)
            if(i == 0)
                Result += "Y = "+B.get(i, 0).setScale(scale, 
                            BigDecimal.ROUND_HALF_EVEN)+" ";
            else{
                if(i == 1)
                    pow = "";
                else
                    pow = "**" + i;

                if(B.get(i, 0).compareTo(new BigDecimal(0)) >= 0)
                    Result += "+ "+B.get(i, 0).setScale(scale,
                            BigDecimal.ROUND_HALF_EVEN)+"X"+pow+" ";
                else
                    Result += "- "+B.get(i, 0).abs().setScale(scale,
                            BigDecimal.ROUND_HALF_EVEN)+"X"+pow+" ";
            }
        Result += "\n";
        BigDecimal R2 = GetR2(B, grades);
        Result += "R**2 = "+R2.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
        return B;
    }
    
    public BigDecimal GetR2(LibMatrix B, int[] grades){
        /*
            Get the value of R2, given the constans and the grades.
        */
        BigDecimal R2 = new BigDecimal(1);
        BigDecimal yp = new BigDecimal(0);
        BigDecimal err2 = new BigDecimal(0);
        BigDecimal yerr2 = new BigDecimal(0);
        
        Enumeration <BigDecimal>e = data.elements();
        for(int i = 0; i < grades.length; i++)
            yp = yp.add(e.nextElement());

        Enumeration <BigDecimal>keys = data.keys();
        Enumeration <BigDecimal>elm = data.elements();
        for(int i = 0; i < data.size(); i++){
            BigDecimal yi = elm.nextElement();
            BigDecimal xi = keys.nextElement();
            err2 = err2.add(evaluate(xi, B, grades).subtract(yi).pow(2));
            yerr2 = yerr2.add(yi.subtract(yp).pow(2));
        }
        R2 = R2.subtract(err2.divide(yerr2, scale, BigDecimal.ROUND_HALF_EVEN));
        return R2;
    }

    public BigDecimal evaluate(BigDecimal val, LibMatrix B, int[] grades){
        /* Return the value when is evaluate in the ecuation */
        BigDecimal value = new BigDecimal(0);
        for(int i = 0; i < grades.length; i++)
            value = value.add(B.get(i, 0).multiply(val.pow(grades[i])));
        return value;
    }

    public int Count(int value){
        /* Return the number of occurrences of value in the array equation */
        int count = 0;
        for(int i = 0; i < equation.length; i++)

            if(equation[i] == value)
                count += 1;
        return count;
    }

    public int[] Grades(){
        /* 
            Return an array with the grades
            Ex -> [1, 0, 1] == a + 0x + bx**2
                  [0, 2]
        */
        int cols = Count(1);
        int[] grades = new int[cols];
        int j = 0;
        for(int i = 0; i < equation.length; i++)
            if(equation[i] == 1){
                grades[j] = i;
                j++;
            }
        return grades;
    }

    public LibMatrix getY(){
        /* Return a LibMatrix object with f(x) values */
        int size = data.size();
        LibMatrix y = new LibMatrix(size, 1);
        int i = 0;
        for(Enumeration <BigDecimal> e = data.elements(); 
                                    e.hasMoreElements(); i++)
            y.set(i, 0, e.nextElement());
        return y;
    }
}
