import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class GameOverWorld extends World
{
    private int timer; 
    private GreenfootImage background;

    public GameOverWorld()
    {    
        super(500, 700, 1); 
        background = new GreenfootImage("gameover.jpg");
        setBackground(background);

        timer = 350; 
    }

    public void act()
    {
        // každé snímkování vytvoříme text se správným fontem
        int secondsLeft = timer / 50;

        // zkopírujeme background, abychom na něj mohli znovu kreslit text
        GreenfootImage bg = new GreenfootImage(background); 

        // font jako v IntroWorld
        Font customFont = new Font("Arial", true, false, 28); 

        // vytvoření textového obrázku
        GreenfootImage textImage = new GreenfootImage("Restart in: " + secondsLeft, 28, Color.WHITE, new Color(0,0,0,0));
        textImage.setFont(customFont);

        // vykreslení textu doprostřed dole
        int textX = getWidth()/2 - textImage.getWidth()/2;
        int textY = getHeight() - 80; // trošku výš od spodní hrany
        bg.drawImage(textImage, textX, textY);

        // nastavení nového pozadí
        setBackground(bg);

        timer--;

        if (timer <= 0 || Greenfoot.isKeyDown("enter"))
        {
            Greenfoot.setWorld(new PingWorld(true));
        }
    }
}
