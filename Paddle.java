import greenfoot.*;


/**
 * A paddle is an object that goes back and forth. Though it would be nice if balls would bounce of it.
 * 
 * @author The teachers 
 * @version 1
 */
public class Paddle extends Actor
{
    private int width;
    private int height;
    private int dx;

    /**
     * Constructs a new paddle with the given dimensions.
     */
    public Paddle(int width, int height)
    {
        this.width = width;
        this.height = height;
        dx = 1;
        createImage();
    }

    /**
     * Act - do whatever the Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.isKeyDown("left") && getX() > width / 2 ){
           setLocation(getX() - 10 , getY());
        }else if (Greenfoot.isKeyDown("right") && getX() < getWorld().getWidth() - width / 2 ){
           setLocation(getX() + 10 , getY());
        }
    }    

    /**
     * Will rotate the paddle 180 degrees if the paddle is at worlds edge.
     */
    private void tryChangeDirection()
    {

        if(getX() + width/2 >= getWorld().getWidth() || getX() - width/2 <= 0)
        {
            dx = dx * -1;
        }
    }

    /**
     * Creates and sets an image for the paddle, the image will have the same dimensions as the paddles width and height.
     */
    private void createImage()
    {
        GreenfootImage image = new GreenfootImage("paddle.png");
        image.scale(width, height); 
        setImage(image);
    }

}
