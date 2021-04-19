package lab;

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



    public static ResultSet solve(IFunc f, double a, double b, double eps){
        ResultSet resultSet = new ResultSet();
        // left
        int n = 1; //начальное число шагов
        double s, s1 = left_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = left_rectangle_integral(f, a, b, n);
        }
        while (Math.abs(s1 - s) > eps);  //сравнение приближений с заданной точностью
        resultSet.setLeft(s1);

        // left eps
        n = 1; //начальное число шагов
        s1 = left_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = left_rectangle_integral(f, a, b, n);
        }
        while (Math.abs(s1 - s) > Math.pow(eps, 2));  //сравнение приближений с заданной точностью
        resultSet.setEpsLeft(Math.abs(resultSet.getLeft()-s1));

        // right
        n = 1; //начальное число шагов
        s1 = right_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = right_rectangle_integral(f, a, b, n);
        }
        while (Math.abs(s1 - s) > eps);  //сравнение приближений с заданной точностью
        resultSet.setRight(s1);

        // right eps
        n = 1; //начальное число шагов
        s1 = right_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = right_rectangle_integral(f, a, b, n);
        }
        while (Math.abs(s1 - s) > Math.pow(eps, 2));  //сравнение приближений с заданной точностью
        resultSet.setEpsRight(Math.abs(resultSet.getRight()-s1));

        // mid
        n = 1; //начальное число шагов
        s1 = mid_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = mid_rectangle_integral(f, a, b, n);
        }
        while (Math.abs(s1 - s) > eps);  //сравнение приближений с заданной точностью
        resultSet.setMid(s1);

        // mid eps
        n = 1; //начальное число шагов
        s1 = mid_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = mid_rectangle_integral(f, a, b, n);
        }
        while (Math.abs(s1 - s) > Math.pow(eps, 2));  //сравнение приближений с заданной точностью
        resultSet.setEpsMid(Math.abs(resultSet.getMid()-s1));
        return resultSet;
    }
}
