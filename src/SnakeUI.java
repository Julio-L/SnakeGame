
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author JulioL
 */
public class SnakeUI {

    private Stage stage;
    private Scene scene;
    private Pane layout;

    private Button play;
    private Text title;
    private Text lost;

    private int height;
    private int width;

    private int ROWS = 20;
    private int COLS = 20;
    private int SIZE = 35;
    private int OFFSET = 2;

    private Cell grid[][];

    private SnakeModel snake;

    private int xFood;
    private int yFood;

    private boolean gameover;

    private Timeline timeL;
    private int speed = 200;
    private boolean keyPressed = false;

    public SnakeUI() {
        stage = new Stage();
        layout = new Pane();
        layout.setStyle("-fx-background-color: black");

        play = new Button("Play");
        play.setOnMousePressed(e -> start());
        title = new Text("Snake Game");
        lost = new Text("You Lost!");

        height = (ROWS + OFFSET * 2) * SIZE;
        width = (COLS + OFFSET * 2) * SIZE;

        scene = new Scene(layout, width, height);
        stage.setScene(scene);

        grid = new Cell[ROWS][COLS];

        snake = new SnakeModel(ROWS / 2, COLS / 2);

        this.setup();
    }

    private void start() {
        gameover = false;
        snake.setup();
        randomFood();
        resetGrid();

        lost.setOpacity(0);
        play.setDisable(true);

        timeL.play();
    }

    private void setup() {
        setupGrid();
        setupKeys();
        setupAuto();
        setupLayout();
    }

    private void setupLayout() {
        title.setFont(new Font("contrast", SIZE));
        title.setLayoutX(width / 2 - (title.getBoundsInLocal().getWidth() / 2));
        title.setLayoutY(SIZE);
        title.setFill(Color.WHITE);

        lost.setFont(new Font("contrast", SIZE * 2));
        lost.setLayoutX(width / 2 - (lost.getBoundsInLocal().getWidth() / 2));
        lost.setLayoutY(height / 2 - (lost.getBoundsInLocal().getHeight() / 2));
        lost.setFill(Color.WHITE);
        lost.setOpacity(0);

        play.setPrefWidth(100);
        play.setLayoutX(width / 2 - 50);
        play.setLayoutY(OFFSET * SIZE + ROWS * (SIZE + 1));

        layout.getChildren().addAll(title, lost, play);
    }

    private void setupGrid() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int off = SIZE * (OFFSET);
                grid[r][c] = new Cell(off + (c * SIZE), off + (r * SIZE), SIZE);
                layout.getChildren().add(grid[r][c]);
            }
        }
    }

    private void setupAuto() {
        timeL = new Timeline(new KeyFrame(Duration.millis(speed),
                (e) -> {
                    boolean valid = snake.move(grid);
                    if(!valid){
                        System.out.println(snake.getHead().getX() + ", " + snake.getHead().getY());
                        gameOver();
                    }else{
                         checkFood();
                    }
                }
        ));
        timeL.setCycleCount(Timeline.INDEFINITE);
    }

    private void setupKeys() {
        scene.setOnKeyPressed(e -> {
            KeyCode kc = e.getCode();
            if ((kc == KeyCode.W || kc == KeyCode.A
                    || kc == KeyCode.S || kc == KeyCode.D)
                    && !gameover) {
                timeL.pause();
                if (kc == KeyCode.W) {
                    snake.setDir(0, -1);
                } else if (kc == KeyCode.A) {
                    snake.setDir(-1, 0);
                } else if (kc == KeyCode.S) {
                    snake.setDir(0, 1);
                } else {
                    snake.setDir(1, 0);
                }
            }
            timeL.play();
        });
    }

    private void randomFood() {
        xFood = (int) (Math.random() * COLS);
        yFood = (int) (Math.random() * ROWS);
        grid[yFood][xFood].setCell(Type.FOOD);
    }

    public void checkFood() {
        Body head = snake.getBody().get(0);
        int x = head.getX();
        int y = head.getY();
        if (x == xFood && y == yFood) {
            randomFood();
            snake.grow();
        }
    }

    private void resetGrid() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                grid[r][c].setCell(Type.EMPTY);
            }
        }
        grid[yFood][xFood].setCell(Type.FOOD);
        grid[snake.getyStart()][snake.getxStart()].setCell(Type.FILLED);

    }

    public void gameOver() {
        gameover = true;
        timeL.stop();
        lost.setOpacity(1);
        play.setDisable(false);
    }

    public void show() {
        stage.show();
    }

}
