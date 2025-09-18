import greenfoot.*;

/**
 * Horní paddle – automaticky se aktivuje po odrazu od hráče.
 * Predikuje, kde míček dopadne v úrovni pálky (zohledňuje rychlost a odrazy od stěn).
 */
public class AutoEnemyPaddle extends Actor {
    private int speed;         
    private int baseSpeed;     
    private int width, height;

    private boolean active = false; 
    private int targetX;            

    public AutoEnemyPaddle(int w, int h, int s) {
        width = w;
        height = h;
        baseSpeed = s;
        speed = s;
        updateImage();
    }

    public void act() {
        if (active) {
            moveTowardTarget();
        }
    }

    /** Prediktivní pohyb k předpokládanému místu dopadu míčku */
    public void prepareToIntercept(Ball ball) {
        if (ball == null || getWorld() == null) {
            targetX = (int)getWorld().getWidth() / 2;
            active = true;
            return;
        }

        double angleRad = Math.toRadians(ball.getRotation());
        double ballSpeed = ball.getSpeed();                
        double vx = Math.cos(angleRad) * ballSpeed;        
        double vy = Math.sin(angleRad) * ballSpeed;         

        double x0 = ball.getX();
        double y0 = ball.getY();

        double paddleHalf = getImage().getHeight() / 2.0;
        double ballHalf   = ball.getImage().getHeight() / 2.0;

        double yTarget = getY() + paddleHalf + ballHalf;

        if (Math.abs(vy) < 1e-6) {
            targetX = (int)x0;
            active = true;
            return;
        }

        double steps = (yTarget - y0) / vy;

        if (steps <= 0) {
            targetX = (int)x0;
            active = true;
            return;
        }

        double xPred = x0 + vx * steps;

        double worldWidth = getWorld().getWidth();
        double minX = ballHalf;
        double maxX = worldWidth - ballHalf;

        while (xPred < minX || xPred > maxX) {
            if (xPred < minX) {
                xPred = 2 * minX - xPred;            
            } else if (xPred > maxX) {
                xPred = 2 * maxX - xPred;            
            }
        }

        int worldW = getWorld().getWidth();
        int paddleHalfInt = width / 2;
        double clamped = Math.max(paddleHalfInt, Math.min(xPred, worldW - paddleHalfInt));

        targetX = (int)Math.round(clamped);

        double dist = Math.abs(targetX - getX());
        double timeAvailable = steps; 
        if (timeAvailable > 0) {
            int requiredSpeed = (int)Math.ceil(dist / timeAvailable);
            int maxAllowed = Math.max(baseSpeed, baseSpeed * 4);
            if (requiredSpeed > speed) {
                speed = Math.min(requiredSpeed, maxAllowed);
            }
        }

        active = true;
    }

    /** po zásahu míčku zůstane paddle stát a obnoví základní rychlost */
    public void deactivate() {
        active = false;
        speed = baseSpeed;
    }

    private void moveTowardTarget() {
        int x = getX();
        int dx = targetX - x;
        if (Math.abs(dx) > 1) {
            int step = Math.min(Math.abs(dx), speed);
            if (dx > 0) x += step;
            else x -= step;
            setLocation(x, getY());
        } else {
            setLocation(targetX, getY());
        }
    }

    public void setSpeed(int s) {
        baseSpeed = s;
        speed = s;
    }

    public void setSize(int w, int h) {
        width = w;
        height = h;
        updateImage();
    }

    private void updateImage() {
        GreenfootImage img = new GreenfootImage("paddle.png");
        img.scale(width, height);
        setImage(img);
    }
}
