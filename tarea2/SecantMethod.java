/*
    Student Name: Ricardo Azpeitia Pimentel
    Student Number: 1344170
    University: Universidad Autonoma de Nuevo Leon
    Faculty: Facultad de Ciencias Fisico-Matematicas
    Date: 5/09/2009
    Description: Secant Method is a root-finding algorithm
*/

class SecantMethod{
    private int grade;
    private double constants[];
    private long loops;
    private int fail;

    SecantMethod(int grade, double[] constants){
        /*
            Example:
            Grade 3°: a*x^3 + b*x^2 + c*x + d = 0
            Constants: a, b, c, d ; 4 constants
            ConstantsArray = {a, b, c, d}
        */
        this.grade = grade;
        this.constants = constants;
    }

    public double GetLoops(){
        return loops;
    }

    public int GetFail(){
        return fail;
    }

    private double Funct(double x){
        /*
            Evaluate the next function:
            k1*x^grade + k2*x^(grade - 1) + ... + kn*x^0
            And return the result
        */
        double result = 0;
        for(int i = 0; i < grade + 1; i++)
            result += constants[i] * Math.pow(x, grade - i);
        return result;
    }

    public double Solve(double x1, double x2, double error, int max){
        double Fx1 = Funct(x1), Fx2 = Funct(x2);
        double x3, Fx3;
        double root = 0;
        long i = 1;
        fail = 0;
        loops = 0;

        while (i <= max){
            x3  = x1 - Fx1 * (x1 - x2) / (Fx1 - Fx2);
            Fx3 = Funct(x3);
            if (Math.abs(Fx3) <= error){
                root = x3;
                loops = i;
                return root;
            }
            else{
                x2  = x1;
                Fx2 = Fx1;
                x1  = x3;
                Fx1 = Fx3;
            }
            i++;
        }
    
        fail = 1;
        return root;
    }
}
/*
    Code written by Ricardo Azpeitia Pimentel
*/
