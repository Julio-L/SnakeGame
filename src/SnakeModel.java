
import java.util.ArrayList;

/**
 *
 * @author JulioL
 */
public class SnakeModel {

    private ArrayList<Body> body;
    private int[] dir;

    private int xStart;
    private int yStart;
    private int xLast;
    private int yLast;

    public SnakeModel(int x, int y) {
        body = new ArrayList();
        dir = new int[4];
        xStart = x;
        yStart = y;

        setup();
    }

    public void setup() {
        body.clear();
        body.add(new Body(xStart, yStart));
        xLast = xStart;
        yLast = yStart;
        randomDir();
    }

    private void randomDir() {
        int r = (int) ((Math.random() * 100)) % 4;
        if (r == 0) {
            dir[0] = 0;
            dir[1] = -1;
        } else if (r == 1) {
            dir[0] = 0;
            dir[1] = 1;
        } else if (r == 2) {
            dir[0] = 1;
            dir[1] = 0;
        } else {
            dir[0] = -1;
            dir[1] = 0;
        }
        dir[2] = -2;
        dir[3] = -2;
    }

    public void grow() {
        body.add(new Body(xLast, yLast));
    }

    public void setDir(int xDir, int yDir) {
        if ((body.size() > 1) && ((dir[0] + xDir == 0)
                || (dir[1] + yDir == 0))) {
            return;
        }
        dir[2] = xDir;
        dir[3] = yDir;
    }

    public boolean validMove(Cell[][] grid) {
        Body head = body.get(0);
        int newX = head.getX() + dir[0];
        int newY = head.getY() + dir[1];
        if ((newX < 0 || newX >= grid[0].length) || (newY < 0 || newY >= grid.length)) {
            return false;
        }
        return true;
    }

    public boolean move(Cell[][] grid) {
        //check if valid move
        if (dir[2] != -2 && dir[3] != -2) {
            dir[0] = dir[2];
            dir[1] = dir[3];
            dir[2] = -2;
            dir[3] = -2;
        }
        boolean valid = validMove(grid);

        if (!valid) {
            return false;
        }
        //clear tail
        Body tail = body.get(body.size() - 1);
        grid[tail.getY()][tail.getX()].setCell(Type.EMPTY);

        //make sure snake doesnt hit itself
        for (int i = body.size() - 1; i >= 0; i--) {
            Body child = body.get(i);
            if (i == body.size() - 1) {
                xLast = child.getX();
                yLast = child.getY();
            }
            if (i == 0) {
                child.incXY(dir[0], dir[1]);
                //insert new head position in grid
                grid[child.getY()][child.getX()].setCell(Type.FILLED);
            } else {
                Body parent = body.get(i - 1);
                child.setXY(parent.getX(), parent.getY());
            }
        }
        return valid;
    }

    public boolean crossed() {
        Body head = body.get(0);
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Body> getBody() {
        return body;
    }

    public Body getHead() {
        return body.get(0);
    }

    public int getxStart() {
        return xStart;
    }

    public int getyStart() {
        return yStart;
    }

    public int getXDir() {
        return dir[0];
    }

    public int getYDir() {
        return dir[1];
    }

}
