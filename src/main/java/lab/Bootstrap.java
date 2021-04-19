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
        ResultSet result = MathModule.solve(f, a, b, eps);
        System.out.println("Результаты:");
        System.out.println("Левые прямоугольники: "+String.format("%.8f", result.getLeft())+" eps: "+String.format("%.8f", result.getEpsLeft()));
        System.out.println("Правые прямоугольники: "+String.format("%.8f", result.getRight())+" eps: "+String.format("%.8f", result.getEpsRight()));
        System.out.println("Средние прямоугольники: "+String.format("%.8f", result.getMid())+" eps: "+String.format("%.8f", result.getEpsMid()));
    }
}
