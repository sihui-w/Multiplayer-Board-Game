import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;


public class GoBangFrame extends JPanel implements GoBangConfig {

    // painter
    public Graphics g;
    // if this one is avaible
    public int[][] isAvail = new int[15][15];
    // mark the chess position that user had putted
    public ArrayList<ChessPosition> chessPositionList = new ArrayList<ChessPosition>();
    // 1 -> black user
    // 2 -> white user
    public int turn = 0;
    // human battle -> 0 / AI battle -> 1
    public int ChooseType = 0;

    public int[][] weightArray = new int[15][15];

    public static HashMap<String,Integer> map = new HashMap<String,Integer>();

    public static void main(String[] args) {
        GoBangFrame gf = new GoBangFrame();
        gf.initUI();
    }


    public void initUI(){

        // unilaize a layout
        JFrame jf = new JFrame();
        jf.setTitle("Five Chess");
        jf.setSize(800,650);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(3);

        // set jf as the main layout
        jf.setLayout(new BorderLayout());

        // right size
        Dimension dim1 = new Dimension(150,0);
        // left size
        Dimension dim3 = new Dimension(550, 0);
        // right button component
        Dimension dim2 = new Dimension(140, 40);

        // set the size and color of playground
        this.setPreferredSize(dim3);
        this.setBackground(Color.LIGHT_GRAY);
        // add into the main layout
        jf.add(this,BorderLayout.CENTER);

        // button component layout
        JPanel jp = new JPanel();
        jp.setPreferredSize(dim1);
        jp.setBackground(Color.WHITE);
        // add the button to main layout
        jf.add(jp,BorderLayout.EAST);
        jp.setLayout(new FlowLayout());

        String[] btnName = {"Start","Withdraw","Fail"};
        JButton[] button = new JButton[3];

        for(int i = 0; i < btnName.length; i++){
            button[i] = new JButton(btnName[i]);
            button[i].setPreferredSize(dim2);
            jp.add(button[i]);
        }

        // battle pattern
        String[] boxName = {"Human Battle","AI Battle"};
        JComboBox box = new JComboBox(boxName);
        jp.add(box);

        // button listener
        ButtonListener btnListen = new ButtonListener(this,box);

        for(int i = 0; i < btnName.length; i++){
            button[i].addActionListener(btnListen);
        }

        box.addActionListener(btnListen);

        jf.setVisible(true);

    }


    /**
     * paint the chess and playground
     * @param g  the <code>Graphics</code> context in which to paint
     */
    public void paint(Graphics g){
        super.paint(g);

        // paint the playground
        g.setColor(Color.black);
        for(int i = 0; i < row; i++){
            g.drawLine(x, y+size*i, x+size*(column-1),y+size * i);
        }

        for(int j = 0; j < column; j++){
            g.drawLine(x+size*j,y,x+size*j,y+size*(row-1));
        }

        // paint the chess
        for(int i = 0; i<row; i++){
            for(int j = 0; j < column; j++){
                if(isAvail[i][j] == 1){
                    int countx = size * i + 20;
                    int county = size * j + 20;
                    g.setColor(Color.BLACK);
                    g.fillOval(countx-size/2,county-size/2,size,size);
                } else if (isAvail[i][j]==2) {
                    int countx=size*i+20;
                    int county=size*j+20;
                    g.setColor(Color.white);
                    g.fillOval(countx-size/2, county-size/2, size, size);
                }
            }
        }
    }

    static {
        map.put("01", 25);
        map.put("02", 22);
        map.put("001", 17);
        map.put("002", 12);
        map.put("0001", 17);
        map.put("0002", 12);
        map.put("0102", 25);
        map.put("0201", 22);
        map.put("0012", 15);
        map.put("0021", 10);
        map.put("01002", 25);
        map.put("02001", 22);
        map.put("00102", 17);
        map.put("00201", 12);
        map.put("00012", 15);
        map.put("00021", 10);
        map.put("01000", 25);
        map.put("02000", 22);
        map.put("00100", 19);
        map.put("00200", 14);
        map.put("00010", 17);
        map.put("00020", 12);
        map.put("00001", 15);
        map.put("00002", 10);
        map.put("0101", 65);
        map.put("0202", 60);
        map.put("0110", 80);
        map.put("0220", 76);
        map.put("011", 80);
        map.put("022", 76);
        map.put("0011", 65);
        map.put("0022", 60);
        map.put("01012", 65);
        map.put("02021", 60);
        map.put("01102", 80);
        map.put("02201", 76);
        map.put("01120", 80);
        map.put("02210", 76);
        map.put("00112", 65);
        map.put("00221", 60);
        map.put("01100", 80);
        map.put("02200", 76);
        map.put("01010", 75);
        map.put("02020", 70);
        map.put("00110", 75);
        map.put("00220", 70);
        map.put("00011", 75);
        map.put("00022", 70);
        map.put("0111", 150);
        map.put("0222", 140);
        map.put("01112", 150);
        map.put("02221", 140);
        map.put("01110", 1100);
        map.put("02220", 1050);
        map.put("01101", 1000);
        map.put("02202", 800);
        map.put("01011", 1000);
        map.put("02022", 800);
        map.put("01111", 3000);
        map.put("02222", 3500);
    }



}
