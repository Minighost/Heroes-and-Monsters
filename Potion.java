public class Potion{
    private int x;
    private int y;
    private boolean seen;
    
    public Potion(int x, int y){
        this.x = x;
        this.y = y;
        seen = true;
    }
    
    public Potion(){
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public boolean getSeen(){
        return seen;
    }
    
    public void setSeen(boolean newState){
        seen = newState;
    }
}