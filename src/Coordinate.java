public class Coordinate {
    Coordinate(int x,int y){
        this.x = x;
        this.y = y;
    }

    Coordinate(Coordinate toCopy){
        this.x = toCopy.x;
        this.y = toCopy.y;
    }


    public int x;
    public int y;
}
