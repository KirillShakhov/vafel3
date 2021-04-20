package lab;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MathModule {
    //функция для вычисления интеграла методом левых прямоугольников
    static double left_rectangle_integral(IFunc f, double a, double b, int n) {
        double step;
        double sum = 0;
        step = (b - a) / n;  //шаг
        for(double i=a;i<b; i+=step) {
            sum += f.solve(i);//суммируем значения функции в узловых точках
        }
        //приближенное значение интеграла равно сумме площадей прямоугольников
        return sum*step;//множим на величину шага и возвращаем в вызывающую функцию
    }
    static double right_rectangle_integral(IFunc f, double a, double b, int n) {
        double step;
        double sum = 0;
        step = (b - a) / n;  //шаг
        for(int i = 1; i <= n; i++)
        {
            sum += f.solve(a + i*step);
        }
        //приближенное значение интеграла равно сумме площадей прямоугольников
        return sum*step;//множим на величину шага и возвращаем в вызывающую функцию
    }
    static double mid_rectangle_integral(IFunc f, double a, double b, int n) {
        double sum = 0;
        double step = (b - a) / n;  //шаг
        for(int i = 0; i < n; i++) {
            sum += f.solve(a + step * (i + 0.5));//0.5 это тип 1/2
        }
        //приближенное значение интеграла равно сумме площадей прямоугольников
        return sum*step;//множим на величину шага и возвращаем в вызывающую функцию
    }

    static ArrayList<Separation> findSeparation(IFunc func, double a, double b){
        // TODO доделать для промежутка [0, 5], где точка 0 разрыв и для [0, 5], где точка 5 разрыв.
        ArrayList<Separation> array = new ArrayList<>();
        double eps = 0.00000001;
        int scale = 8; // Количество знаков после запятой.
        if(a<=b){
            double left_now = a;
            for(double i = a; i <= b; i+=0.0001){
                if (func.solve(round(i, scale)).isNaN() || func.solve(round(i, scale)).isInfinite()) {
                    array.add(new Separation(left_now, i-eps));
                    left_now = i+eps;
                }
            }
            array.add(new Separation(left_now, b));
        }
        else{
            double left_now = a;
            for(double i = a; i >= b; i-=0.0001){
                if (func.solve(round(i, scale)).isNaN() || func.solve(round(i, scale)).isInfinite()) {
                    array.add(new Separation(left_now, i+eps));
                    left_now = i-eps;
                }
                else if(round(i, scale)==b){
                    array.add(new Separation(left_now, i));
                }
            }
        }
        return array;
    }
    public static double round(double x, int scale){
        return round(x,scale,4);
    }

    public static double round(double x, int scale, int roundingMethod) {
        try {
            double rounded = (new BigDecimal(Double.toString(x))).setScale(scale, roundingMethod).doubleValue();
            return rounded == 0.0D ? 0.0D * x : rounded;
        } catch (NumberFormatException var6) {
            return Double.isInfinite(x) ? x : 0.0D / 0.0;
        }
    }


    public static ResultSet solve(IFunc f, double a, double b, double eps){
        ResultSet resultSet = new ResultSet();
        // left
        int iter = 0;
        int n = 1; //начальное число шагов
        double s, s1 = left_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = left_rectangle_integral(f, a, b, n);
            iter++;
        }
        while (Math.abs(s1 - s) > eps);  //сравнение приближений с заданной точностью
        resultSet.setLeft(s1);

        // left eps
        iter*=2;
        n = 1; //начальное число шагов
        s1 = left_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = left_rectangle_integral(f, a, b, n);
        }
        while (iter-->0);  //сравнение приближений с заданной точностью
        resultSet.setEpsLeft(Math.abs(resultSet.getLeft()-s1));

        // right
        iter = 0;
        n = 1; //начальное число шагов
        s1 = right_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = right_rectangle_integral(f, a, b, n);
            iter++;
        }
        while (Math.abs(s1 - s) > eps);  //сравнение приближений с заданной точностью
        resultSet.setRight(s1);

        // right eps
        iter*=2;
        n = 1; //начальное число шагов
        s1 = right_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = right_rectangle_integral(f, a, b, n);
        }
        while (iter-->0);  //сравнение приближений с заданной точностью
        resultSet.setEpsRight(Math.abs(resultSet.getRight()-s1));

        // mid
        iter = 0;
        n = 1; //начальное число шагов
        s1 = mid_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = mid_rectangle_integral(f, a, b, n);
            iter++;
        }
        while (Math.abs(s1 - s) > eps);  //сравнение приближений с заданной точностью
        resultSet.setMid(s1);

        // mid eps
        iter *= 2;
        n = 1; //начальное число шагов
        s1 = mid_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = mid_rectangle_integral(f, a, b, n);
        }
        while (iter-->0);  //сравнение приближений с заданной точностью
        resultSet.setEpsMid(Math.abs(resultSet.getMid()-s1));
        return resultSet;
    }
}
