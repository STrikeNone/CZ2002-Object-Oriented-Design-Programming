import java.util.Scanner;

class Sphere extends Circle{

    public Sphere(int x, int y, int r){
        super(r, x, y);
    }

    public double getSurfaceArea(){
        return 4 * this.getArea();
    }

}

class Pyramid extends Triangle{
    private int width;

    public Pyramid(int base, int height, int width){
        super(base, height);
        this.width = width;
    }

    public double getSurfaceArea(){
        return this.getArea() * 4 + width * this.getBase();
    }
}

class Cuboid extends Rectangle{
    public int height;

    public Cuboid(int h, int l, int b){
        super(l, b);
        height = h;
    }

    public double getSurfaceArea(){
        return this.getArea() * 2 + this.getLength() * height * 2 + this.getBreadth() * height * 2;
    }
}

class Cylinder extends Cuboid{
    private Circle c;

    public Cylinder(int h, int r){
        super(h, h, h);
        c = new Circle(r);
    }

    public double getSurfaceArea(){
        return 2 * c.getArea() + c.getCircumference() * this.height;
    }
}

class Cone extends Pyramid{
    Circle c;
    public Cone(int b, int h, int w){
        super(b, h, w);
        c = new Circle(b/2);
    }

    public double getSurfaceArea(){
        int radius = this.getBase()/2;
        double l = Math.sqrt(Math.pow(radius, 2) + Math.pow(this.getHeight(), 2));
        return  c.getArea() + Math.PI * l * radius;
    }
}

public class Shape3D {
    public static void main(String[] args) {
        double area = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("How many Sphere to add: ");
        int count = sc.nextInt();
        for(int i=0; i<count; i++){
            System.out.println("Input radius: ");
            int radius = sc.nextInt();
            Sphere s = new Sphere(0, 0, radius);
            area+=s.getSurfaceArea();
        }
        System.out.println(area);
        System.out.println("How many Pyramid to add: ");
        count = sc.nextInt();
        for(int i=0; i<count; i++){
            System.out.println("Input length, slant height and width of Pyramid: ");
            int base = sc.nextInt();
            int height = sc.nextInt();
            int width = sc.nextInt();
            Pyramid p = new Pyramid(base, height, width);
            area+=p.getSurfaceArea();
        }
        System.out.println(area);
        System.out.println("How many Cuboid to add: ");
        count = sc.nextInt();
        for(int i=0; i<count; i++){
            System.out.println("Input height, length, breadth: ");
            int height = sc.nextInt();
            int length = sc.nextInt();
            int breadth = sc.nextInt();
            Cuboid c = new Cuboid(height, length, breadth);
            area+=c.getSurfaceArea();
        }
        System.out.println("Total area is: " + area);
    }

}
