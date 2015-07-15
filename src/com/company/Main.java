package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.*;

public class Main extends JFrame {
    final int height = 100;
    final int buffer = 50;
    final int sticker = 10;

    public void paint(Graphics g) {
            Image img = createImage();
            g.drawImage(img,0,0,this);
        }
        public BufferedImage createImage(){
            BufferedImage buffimg = new BufferedImage(1500,1000,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) buffimg.getGraphics();
            g.setColor(Color.white);
            g.fillRect(0,0,1500,1000);
            for (int i = 0; i < Solver.faces.length; i++) {
            int co_x, co_y;
            String side;
            switch(i){
                case 0: co_x = 3*height;
                        co_y = 6*height;
                        side = "D";
                        break;
                case 1: co_x = 0;
                        co_y = 3*height;
                        side = "L";
                        break;
                case 2: co_x = 3*height;
                        co_y = 3*height;
                        side = "F";
                        break;
                case 3: co_x = 6*height;
                        co_y = 3*height;
                        side = "R";
                        break;
                case 4: co_x = 9*height;
                        co_y = 3*height;
                        side = "B";
                        break;
                case 5: co_x = 3*height;
                        co_y = 0;
                        side = "U";
                        break;
                default: co_x = 0;
                        co_y = 0;
                        side = "Broken";
                        break;
            }
            co_x += buffer;
            co_y += buffer;
            for (int j = 0; j < Solver.faces[i].length; j++) {
                int x = co_x + (j%3)*height;
                int y = co_y + (j/3)*height;
                g.setColor(Color.BLACK);
                g.fillRect(x,y,height,height);
                switch(Solver.faces[i][j]){
                    case 0: g.setColor(Color.white);
                        break;
                    case 1: g.setColor(Color.RED);
                        break;
                    case 2: g.setColor(new Color(33,194,46));
                        break;
                    case 3: g.setColor(new Color(255,115,0));
                        break;
                    case 4: g.setColor(new Color(53,111,219));
                        break;
                    case 5: g.setColor(Color.YELLOW);
                        break;
                    default: g.setColor(Color.BLACK);
                        break;
                }
                g.fillRect(x+sticker,y+sticker,height-(2*sticker),height-(2*sticker));
            }
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.BOLD, height-(4*sticker)));
            g.drawString(side,co_x+height+(2*sticker)+5,co_y+(2*height)-(2*sticker)-5);
        }
        return buffimg;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Solver s = new Solver();
        Main w = new Main();
        //w.setUndecorated(true);
        w.setTitle("Kevin's Rubik's Cube");
        w.setSize(1500, 1000);
        //w.setVisible(true);
        w.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        System.out.println("For test case, type t. For randomly scrambled, type r. For user input, type i.");
        char input = in.next().toLowerCase().toCharArray()[0];
        switch(input){
            case 'r':
                s.solvedown();
                s.scramble(500);
                break;
            case 'i':
                s.solvedown();
                w.setVisible(true);
                //w.userInput();
                String[] colours = new String[]{"white", "red", "green", "orange", "blue", "yellow"};
                for (int i = 0; i < 6; i++) {
                    System.out.println("Input values for the " + colours[i] + " face.");
                    for (int j = 0; j < 9; j++) {
                        Solver.faces[i][j] = (byte)in.nextInt();
                        w.repaint();
                    }
                }
                break;
            case 'v':
                w.setVisible(true);
        }

        s.saveState();
        w.repaint();
        s.solveFirstEdge();
        w.repaint();
        while(s.checkMiddleEdges()<4){
            boolean cont = true;
            while (cont){
                cont = s.checkMiddleEdgeTop4();
            }
            s.solveExtraMiddleEdges();
        }
        w.repaint();
        s.solveTopEdges();
        w.repaint();
        s.convertToString();
        s.solveTopCorners();
        w.repaint();
        s.convertToString();
        s.cleanSolution();
        System.out.print("Final Solution: "); s.convertToString();
        s.userFriendlyOutput();
        s.recallState();
        w.setVisible(true);
        s.sleep(3000);
        for (int i = 0; i < Solver.allMoves.size(); i++) {
            s.evaluateNumber(Solver.allMoves.get(i));
            w.repaint();
            s.sleep(500);
        }
        // allows the user to manually input turns
        while(true){
            String str = in.next();
            if(str.equals("r"))s.right();
            else if(str.equals("R"))s.rightPrime();
            else if(str.equals("L")) s.leftPrime();
            else if(str.equals("l")) s.left();
            else if(str.equals("B")) s.backPrime();
            else if(str.equals("b"))s.back();
            else if(str.equals("f"))s.front();
            else if(str.equals("F"))s.frontPrime();
            else if(str.equals("d")) s.down();
            else if(str.equals("D"))s.downPrime();
            else if(str.equals("u"))s.up();
            else if(str.equals("U"))s.upPrime();
            w.repaint();
        }
    }
}