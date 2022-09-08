package com.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener {

    JLabel heading;
    JButton restart = new JButton("Restart");
    Font f = new Font("", Font.BOLD, 40);

    JPanel gameboard = new JPanel();
    JPanel restartButton = new JPanel();
    JButton []btns = new JButton[9];

    //game instances
    int []gameChances = {2,2,2,2,2,2,2,2,2};
    int activePlayer = 0;
    int winner;

    //winning positions
    int [][]wps = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };

    boolean gameOver = false;

    MyGame(){
        super.setTitle("Tic Tac Toe Game");
        super.setSize(850,850);
        super.setLocation(300, 50);
        ImageIcon logo = new ImageIcon("img/logo.png");
        setIconImage(logo.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.createGUI();
        setVisible(true);
    }

    public void createGUI(){
        this.getContentPane().setBackground(Color.decode("#1f2e46"));
        this.setLayout(new BorderLayout());

        //creating heading
        heading = new JLabel("Tic Tac Toe");
        heading.setFont(f);
        heading.setForeground(Color.white);
        this.add(heading,BorderLayout.NORTH);
        heading.setHorizontalAlignment(SwingConstants.CENTER);


        //restart button
        restartButton.add(restart);
        this.add(restartButton,BorderLayout.SOUTH);
        restart.setFont(f);
        restart.setBackground(Color.white);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new MyGame();
            }
        });

        //creating clock
        /*   clocklabel = new JLabel("Clock");
        clocklabel.setFont(f);
        clocklabel.setForeground(Color.white);
        this.add(clocklabel,BorderLayout.SOUTH);
        clocklabel.setHorizontalAlignment(0);

        //timer
        Thread t =  new Thread(){
            public void run(){
                try{
                    while(true) {
                        String datetime = new Date().toLocaleString();
                        clocklabel.setText(datetime);

                        Thread.sleep(1000);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        t.start();
*/

        //Panel GUI
        gameboard.setLayout(new GridLayout(3, 3));
        for(int i=0;i<9;i++){
            JButton btn = new JButton();
            btn.setBackground(Color.decode("#deeaff"));
            gameboard.add(btn);
            btns[i] = btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i));
        }
        this.add(gameboard,BorderLayout.CENTER);
    }

    //game logic
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton currButton = (JButton) e.getSource();
        String nameStr = currButton.getName();
        int name = Integer.parseInt(nameStr.trim());

        if(gameOver==true){
            JOptionPane.showMessageDialog(this, "Game Over!");
            return;
        }

        if(gameChances[name]==2){
            if(activePlayer==1){
                currButton.setIcon(new ImageIcon("img/x.png"));
                gameChances[name]=activePlayer;
                activePlayer=0;
            }else{
                currButton.setIcon(new ImageIcon("img/0.png"));
                gameChances[name]=activePlayer;
                activePlayer=1;
            }

            //winner logic
            for(int []temp:wps){
                if((gameChances[temp[0]]==gameChances[temp[1]]) && (gameChances[temp[1]]==gameChances[temp[2]]) && gameChances[temp[0]]!=2){
                    winner=gameChances[temp[0]];
                    gameOver=true;
                    JOptionPane.showMessageDialog(null, "Player "+(winner+1)+" has won!!");
                    int i = JOptionPane.showConfirmDialog(this, "Do you wish to continue?");
                    if(i==0){
                        this.setVisible(false);
                        new MyGame();
                    }else if(i==2){
                        System.exit(0);
                    }else{

                    }
                    break;
                }
            }

            //draw logic
            int counter = 0;
            for(int x:gameChances){
                if(x==2){
                    counter++;
                    break;
                }
            }
            if(counter==0 && gameOver==false){
                JOptionPane.showMessageDialog(null, "It's a draw!");
                int i = JOptionPane.showConfirmDialog(this, "Play Again?");
                if(i==0){
                    this.setVisible(false);
                    new MyGame();
                }else if(i==2){
                    System.exit(0);
                }else {

                }
                gameOver=true;
            }
        }else{
            JOptionPane.showMessageDialog(this, "Position already occupied!!!");
        }
    }
}
