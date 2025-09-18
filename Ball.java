import greenfoot.*;

/* (neuvádím komentáře znovu, najdi a nahraď svůj Ball.java tímto kompletním obsahem) */

public class Ball extends Actor
{
    private static final int BALL_SIZE = 50;
    private static final int BOUNCE_DEVIANCE_MAX = 5;
    private static final int STARTING_ANGLE_WIDTH = 90;
    private static final int DELAY_TIME = 100;
    
    private int hitCount = 0;
    private boolean isMovingDown;
    private int level = 1;
    
    private int speed;
    private boolean hasBouncedHorizontally;
    private boolean hasBouncedVertically;
    private int delay;
    
    public Ball(){
        this.isMovingDown = true;
        createImage();
        init(); 
    }
    
    public int getLevel() {
        return this.level;
    }

    // getter pro predikce
    public int getSpeed() {
        return speed;
    }

    private void createImage(){
        GreenfootImage ballImage = new GreenfootImage("ball.png");
        ballImage.scale(BALL_SIZE, BALL_SIZE);
        setImage(ballImage);
    }

    public void act() {
        if (delay > 0){
            delay--;
        }else{
            move(speed);
            checkBounceOffWalls();
            checkBounceOffCeiling();
            checkBounceOffPaddle();
            checkBounceOffEnemyPaddle();
            checkBounceOffAutoEnemyPaddle();
            checkRestart();
        }
    }    
    
    private boolean isTouchingSides() {
        return (getX() <= BALL_SIZE/2 || getX() >= getWorld().getWidth() - BALL_SIZE/2);
    }

    private boolean isTouchingCeiling() {
        return (getY() <= BALL_SIZE/2);
    }

    private boolean isTouchingFloor() { 
        return (getY() >= getWorld().getHeight() - BALL_SIZE/2);
    }

    private void checkBounceOffWalls() {
        if (isTouchingSides()){
            if (! hasBouncedHorizontally){
                Greenfoot.playSound("wall.wav");
                revertHorizontally();
            }
        } else {
            hasBouncedHorizontally = false;
        }
    }

    private void checkBounceOffCeiling(){
        // pokud se dotýká stropu, zkontrolujeme zda jej neměl chytnout AutoEnemyPaddle
        if (isTouchingCeiling()){
            Actor top = getOneIntersectingObject(AutoEnemyPaddle.class);
            if (top == null) {
                // nikdo nechytl míček — bod pro hráče
                if (getWorld() instanceof PingWorld) {
                    ((PingWorld)getWorld()).playerScoresGame();
                } else {
                    // fallback: revert
                    if (! hasBouncedVertically){
                        Greenfoot.playSound("wall.wav");
                        this.isMovingDown = true;
                        revertVertically();
                    }
                }
            } else {
                // pokud tam je AutoEnemyPaddle, necháme normální odraz řešit checkBounceOffAutoEnemyPaddle()
                if (! hasBouncedVertically){
                    Greenfoot.playSound("wall.wav");
                    this.isMovingDown = true;
                    revertVertically();
                }
            }
        }else{
           if (getOneIntersectingObject(Paddle.class) == null) {
              hasBouncedVertically = false;
           }
        }
    }
        
    private void checkBounceOffPaddle(){
        Actor paddle = getOneIntersectingObject(Paddle.class);
        if (paddle != null && !hasBouncedVertically && isMovingDown){
            this.isMovingDown = false;
            move(-speed); 
            revertVertically();
            hasBouncedVertically = true;
            hitCount++;
            Greenfoot.playSound("paddle.wav");
            if(hitCount%10==0){
                speed++;
                level++;
            }

            // Aktivace horní AutoEnemyPaddle po odrazu od hráče
            if (getWorld() instanceof PingWorld) {
                ((PingWorld)getWorld()).activateTopEnemyPaddle(this);
            }
        }
    }
    
    private void checkBounceOffEnemyPaddle(){
        Actor enemy = getOneIntersectingObject(EnemyPaddle.class);
        if (enemy != null){
            if(getY() > enemy.getY() && this.isMovingDown == false && !hasBouncedVertically){ 
                Greenfoot.playSound("paddle.wav");
                move(-speed); 
                revertVertically();
                hasBouncedVertically = true;
                this.isMovingDown = true;
            }
        }else {
            if (!isTouchingCeiling() && getOneIntersectingObject(Paddle.class) == null) {
                hasBouncedVertically = false;
            }
        }
    }

    private void checkBounceOffAutoEnemyPaddle(){
        Actor topEnemy = getOneIntersectingObject(AutoEnemyPaddle.class);
        if (topEnemy != null){
            // jsme pod pálkou a míček jde nahoru
            if(getY() < topEnemy.getY() && this.isMovingDown == false && !hasBouncedVertically){ 
                Greenfoot.playSound("paddle.wav");
                move(-speed); 
                revertVertically();
                hasBouncedVertically = true;
                this.isMovingDown = true;

                // Deaktivace horní AutoEnemyPaddle po zásahu míčku
                if (getWorld() instanceof PingWorld) {
                    ((PingWorld)getWorld()).topEnemyPaddleHit();
                }
            }
        } else {
            if (!isTouchingCeiling() && getOneIntersectingObject(Paddle.class) == null) {
                hasBouncedVertically = false;
            }
        }
    }

    private void checkRestart() {
        if (isTouchingFloor()) {
            // bod pro počítač (hráč minul)
            if (getWorld() instanceof PingWorld) {
                ((PingWorld)getWorld()).computerScoresGame();
            } else {
                Greenfoot.playSound("gameover.wav");
                Greenfoot.delay(76);
                Greenfoot.setWorld(new GameOverWorld());
            }
        }
    }

    private void revertHorizontally() {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX)- BOUNCE_DEVIANCE_MAX / 2;
        setRotation((180 - getRotation()+ randomness + 360) % 360);
        hasBouncedHorizontally = true;
    }

    private void revertVertically() {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX)- BOUNCE_DEVIANCE_MAX / 2;
        setRotation((360 - getRotation()+ randomness + 360) % 360);
        hasBouncedVertically = true;
    }

    private void init() {
        speed = 2;
        level = 1;
        hitCount = 0;
        delay = DELAY_TIME;
        hasBouncedHorizontally = false;
        hasBouncedVertically = false;
        setRotation(Greenfoot.getRandomNumber(STARTING_ANGLE_WIDTH)+STARTING_ANGLE_WIDTH/2);
    }
}
