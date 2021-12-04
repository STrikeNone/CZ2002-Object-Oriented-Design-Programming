import java.util.Scanner;

class Circle extends Shape{
    private int radius;
    public Circle(int radius, int x, int y){
        super(x, y);
        this.radius = radius;

    }

    public Circle(int radius){
        super();
        this.radius = radius;
    }

    public double getArea(){
        return Math.PI * Math.pow(radius, 2);
    }

    public double getCircumference(){
        return 2 * Math.PI * radius;
    }
}

class Triangle extends Shape{
    private int base;
    private int height;

    public Triangle(int base, int height){
        super();
        this.base = base;
        this.height = height;
    }

    public double getArea() {
        return 0.5 * base * height;
    }

    public int getBase(){
        return this.base;
    }

    public int getHeight(){
        return this.height;
    }
}

class Rectangle extends Shape{
    private int length;
    private int breadth;

    public Rectangle(int length, int breadth){
        this.length = length;
        this.breadth = breadth;
    }

    public double getArea(){
        return breadth * length;
    }

    public int getLength(){
        return this.length;
    }

    public int getBreadth(){
        return this.breadth;
    }
}


public class Shape2DApp {
    public static void main(String[] args){
        double area = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("How many rectangles to add: ");
        int count = sc.nextInt();
        for(int i=0; i<count; i++){
            System.out.println("Input length and breadth: ");
            int length = sc.nextInt();
            int breadth = sc.nextInt();
            Rectangle r = new Rectangle(length, breadth);
            area+=r.getArea();
        }
        System.out.println("How many triangle to add: ");
        count = sc.nextInt();
        for(int i=0; i<count; i++){
            System.out.println("Input base and height of triangle: ");
            int base = sc.nextInt();
            int height = sc.nextInt();
            Triangle t = new Triangle(base, height);
            area+=t.getArea();
        }
        System.out.println("How many Circles to add: ");
        count = sc.nextInt();
        for(int i=0; i<count; i++){
            System.out.println("Input radius: ");
            int radius = sc.nextInt();
            Circle c = new Circle(radius, 0, 0);
            area+=c.getArea();
        }
        System.out.println("Total area is: " + area);
    }
}
