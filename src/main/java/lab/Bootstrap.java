package lab;

import java.util.ArrayList;

public class Bootstrap {
    public static void main(String[] args) {
        System.out.println("Нахождение интегралов методом прямоугольников(левых, правых, средних)");
        ArrayList<IFunc> funcs = new ArrayList<>();
        funcs.add(x -> 2*x+Math.sin(x));

        for(IFunc func : funcs){
            MathModule.solve(func, 1, 2, 0.001);
        }
    }
}
