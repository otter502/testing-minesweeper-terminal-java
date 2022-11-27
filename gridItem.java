public class gridItem{
    public boolean isFlagged;
    public boolean isBomb;
    public boolean isVisible;
    public boolean hasChecked;//this is a variable changed for the cascading mine
    //calculated when the user reveals it
    public int bombNear;
    //public int flagNear;
    public gridItem(boolean isBomb){
        this.isBomb = isBomb;
        hasChecked = false;
        isVisible = false;
        isFlagged = false;
    }
    public boolean reveal(int bombNearCount){
        isVisible = true;
        bombNear = bombNearCount;
        //calculates # of flags and bombs nearby
        //then if bomb near == 0
        //return boolean true if cascade
        if(bombNearCount == 0){
            return(true);
        }
        return false;
    }
}
