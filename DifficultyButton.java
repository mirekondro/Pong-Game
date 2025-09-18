import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DifficultyButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DifficultyButton extends Actor
{
    private String difficulty;  // "easy" / "normal" / "hard"

    public DifficultyButton(String difficulty, int width, int height, Color color) {
        this.difficulty = difficulty;

        // draw button image
        GreenfootImage img = new GreenfootImage(width, height);
        img.setColor(color);
        img.fillRect(0, 0, width, height);

        img.setColor(Color.BLACK);
        img.drawString(difficulty.toUpperCase(), 10, height/2 + 5);

        setImage(img);
    }

    public String getDifficulty() {
        return difficulty;
    }
    
    public void act()
    {
        // Add your action code here.
    }
}
