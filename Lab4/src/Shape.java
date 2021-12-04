public abstract class Shape {
    private int x;
    private int y;

    public Shape(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Shape(){
        x = 0;
        y = 0;
    }

    public abstract double getArea();
}
