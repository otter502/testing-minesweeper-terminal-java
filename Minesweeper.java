import java.util.Scanner;

public class Minesweeper {
    public static void main(String[] args) {
        int sizeX = 10;
        int sizeY = 10;
        grid t = new grid(sizeX,sizeY,0.1);
        pointer player = new pointer(sizeX, sizeY, t);
        t.populateGrid(); //need to remake generation method since I need to use bombCounter not bombPopChance
        //System.out.println(t.bombCount(0, 0));
        
        player.renderGridPointer(true);
        player.playerInput(true);
        boolean hasEnded = false;
        boolean hasLost = false;
        
        player.renderGridPointer(true);
        player.playerInput(true);
        while(!hasEnded && !hasLost){ //gameLoop
            player.renderGridPointer(true);
            hasLost = player.playerInput(false);
            hasEnded = t.AllFoundOrBomb();   
        }
        player.gameEnd();
        player.renderGridPointer(false);
        if(hasEnded){
            System.out.println("you won");
        }else if(hasLost){
            System.out.println("ya lost, I mean how, unless you are testing it then you just failed at a game thats like 33 years old, and its not chess, there is an algorithmic way to play this game and you somehow lost and you coded the thing, really? REALLY? unless you are testing then how in the world did you lose\n or you encountered a known error, just mine as your first move and its guarentted to be a non mine");
        }
    }
}
