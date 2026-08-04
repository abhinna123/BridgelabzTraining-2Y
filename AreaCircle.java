import java.util.Scanner;

public class AreaCircle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double radius = sc.nextDouble();

        double area = Math.PI * radius * radius;

        System.out.println("Area = " + area);
    }
}