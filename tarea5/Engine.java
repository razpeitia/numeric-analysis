import java.math.BigDecimal;

class Engine{
    private BigDecimal[] eqn;
    private BigDecimal lrange;
    private BigDecimal rrange;
    private int n;

    Engine(BigDecimal[] eqn, BigDecimal lrange, BigDecimal rrange, int n){
        this.eqn = eqn; //Equation array
        this.lrange = lrange; //Left Limit
        this.rrange = rrange; //Rigth Limit
        this.n = n;
    }
    
    private BigDecimal Evaluate(BigDecimal x){
        /* Evaluate the equation in the value given */
        BigDecimal buffer = new BigDecimal(0);
        for(int i = 0; i < eqn.length; i++)
            buffer = buffer.add( eqn[i].multiply(x.pow(i)) );
        return buffer;
    }

    private BigDecimal Integrate(){
        /* Return the value of the integral with the trapezoidal rule 
           The limits and the number of subareas are given in the
           constructor.
        */
        BigDecimal buffer = new BigDecimal(0);
        BigDecimal h = rrange.subtract(lrange).divide(
                new BigDecimal(n), 1000, BigDecimal.ROUND_HALF_EVEN);
        buffer = buffer.add(Evaluate(lrange));
        for(int i = 1; i <= n - 1; i++)
            buffer = buffer.add(
                Evaluate(h.multiply(new BigDecimal(i)).add(lrange)).multiply(
                new BigDecimal(2)));
        buffer = buffer.add(Evaluate(rrange));
        buffer = buffer.multiply(h).divide(new BigDecimal(2));
        return buffer;
    }

    private BigDecimal divide(BigDecimal a, BigDecimal b){
        return a.divide(b, 1000, BigDecimal.ROUND_HALF_EVEN);
    }

    public BigDecimal Romber(int precision){
        /*
            Return the result, of the Romber Method:
            I = (4*Ih1 - 3Ih2)/3
        */
        BigDecimal result = new BigDecimal(0);
        BigDecimal I, I2, K1, K2;
        I = Integrate();
        n *= 2;
        I2 = Integrate();
        n /= 2;
        K1 = divide(new BigDecimal(4), new BigDecimal(3));
        K2 = divide(new BigDecimal(-1), new BigDecimal(3));
        result = K1.multiply(I).add(K2.multiply(I2));
        result = result.setScale(precision, BigDecimal.ROUND_HALF_EVEN);
        return result;
    }
}
