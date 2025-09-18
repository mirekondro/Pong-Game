import greenfoot.*;

public class PingWorld extends World
{
    private static final int WORLD_WIDTH = 500;
    private static final int WORLD_HEIGHT = 700;

    private Ball ball;
    private LevelText levelText;

    int buttonX = 80;   
    int buttonY = 50;   
    int spacing = 40;   

    private String difficulty = "normal";
    
    private EnemyPaddle enemyPaddle;

    private AutoEnemyPaddle enemyPaddleTop;

    private DifficultyButton easyButton;
    private DifficultyButton normalButton;
    private DifficultyButton hardButton;

    private int playerGamesWon = 0;
    private int computerGamesWon = 0;

    public PingWorld(boolean gameStarted){
        super(WORLD_WIDTH, WORLD_HEIGHT, 1); 
        
        setBackground(new GreenfootImage("pitch.png"));

        if (gameStarted){
            levelText = new LevelText(); 
            addObject(levelText, WORLD_WIDTH  - 100, 50);

            ball = new Ball();
            addObject(ball, WORLD_WIDTH/2, WORLD_HEIGHT/2);
            addObject(new Paddle(100,20), WORLD_WIDTH/2, WORLD_HEIGHT - 50);

            easyButton = new DifficultyButton("easy", 120, 30, Color.GREEN);
            normalButton = new DifficultyButton("normal", 120, 30, Color.YELLOW);
            hardButton = new DifficultyButton("hard", 120, 30, Color.RED);

            addObject(easyButton, buttonX, buttonY);              
            addObject(normalButton, buttonX, buttonY + spacing);  
            addObject(hardButton, buttonX, buttonY + spacing*2);  

            addEnemyPaddle();
        }else{
            Greenfoot.setWorld(new IntroWorld());
        }
    }
    
    public void act(){
        if(ball!=null){
            levelText.upgradeLevel(ball.getLevel());
        }

        showText("Player wins: " + playerGamesWon, 120, 20);
        showText("Computer wins: " + computerGamesWon, WORLD_WIDTH - 140, 20);

        checkButtonClicks();
    }

    private void addEnemyPaddle() {
        int width = 80; 
        int height = 20;
        int speed = 0;

        if ("easy".equals(difficulty)) {
            width = 130;
            speed = 1;
        } else if ("normal".equals(difficulty)) {
            width = 80;
            speed = 2;
        } else if ("hard".equals(difficulty)) {
            width = 50;
            speed = 4;
        }

        int y = Greenfoot.getRandomNumber(WORLD_HEIGHT - 150) + 80;
        if (enemyPaddle == null) {
            enemyPaddle = new EnemyPaddle(width, height, speed);
            addObject(enemyPaddle, 60, y);
        } else {
            enemyPaddle.setSize(width, height);
            enemyPaddle.setSpeed(speed);
            enemyPaddle.setLocation(60, y);
        }

        if (enemyPaddleTop == null) {
            enemyPaddleTop = new AutoEnemyPaddle(width, height, speed);
            addObject(enemyPaddleTop, WORLD_WIDTH/2, 30); 
        } else {
            enemyPaddleTop.setSize(width, height);
            enemyPaddleTop.setSpeed(speed);
            enemyPaddleTop.setLocation(WORLD_WIDTH/2, 30);
        }
    }

    /**
     * Detekuje kliknutí na buttony obtížnosti
     */
    private void checkButtonClicks() {
        if (Greenfoot.mouseClicked(easyButton)) {
            difficulty = "easy";
            addEnemyPaddle();
        } else if (Greenfoot.mouseClicked(normalButton)) {
            difficulty = "normal";
            addEnemyPaddle();
        } else if (Greenfoot.mouseClicked(hardButton)) {
            difficulty = "hard";
            addEnemyPaddle();
        }
    }

    public void restartBall() {
        if (ball != null) {
            removeObject(ball);
        }
        ball = new Ball();
        addObject(ball, WORLD_WIDTH/2, WORLD_HEIGHT/2);
    }

    public void activateTopEnemyPaddle(Ball b) {
        if (enemyPaddleTop != null) {
            enemyPaddleTop.prepareToIntercept(b);
        }
    }

    public void topEnemyPaddleHit() {
        if (enemyPaddleTop != null) {
            enemyPaddleTop.deactivate();
        }
    }

    public void playerScoresGame() {
        playerGamesWon++;
        restartBall();

        if (playerGamesWon >= 5) {
            Greenfoot.setWorld(new GameOverWorld());
        }
    }

    public void computerScoresGame() {
        computerGamesWon++;
        restartBall();

        if (computerGamesWon >= 5) {
            Greenfoot.setWorld(new GameOverWorld());
        }
    }
}
