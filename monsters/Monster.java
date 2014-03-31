package textrpg.monsters;

import textrpg.Entity;
import textrpg.items.*;
import textrpg.skills.Skill;

public class Monster extends Entity
{
    private Item[] loot;
    private int goldWorth;
    private int expWorth;
    private int weakness;//the elemental weakness the monster has
    //1 = physical, 2 = fire, 3 = water, 4 = lightning, 5 = ice, more?!
    
    
    public Item[] getLoot(){return loot;}
    public void setLoot(Item[] l){loot = l;}
    
    public int getGoldWorth(){return goldWorth;}//usually called in a statement multiplied by a random decimal 
    public void setGoldWorth(int g){goldWorth = g;}
    
    public int getExpWorth(){return expWorth;}
    public void setExpWorth(int i){expWorth = i;}
    
    public int getWeakness(){return weakness;}
    public void setWeakness(int i){weakness = i;}
    
    //public Skill[] getSkills(){return blahhh;}
}
