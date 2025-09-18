import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LevelText here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LevelText extends Actor
{
    
    /**
     * Act - do whatever the LevelText wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public void act()
    {
        getWorld().setPaintOrder(Ball.class, Paddle.class, EnemyPaddle.class, LevelText.class);

    }
    
    public void upgradeLevel(int level){
        GreenfootImage textImage = new GreenfootImage("Game Level: " + level , 24 , Color.BLACK , new Color(0,0,0,0));
        setImage(textImage);
    }
}
