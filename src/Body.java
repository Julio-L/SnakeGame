
/**
 *
 * @author JulioL
 */
public class Body {
    private int x;
    private int y;
    private Type type;
    
    
    public Body(int x, int y){
        this.x = x;
        this.y = y;
        type = Type.FILLED;
    }
    
    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void incXY(int xDir, int yDir){
        this.x += xDir;
        this.y += yDir;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getType() {
        return type;
    }
    
    public boolean equals(Body other){
        return this.x == other.x && this.y == other.y;
    }
    
    
}
