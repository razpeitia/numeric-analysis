import java.util.Hashtable;
import java.util.Enumeration;
import java.math.BigDecimal;

class Engine{
    
    public Hashtable<BigDecimal, BigDecimal> data;
    private BigDecimal value;
    private BigDecimal S;
    private BigDecimal df[][];
    
    Engine(Hashtable<BigDecimal, BigDecimal> data, BigDecimal value){
        this.data = data;
        this.value = value;
        setS();
        setdf();
    }

    public BigDecimal getValue(){
        return value;
    }

    public void setValue(BigDecimal Value){
        value = Value;
        setS();
    }

    private void setS(){
        BigDecimal x0, x1, s;
        Enumeration <BigDecimal> x = data.keys();
        x0 = x.nextElement();
        x1 = x.nextElement();
        s = value.subtract(x0).divide(x1.subtract(x0), 1000, 
                                      BigDecimal.ROUND_HALF_EVEN);
        S = s;
    }

    private void setdf(){
        Enumeration <BigDecimal> fx = data.elements();

        int size = data.size();
        df = new BigDecimal[size][size];
        for(int i = 0; i < size; i++)
            df[i][0] = fx.nextElement();

        for(int i = 1; i < size; i++)
            for(int j = 0; j < (size - i); j++)
                df[j][i] = df[ (j + 1) ][ (i-1) ].subtract( df[ j ][ (i-1) ] );
    }

    private BigDecimal combination(int n){
        BigDecimal c1 = new BigDecimal(1);
        BigDecimal c2 = new BigDecimal(1);
        if(n <= 0)
            return c1;
        for(int i = 1; i <= n; i++){
            c1 = c1.multiply(S.subtract(new BigDecimal(i - 1)));
            c2 = c2.multiply(new BigDecimal(i));
        }
        return c1.divide(c2, 1000, BigDecimal.ROUND_HALF_EVEN);
    }

    public String getResult(){
        String Result = "";
        BigDecimal fx = new BigDecimal(0);
        int size = df.length - 1;

        for(int i = 0; i < df[0].length; i++)
            fx = fx.add(combination(i).multiply(df[0][i]));
        fx = fx.setScale(7, BigDecimal.ROUND_HALF_EVEN);
        Result += fx;
        return Result;
    }
}
