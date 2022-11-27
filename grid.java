import java.util.Arrays;
import java.util.Random;
public class grid{
    static int[][] relativePos = {{1,1},{-1,-1},{1,-1},{-1,1},{0,1},{0,-1},{1,0},{-1,0}};
    public gridItem[][] gameGrid; 
    public int sizeX;
    public int sizeY;
    public int bombCount;
    public double bombPopChance; //chance that a generated GridItem will be a bomb
    public int pointerX;
    public int pointerY;

    public grid(int sizeX, int sizeY,double bombPopChance){
        this.bombPopChance = bombPopChance;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        gameGrid = new gridItem[sizeY][sizeX]; //first variation: https://stackoverflow.com/questions/34954795/initialize-2d-array-of-custom-objects 
    }
    public grid(grid copy){
        this.gameGrid = copy.gameGrid;
        this.sizeX = copy.sizeX;
        this.sizeY = copy.sizeY;
        this.bombCount = copy.bombCount;
        this.bombPopChance = copy.bombPopChance;
    }

    public void populateGrid(){
        Random r = new Random();
        for(int i = 0; i < gameGrid.length;i++){
            gridItem[] currentLine = gameGrid[i];
            for(int j = 0; j<currentLine.length;j++){
                double y = r.nextDouble();
                boolean x = (y<bombPopChance);
                gameGrid[i][j] = new gridItem(x);
                System.out.print(((gameGrid[i][j].isBomb)?1:0) +", ");
            }
            System.out.println();
            
        }
       
    }
    public void nullifyGrid(){
        for(int i = 0; i < gameGrid.length;i++){
            gridItem[] currentLine = gameGrid[i];
            for(int j = 0; j<currentLine.length;j++){
                double y = r.nextDouble();
                boolean x = (y<bombPopChance);
                gameGrid[i][j] = null;
                System.out.print(((gameGrid[i][j].isBomb)?1:0) +", ");
            }
        }
    }


    public int bombCount(int x,int y){
        int bombCounter = 0;        
        
        for(int[] i : relativePos){
            try{
                gridItem currentItem = gameGrid[y+i[0]][x+i[1]];
                boolean currentIsBomb = currentItem.isBomb; 
                if(currentIsBomb){
                    bombCounter += 1;
                }
            }catch(ArrayIndexOutOfBoundsException e){;}
        }
        return(bombCounter);
    }


    public void mineRecursion(int x, int y){
        boolean cascade = gameGrid[y][x].reveal(bombCount(x,y));
        if(gameGrid[y][x].isBomb){
            throw new Error("ummm you shouldn't see this, if you do please tell me, recursion found a bomb...");//throw new Error();
        }
        if(cascade && !gameGrid[y][x].hasChecked){
            for(int[] i : relativePos){
                    gameGrid[y][x].hasChecked = true;
                    try{
                        mineRecursion(x+i[0],y+i[1]);
                    }catch(ArrayIndexOutOfBoundsException e){;}
            }
        }
        
    }
    
    public boolean mine(int x, int y){
        if(gameGrid[y][x].isBomb){
            gameGrid[y][x].isVisible = true;
            return(true);
            
        }else{
            mineRecursion(x, y);
            for(int i = 0; i < gameGrid.length;i++){
                gridItem[] currentLine = gameGrid[i];
                for(int j = 0; j<currentLine.length;j++){
                    gameGrid[i][j].hasChecked = false;
                }            
            }
            return(false);
        }
    }
    
    public void flag(int x, int y){
        if(!gameGrid[y][x].isVisible){
            gameGrid[y][x].isFlagged = !gameGrid[y][x].isFlagged;
        }else if(gameGrid[y][x].isFlagged){
            gameGrid[y][x].isFlagged = !gameGrid[y][x].isFlagged;
        }
    }

    public boolean AllFoundOrBomb(){
        int nonVisCount = 0;
        for(int i = 0; i < gameGrid.length;i++){
            gridItem[] currentLine = gameGrid[i];
            for(int j = 0; j<currentLine.length;j++){
                nonVisCount += !(gameGrid[i][j].isBomb ^ gameGrid[i][j].isVisible)? 1:0;
            }            
        }
        return(!(nonVisCount>0));
    }

    public void gridReveal(){
        for(int i = 0; i < gameGrid.length;i++){
            gridItem[] currentLine = gameGrid[i];
            for(int j = 0; j<currentLine.length;j++){
                gameGrid[i][j].reveal(bombCount(j, i));
                gameGrid[i][j].isFlagged = false;

            }            
        }
    }
}
