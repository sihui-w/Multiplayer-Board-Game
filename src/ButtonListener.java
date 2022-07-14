import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * button listener
 */
public class ButtonListener implements GoBangConfig, ActionListener {
    public GoBangFrame gf;
    public JComboBox box;

    public ButtonListener(GoBangFrame gf, JComboBox box){

        this.gf = gf;
        this.box = box;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // if user click "start", set listener to left size
        if(e.getActionCommand().equals("Start")){
            FrameListener fl = new FrameListener();
            fl.setGraphics(gf);
            gf.addMouseListener(fl);
        } else if (e.getActionCommand().equals("Withdraw")) {

            if(gf.chessPositionList.size() > 1){
                ChessPosition lot = new ChessPosition();
                // remove the withdraw chess and get its position
                lot = gf.chessPositionList.remove(gf.chessPositionList.size()-1);
                // this position is available now
                gf.isAvail[lot.listi][lot.listj] = 0;
                if(gf.turn == 1)gf.turn++;
                else gf.turn--;

                // repaint the playground
                gf.repaint();
            }else {
                System.out.println("Cannot withdraw");
            }

        }else if(e.getActionCommand().equals("Fail")){
            if(gf.turn == 1) System.out.println("Whiter Win!");
            else System.out.println("Blacker Win!");
        } else if (box.getSelectedItem().equals("Human Battle")) {
            gf.ChooseType=0;
            gf.turn=0;
        }else if (box.getSelectedItem().equals("AI Battle")) {
            gf.ChooseType=1;
            gf.turn=0;
        }
    }
}
