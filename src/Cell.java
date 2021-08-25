
import javafx.scene.shape.Rectangle;


/**
 *
 * @author JulioL
 */
public class Cell extends Rectangle {
    private int x;
    private int y;
    private int size;
    private boolean on;
    
    private Type type;

    String[] styles = {
        "-fx-fill: black; -fx-stroke: blue",
        "-fx-fill: blue; -fx-stroke: white;",
        "-fx-fill: red; -fx-stroke: blue"
    };
    
    public Cell(int x, int y, int size){
        super(x, y, size, size);
        this.x = x;
        this.y = y;
        this.size = size;
        this.type = Type.EMPTY;
        this.setStyle(styles[0]);
    }
    
    
    public void setCell(Type type){
        this.type = type;
        this.setStyle(styles[type.ordinal()]);
    }

    
}
