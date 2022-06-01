package ludo;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author shubhamjain
 */
public class Ludo extends JComponent implements Runnable {

    //Signifies turn
    //0-Red 1-Green 2-Blue 3-Yellow
    public static Color turn;

    //0-Red 1-Green 2-Blue 3-Yellow
    public static boolean bonus;

    //Number of players
    public static int noOfPlayers;

    //Mode- BvsG-1 or RvsY-0
    public static int mode;

    //Number returned by dice
    public static int roll;

    //Winner of the game
    public static String winner;

    private static final int XOFFSET = 0;
    private static final int YOFFSET = 0;

    // current board
    private static int[][] myboard; //0-Red 1-Green 2-Blue 3-Yellow

    //state of the game
    private int state; //0-roll dice 1-choose token

    // colors
    private Color[] colors; //0-Red 1-Green 2-Blue 3-Yellow

    //Images to be used
    private BufferedImage redImage;
    private BufferedImage greenImage;
    private BufferedImage yellowImage;
    private BufferedImage blueImage;
    private BufferedImage redEndImage;
    private BufferedImage greenEndImage;
    private BufferedImage yellowEndImage;
    private BufferedImage blueEndImage;
    private BufferedImage redCellImage;
    private BufferedImage greenCellImage;
    private BufferedImage yellowCellImage;
    private BufferedImage blueCellImage;
    //private BufferedImage starImage;
    private BufferedImage midImage;

    //Map to store next cells
    static HashMap<Integer, Integer> nextMove;

    //Map to store next cells
    static HashMap<Color, Integer> colorMap;

    public Ludo() {
        init();
    }

    private void init() {
        do{
        try
        {
            mode = Integer.parseInt(JOptionPane.showInputDialog("Please input 0 for RvsY and 1 for BvsG (default is RvsY):"));
        }
        catch(Exception e){System.out.println(""+e);}
        }while(mode!=0 && mode!=1);

        if (mode == 0) {
            turn = Color.red;
        } else {
            turn = Color.GREEN;
        }

        state = 0;
        noOfPlayers = 2;

        String path = "/Users/shubhamjain/NetBeansProjects/Ludo/resources/";
        try {
            blueImage = ImageIO.read(new File(path + "blueImage.png"));
            redImage = ImageIO.read(new File(path + "redImage.png"));
            yellowImage = ImageIO.read(new File(path + "yellowImage.png"));
            greenImage = ImageIO.read(new File(path + "greenImage.png"));
            redEndImage = ImageIO.read(new File(path + "redEnd.png"));
            greenEndImage = ImageIO.read(new File(path + "greenEnd.png"));
            yellowEndImage = ImageIO.read(new File(path + "yellowEnd.png"));
            blueEndImage = ImageIO.read(new File(path + "blueEnd.png"));
            redCellImage = ImageIO.read(new File(path + "redCell.png"));
            greenCellImage = ImageIO.read(new File(path + "greenCell.png"));
            yellowCellImage = ImageIO.read(new File(path + "yellowCell.png"));
            blueCellImage = ImageIO.read(new File(path + "blueCell.png"));
            //starImage = ImageIO.read(new File(path+"star.png"));
            midImage = ImageIO.read(new File(path + "midImage.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        myboard = new int[4][4];
        myboard[0][0] = 16;
        myboard[0][1] = 19;
        myboard[0][2] = 61;
        myboard[0][3] = 64;
        myboard[2][0] = 151;
        myboard[2][1] = 154;
        myboard[2][2] = 196;
        myboard[2][3] = 199;
        myboard[3][0] = 160;
        myboard[3][1] = 205;
        myboard[3][2] = 208;
        myboard[3][3] = 163;
        myboard[1][0] = 25;
        myboard[1][1] = 70;
        myboard[1][2] = 28;
        myboard[1][3] = 73;

        colors = new Color[4];
        colors[0] = Color.RED;
        colors[1] = Color.GREEN;
        colors[2] = Color.BLUE;
        colors[3] = Color.YELLOW;

        initializeMap();

        colorMap = new HashMap<Color, Integer>();
        colorMap.put(Color.red, 0);
        colorMap.put(Color.blue, 2);
        colorMap.put(Color.green, 1);
        colorMap.put(Color.yellow, 3);

    }

    private void initializeMap() {
        nextMove = new HashMap<Integer, Integer>();
        nextMove.put(6, 7);
        nextMove.put(7, 8);
        nextMove.put(8, 23);
        nextMove.put(21, 6);
        nextMove.put(22, 37);
        nextMove.put(23, 38);
        nextMove.put(36, 21);
        nextMove.put(37, 52);
        nextMove.put(38, 53);
        nextMove.put(51, 36);
        nextMove.put(52, 67);
        nextMove.put(53, 68);
        nextMove.put(66, 51);
        nextMove.put(67, 82);
        nextMove.put(68, 83);
        nextMove.put(81, 66);
        nextMove.put(82, 97);
        nextMove.put(83, 99);
        nextMove.put(90, 91);
        nextMove.put(91, 92);
        nextMove.put(92, 93);
        nextMove.put(93, 94);
        nextMove.put(94, 95);
        nextMove.put(95, 81);
        nextMove.put(96, -250);
        nextMove.put(96, -250);
        nextMove.put(97, -250);
        nextMove.put(97, -250);
        nextMove.put(98, -250);
        nextMove.put(98, -250);
        nextMove.put(99, 100);
        nextMove.put(100, 101);
        nextMove.put(101, 102);
        nextMove.put(102, 103);
        nextMove.put(103, 104);
        nextMove.put(104, 119);
        nextMove.put(105, 90);
        nextMove.put(106, 107);
        nextMove.put(107, 108);
        nextMove.put(108, 109);
        nextMove.put(109, 110);
        nextMove.put(110, 111);
        nextMove.put(111, -250);
        nextMove.put(111, -250);
        nextMove.put(112, -250);
        nextMove.put(112, -250);
        nextMove.put(113, -250);
        nextMove.put(113, -250);
        nextMove.put(114, 113);
        nextMove.put(115, 114);
        nextMove.put(116, 115);
        nextMove.put(117, 116);
        nextMove.put(118, 117);
        nextMove.put(119, 134);
        nextMove.put(120, 105);
        nextMove.put(121, 120);
        nextMove.put(122, 121);
        nextMove.put(123, 122);
        nextMove.put(124, 123);
        nextMove.put(125, 124);
        nextMove.put(126, -250);
        nextMove.put(126, -250);
        nextMove.put(127, -250);
        nextMove.put(127, -250);
        nextMove.put(128, -250);
        nextMove.put(128, -250);
        nextMove.put(129, 143);
        nextMove.put(130, 129);
        nextMove.put(131, 130);
        nextMove.put(132, 131);
        nextMove.put(133, 132);
        nextMove.put(134, 133);
        nextMove.put(141, 125);
        nextMove.put(142, 127);
        nextMove.put(143, 158);
        nextMove.put(156, 141);
        nextMove.put(157, 142);
        nextMove.put(158, 173);
        nextMove.put(171, 156);
        nextMove.put(172, 157);
        nextMove.put(173, 188);
        nextMove.put(186, 171);
        nextMove.put(187, 172);
        nextMove.put(188, 203);
        nextMove.put(201, 186);
        nextMove.put(202, 187);
        nextMove.put(203, 218);
        nextMove.put(216, 201);
        nextMove.put(217, 216);
        nextMove.put(218, 217);

    }

    @Override
    public void run() {
        repaint();
    }

    public void drawTokens(Graphics2D g) {
        for (int i = 0; i < 4; i++) {
            g.setColor(colors[i]);
            if (colors[i] == Color.yellow) {
                g.setColor(Color.yellow.darker());
            }
            for (int j = 0; j < 4; j++) {
                //g.fillRect((myboard[i][j] / 15) * 40 + 15, (myboard[i][j] % 15) * 40 + 15, 10, 10);
                //System.out.println("i:"+i+"j:"+j+" Color:"+colors[i]+"myboard[i][j]"+myboard[i][j]);

                g.drawRect((myboard[i][j] % 15) * 40 + 15, (myboard[i][j] / 15) * 40 + 5, 10, 10);
                g.drawRect((myboard[i][j] % 15) * 40 + 10, (myboard[i][j] / 15) * 40 + 20, 20, 10);
            }
        }
    }

    public static void placeAtHome(int i, int j) {
        if (i == 0 && j == 0) {
            myboard[0][0] = 16;
            return;
        }
        if (i == 0 && j == 1) {
            myboard[0][1] = 19;
            return;
        }
        if (i == 0 && j == 2) {
            myboard[0][2] = 61;
            return;
        }
        if (i == 0 && j == 3) {
            myboard[0][3] = 64;
            return;
        }
        if (i == 1 && j == 0) {
            myboard[1][0] = 25;
            return;
        }
        if (i == 1 && j == 1) {
            myboard[1][1] = 70;
            return;
        }
        if (i == 1 && j == 2) {
            myboard[1][2] = 28;
            return;
        }
        if (i == 1 && j == 3) {
            myboard[1][3] = 73;
            return;
        }
        if (i == 2 && j == 0) {
            myboard[2][0] = 151;
            return;
        }
        if (i == 2 && j == 1) {
            myboard[2][1] = 154;
            return;
        }
        if (i == 2 && j == 2) {
            myboard[2][2] = 196;
            return;
        }
        if (i == 2 && j == 3) {
            myboard[2][3] = 199;
            return;
        }
        if (i == 3 && j == 0) {
            myboard[3][0] = 160;
            return;
        }
        if (i == 3 && j == 1) {
            myboard[3][1] = 205;
            return;
        }
        if (i == 3 && j == 2) {
            myboard[3][2] = 208;
            return;
        }
        if (i == 3 && j == 3) {
            myboard[3][3] = 163;
            return;
        }

    }

    private static boolean isblocked(int currentCell,int count, Color c) {

        int color = colorMap.get(c);

        for (int i = 0; i < 4; ++i) {
                if (myboard[color][i] == currentCell ) {
                        return true;
                    }
        }
                
        return false;
    }

    public static int nextCell(int currentCell, Color c, int count, boolean state) {

        if (count == 0 && isblocked(currentCell,count, c)) {
            return -250;
        }

        if (count == 0) {
            if (state) {
                for (int i = 0; i < 4; ++i) {
                    if (i != colorMap.get(c)) {
                        for (int j = 0; j < 4; ++j) {
                            if (myboard[i][j] == currentCell && currentCell != 91 && currentCell != 201 && currentCell != 23 && currentCell != 133) {
                                bonus = true;
                                placeAtHome(i, j);
                            }
                        }
                    }
                }
            }
            return currentCell;
        } else if (c == Color.red && (currentCell == 16 || currentCell == 19 || currentCell == 61 || currentCell == 64) && (count == 6 || count == 1)) {
            return 91;
        } else if (c == Color.green && (currentCell == 25 || currentCell == 70 || currentCell == 28 || currentCell == 73) && (count == 6 || count == 1)) {
            return 23;
        } else if (c == Color.yellow && (currentCell == 160 || currentCell == 163 || currentCell == 208 || currentCell == 205) && (count == 6 || count == 1)) {
            return 133;
        } else if (c == Color.blue && (currentCell == 151 || currentCell == 154 || currentCell == 196 || currentCell == 199) && (count == 6 || count == 1)) {
            return 201;
        } else {
            try {
                if (c == Color.red && currentCell == 105) {
                    return nextCell(106, c, count - 1, state);
                } else if (c == Color.blue && currentCell == 217) {
                    return nextCell(202, c, count - 1, state);
                } else if (c == Color.green && currentCell == 7) {
                    return nextCell(22, c, count - 1, state);
                } else if (c == Color.yellow && currentCell == 119) {
                    return nextCell(118, c, count - 1, state);
                } else {
                    return nextCell(nextMove.get(currentCell), c, count - 1, state);
                }
            } catch (Exception e) {
                return -250;
            }
        }
    }

    private boolean isValid(Color c, int roll, boolean state) {

        int k = colorMap.get(c);

        for (int i = 0; i < 4; ++i) {
            //System.out.println("nextCell(myboard[k][i],c, roll)"+nextCell(myboard[k][i],c, roll));
            if (nextCell(myboard[k][i], c, roll, state) > 0) {
                return true;
            }
        }

        return false;
    }

    private void checkEnd(int i) {
        if (i == 0) {
            if (myboard[i][0] == 111 && myboard[i][1] == 111 && myboard[i][2] == 111 && myboard[i][3] == 111) {
                winner = "Red ";
            }
        } else if (i == 1) {
            if (myboard[i][0] == 97 && myboard[i][1] == 97 && myboard[i][2] == 97 && myboard[i][3] == 97) {
                winner = "Green ";
            }
        } else if (i == 2) {
            if (myboard[i][0] == 113 && myboard[i][1] == 113 && myboard[i][2] == 113 && myboard[i][3] == 113) {
                winner = "Yellow ";
            }
        } else if (i == 3) {
            if (myboard[i][0] == 127 && myboard[i][1] == 127 && myboard[i][2] == 127 && myboard[i][3] == 127) {
                winner = "Blue ";
            }
        }

    }

    @Override
    public void paint(Graphics u) {
        Graphics2D g = (Graphics2D) u;

        // fill background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1024, 1024);

        // translate origin
        g.translate(XOFFSET, YOFFSET);

        // draw current state
        g.setColor(Color.BLACK);
        String s = "";

        g.drawImage(redImage, 0, 0, 240, 240, this);
        g.drawImage(yellowImage, 360, 360, 240, 240, this);
        g.drawImage(blueImage, 0, 360, 240, 240, this);
        g.drawImage(greenImage, 360, 0, 240, 240, this);
        g.drawImage(redEndImage, 40, 280, 200, 40, this);
        g.drawImage(greenEndImage, 280, 40, 40, 200, this);
        g.drawImage(yellowEndImage, 360, 280, 200, 40, this);
        g.drawImage(blueEndImage, 280, 360, 40, 200, this);
        g.drawImage(redCellImage, 40, 240, 40, 40, this);
        g.drawImage(greenCellImage, 320, 40, 40, 40, this);
        g.drawImage(yellowCellImage, 520, 320, 40, 40, this);
        g.drawImage(blueCellImage, 240, 520, 40, 40, this);
//        g.drawImage(starImage, 240, 80, 40, 40, this);
//        g.drawImage(starImage, 80, 320, 40, 40, this);
//        g.drawImage(starImage, 480, 240, 40, 40, this);
//        g.drawImage(starImage, 320, 480, 40, 40, this);
        g.drawImage(midImage, 240, 240, 120, 120, this);

        // draw board
        g.drawRect(0, 0, 600, 600);

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                //Print grid numbers                 
                //g.drawString("" + (i + j * 15), i * 40 + 10, j * 40 + 10);
                g.drawRect(i * 40, j * 40, 40, 40);
                g.setColor(Color.black);
            }
        }

        g.drawImage(midImage, 240, 240, 120, 120, this);

        // draw board numbering
        g.setColor(Color.BLACK);

        // draw roll button
        {
            int wx = 600 + 32, wy = 32;
            g.translate(wx, wy);
            if (turn == Color.red) {
                g.setColor(new Color(253, 121, 151));
            } else if (turn == Color.green) {
                g.setColor(new Color(198, 223, 181));
            } else if (turn == Color.yellow) {
                g.setColor(new Color(255, 241, 206));
            } else if (turn == Color.blue) {
                g.setColor(new Color(100, 172, 240));
            }
            g.fillRect(-24, -24, 64, 64);
            g.setColor(Color.black);
            switch (roll) {
                case 1:
                    g.fillRect(5, 5, 6, 6);
                    break;
                case 2:
                    g.fillRect(-14, -14, 6, 6);
                    g.fillRect(24, 24, 6, 6);
                    break;
                case 3:
                    g.fillRect(5, 5, 6, 6);
                    g.fillRect(-14, -14, 6, 6);
                    g.fillRect(24, 24, 6, 6);
                    break;
                case 4:
                    g.fillRect(24, -14, 6, 6);
                    g.fillRect(-14, 24, 6, 6);
                    g.fillRect(24, 24, 6, 6);
                    g.fillRect(-14, -14, 6, 6);
                    break;
                case 5:
                    g.fillRect(24, -14, 6, 6);
                    g.fillRect(-14, 24, 6, 6);
                    g.fillRect(5, 5, 6, 6);
                    g.fillRect(-14, -14, 6, 6);
                    g.fillRect(24, 24, 6, 6);
                    break;
                case 6:
                    g.fillRect(24, -14, 6, 6);
                    g.fillRect(-14, 24, 6, 6);
                    g.fillRect(24, 24, 6, 6);
                    g.fillRect(-14, -14, 6, 6);
                    g.fillRect(-14, 5, 6, 6);
                    g.fillRect(24, 5, 6, 6);
                    break;
                default:
                    g.fillRect(5, 5, 6, 6);

            }

            int t = colorMap.get(turn);
            if (t == 0) {
                g.drawString("Turn: Red", -18, 64);
            }
            if (t == 2) {
                g.drawString("Turn: Blue", -18, 64);
            }
            if (t == 1) {
                g.drawString("Turn: Green", -18, 64);
            }
            if (t == 3) {
                g.drawString("Turn: Yellow", -18, 64);
            }

            g.drawString("Roll:" + roll, -18, 80);

            if (winner!=null && !winner.isEmpty()) {
                g.drawString(winner + "wins.", -18, 100);
            }

            g.translate(-wx, -wy);
        }

        drawTokens(g);
        // repaint if animation isn't complete
        // or      invoke ai if it's black's turn
        repaint();

    }

    @Override
    protected void processMouseEvent(MouseEvent e) {

        
        if (e.getID() != MouseEvent.MOUSE_PRESSED) {
            return;
        }

        // calculate clicked field position
        int xclick = (e.getX() - XOFFSET) / 40;
        int yclick = (e.getY() - YOFFSET) / 40;

        //System.out.println("xclick" + xclick);
        //System.out.println("yclick" + yclick);
        if (state == 0) {
            if (xclick > 14 && yclick < 2) {
                Random rand = new Random();
                roll = rand.nextInt(6) + 1;
                if (isValid(turn, roll, false)) {
                    state = 1;
                    repaint();
                    return;
                } else {
                    updateTurn(turn, noOfPlayers);
                    repaint();
                    return;
                }
            } else {
                return;
            }

        } else if (state == 1) {
            int i = colorMap.get(turn);

            //System.out.println("myboard[i][0]" + myboard[i][0]);
            //System.out.println("xclick + (yclick * 15)" + (xclick + (yclick * 15)));
            if (myboard[i][0] == xclick + (yclick * 15)) {
                int n = nextCell(myboard[i][0], turn, roll, true);
                if (n < 0) {
                    return;
                }
                myboard[i][0] = n;
                checkEnd(i);
                state = 0;
                updateTurn(turn, noOfPlayers);
                repaint();
            } else if (myboard[i][1] == xclick + (yclick * 15)) {
                int n = nextCell(myboard[i][1], turn, roll, true);
                if (n < 0) {
                    return;
                }
                myboard[i][1] = n;
                checkEnd(i);
                state = 0;
                updateTurn(turn, noOfPlayers);
                repaint();
            } else if (myboard[i][2] == xclick + (yclick * 15)) {
                int n = nextCell(myboard[i][2], turn, roll, true);
                if (n < 0) {
                    return;
                }
                myboard[i][2] = n;
                checkEnd(i);
                state = 0;
                updateTurn(turn, noOfPlayers);
                repaint();
            } else if (myboard[i][3] == xclick + (yclick * 15)) {
                int n = nextCell(myboard[i][3], turn, roll, true);
                if (n < 0) {
                    return;
                }
                myboard[i][3] = n;
                checkEnd(i);
                state = 0;
                updateTurn(turn, noOfPlayers);
                repaint();
            } else {
                return;
            }

        }

        //repaint();
    }

    private void updateTurn(Color c, int noOfPlayers) {
        //if (roll == 6 || bonus) {
        if (roll == 6) {
            bonus = false;
            return;
        }
        if (mode == 0) {
            if (turn == Color.RED) {
                turn = Color.yellow;
            } else if (turn == Color.yellow) {
                turn = Color.red;
            }
        } else {
            if (turn == Color.blue) {
                turn = Color.green;
            } else if (turn == Color.green) {
                turn = Color.blue;
            }
        }
    }

    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("Ludo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Ludo ch = new Ludo();
        ch.setPreferredSize(new Dimension(XOFFSET + 675, YOFFSET + 600));
        ch.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        frame.add(ch);
        frame.pack();
        frame.show();
        //System.out.println("" + nextCell(217, Color.yellow, 1));
    }

}
