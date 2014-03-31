//This class handles all of the battle functions needed for a standard battle

package textrpg;

import java.util.Random;
import textrpg.monsters.Monster;
import java.util.Scanner;
import textrpg.items.Item;
import textrpg.skills.Skill;

public final class Battle
{
    Scanner scan = new Scanner(System.in);
    Player hero;//not sure if health will recover after battle
    Monster monster;
    int turns;
    Random rand = new Random();
    
    
    public Battle(Player h, Monster m)
    {
        hero = h;
        monster = m;
        turns = 1;
        
        Game.printBreak();
        System.out.println("You have engaged in battle with " + m.getName() + "!");
        
        if(moveFirst()){playerTurn();}
        else{monsterTurn();turns = 2;}
                    
        while(hero.getCurrentHealth() > 0 && monster.getCurrentHealth() > 0)
        {
            System.out.println("-----");
            if(turns%2 == 1){monsterTurn();}
            else{playerTurn();}
            turns++;
        }
        
        //if the player doesn't win then game over!!!!!!
        System.out.println("The battle is like done yo.");//method for this probably
        h.setGold(hero.getGold() + m.getGoldWorth());
        h.setCurrentExp(hero.getCurrentExp() + m.getExpWorth());//need a way to check if it's ever over
        for(Item i: m.getLoot())
        {
            h.addInventory(i);
        }
        
        Game.printBreak();
    }
    
    public boolean moveFirst()//calculates who gets the first turn based on agility
    {
        if(hero.getCurrentAgility() > monster.getCurrentAgility())
            return true;
        else
            return false;
    }
    
    public void playerTurn()//need to organize this like totally better
    {
        System.out.println("HP: " + hero.getCurrentHealth() + " MP: " + hero.getCurrentMana());
        decidePlayerAction();  
    }
    
    public void decidePlayerAction()
    {
        int pass = 0;//set to 1 if the player makes a valid move
        
        do
        {
            System.out.println("Attack = 1, Skills = 2, Items = 3");
            
            int input = scan.nextInt();
            scan.nextLine();

            int dmg = 0;
            if(input == 1) //separate method make you should
            {
                for (Skill skill : hero.getJob().getSkills()) 
                    if (skill.getSkillName().equals("Attack")) 
                        dmg = skill.use();

                monster.setCurrentHealth(monster.getCurrentHealth() - dmg);
                System.out.println("Did " + dmg + " damage! Remaining HP of monster:" + monster.getCurrentHealth());
                pass = 1;//PASSED!
            }
            else if(input == 2)//see previous
            {
                for(int i = 0; i < hero.getJob().getSkills().length; i++) 
                {
                    if(!hero.getJob().getSkills()[i].getSkillName().equals("Attack"))//Need to remove getSkills from adding Attack  
                        System.out.print(hero.getJob().getSkills()[i].getSkillName() + "(" + i + ")" + ", ");
                }
                System.out.println();//print statement above

                System.out.println("Which skill will you use?");
                int skillChosen = scan.nextInt();
                scan.nextLine();//maybe i should just cast ints...
                if(hero.getCurrentMana() >= hero.getJob().getSkills()[skillChosen].getManaCost())
                {    
                    dmg = hero.getJob().getSkills()[skillChosen].use();
                    
                    if(monster.getWeakness() == hero.getJob().getSkills()[skillChosen].getType())
                        dmg = (int)(dmg * 1.5);
                    
                    monster.setCurrentHealth(monster.getCurrentHealth() - dmg);
                    hero.setCurrentMana(hero.getCurrentMana() - hero.getJob().getSkills()[skillChosen].getManaCost());//uses up mana
                    
                    System.out.println("Did " + dmg + " damage! Remaining HP of monster:" + monster.getCurrentHealth());
                    pass = 1;//PASSED!
                }
                else
                {
                    System.out.println("Not enough mana!");
                }
            }
            else//you know what to do
            {
                for(int i = 0; i < hero.getInventory().size(); i++)
                    System.out.print(hero.getInventory().get(i).getName() + "(" + i + ")" + ", ");
                System.out.println();

                System.out.println("Which item will you use?");
                int itemChosen = Integer.parseInt(scan.nextLine());

                if(hero.getInventory().get(itemChosen).getItemType() == 1)//if the item type is consumable
                {
                    //probably a better way to do this
                    int[] statusModified = hero.getInventory().get(itemChosen).use();//need to see which stats to modify

                    hero.useItem(statusModified);
                    pass = 1;//PASSED!
                }
                else
                {
                    hero.getInventory().get(itemChosen).itemError();
                }
            }
        
        }while(pass != 1);
    }
    
    public void monsterTurn()
    {
        System.out.println(monster.getName() + "'s turn!");
        System.out.println("Zesty is gay!!!");
        //monster.getSkills();
    }
}
