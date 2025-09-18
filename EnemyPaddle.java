import greenfoot.*;

public class EnemyPaddle extends Actor
{
    private int width;
    private int height;
    private int speed;
    private int dx;

    /**
     * Konstruktor s možností zadat rozměry a rychlost.
     */
    public EnemyPaddle(int width, int height, int speed)
    {
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.dx = speed; 
        createImage();
    }

    public void act() 
    {
        tryChangeDirection();
        setLocation(getX() + dx, getY());
    }    

    /**
     * Otočí směr, když paddle narazí na pravý okraj a přesune ho na levý.
     */
    private void tryChangeDirection(){
        int worldWidth = getWorld().getWidth();
        int maxY = getWorld().getHeight() - 200; 

        if (getX() + getImage().getWidth()/2 >= worldWidth) {
            int randomY = Greenfoot.getRandomNumber(maxY - 50) + 50;
            setLocation(getImage().getWidth()/2, randomY);
        }
    }

    private void createImage()
    {
        GreenfootImage image = new GreenfootImage("paddle.png");
        image.scale(width, height);
        setImage(image);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        createImage();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        if (dx < 0) dx = -speed;
        else dx = speed;
    }
}
