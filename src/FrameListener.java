import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FrameListener implements GoBangConfig, MouseListener {

    public GoBangFrame gf;

    public void setGraphics(GoBangFrame gf){
        this.gf = gf;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        // count the point that chess would go
        int countx = (x/40)*40 + 20;
        int county = (y/40)*40 + 20;
        Graphics g = gf.getGraphics();

        int colu = (countx - 20) / 40;
        int ro = (county - 20) / 40;

        // if this point is not available
        if(gf.isAvail[ro][colu] != 0){
            System.out.println("Not Available, choose another point please");
        }else {
            // if point is available

            // human battle
            if(this.gf.ChooseType == 0){
                // black user
                if (gf.turn == 1) {
                    String winner = "Black";
                    // check if this round win
                    checkWin(colu, ro, winner, 1,g,2,countx,county,Color.BLACK);

                } else {
                    // white user
                    String winner = "White";
                    // check if this round win
                    checkWin(colu, ro, winner, 2,g,1,countx,county,Color.WHITE);
                }
            }
            // AI battle
            else{

                if (gf.turn == 1) {
                    // black user -> human
                    String winner = "Black";
                    // check if this round win
                    checkWin(colu, ro, winner, 1,g,2,countx,county,Color.BLACK);

                }else {
                    // AI user

                    // get the value of each position
                    for(int i = 0; i < gf.isAvail.length; i++){
                        for(int j = 0; j < gf.isAvail[i].length; j++){
                            // empty position
                            if(gf.isAvail[i][j] == 0){
                                String connectType = "0";
                                // calculate the value of every position
                                calPstValue(connectType,i,j);
                            }
                        }
                    }

                    // print the chess value
                    for(int i = 0; i < 14; i++){
                        for(int j = 0; j < 14; j++){
                            System.out.printf(gf.weightArray[i][j]+" ");
                        }
                        System.out.println();
                    }

                    // get the bigest value
                    int AIi=0,AIj=0;
                    int weightmax=0;
                    for(int i=0;i<14;i++) {
                        for(int j=0;j<14;j++) {
                            if(weightmax<gf.weightArray[i][j]) {
                                weightmax=gf.weightArray[i][j];
                                AIi=i;
                                AIj=j;
                                System.out.println(AIi+" "+AIj);
                            }
                        }
                    }

                    countx = 20 + AIj * 40;
                    county = 20 + AIi * 40;

                    // reset the position value
                    for(int i = 0; i < 14; i++){
                        for(int j = 0; j < 14; j++){
                            gf.weightArray[i][j] = 0;
                        }
                    }

                    String winner = "White";
                    // check if this round win
                    checkWin(AIj, AIi, winner, 2,g,1,countx,county,Color.WHITE);

                }
            }
        }
    }

    public void calPstValue(String connectType, int i, int j ){
        // left size
        int jmin = Math.max(0, j - 4);

        for(int positionj = j - 1; positionj >= jmin; positionj--){
            // add the chess
            connectType = connectType + gf.isAvail[i][positionj];
        }

        Integer valueLeft = gf.map.get(connectType);
        if(valueLeft != null) gf.weightArray[i][j] += valueLeft;


        // right size
        connectType="0";
        int jmax=Math.min(14, j+4);
        for(int positionj=j+1;positionj<=jmax;positionj++) {

            connectType=connectType+gf.isAvail[i][positionj];
        }

        Integer valueRight = gf.map.get(connectType);
        if(valueRight != null) gf.weightArray[i][j] += valueRight;

        // check row
        gf.weightArray[i][j] += unionWeight(valueLeft,valueRight);


        // up size
        connectType = "0";

        int imin = Math.max(0, i - 4);

        for(int positioni = i - 1; positioni >= imin; positioni--){
            // add the chess
            connectType = connectType + gf.isAvail[positioni][j];
        }

        Integer valueUp = gf.map.get(connectType);
        if(valueUp != null) gf.weightArray[i][j] += valueUp;


        // down size
        connectType="0";
        int imax=Math.min(14, i+4);
        for(int positioni=i+1;positioni<=imax;positioni++) {

            connectType=connectType+gf.isAvail[positioni][j];
        }

        Integer valueDown = gf.map.get(connectType);
        if(valueDown != null) gf.weightArray[i][j] += valueDown;

        // check row
        gf.weightArray[i][j] += unionWeight(valueUp,valueDown);


        // leftUp
        connectType="0";
        for(int position=-1;position>=-4;position--) {
            if((i+position>=0)&&(i+position<=14)&&(j+position>=0)&&(j+position<=14))
                connectType=connectType+gf.isAvail[i+position][j+position];
        }

        Integer valueLeftUp=gf.map.get(connectType);
        if(valueLeftUp!=null) gf.weightArray[i][j]+=valueLeftUp;


        // right down
        connectType="0";
        for(int position=1;position<=4;position++) {
            if((i+position>=0)&&(i+position<=14)&&(j+position>=0)&&(j+position<=14))
                connectType=connectType+gf.isAvail[i+position][j+position];
        }

        Integer valueRightDown=gf.map.get(connectType);
        if(valueRightDown!=null) gf.weightArray[i][j]+=valueRightDown;


        gf.weightArray[i][j]+=unionWeight(valueLeftUp,valueRightDown);

        //left down
        connectType="0";
        for(int position=1;position<=4;position++) {
            if((i+position>=0)&&(i+position<=14)&&(j-position>=0)&&(j-position<=14))
                connectType=connectType+gf.isAvail[i+position][j-position];
        }

        Integer valueLeftDown=gf.map.get(connectType);
        if(valueLeftDown!=null) gf.weightArray[i][j]+=valueLeftDown;

        // right up
        connectType="0";
        for(int position=1;position<=4;position++) {
            if((i-position>=0)&&(i-position<=14)&&(j+position>=0)&&(j+position<=14))
                connectType=connectType+gf.isAvail[i-position][j+position];
        }

        Integer valueRightUp=gf.map.get(connectType);
        if(valueRightUp!=null) gf.weightArray[i][j]+=valueRightUp;

        gf.weightArray[i][j]+=unionWeight(valueLeftDown,valueRightUp);

    }


    public void checkWin(int colu, int ro, String winner,int turn,Graphics g,int nextTurn, int countx, int county, Color color){

        // set the color of chess
        g.setColor(color);
        // fill the chess
        g.fillOval(countx - size / 2, county - size / 2, size, size);
        // not available now, black one
        gf.isAvail[ro][colu] = turn;
        // add this point to the list
        gf.chessPositionList.add(new ChessPosition(ro, colu));
        // next turn
        gf.turn = nextTurn;

        // 180 degree
        int imin = ro - 4, imax = ro + 4;
        if(imin < 0) imin = 0;
        if(imax > 14) imax = 14;
        int linkedChess = 0;

        for(int i = imin; i <= imax; i++){
            if(gf.isAvail[i][colu] == turn) linkedChess++;
            else linkedChess = 0;

            if(linkedChess == 5){
                System.out.printf("%s Win!",winner);
                return;
            }
        }

        // 0 degree
        int jmin = colu - 4, jmax = colu + 4;
        if(jmin < 0) jmin = 0;
        if(jmax > 14) jmax = 14;
        int linkedChess2 = 0;

        for(int j = jmin; j <= jmax; j++){
            if(gf.isAvail[ro][j] == turn) linkedChess2++;
            else linkedChess2 = 0;

            if(linkedChess2 == 5){
                System.out.printf("%s Win!",winner);
                return;
            }
        }

        // 135 degree
        int linkedChess3 = 0;
        for(int i = -4; i <= 4; i++){
            if((ro+i>=0)&&(colu+i>=0)&&(ro+i<=14)&&(colu+i<=14)) {
                if(gf.isAvail[ro+i][colu+i]== turn) linkedChess3++;
                else linkedChess3=0;
                if(linkedChess3==5) {
                    System.out.printf("%s Win!",winner);
                    return;
                }
            }
        }

        // 45 degree
        int linkedChess4=0;
        for(int i=-4;i<=4;i++) {
            if((ro+i>=0)&&(colu-i>=0)&&(ro+i<=14)&&(colu-i<=14)) {
                if(gf.isAvail[ro+i][colu-i]== turn) linkedChess4++;
                else linkedChess4=0;
                if(linkedChess4==5) {
                    System.out.printf("%s Win!",winner);
                    return;
                }
            }
        }
    }

    public Integer unionWeight(Integer a, Integer b){
        if((a == null) || (b == null)) return 0;

        else if((a>=10)&&(a<=25)&&(b>=10)&&(b<=25)) return 60;

        else if(((a>=10)&&(a<=25)&&(b>=60)&&(b<=80))||((a>=60)&&(a<=80)&&(b>=10)&&(b<=25))) return 800;

        else if(((a>=10)&&(a<=25)&&(b>=140)&&(b<=1000))||((a>=140)&&(a<=1000)&&(b>=10)&&(b<=25))||((a>=60)&&(a<=80)&&(b>=60)&&(b<=80)))
            return 3000;

        else if(((a>=60)&&(a<=80)&&(b>=140)&&(b<=1000))||((a>=140)&&(a<=1000)&&(b>=60)&&(b<=80))) return 3000;
        else return 0;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
