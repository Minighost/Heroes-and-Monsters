public class Farmer{
    private boolean decision;
    private int x;
    private int y;
    private boolean seen;
    private String type;
    
    public Farmer(int x, int y, String t){
        this.x = x;
        this.y = y;
        decision = false;
        seen = false;
        this.type = t;
    }
    
    public Farmer(){
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public String getType(){
        return type;
    }
    
    public boolean getSeen(){
        return seen;
    }
    
    public void setDecision(boolean newVal){
        decision = newVal;
    }
    
    public void setSeen(boolean newState){
        seen = newState;
    }
}