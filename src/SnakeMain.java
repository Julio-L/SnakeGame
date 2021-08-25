
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author JulioL
 */
public class SnakeMain extends Application{
    
    static SnakeUI snakeGame = new SnakeUI();

    @Override
    public void start(Stage stage) throws Exception {
        snakeGame.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
    
}
