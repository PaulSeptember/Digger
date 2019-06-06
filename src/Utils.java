public class Utils {

    public static Coordinate add(Coordinate c,int bias,Direction direction){
        Coordinate ans = new Coordinate(c.x,c.y);

        switch (direction){
            case UP:
                ans.x -= bias;
                break;
            case DOWN:
                ans.x += bias;
                break;
            case LEFT:
                ans.y -= bias;
                break;
            case RIGHT:
                ans.y += bias;
        }
        return ans;
    }

    public static Direction find(Block[][] field, int playerX,int playerY,int enemyX, int enemyY, Direction enemyDirection){
        int fieldX = field.length;
        int fieldY = field[0].length;
        int x[] = new int[500];
        int y[] = new int[500];
        boolean used[][]=new boolean[fieldX][fieldY];
        Direction prev[][] = new Direction[fieldX][fieldY];
        int s=0,f=1;
        x[0] = enemyX;
        y[0] = enemyY;
        prev[enemyX][enemyY] = Direction.NONE;
        prev[playerX][playerY] = Direction.NONE;
        used[enemyX][enemyY] = true;
        while(s!=f){
            int curX = x[s];
            int curY = y[s];
            s++;


            if (curX>0&&enemyDirection==Direction.DOWN)
                if (field[curX-1][curY].isEmpty() && !used[curX-1][curY] && field[curX-1][curY].getHealthX(4)==0 && field[curX][curY].getHealthX(0)==0){
                    prev[curX-1][curY] = Direction.DOWN;
                    x[f] = curX-1;
                    y[f] = curY;
                    used[curX-1][curY] = true;
                    f++;
                }
            if (curX<fieldX-1&&enemyDirection==Direction.UP)
                if(field[curX+1][curY].isEmpty()&&!used[curX+1][curY] && field[curX+1][curY].getHealthX(0)==0 && field[curX][curY].getHealthX(4)==0){
                    prev[curX+1][curY] = Direction.UP;
                    x[f] = curX+1;
                    y[f] = curY;
                    used[curX+1][curY] = true;
                    f++;
                }
            if (curY>0&&enemyDirection==Direction.LEFT)
                if (field[curX][curY-1].isEmpty()&&!used[curX][curY-1]  && field[curX][curY-1].getHealthY(4)==0 && field[curX][curY].getHealthY(0)==0){
                    prev[curX][curY-1] = Direction.LEFT;
                    x[f] = curX;
                    y[f] = curY-1;
                    used[curX][curY-1] = true;
                    f++;
                }

            if (curY<fieldY-1&&enemyDirection==Direction.RIGHT)
                if(field[curX][curY+1].isEmpty()&&!used[curX][curY+1]  && field[curX][curY + 1].getHealthY(0)==0 && field[curX][curY].getHealthY(4)==0){
                    prev[curX][curY+1] = Direction.RIGHT;
                    x[f] = curX;
                    y[f] = curY+1;
                    used[curX][curY+1] = true;
                    f++;
                }

            if (curX>0)
                if (field[curX-1][curY].isEmpty() && !used[curX-1][curY] && field[curX-1][curY].getHealthX(4)==0 && field[curX][curY].getHealthX(0)==0){
                    prev[curX-1][curY] = Direction.DOWN;
                    x[f] = curX-1;
                    y[f] = curY;
                    used[curX-1][curY] = true;
                    f++;
            }
            if (curX<fieldX-1)
                if(field[curX+1][curY].isEmpty()&&!used[curX+1][curY] && field[curX+1][curY].getHealthX(0)==0 && field[curX][curY].getHealthX(4)==0){
                    prev[curX+1][curY] = Direction.UP;
                    x[f] = curX+1;
                    y[f] = curY;
                    used[curX+1][curY] = true;
                    f++;
            }
            if (curY>0)
                if (field[curX][curY-1].isEmpty()&&!used[curX][curY-1]  && field[curX][curY-1].getHealthY(4)==0 && field[curX][curY].getHealthY(0)==0){
                    prev[curX][curY-1] = Direction.LEFT;
                    x[f] = curX;
                    y[f] = curY-1;
                    used[curX][curY-1] = true;
                    f++;
            }

            if (curY<fieldY-1)
                if(field[curX][curY+1].isEmpty()&&!used[curX][curY+1]  && field[curX][curY + 1].getHealthY(0)==0 && field[curX][curY].getHealthY(4)==0){
                    prev[curX][curY+1] = Direction.RIGHT;
                    x[f] = curX;
                    y[f] = curY+1;
                    used[curX][curY+1] = true;
                    f++;
            }
        }
        if (prev[playerX][playerY]==Direction.NONE)
            return Direction.NONE;
        int curX = playerX;
        int curY = playerY;
        Direction pr = Direction.NONE;
        while(true){
            if (prev[curX][curY]==Direction.NONE)return pr;
            pr = prev[curX][curY];
            if (pr == null){
                int i = 0;
            }
            switch (prev[curX][curY]){
                case UP:
                    curX--;
                    break;
                case DOWN:
                    curX++;
                    break;
                case LEFT:
                    curY++;
                    break;
                case RIGHT:
                    curY--;
                    break;
            }
        }

    }
}
