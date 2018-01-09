import java.util.*;
import java.io.*;
//
//PUT IN "SEEN" METHODS
// EVERYTHING IS INVISIBLE RN
//  OKAY?
//
public class Driver{
    public static void main (String args[]){
        
        System.out.println("\f");
        System.out.println("Please play the game in a fullscreen terminal window for the best experience.");
        System.out.println("");
        promptEnterKey();
        System.out.println("\f");
        
        int[][] map = new int[10][10];
        Monster[] monsters = new Monster[6];
        int[][] takenSpots = new int[11][2];
        int[] currentCoord = new int[2];
        int currentIndex = 1;
        
        String toNorth = "";
        String toEast = "";
        String toSouth = "";
        String toWest = "";
        
        String lastAction = "";
        
        Scanner scanner = new Scanner(System.in);
        
        for(int i = 0; i < map.length; i++){
            for(int k = 0; k < map.length; k++){
                map[i][k] = 0;
            }
        }
        
        //hero
        Hero hero = new Hero(100, 0, 9);
        takenSpots[0][0] = 0;
        takenSpots[0][1] = 9;
        
        //monsters
        for(int i = 0; i < monsters.length; i++){ 
            currentCoord = newCoord(takenSpots);
            int hp = (int)(Math.random() * 99) + 1;
            int speed = (int)(Math.random() * 4);
            monsters[i] = new Monster(hp, speed, currentCoord[0], currentCoord[1], i);
            takenSpots[currentIndex][0] = currentCoord[0];
            takenSpots[currentIndex][1] = currentCoord[1];
            currentIndex++;
        }
        
        //sword farmer
        currentCoord = newCoord(takenSpots);
        Farmer swordFarmer = new Farmer(currentCoord[0], currentCoord[1], "sword");
        takenSpots[currentIndex][0] = currentCoord[0];
        takenSpots[currentIndex][1] = currentCoord[1];
        currentIndex++;
        
        //armor farmer
        currentCoord = newCoord(takenSpots);
        Farmer armorFarmer = new Farmer(currentCoord[0], currentCoord[1], "armor");
        takenSpots[currentIndex][0] = currentCoord[0];
        takenSpots[currentIndex][1] = currentCoord[1];
        currentIndex++;
        
        //potion 1
        currentCoord = newCoord(takenSpots);
        Potion potion1 = new Potion(currentCoord[0], currentCoord[1]);
        takenSpots[currentIndex][0] = currentCoord[0];
        takenSpots[currentIndex][1] = currentCoord[1];
        currentIndex++;
        
        //potion 2
        currentCoord = newCoord(takenSpots);
        Potion potion2 = new Potion(currentCoord[0], currentCoord[1]);
        takenSpots[currentIndex][0] = currentCoord[0];
        takenSpots[currentIndex][1] = currentCoord[1];
        currentIndex++;
        
        //loop
        while(true){
            int currentX = hero.getX();
            int currentY = hero.getY();
            map[currentY][currentX] = 1; //hero
        
            for(int i = 0; i < monsters.length; i++){ //monsters
                if(monsters[i] == null){
                    continue;
                }
                currentX = monsters[i].getX();
                currentY = monsters[i].getY();
                map[currentY][currentX] = 2;
            }
            
            currentX = swordFarmer.getX();
            currentY = swordFarmer.getY();
            map[currentY][currentX] = 3;
    
            currentX = armorFarmer.getX();
            currentY = armorFarmer.getY();
            map[currentY][currentX] = 3;
            
            if(potion1 != null){
                currentX = potion1.getX();
                currentY = potion1.getY();
                map[currentY][currentX] = 4;
            }
            
            if(potion2 != null){
                currentX = potion2.getX();
                currentY = potion2.getY();
                map[currentY][currentX] = 4;
            }
            
            printMap(map, monsters, swordFarmer, armorFarmer, potion1, potion2);
            
            toNorth = checkNorth(hero, map);
            toEast = checkEast(hero, map);
            toSouth = checkSouth(hero, map);
            toWest = checkWest(hero, map);
            
            System.out.println("---Status---\n" + hero);
            System.out.println("\nLast action: " + updateStatus(lastAction) + "\n");
            
            System.out.println("North: " + toNorth);
            System.out.println("West: " + toWest);
            System.out.println("South: " + toSouth);
            System.out.println("East: " + toEast);
            
            System.out.println("");
            System.out.print("\t\tW - North");
            if(hero.checkInventory() > 0){
                System.out.println("\tE - Restore HP");
            }
            System.out.println("\nA - West\tS - South\tD - East");

            String action = scanner.next();
            switch(action){
                case "w":
                    switch(staticCheck(hero, map, action)){
                        case 0:
                            map[hero.getY()][hero.getX()] = 9;
                            hero.setY(hero.getY() - 1);
                            lastAction = action;
                            break;
                        case 2:
                            Monster attacker = monsters[findMonster(monsters, hero.getX(), hero.getY() - 1)];
                            attackSeq(map, hero, attacker, monsters, action);
                            lastAction = "monster";
                            break;
                        case 3:
                            String whichFarmer = findFarmer(hero, swordFarmer, action);
                            Farmer npc = new Farmer();
                            if(whichFarmer.equals("sword")){
                                npc = swordFarmer;
                            }
                            if(whichFarmer.equals("armor")){
                                npc = armorFarmer;
                            }
                            farmerSeq(map, hero, npc);
                            lastAction = "farmer";
                            break;
                        case 4:
                            hero.setPotions(hero.checkInventory() + 1);
                            lastAction = "potion";
                            int num = findPotion(hero.getX(), hero.getY() - 1, potion1);
                            if(num == 1){
                                potion1 = null;
                            } else {
                                potion2 = null;
                            }
                            map[hero.getY()][hero.getX()] = 9;
                            hero.setY(hero.getY() - 1);
                            break;
                        case 10:
                            lastAction = "stupid";
                            hero.setHP(hero.getHP() - 10);
                            break;
                    }
                    break;
                case "a":
                    switch(staticCheck(hero, map, action)){
                        case 0:
                            map[hero.getY()][hero.getX()] = 9;
                            hero.setX(hero.getX() - 1);
                            lastAction = action;
                            break;
                        case 2:
                            Monster attacker = monsters[findMonster(monsters, hero.getX() - 1, hero.getY())];
                            attackSeq(map, hero, attacker, monsters, action);
                            lastAction = "monster";
                            break;
                        case 3:
                            String whichFarmer = findFarmer(hero, swordFarmer, action);
                            Farmer npc;
                            if(whichFarmer.equals("sword")){
                                npc = swordFarmer;
                            } else {
                                npc = armorFarmer;
                            }
                            farmerSeq(map, hero, npc);
                            lastAction = "farmer";
                            break;
                        case 4:
                            hero.setPotions(hero.checkInventory() + 1);
                            lastAction = "potion";
                            int num = findPotion(hero.getX() - 1, hero.getY(), potion1);
                            if(num == 1){
                                potion1 = null;
                            } else {
                                potion2 = null;
                            }
                            map[hero.getY()][hero.getX()] = 9;
                            hero.setX(hero.getX() - 1);
                            break;
                        case 10:
                            lastAction = "stupid";
                            hero.setHP(hero.getHP() - 10);
                            break;
                    }
                    break;
                case "s":
                    switch(staticCheck(hero, map, action)){
                        case 0:
                            map[hero.getY()][hero.getX()] = 9;
                            hero.setY(hero.getY() + 1);
                            lastAction = action;
                            break;
                        case 2:
                            Monster attacker = monsters[findMonster(monsters, hero.getX(), hero.getY() + 1)];
                            attackSeq(map, hero, attacker, monsters, action);
                            lastAction = "monster";
                            break;
                        case 3:
                            String whichFarmer = findFarmer(hero, swordFarmer, action);
                            Farmer npc;
                            if(whichFarmer.equals("sword")){
                                npc = swordFarmer;
                            } else {
                                npc = armorFarmer;
                            }
                            farmerSeq(map, hero, npc);
                            lastAction = "farmer";
                            break;
                        case 4:
                            hero.setPotions(hero.checkInventory() + 1);
                            lastAction = "potion";
                            int num = findPotion(hero.getX(), hero.getY() + 1, potion1);
                            if(num == 1){
                                potion1 = null;
                            } else {
                                potion2 = null;
                            }
                            map[hero.getY()][hero.getX()] = 9;
                            hero.setY(hero.getY() + 1);
                            break;
                        case 10:
                            lastAction = "stupid";
                            hero.setHP(hero.getHP() - 10);
                            break;
                    }
                    break;
                case "d":
                    switch(staticCheck(hero, map, action)){
                        case 0:
                            map[hero.getY()][hero.getX()] = 9;
                            hero.setX(hero.getX() + 1);
                            lastAction = action;
                            break;
                        case 2:
                            Monster attacker = monsters[findMonster(monsters, hero.getX() + 1, hero.getY())];
                            attackSeq(map, hero, attacker, monsters, action);
                            lastAction = "monster";
                            break;
                        case 3:
                            String whichFarmer = findFarmer(hero, swordFarmer, action);
                            Farmer npc;
                            if(whichFarmer.equals("sword")){
                                npc = swordFarmer;
                            } else {
                                npc = armorFarmer;
                            }
                            farmerSeq(map, hero, npc);
                            lastAction = "farmer";
                            break;
                        case 4:
                            hero.setPotions(hero.checkInventory() + 1);
                            lastAction = "potion";
                            int num = findPotion(hero.getX() + 1, hero.getY(), potion1);
                            if(num == 1){
                                potion1 = null;
                            } else {
                                potion2 = null;
                            }
                            map[hero.getY()][hero.getX()] = 9;
                            hero.setX(hero.getX() + 1);
                            break;
                        case 10:
                            lastAction = "stupid";
                            hero.setHP(hero.getHP() - 10);
                            break;
                    }
                    break;
                case "e":
                    if(hero.checkInventory() > 0){
                        lastAction = "drink";
                        hero.setHP(100);
                        hero.setPotions(hero.checkInventory() - 1);
                    }
                    break;
                case "kill":
                    System.out.println("DED.");
                    return;
                default:
                    System.out.println("Invalid direction.");
                    break;
            }
            if(checkWin(monsters)){
                System.out.println("\fAll monsters killed, you win!!!");
                System.out.println("\nThe Hero ascended to the heavens, his deed finally completed...\n");
                promptEnterKey();
                System.out.println("\nThanks for playing!");
                System.out.println("\nCONNECTION TERMINATED. DATALOG 'human experiment' UPDATED.");
                break;
            }
            System.out.println("\f");
        }
    }
    
    public static int[] newCoord(int[][] tookSpots){
        int[] temp_array = new int[2];
        int x = (int)(Math.random() * 10);
        int y = (int)(Math.random() * 10);
        for(int j = 0; j < 11; j++){
            while(x == tookSpots[j][0] && y == tookSpots[j][1]){
                x = (int)(Math.random() * 10);
                y = (int)(Math.random() * 10);
            }
        }
        temp_array[0] = x;
        temp_array[1] = y;
        return temp_array;
    }
    
    public static boolean checkWin(Monster[] m){
        for(int i = 0; i < m.length; i++){
            if(m[i] != null){
                return false;
            }
        }
        return true;
    }
    
    public static int findPotion(int x, int y, Potion p){
        if(p == null){
            return 2;
        }
        if(p.getX() == x && p.getY() == y){
            return 1;
        } else {
            return 2;
        }
    }
    
    public static String updateStatus(String str){
        String temp_str = "";
        switch(str){
            case "w":
                temp_str = "Moved North";
                break;
            case "a":
                temp_str = "Moved East";
                break;
            case "s":
                temp_str = "Moved South";
                break;
            case "d":
                temp_str = "Moved West";
                break;
            case "potion":
                temp_str = "Picked up potion";
                break;
            case "monster":
                temp_str = "Killed monster";
                break;
            case "farmer":
                temp_str = "Met farmer";
                break;
            case "stupid":
                temp_str = "You walked into a wall and took 10 damage";
                break;
            case "drink":
                temp_str = "HP RESTORED!";
                break;
            default:
                temp_str = "Spawned in";
                break;
        }
        return temp_str;
    }
    
    public static String findFarmer(Hero h, Farmer sf, String direction){
        int x = h.getX();
        int y = h.getY();
        String temp_str = "";
        boolean swordOrNah = false;
        switch(direction){
            case "w":
                y--;
                if(x == sf.getX() && y == sf.getY()){
                    swordOrNah = true;
                }
                break;
            case "a":
                x--;
                if(x == sf.getX() && y == sf.getY()){
                    swordOrNah = true;
                }
                break;
            case "s":
                y++;
                if(x == sf.getX() && y == sf.getY()){
                    swordOrNah = true;
                }
                break;
            case "d":
                x++;
                if(x == sf.getX() && y == sf.getY()){
                    swordOrNah = true;
                }
                break;
        }
        if(swordOrNah){
            temp_str = "sword";
        } else {
            temp_str = "armor";
        }
        return temp_str;
    }
    
    public static Farmer whichFarmer(Farmer sf, Farmer af, int x, int y){
        Farmer return_f = new Farmer();
        if(sf.getX() == x && sf.getY() == y){
            return_f = sf;
        } else {
            return_f = af;
        }
        return return_f;
    }
    
    public static void printMap(int[][] master, Monster[] monsters, Farmer sf, Farmer af, Potion p1, Potion p2){
        for(int i = 0; i < 10; i++){
            for(int k = 0; k < 10; k++){
                if(master[i][k] == 0){
                    System.out.print(".\t");
                } else if(master[i][k] == 1){
                    System.out.print("👑\t");
                } else if(master[i][k] == 2){
                    if(monsters[findMonster(monsters, k, i)].getSeen()){
                        System.out.print("👹\t");
                    } else {
                        System.out.print(".\t");
                    }
                } else if(master[i][k] == 3){
                    Farmer current = whichFarmer(sf, af, i, k);
                    if(current.getSeen()){
                        System.out.print("🌾\t");
                    } else {
                        System.out.print(".\t");
                    }
                } else if(master[i][k] == 4){
                    System.out.print(".\t");
                } else if(master[i][k] == 9){
                    System.out.print("=\t");
                } else if(master[i][k] == 22){
                    System.out.print("X\t");
                } else {
                    System.out.print(master[i][k] + "\t");
                }
                if(k == 9){
                    if(i == 2){
                        System.out.print("\t\tKey:");
                    }
                    if(i == 3){
                        System.out.print("\t\t👑 - Hero");
                    }
                    if(i == 4){
                        System.out.print("\t\t👹 - Monster");
                    }
                    if(i == 5){
                        System.out.print("\t\t🌾 - Farmer");
                    }
                    if(i == 6){
                        System.out.print("\t\t🍷 - Potion");
                    }
                    System.out.println("\n\n");
                }
            }
        }
    }
    
    public static String checkNorth(Hero h, int[][] map){
        String temp_str = "";
        int x = h.getX();
        int y = h.getY() - 1;
        if(h.getY() == 0){
            temp_str = "A wall.";
            return temp_str;
        }
        switch(map[y][x]){
            case 9:
            case 0:
                temp_str = "Open plains.";
                break;
            case 2:
                temp_str = "A monster in the distance.";
                break;
            case 3:
                temp_str = "A Farmer in the distance.";
                break;
            case 4:
                temp_str = "A glint of glass.";
                break;
            case 22:
                temp_str = "A decaying corpse of a monster.";
                break;
            default:
                temp_str = "u f'd up";
                break;
        }
        return temp_str;
    }
    
    public static String checkEast(Hero h, int[][] map){
        String temp_str = "";
        int x = h.getX() + 1;
        int y = h.getY();
        if(h.getX() == 9){
            temp_str = "A wall.";
            return temp_str;
        }
        switch(map[y][x]){
            case 9:
            case 0:
                temp_str = "Open plains.";
                break;
            case 2:
                temp_str = "A monster in the distance.";
                break;
            case 3:
                temp_str = "A Farmer in the distance.";
                break;
            case 4:
                temp_str = "A glint of glass.";
                break;
            case 22:
                temp_str = "A decaying corpse of a monster.";
                break;
            default:
                temp_str = "u f'd up";
                break;
        }
        return temp_str;
    }
    
    public static String checkSouth(Hero h, int[][] map){
        String temp_str = "";
        int x = h.getX();
        int y = h.getY() + 1;
        if(h.getY() == 9){
            temp_str = "A wall.";
            return temp_str;
        }
        switch(map[y][x]){
            case 9:
            case 0:
                temp_str = "Open plains.";
                break;
            case 2:
                temp_str = "A monster in the distance.";
                break;
            case 3:
                temp_str = "A Farmer in the distance.";
                break;
            case 4:
                temp_str = "A glint of glass.";
                break;
            case 22:
                temp_str = "A decaying corpse of a monster.";
                break;
            default:
                temp_str = "u f'd up";
                break;
        }
        return temp_str;
    }
    
    public static String checkWest(Hero h, int[][] map){
        String temp_str = "";
        int x = h.getX() - 1;
        int y = h.getY();
        if(h.getX() == 0){
            temp_str = "A wall.";
            return temp_str;
        }
        switch(map[y][x]){
            case 9:
            case 0:
                temp_str = "Open plains.";
                break;
            case 2:
                temp_str = "A monster in the distance.";
                break;
            case 3:
                temp_str = "A Farmer in the distance.";
                break;
            case 4:
                temp_str = "A glint of glass.";
                break;
            case 22:
                temp_str = "A decaying corpse of a monster.";
                break;
            default:
                temp_str = "u f'd up";
                break;
        }
        return temp_str;
    }
    
    public static int staticCheck(Hero h, int[][] map, String direction){
        int temp = 0;
        int x = h.getX();
        int y = h.getY();
        switch(direction){
            case "w":
                y--;
                if(y < 0){
                    return 10;
                }
                switch(map[y][x]){
                    case 0:
                        temp = 0;
                        break;
                    case 2:
                        temp = 2;
                        break;
                    case 3:
                        temp = 3;
                        break;
                    case 4:
                        temp = 4;
                        break;
                }
                break;
            case "a":
                x--;
                if(x < 0){
                    return 10;
                }
                switch(map[y][x]){
                    case 0:
                        temp = 0;
                        break;
                    case 2:
                        temp = 2;
                        break;
                    case 3:
                        temp = 3;
                        break;
                    case 4:
                        temp = 4;
                        break;
                }
                break;
            case "s":
                y++;
                if(y > 9){
                    return 10;
                }
                switch(map[y][x]){
                    case 0:
                        temp = 0;
                        break;
                    case 2:
                        temp = 2;
                        break;
                    case 3:
                        temp = 3;
                        break;
                    case 4:
                        temp = 4;
                        break;
                }
                break;
            case "d":
                x++;
                if(x > 9){
                    return 10;
                }
                switch(map[y][x]){
                    case 0:
                        temp = 0;
                        break;
                    case 2:
                        temp = 2;
                        break;
                    case 3:
                        temp = 3;
                        break;
                    case 4:
                        temp = 4;
                        break;
                }
                break;
        }
        return temp;
    }
    
    public static void attackSeq(int[][] map, Hero hero, Monster attacker, Monster[] monsters, String direction){
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("\f");
        System.out.println("Monster met!\n");
        promptEnterKey();
        while(attacker.getHP() > 0 && hero.getHP() > 0){
            System.out.println("\f");
            System.out.println("Your stats: " + hero);
            System.out.println("Monster stats: " + attacker + "\n");
            System.out.println("Your turn!");
            System.out.println("1. Attack monster");
            System.out.println("2. Run away");
            System.out.print("Choice: ");
            int nextAction = scanner2.nextInt();
            if(nextAction == 1){
                int dmg = generateDmg(hero.getWeap());
                System.out.println("\nYou score a hit on the monster!");
                attacker.setHP(attacker.getHP() - dmg);
                System.out.println("Damage dealt: " + dmg + "\n");
                if(attacker.getHP() <= 0){
                    System.out.println("You kill the monster! Stepping around the corpse, you look around for your next move...\n");
                    monsters[attacker.getIndex()] = null;
                    hero.oneKill();
                    switch(direction){
                        case "w":
                            map[hero.getY() - 1][hero.getX()] = 22;
                            break;
                        case "a":
                            map[hero.getY()][hero.getX() - 1] = 22;
                            break;
                        case "s":
                            map[hero.getY() + 1][hero.getX()] = 22;
                            break;
                        case "d":
                            map[hero.getY()][hero.getX() + 1] = 22;
                            break;
                    }
                    break;
                }
            } else if(nextAction == 2){
                double chance = Math.random();
                if(attacker.getSpeed() == 0 && chance <= 0.75){
                    System.out.println("\nYou run away, leaving the monster far behind.");
                    attacker.setSeen(true);
                    break;
                } else if(attacker.getSpeed() == 1 && chance <= 0.5){
                    System.out.println("\nYou run away, leaving the monster far behind.");
                    attacker.setSeen(true);
                    break;
                } else if(attacker.getSpeed() == 2 && chance <= 0.25){
                    System.out.println("\nYou run away, leaving the monster far behind.");
                    attacker.setSeen(true);
                    break;
                } else {
                    System.out.println("\nYou ran away... but the monster caught up!\n");
                    promptEnterKey();
                }
            } else {
                System.out.println("Not a valid option.\n");
                promptEnterKey();
                continue;
            }
            System.out.println("Monster's turn!");
            int monster_dmg = (int)(Math.random() * 29) + 1;
            if(hero.getArmor()){
                monster_dmg = (int)(monster_dmg/3);
            }
            System.out.println("Monster attacks, dealing " + monster_dmg + " damage to the hero.\n\n");
            hero.setHP(hero.getHP() - monster_dmg);
            if(hero.getHP() <= 0){
                System.out.println("You died!");
                System.exit(0);
            }
            promptEnterKey();
        }
        promptEnterKey();
        System.out.println("\f");
    }
    
    public static void farmerSeq(int[][] m, Hero h, Farmer f){
        System.out.println("\f");
        System.out.println("You meet a farmer...");
        System.out.println("");
        promptEnterKey();
        switch(f.getType()){
            case "armor":
                System.out.println("Farmer: Ah, over here!");
                System.out.println("\n--You walk over to him--");
                System.out.println("\nFarmer: I see you have killed " + h.getKills() + " monsters.");
                promptEnterKey();
                if(h.getKills() >= 2){
                    System.out.println("\nFor your bravery, I give you my bronze armor!");
                    System.out.println("\n--You equipped Bronze Armor--");
                    h.setArmor(true);
                } else {
                    System.out.println("\nFarmer: You have not yet killed enough monsters to earn my favor...");
                    System.out.println("        Come back later after you have killed " + (2 - h.getKills()) + " more monsters.\n");
                }
                f.setSeen(true);
                promptEnterKey();
                break;
            case "sword":
                System.out.println("Farmer: Oho! Over here!");
                System.out.println("\n--You walk over to him--");
                System.out.println("\nFarmer: I see you have killed " + h.getKills() + " monsters.");
                System.out.println("");
                promptEnterKey();
                if(h.getKills() >= 4){
                    System.out.println("For your bravery, I give you my broadsword!");
                    System.out.println("\n--You equipped Broadsword--");
                    h.setNewWeap("sword");
                } else {
                    System.out.println("\nFarmer: You have not yet killed enough monsters to earn my favor...");
                    System.out.println("        Come back later after you have killed " + (4 - h.getKills()) + " more monsters.\n");
                }
                f.setSeen(true);
                promptEnterKey();
                break;
        }
        System.out.println("\f");
    }
    
    public static int findMonster(Monster[] monsters, int x, int y){
        int temp = 0;
        Monster current;
        for(int i = 0; i < monsters.length; i++){
            current = monsters[i];
            if(current == null){
                continue;
            }
            if(x == current.getX() && y == current.getY()){
                temp = current.getIndex();
            }
        }
        return temp;
    }
    
    public static int generateDmg(String type){
        int temp = 1;
        switch(type){
            case "dagger":
                temp = (int)(Math.random() * 19) + 11;
                break;
            case "sword":
                temp = (int)(Math.random() * 29) + 21;
                break;
        }
        return temp;
    }
    
    public static void promptEnterKey(){
        System.out.println("Press ENTER to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}