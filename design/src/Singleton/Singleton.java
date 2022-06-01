package Singleton;

public class Singleton {

    private Singleton() {
        System.out.println("Cons");
    }

    static class SingletonFactory {
        private static final Singleton singleton = new Singleton();
    }

    public Singleton getInstance() {
        return SingletonFactory.singleton;
    }

}


class Square
{
    public int side;

    public Square(int side)
    {
        this.side = side;
    }
}

interface Rectangle
{
    int getWidth();
    int getHeight();

    default int getArea()
    {
        return getWidth() * getHeight();
    }
}

class SquareToRectangleAdapter implements Rectangle
{
    Square square;

    public SquareToRectangleAdapter(Square square)
    {
        this.square = square;
    }

    @Override
    public int getWidth() {
        return square.side;
    }

    @Override
    public int getHeight() {
        return square.side;
    }
}