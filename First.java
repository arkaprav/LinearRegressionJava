import java.util.*;
import java.io.*;

class LR{
    private ArrayList<ArrayList> X = new ArrayList<ArrayList>();
    private ArrayList<Double> y = new ArrayList<Double>();
    private int epochs = 2147483647;
    private double l = 0.0000001;
    private double mp;
    private double bp;
    public void fit(ArrayList<ArrayList> args, ArrayList<Double> target, int e, double k){
        epochs = e;
        l = k;
        X = args;
        y = target;
        double[] res = process();
        for(int i = 0; i < 3;i++){
            System.out.print(res[i]+" ");
        }
    }
    public void fit(ArrayList<ArrayList> args, ArrayList<Double> target, int e){
        epochs = e;
        X = args;
        y = target;
        double[] res = process();
        for(int i = 0; i < 3;i++){
            System.out.print(res[i]+" ");
        }
    }
    public void fit(ArrayList<ArrayList> args, ArrayList<Double> target){
        X = args;
        y = target;
        double[] res = process();
        for(int i = 0; i < 3;i++){
            System.out.print(res[i]+" ");
        }
    }
    public double[] process(){
        double[] res = new double[3];
        double m = 1.0;
        double b = 0.0;
        double minloss = Integer.MAX_VALUE;
        for(int i = 0; i < epochs; i++){
            double loss = mse(m, b);
            if(i%1000000000==0){
                System.out.println("epochs completed: "+i+" --- Loss: "+loss);
            }
            if(minloss > loss){
                minloss = loss;
                mp = m;
                bp = b;
            }
            double[] luck = gradient_descent(m, b);
            m = luck[0];
            b = luck[1];
        }
        res[0] = mp;
        res[1] = bp;
        res[2] = minloss;
        return res;
    }
    public double mse(double m,double b){
        double loss = 0;
        for(int i = 0; i < y.size(); i++){
            double s = ((double)y.get(i) - (m * (double)X.get(0).get(i)+b));
            loss += s*s;
        }
        double ans = loss/(double) y.size();
        return ans;
    }
    public double[] gradient_descent(double m, double b){
        double[] gd = new double[2];
        int n = y.size();
        double mg = 0;
        double bg = 0;
        for(int i = 0; i < n; i++){
            double z = ((double)y.get(i) - (m * (double)X.get(0).get(i)+b));
            mg += -(2.0/(double)n)*(double)X.get(0).get(i)*z;
            bg += -(2.0/(double)n)*z;
        }
        double mp = m - l*mg;
        double bp = b - l*bg;
        gd[0] = mp;
        gd[1] = bp;
        return gd;
    }
    public double[] predict(ArrayList<ArrayList> A){
        double[] y_pred = new double[A.size()];
        for(int i = 0; i < A.size(); i++){
            y_pred[i] = mp * (double)A.get(0).get(i) + bp;
        }
        return y_pred;
    }
}

public class First{
    public static void main(String[] args) {
        LR p = new LR();
        ArrayList<ArrayList> x = new ArrayList<ArrayList>();
        ArrayList<Double> y = new ArrayList<Double>();
        String sample = ",";
        String mystring;
        try
        {
            BufferedReader brdrd = new BufferedReader(new FileReader("C://data.csv"));
            ArrayList<Double> e = new ArrayList<Double>();
            int i = 0;
            while ((mystring = brdrd.readLine()) != null)  //Reads a line of text
            {
                if(i != 0){
                    String[] student = mystring.split(sample);//utilized to split the string
                    e.add(Double.parseDouble(student[0]));
                    y.add(Double.parseDouble(student[1]));
                }
                i++;
            }
            x.add(e);
        }
        catch (IOException e)//catches exception in the try block
        {
            e.printStackTrace();//Prints this throwable and its backtrace
        }
        p.fit(x,y);
        double[] a1 = p.predict(x);
        System.out.println(a1[0]);
    }
}