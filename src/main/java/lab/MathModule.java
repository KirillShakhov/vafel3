package lab;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MathModule {
    //функция для вычисления интеграла методом левых прямоугольников
    static double left_rectangle_integral(IFunc f, double a, double b, int n) {
        boolean isNegative = false;
        if(a>b){
            double t = a;
            a = b;
            b = t;
            isNegative = true;
        }
        double step;
        double sum = 0;
        step = (b - a) / n;  //шаг
        for(double i=a;i<b; i+=step) {
            sum += f.solve(i);//суммируем значения функции в узловых точках
        }
        //приближенное значение интеграла равно сумме площадей прямоугольников
        double result = sum*step;//множим на величину шага и возвращаем в вызывающую функцию
        if(isNegative){
            return -1*result;
        }
        else {
            return result;
        }
    }
    static double right_rectangle_integral(IFunc f, double a, double b, int n) {
        boolean isNegative = false;
        if(a>b){
            double t = a;
            a = b;
            b = t;
            isNegative = true;
        }
        double step;
        double sum = 0;
        step = (b - a) / n;  //шаг
        for(int i = 1; i <= n; i++)
        {
            sum += f.solve(a + i*step);
        }
        //приближенное значение интеграла равно сумме площадей прямоугольников
        double result = sum*step;//множим на величину шага и возвращаем в вызывающую функцию
        if(isNegative){
            return -1*result;
        }
        else {
            return result;
        }    }
    static double mid_rectangle_integral(IFunc f, double a, double b, int n) {
        boolean isNegative = false;
        if(a>b){
            double t = a;
            a = b;
            b = t;
            isNegative = true;
        }
        double sum = 0;
        double step = (b - a) / n;  //шаг
        for(int i = 0; i < n; i++) {
            sum += f.solve(a + step * (i + 0.5));//0.5 это тип 1/2
        }
        //приближенное значение интеграла равно сумме площадей прямоугольников
        double result = sum*step;//множим на величину шага и возвращаем в вызывающую функцию
        if(isNegative){
            return -1*result;
        }
        else {
            return result;
        }
    }

    static ArrayList<Separation> findSeparations(IFunc func, double a, double b){
        ArrayList<Separation> array = new ArrayList<>();
        double eps = 0.00000001;
        int scale = 8; // Количество знаков после запятой.
        // Проверка первого элемента
        Double first = func.solve(round(a, scale));
        double left_now = first.isNaN() || first.isInfinite() ? a + eps : a;
        // Проверка элементов (a, b)
        if(a<=b){
            for(double i = a+0.0001; i < b; i+=0.0001){
                if (func.solve(round(i, scale)).isNaN() || func.solve(round(i, scale)).isInfinite()) {
                    array.add(new Separation(left_now, i-eps));
                    left_now = i+eps;
                }
            }
        }
        else{
            for(double i = a; i > b; i-=0.0001){
                if (func.solve(round(i, scale)).isNaN() || func.solve(round(i, scale)).isInfinite()) {
                    array.add(new Separation(left_now, i+eps));
                    left_now = i-eps;
                }
            }
        }
        // Проверка последнего элемента
        Double end = func.solve(round(b, scale));
        end = end.isNaN() || end.isInfinite() ? b - eps : b;
        array.add(new Separation(left_now, end));
        return array;
    }
    public static Double round(double x, int scale) {
        try {
            double rounded = (new BigDecimal(Double.toString(x))).setScale(scale, 4).doubleValue();
            return rounded == 0.0D ? 0.0D * x : rounded;
        } catch (NumberFormatException var6) {
            return Double.isInfinite(x) ? x : Double.NaN;
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
