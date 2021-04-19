package lab;

import java.util.ArrayList;
import java.util.Scanner;

public class Bootstrap {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Нахождение интегралов методом прямоугольников(левых, правых, средних)");
        ArrayList<IFunc> funcs = new ArrayList<>();
        funcs.add(x -> Math.sin(x)/x);

//        for(IFunc func : funcs){
//            MathModule.solve(func, 1, 2, 0.001);
//        }
        IFunc f = funcs.get(0);
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
