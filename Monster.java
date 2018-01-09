public class Monster{
    private int hp;
    private int speed;
    private int x;
    private int y;
    private boolean seen;
    private int self_index;
    
    public Monster(int h, int s, int x, int y, int i){
        hp = h;
        speed = s;
        this.x = x;
        this.y = y;
        self_index = i;
        seen = false;
    }
    
    public int getHP(){
        return hp;
    }
    
    public int getSpeed(){
        return speed;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getIndex(){
        return self_index;
    }
    
    public boolean getSeen(){
        return seen;
    }
    
    public void setHP(int newHP){
        this.hp = newHP;
    }
    
    public void setSeen(boolean newState){
        seen = newState;
    }
    
    public String toString(){
        String temp_str = "";
        temp_str += "HP: " + hp + ", Speed: " + speed;
        return temp_str;
    }
}