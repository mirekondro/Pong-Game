import greenfoot.*;

/**
 * Intro World – úvodní obrazovka hry
 */
public class IntroWorld extends World
{
    private static final int WORLD_WIDTH = 500;
    private static final int WORLD_HEIGHT = 700;

    public IntroWorld()
    {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1); 
        
        GreenfootImage bg = new GreenfootImage("introbg.jpg");
        bg.scale(WORLD_WIDTH, WORLD_HEIGHT); 
        setBackground(bg);

        GreenfootImage coverImage = new GreenfootImage("CoverImage.png");
        coverImage.scale(250, 200); 

        int coverX = WORLD_WIDTH/2 - coverImage.getWidth()/2;
        int coverY = WORLD_HEIGHT/2 - coverImage.getHeight()/2 - 40; 
        bg.drawImage(coverImage, coverX, coverY);

        Font customFont = new Font("Arial", true, false, 28); 
        GreenfootImage textImage = new GreenfootImage("Press ENTER to start game...", 28, Color.WHITE, new Color(0,0,0,0));
        textImage.setFont(customFont);

        int textX = WORLD_WIDTH/2 - textImage.getWidth()/2;
        int textY = coverY + coverImage.getHeight() + 20; 
        bg.drawImage(textImage, textX, textY);
    }
    
    public void act()
    {
        String key = Greenfoot.getKey();
        if (key != null && key.equals("enter"))
        {
            Greenfoot.setWorld(new PingWorld(true));
        }
    }
}
