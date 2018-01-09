public class Hero{
    private int hp;
    private boolean armor;
    private int posX;
    private int posY;
    private int potions;
    private String currentWeap;
    private int kills;
    
    public Hero(int h, int x, int y){
        hp = h;
        armor = false;
        posX = x;
        posY = y;
        currentWeap = "dagger";
        potions = 0;
        kills = 0;
    }
    
    public int getHP(){
        return hp;
    }
    
    public boolean getArmor(){
        return armor;
    }
    
    public int getX(){
        return posX;
    }
    
    public int getY(){
        return posY;
    }
    
    public String getWeap(){
        return currentWeap;
    }
    
    public int checkInventory(){
        return potions;
    }
    
    public int getKills(){
        return kills;
    }
    
    public void setHP(int newHP){
        this.hp = newHP;
    }
    
    public void setArmor(boolean newState){
        this.armor = newState;
    }
    
    public void setX(int newPosX){
        this.posX = newPosX;
    }
    
    public void setY(int newPosY){
        this.posY = newPosY;
    }
    
    public void setNewWeap(String newWeap){
        currentWeap = newWeap;
    }
    
    public void setPotions(int newVal){
        potions = newVal;
    }
    
    public void oneKill(){
        kills++;
    }
    
    public String toString(){
        String temp_str = "";
        temp_str += "HP: " + hp + ", Current Weapon: " + currentWeap + ", Potions: " + potions;
        return temp_str;
    }
}