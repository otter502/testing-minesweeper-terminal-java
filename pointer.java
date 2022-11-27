import java.util.Arrays;
import java.util.Scanner;
public class pointer {
    int sizeX;
    int sizeY;
    int x;
    int y;
    grid gridObj;
    Scanner stdin;

    public pointer(int sizeX,int sizeY, grid gridObj){ 
        stdin = new Scanner(System.in);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        x = sizeX/2;
        y = sizeY/2;
        this.gridObj = gridObj;
    }
    //todo note: you need to make the rendergrid function occur after each mining
    //todo when mining it returns true or false depending on whether there is a bomb being mined  
    public boolean playerInput(boolean isFirst){
        boolean gameEnd = false;
        char[] validIns = {'w','a','s','d','e','q'};
        System.out.println("input? (WASD)(E=flag)(Q=mine)\n");
        String userInString;
        char playChar;
        boolean contains = false;
        
        //user validation
        do{ 
            userInString = stdin.next();
            playChar = Character.toLowerCase(userInString.charAt(0));
            for (char s : validIns) {
                if (s == playChar) {
                   contains = true;
                }
            }
            if(!contains){
                System.out.println("please input a valid input (WASD)(E=flag)(Q=mine)");
            }
        }while(!contains);
        // playChar = character input
        if(playChar == 'w'){
            pointerMove(0, -1);
        }else if(playChar == 'a'){
            pointerMove(-1, 0);
        }else if(playChar == 'd'){
            pointerMove(1, 0);
        }else if(playChar == 's'){
            pointerMove(0, 1);
        }else if(playChar == 'e'){
            gridObj.flag(x, y);
        }else if(playChar == 'q'){
            if(isFirst){
                while(gridObj.gameGrid[y][x].isBomb){
                    gridObj.populateGrid();
                }
            }
            gameEnd = gridObj.mine(x, y);
            
        }
        return(gameEnd);
    }

    public void pointerMove(int Dx,int Dy){
        int newX = x+Dx;
        int newY = y+Dy;
        if((0<=newX)&&(newX<sizeX)&&(0<=newY)&&(newY<sizeY)){
            x+=Dx;
            y+=Dy;
        }
    }

    

    public void gameEnd(){ 
        stdin.close();
        gridObj.gridReveal();
    }


    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  

    public void renderGridPointer(boolean renderPointer){
        clearScreen();
        String bomb = "X";
        String empty = "-";
        String flag = "P";
        String pointerIcon = "@";

        //todo need to figure out this
        gridItem[][] gameGrid = gridObj.gameGrid;
        String[][] renderGrid = new String[gameGrid.length][gameGrid[0].length];
        //adding items to the render grid
        for(int i = 0; i<gameGrid.length;i++){
            gridItem[] currentLine = gameGrid[i];
            for(int j = 0; j<currentLine.length;j++){
                gridItem currentGridItem = currentLine[j];
                String itemRender;
                if(currentGridItem.isFlagged){
                    itemRender = flag;
                }else if(!currentGridItem.isVisible){
                    itemRender = empty;
                } else if(currentGridItem.isBomb){
                    itemRender = bomb;
                }else{ 
                    itemRender = colorNum(currentGridItem.bombNear);//Integer.toString( currentGridItem.bombNear); //cant cast from int to string directly have to do this
                }
                renderGrid[i][j] = itemRender;
            }
        }
        //set the pointers position to the pointer icon
        if(renderPointer){
            renderGrid[y][x] = pointerIcon;
        }
        //printing out the function
        for(int i = 0; i<renderGrid.length;i++){
            String[] currentLine = renderGrid[i];
            for(int j = 0; j<currentLine.length;j++){
                String itemRender = currentLine[j];
                System.out.print(itemRender+", ");
            }
            System.out.println();
        }
        
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String GREY = "\u001B[90m";   // grey
    public static final String BLACK = "\u001B[30m";   // BLACK
    public static final String RED = "\u001B[31m";     // RED
    public static final String GREEN = "\u001B[32m";   // GREEN
    public static final String YELLOW = "\u001B[33m";  // YELLOW
    public static final String BLUE = "\u001B[34m";    // BLUE
    public static final String PURPLE = "\u001B[35m";  // PURPLE
    public static final String CYAN = "\u001B[36m";    // CYAN
    public static final String WHITE = "\u001B[37m";   // WHITE

    public static String colorNum(int num) { 
        if(num == 8){
            return(WHITE+num+ANSI_RESET);
        } else if(num == 7){
            return(PURPLE+num+ANSI_RESET);
        } else if(num == 6){
            return(CYAN+num+ANSI_RESET);
        } else if(num == 5){
            return(RED+num+ANSI_RESET);
        } else if(num == 4){
            return(BLUE+num+ANSI_RESET);
        } else if(num == 3){
            return(YELLOW+num+ANSI_RESET);
        } else if(num == 2){
            return(GREEN+num+ANSI_RESET);
        } else if(num == 1){
            return(GREY+num+ANSI_RESET);
        } else if(num == 0){
            return(BLACK + num+ANSI_RESET);
        }
        return(num+"");
    }



}
