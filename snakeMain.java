/*
* To-do: a menu when game starts
*
*
* */


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JTextField;


public class snakeMain
{

    //Global vars
    // chars
    final char[][] map = new char[21][51]; // stores the map

    //ints
    int Dir = 5; // direction
    int score = 0; // score
    int playerRow = 0; // players row location
    int playerCol = 0; // player col location
    final int[][] foodLoc = new int[1][2];
    int[][] snakeTail = tailArray();
    int speed = 250; // how fast the game refreshes.

    //bools
    boolean foodExists = false; // does food exist
    boolean playerAlive = true; // is player alive
    boolean playerExist = false; // does the player exist

    // fills array with map data
    private void startup()
    {
        Scanner console = new Scanner(System.in);
        int selection;

        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        System.out.println("");
        System.out.println("Please select a difficulty: ");

        if(!console.hasNextInt())
        {
            System.out.println("Please enter a valid number.");
            console.next();
        }

        selection = console.nextInt();

        if(selection == 1)
            speed = 300;
        else if (selection == 2)
            speed = 150;
        else if (selection == 3)
            speed = 50;
        else
        {
            System.out.println("Not a recognised number defaulting to medium settings ");
        }


        for(int i=0; i < 51; i++)
        {
            map[0][i] = '#';
        }

        map[0][50] = '~';

        for(int row=1; row < 20; row++)
        {
            map[row][0] = '#';

            for (int col=1; col < 50; col++)
            {
                map[row][col] = ' ';
            }

            map[row][49] = '#';
            map[row][50] = '~';
        }

        for(int i=0; i < 50; i++)
        {
            map[20][i] = '#';
        }

    }
    // updates the map
    private void updateMap()
    {

        if (!foodExists)
        {
            spawnFood();
        }

        if (playerAlive)
        {
            for (int row=0; row < 21; row++)
            {
                for(int col=0; col < 51; col++)
                {
                    if (map[row][col] == '~')
                    {
                        System.out.println();
                    }
                    else
                    {
                        System.out.print(map[row][col]);
                    }


                }

            }
            System.out.println();
            System.out.println("Score: " + score);
        }
        else
        {
            
            Scanner console = new Scanner(System.in);
            String selection;

            System.out.println("Game Over");
            System.out.println("Score: " + score);
            System.out.println("Would you like to play again (y/n)");

            selection = console.next();

          if(selection.equals("y") || selection.equals("Y"))
          {
            snakeMain newGame = new snakeMain();
            newGame.run();
          }
          else
          {
              System.exit(0);
          }
        }
    }

    // gets keyboard input
    private void input()
    {
        JFrame frame = new JFrame("Key listen");

        Container Pane = frame.getContentPane();

        KeyListener listener = new KeyListener()

        {

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                int key = e.getKeyCode();


                if (key == KeyEvent.VK_W)
                {
                    //System.out.println("W was pressed");

                    Dir = 1;

                }
                else if (key == KeyEvent.VK_A)
                {
                    //System.out.println("A was pressed");
                    Dir = 3;
                }
                else if (key == KeyEvent.VK_S)
                {
                    //System.out.println("S was pressed");
                    Dir = 0;
                }
                else if (key == KeyEvent.VK_D)
                {
                    //System.out.println("D was pressed");
                    Dir = 2;
                }
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyTyped(KeyEvent arg0) {
                // TODO Auto-generated method stub

            }
        };
        JTextField textField = new JTextField();

        textField.addKeyListener(listener);

        Pane.add(textField, BorderLayout.CENTER);

        frame.pack();

        frame.setVisible(true);
    }

    // generates random map coordinates, either col or row can be specified
    private int randomMapCoord(String args0)
    {
        Random rand = new Random();



        if (args0.equals("col") )
        {
            return rand.nextInt(48) + 1;
        }
        else if (args0.equals("row") )
        {
            return rand.nextInt(19) + 1;
        }
        else
        {
            return 0;
        }


    }

    // spawns the food
    private void spawnFood()
    {

        int foodRow, foodCol;

        foodRow = randomMapCoord("row");
        foodCol = randomMapCoord("col");

        map[foodRow][foodCol] = '*';

        foodLoc[0][0] = foodRow;
        foodLoc[0][1] = foodCol;

        foodExists = true;
    }

    private void checkEnvironment() // checks if player is interacting with the environment
    {
        if (map[playerRow][playerCol] == '#' || map[playerRow][playerCol] == 'Q' )
        {
            playerAlive = false;
        }


        if (map[playerRow][playerCol] == '*')
        {
            foodExists = false;
            snakeTail = tailArray();
            score++;
        }
    }



    private int[][] tailArray()
    {
        // try this idea
        int[][] tempTail = new int[score + 1][2];

        for(int i = 0; i < score - 1; i++)
        {
            tempTail[i][0] = snakeTail[i][0];
            tempTail[i][1] = snakeTail[i][1];
        }


        return tempTail;
    }



    private void updateTail(int row, int col)
    {
        // update tail function

        if (snakeTail.length >= 2)
            for(int i = 0; i < snakeTail.length - 1; i++)
            {
                snakeTail[snakeTail.length - 1 - i][0] = snakeTail[snakeTail.length -2 -i][0];
                snakeTail[snakeTail.length - 1 - i][1] = snakeTail[snakeTail.length -2 -i][1];
            }



        snakeTail[0][0] = row;
        snakeTail[0][1] = col;


       for(int rowA=1; rowA < 20; rowA++)
        {
            for (int colA=1; colA < 49; colA++)
            {
                map[rowA][colA] = ' ';
            }
        }



        for(int i = 0; i <= snakeTail.length - 1; i++)
        {
            map[snakeTail[i][0]][snakeTail[i][1]] = 'Q';
        }


        map[foodLoc[0][0]][foodLoc[0][1]] = '*';


    }

    //Handles everything related to the player
    private void Player()
    {

        Random rand = new Random();


        if (!playerExist) // spawn player
        {
            playerRow = randomMapCoord("row");
            playerCol = randomMapCoord("col");

            map[playerRow][playerCol] = 'Q';
            playerExist = true;
            playerAlive = true;

        }

        if (Dir == 5) // if 5 generate random direction
        {
            Dir = rand.nextInt(3);
            System.out.println(Dir);
        }

        while (playerAlive) // movement and scoring
        {
            if (Dir == 0)
            {
                // move down

                playerRow++;

                checkEnvironment();


                //map[playerRow][playerCol] = 'Q';


                updateTail(playerRow, playerCol);


            }
            else if (Dir == 1)
            {
                // move up

                playerRow--;

                checkEnvironment();


              //  map[playerRow][playerCol] = 'Q';

                updateTail(playerRow, playerCol);
            }
            else if (Dir == 2)
            {
                // move right



                playerCol++;

                checkEnvironment();


               // map[playerRow][playerCol] = 'Q';

                updateTail(playerRow, playerCol);
            }
            else if (Dir == 3)
            {


                playerCol--;

                checkEnvironment();


              //  map[playerRow][playerCol] = 'Q';
                updateTail(playerRow, playerCol);

            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
            updateMap();
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }




    }


    private void run()
    {
        startup();
        updateMap();

        Thread InputThread = new Thread()
        {
            public void run()
            {
                input();
            }
        };

        InputThread.start();

        while (true)
        {
            Player();
        }

    }


    public static void main(String[] args)
    {
        snakeMain main = new snakeMain();
        main.run();
    }

}

