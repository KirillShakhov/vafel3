package lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bootstrap {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Нахождение интегралов методом прямоугольников(левых, правых, средних)");
        Map<String, IFunc> funcs = new HashMap<>();
        // 1
        funcs.put("sin(x)/x", x -> Math.sin(x)/x);
        funcs.put("sin(x)/x-6", x -> Math.sin(x)/(x-6));
        // 2
        funcs.put("2x", x -> 2*x);

        //

        /*
        Вывод и обработка ввода. Не трогать.
        */
        int i = 1;
        ArrayList<String> keys = new ArrayList<>();
        for (Map.Entry<String, IFunc> entry : funcs.entrySet()) {
            System.out.println((i++) + ". " + entry.getKey());
            keys.add(entry.getKey());
        }
        String str = scanner.nextLine();
        try {
            IFunc func1 = funcs.get(keys.get(Integer.parseInt(str) - 1));
            solve(func1);
        } catch (Exception e) {
            System.out.println("Нет такого уравнения");
        }
    }

    static void solve(IFunc f){
        System.out.println("Введите a:");
        double a = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите b:");
        double b = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите погрешность:");
        double eps = Double.parseDouble(scanner.nextLine());
        ArrayList<Separation> array = MathModule.findSeparations(f, a, b);
        double sum_left = 0.0d;
        double sum_right = 0.0d;
        double sum_mid = 0.0d;
        for(Separation s : array){
            ResultSet result = MathModule.solve(f, s.getLeft(), s.getRight(), eps);
            sum_left += result.getLeft();
            sum_right += result.getRight();
            sum_mid += result.getMid();
            System.out.println("Результаты для отрезка от "+s.getLeft()+" до "+s.getRight()+":");
            System.out.println("Левые прямоугольники: "+String.format("%.8f", result.getLeft())+" eps: "+String.format("%.8f", result.getEpsLeft()));
            System.out.println("Правые прямоугольники: "+String.format("%.8f", result.getRight())+" eps: "+String.format("%.8f", result.getEpsRight()));
            System.out.println("Средние прямоугольники: "+String.format("%.8f", result.getMid())+" eps: "+String.format("%.8f", result.getEpsMid()));
        }
        System.out.println("Сумма для метода левых прямоугольников: "+MathModule.round(sum_left,8));
        System.out.println("Сумма для метода правых прямоугольников: "+MathModule.round(sum_right,8));
        System.out.println("Сумма для метода средних прямоугольников: "+MathModule.round(sum_mid,8));
    }
}
