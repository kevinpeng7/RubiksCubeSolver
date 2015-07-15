package com.company;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kevin on 2015-06-12.
 */
public class Solver {
    static byte[][] faces = {
            {1,3,4,0,0,0,3,3,2},
            {5,1,0,2,2,4,0,1,4},
            {2,2,2,5,3,0,5,2,1},
            {5,3,2,2,4,4,0,3,3},
            {3,4,4,0,1,5,0,4,4},
            {3,1,5,5,5,5,1,1,1} }; // test case
    static byte[][] original = new byte[6][9];
    static int complete_c = 0; // field for solving bottom faces, number of corners in place
    static int complete_e = 0; // number of edges in place
    static int complete_middle_edges = 0; // number of edges in place in the middle layer
    static ArrayList<Integer> allMoves = new ArrayList<Integer>();
    static ArrayList<Integer> edgeMoves = new ArrayList<Integer>();

    public void saveState(){
        for (int i = 0; i < faces.length; i++) {
            for (int j = 0; j < faces[0].length; j++) {
                original[i][j] = (byte)faces[i][j];
            }
        }
    }
    public void recallState(){
        for (int i = 0; i < faces.length; i++) {
            for (int j = 0; j < faces[0].length; j++) {
                faces[i][j] = (byte)original[i][j];
            }
        }
    }

    public void solvedown() {
        for (byte i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j++) {
                faces[i][j] = i;
            }
        }
    }

    //takes a 3D array as input
    //allows the user to pass multiple lists of tuples, that represent the "coordinate" of a sticker on the cube.
    // This will shift the tuples. 1st tuple replaces the 2nd. The 2nd replaces the third ... the nth replaces the first.
    // this allows the cube to simulate face rotations.
    public void cycle(int[][][] list){
        for (int i = 0; i < list.length; i++) {
            byte temp = faces[list[i][0][0]][list[i][0][1]];
            faces[list[i][0][0]][list[i][0][1]] = faces[list[i][1][0]][list[i][1][1]];
            faces[list[i][1][0]][list[i][1][1]] = faces[list[i][2][0]][list[i][2][1]];
            faces[list[i][2][0]][list[i][2][1]] = faces[list[i][3][0]][list[i][3][1]];
            faces[list[i][3][0]][list[i][3][1]] = temp;
        }
    }

    // ********************************************** \\
    // TURN SIMULATIONS
    public void up(){
        rotate((byte) 5);
        cycle(new int[][][]{{{1, 0}, {2, 0}, {3, 0}, {4, 0}}, {{1, 1}, {2, 1}, {3, 1}, {4, 1}}, {{1, 2}, {2, 2}, {3, 2}, {4, 2}}});
        //allMoves.add(5);
    }
    public void down(){
        rotate((byte)0);
        cycle(new int[][][]{{{1, 6}, {4, 6}, {3, 6}, {2, 6}}, {{1, 7}, {4, 7}, {3, 7}, {2, 7}}, {{1, 8}, {4, 8}, {3, 8}, {2, 8}}});
        //allMoves.add(0);
    }
    public void front(){
        rotate((byte)2);
        cycle(new int[][][]{{{5, 6}, {1, 8}, {0, 2}, {3, 0}}, {{5, 7}, {1, 5}, {0, 1}, {3, 3}}, {{5, 8}, {1, 2}, {0, 0}, {3, 6}}});
        //allMoves.add(2);
    }
    public void back(){
        rotate((byte)4);
        cycle(new int[][][]{{{0, 6}, {1, 0}, {5, 2}, {3, 8}}, {{0, 7}, {1, 3}, {5, 1}, {3, 5}}, {{0, 8}, {1, 6}, {5, 0}, {3, 2}}});
        //allMoves.add(4);
    }
    public void left(){
        rotate((byte) 1);
        cycle(new int[][][]{{{0, 0}, {2, 0}, {5, 0}, {4, 8}}, {{0, 3}, {2, 3}, {5, 3}, {4, 5}}, {{0, 6}, {2, 6}, {5, 6}, {4, 2}}});
        //allMoves.add(1);
    }
    public void right(){
        rotate((byte)3);
        cycle(new int[][][]{{{0, 2}, {4, 6}, {5, 2}, {2, 2}}, {{0, 5}, {4, 3}, {5, 5}, {2, 5}}, {{0, 8}, {4, 0}, {5, 8}, {2, 8}}});
        //allMoves.add(3);
    }
    public void upPrime() {
        for (int i = 0; i < 3; i++) {
            up();
        }
        //allMoves.add(11);
    }
    public void downPrime(){
        for (int i = 0; i < 3; i++) {
            down();
        }
        //allMoves.add(6);
    }
    public void frontPrime(){
        for (int i = 0; i < 3; i++) {
            front();
        }
        //allMoves.add(8);
    }
    public void backPrime(){
        for (int i = 0; i < 3; i++) {
            back();
        }
        //allMoves.add(10);
    }
    public void leftPrime(){
        for (int i = 0; i < 3; i++) {
            left();
        }
        //allMoves.add(7);
    }
    public void rightPrime(){
        for (int i = 0; i < 3; i++) {
            right();
        }
        //allMoves.add(9);
    }
    public void rotate(byte face){
        shiftValues(face, new int[]{0, 6, 8, 2});
        shiftValues(face, new int[]{1, 3, 7, 5});
    }
    // used by rotate to shift given values from an array
    public void shiftValues(byte face, int[] stickers){
        byte temp = faces[face][stickers[0]];
        int lastIndex = stickers.length-1;
        for (int i = 0; i < lastIndex; i++) {
            faces[face][stickers[i]] = faces[face][stickers[i+1]];
        }
        faces[face][stickers[lastIndex]] = temp;
    }
    // Interprets a number as one of 12 possible Moves
    public void evaluateNumber(int n){
        switch(n){
            case 0: down(); break;
            case 1: left(); break;
            case 2: front(); break;
            case 3: right(); break;
            case 4: back(); break;
            case 5: up(); break;
            case 6: downPrime(); break;
            case 7: leftPrime(); break;
            case 8: frontPrime(); break;
            case 9: rightPrime(); break;
            case 10: backPrime(); break;
            case 11: upPrime(); break;
        }

    }
    public void scramble(int n){
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            evaluateNumber(r.nextInt(12));
        }
    }

    // Checks to see if the given piece coordinate is the correct colour. That is, checks if it is the same colour as the middle piece on the same face.
    public boolean checkPiece(int a, int b){
        return faces[a][b] == faces[a][4];
    }

    public int checkFirstEdge(){
        int counter = 0;
        byte center = faces[0][4];
        if (faces[0][1] == center && faces[2][7] == faces[2][4])counter ++;
        if (faces[0][3] == center && faces[1][7] == faces[1][4])counter ++;
        if (faces[0][7] == center && faces[4][7] == faces[4][4])counter ++;
        if (faces[0][5] == center && faces[3][7] == faces[3][4])counter ++;
        return counter;
    }
    public int checkFirstCorner(){
        int counter = 0;
        byte center = faces[0][4];
        if (faces[0][0] == center && faces[2][6] == faces[2][4] && faces[1][8] == faces[1][4])counter ++;
        if (faces[0][2] == center && faces[2][8] == faces[2][4] && faces[3][6] == faces[3][4])counter ++;
        if (faces[0][6] == center && faces[1][6] == faces[1][4] && faces[4][8] == faces[4][4])counter ++;
        if (faces[0][8] == center && faces[3][8] == faces[3][4] && faces[4][6] == faces[4][4])counter ++;
        return counter;
    }
    public int checkMiddleEdges(){
        int counter = 0;
        if(checkPiece(1,5)&&checkPiece(2,3))counter++;
        if(checkPiece(2,5)&&checkPiece(3,3))counter++;
        if(checkPiece(3,5)&&checkPiece(4,3))counter++;
        if(checkPiece(4,5)&&checkPiece(1,3))counter++;
        return counter;
    }
    public boolean checkMiddleEdgeTop4(){
        byte center = faces[5][4];
        if(faces[5][1] != center && faces[4][1] != center) solveMiddle(4,1,1);
        else if(faces[5][3] != center && faces[1][1] != center) solveMiddle(1,1,3);
        else if(faces[5][5] != center && faces[3][1] != center) solveMiddle(3,1,5);
        else if(faces[5][7] != center && faces[2][1] != center) solveMiddle(2,1,7);
        else return false; // returns false if there are no more edges to solve.
        return true; //returns true if it solves an edge
    }

    // solves edge pieces that aren't on the top layer
    public void solveExtraMiddleEdges(){
        if(checkMiddleEdges()<4){
            if(!(checkPiece(1,5)&&checkPiece(2,3)))downAndLeftMiddleAlgorithm(2);
            if(!(checkPiece(2,5)&&checkPiece(3,3)))downAndRightMiddleAlgorithm(2);
            if(!(checkPiece(3,5)&&checkPiece(4,3)))downAndLeftMiddleAlgorithm(4);
            if(!(checkPiece(4,5)&&checkPiece(1,3)))downAndRightMiddleAlgorithm(4);
        }
    }

    //check the number of properly oriented edge pieces on the top face
    public int checkTopEdges(){
        int counter = 0;
        for (int i = 1; i <= 4; i++) {
            if(checkPiece(i,1))counter++;
        }
        return counter;
    }

    public void solveTopEdges(){
        if(checkPiece(5,1)&&checkPiece(5,3)&&checkPiece(5,5)&&checkPiece(5,7)){ // + cross
            orientTopEdges();
        }
        else if((!checkPiece(5,1))&&(!checkPiece(5,3))&&(!checkPiece(5,5))&&(!checkPiece(5,7))){ // none
            topLineAlgorithm(2);
            solveTopEdges();
        }
        else if(checkPiece(5,1)&&checkPiece(5,3)){ //  _|
            topLShapedAlgorithm(2);
            orientTopEdges();
        }
        else if(checkPiece(5,1)&&checkPiece(5,5)){ //  |_
            topLShapedAlgorithm(1);
            orientTopEdges();
        }
        else if(checkPiece(5,5)&&checkPiece(5,7)){ //  |-
            topLShapedAlgorithm(4);
            orientTopEdges();
        }
        else if(checkPiece(5,3)&&checkPiece(5,7)){ //  -|
            topLShapedAlgorithm(3);
            orientTopEdges();
        }
        else if(checkPiece(5,1)&&checkPiece(5,7)){ //  |
            topLineAlgorithm(1);
            orientTopEdges();
        }
        else if(checkPiece(5,3)&&checkPiece(5,5)){  //  ---
            topLineAlgorithm(2);
            orientTopEdges();
        }
    }
    public void orientTopEdges(){
        int positioned = checkTopEdges();
        if(positioned<2){
            upPrime();
            allMoves.add(11);
            orientTopEdges();
        }
        else if(positioned==4){
            solveTopCorners();
        }
        else if(checkPiece(1,1)&&checkPiece(3,1)){
            topCrossAlgorithm(1);
            orientTopEdges();
        }
        else if(checkPiece(2,1)&&checkPiece(4,1)){
            topCrossAlgorithm(2);
            orientTopEdges();
        }
        else{
            for (int i = 1; i <= 4; i++) {
                if(checkPiece(i,1)&checkPiece((i%4 + 1),1)){
                    topCrossAlgorithm((i+2)%4+1);
                    break;
                }
            }
        }
    }
    // checks if the piece is in the right spot, not necessarily oriented properly
    public void solveTopCorners(){
        if (checkTopCorners()==4){
            orientTopCorners();
            return;
        }
        int pos = 1;
        if(checkCorrectPosition(new int[]{faces[5][0],faces[1][0],faces[4][2]},new int[]{faces[5][4],faces[1][4],faces[4][4]})) pos = 4;
        else if(checkCorrectPosition(new int[]{faces[5][2],faces[3][2],faces[4][0]},new int[]{faces[5][4],faces[3][4],faces[4][4]})) pos = 3;
        else if(checkCorrectPosition(new int[]{faces[5][8],faces[3][0],faces[2][2]},new int[]{faces[5][4],faces[3][4],faces[2][4]})) pos = 2;
        else if(checkCorrectPosition(new int[]{faces[5][6],faces[1][2],faces[2][0]},new int[]{faces[5][4],faces[1][4],faces[2][4]})) pos = 1;
        topCornersClockwiseAlgorithm(pos);
        solveTopCorners();
    }

    public static boolean checkCorrectPosition(int[] piece, int[] cube) {
        for (int i = 0; i < piece.length; i++) {
            boolean in = false;
            for (int j = 0; j < cube.length; j++) {
                if(piece[i]==cube[j])in=true;
            }
            if(!in) return false;
        }
        return true;
    }

    public int checkTopCorners(){
        int counter = 0;
        if(checkCorrectPosition(new int[]{faces[5][0],faces[1][0],faces[4][2]},new int[]{faces[5][4],faces[1][4],faces[4][4]})) counter ++;
        if(checkCorrectPosition(new int[]{faces[5][2],faces[3][2],faces[4][0]},new int[]{faces[5][4],faces[3][4],faces[4][4]})) counter ++;
        if(checkCorrectPosition(new int[]{faces[5][8],faces[3][0],faces[2][2]},new int[]{faces[5][4],faces[3][4],faces[2][4]})) counter++;
        if(checkCorrectPosition(new int[]{faces[5][6],faces[1][2],faces[2][0]},new int[]{faces[5][4],faces[1][4],faces[2][4]})) counter++;
        return counter;
    }

    public void orientTopCorners(){
        for (int i = 0; i < 4; i++) {
            while(!checkPiece(5,6)){
                rotationAlgorithm(2);
            }
            up(); allMoves.add(5);
        }
    }


    public void search(int depth, ArrayList<Integer> sequence){
        int e = checkFirstEdge();
        int c = checkFirstCorner();
        int m = checkMiddleEdges();
        if (e>complete_e) {
            complete_e = e;
            complete_c = c;
            complete_middle_edges = m;
            edgeMoves.clear();
            edgeMoves.addAll(sequence);
        }
        else if(e == complete_e && c>complete_c){
            complete_c = c;
            complete_middle_edges = m;
            edgeMoves.clear();
            edgeMoves.addAll(sequence);
        }
        else if (e == complete_e && c==complete_c && m>complete_middle_edges){
            complete_middle_edges = m;
            edgeMoves.clear();
            edgeMoves.addAll(sequence);
        }
        else if (e == complete_e && c == complete_c && m == complete_middle_edges && sequence.size()< edgeMoves.size()){
            edgeMoves.clear();
            edgeMoves.addAll(sequence);
        }
        if (depth > 0){
            for (int i = 0; i < 12; i++) {
                evaluateNumber(i);
                sequence.add(i);

                search(depth - 1, sequence);
                evaluateNumber((i+6)%12); // shifts the operation to its opposite operation ex: clockwise u to counterclockwise u
                sequence.remove(sequence.size()-1);
            }
        }
    }
    // brute force method for finding an efficient solution to the bottom face
    public void solveFirstEdge(){
        int count = 0;
        int depth = 6;
        while ((complete_c + complete_e) != 8 && count < 20){
            count++;
            ArrayList<Integer> empty = new ArrayList();
            for (int i = 0; i < allMoves.size(); i++) {
                evaluateNumber(allMoves.get(i));
            }
            search(depth, empty);
            for (int i = allMoves.size()-1; i >=0; i--) {
                evaluateNumber((allMoves.get(i)+6)%12);
            }
            if (edgeMoves.size()==0)depth+=1;
            else allMoves.addAll(edgeMoves);
            convertToString();
        }
        for (int i = 0; i < allMoves.size(); i++) {
            evaluateNumber(allMoves.get(i));
        }
    }
    public void solveMiddle(int side, int position, int top_color){
        ArrayList<Integer> rotate = new ArrayList<Integer>();
        byte topcolor = faces[5][top_color];
        byte frontcolor = faces[side][position];
        int dest=0;
        for (int i = 1; i < 5; i++) {
            if (faces[i][4]==frontcolor) dest = i;
        }
        int x = dest-side;
        if (x<0) x+=4;
        switch(x){
            case 1: rotate.add(11);
                upPrime();
                break;
            case 2: rotate.add(5);
                rotate.add(5);
                up();
                up();
                break;
            case 3:rotate.add(5);
                up();
                break;
            default: break;
        }
        allMoves.addAll(rotate);
        int left = ((dest-2)%4);
        if (left<0) left+=4;
        left += 1;
        if (faces[left][4]== topcolor)downAndLeftMiddleAlgorithm(dest);
        else downAndRightMiddleAlgorithm(dest);

        convertToString();
    }

    public void downAndLeftMiddleAlgorithm(int pos){
        int[] left = {11,7,5,1,8,1,2,7};
        left = transposeAlgorithm(pos,left);
        executeAlgorithm(left);
    }
    // front face's color is the color of the cubie's top sticker
    public void downAndRightMiddleAlgorithm(int pos){
        int[] right = {5,3,11,9,2,9,8,3};
        right = transposeAlgorithm(pos,right);
        executeAlgorithm(right);
    }
    public void topLineAlgorithm(int pos){
        int[] moves = transposeAlgorithm(pos,new int[]{2,3,5,9,11,8});
        executeAlgorithm(moves);
    }
    public void topLShapedAlgorithm(int pos){
        int[] moves = transposeAlgorithm(pos, new int[]{2,5,3,11,9,8});
        executeAlgorithm(moves);
    }
    public void topCrossAlgorithm(int pos){
        int[] moves = transposeAlgorithm(pos, new int[]{3,5,9,5,3,5,5,9,5});
        executeAlgorithm(moves);
    }
    public void topCornersClockwiseAlgorithm(int pos){
        int[] moves = transposeAlgorithm(pos, new int[]{7,5,3,11,1,5,9,11});
        executeAlgorithm(moves);
    }
    public void topCornersCounterclockwiseAlgorithm(int pos){
        int[] moves = transposeAlgorithm(pos, new int[]{5,3,11,7,5,9,11,1});
        executeAlgorithm(moves);
    }
    public void rotationAlgorithm(int pos){
        int[] moves = transposeAlgorithm(pos, new int[]{1,0,7,6,1,0,7,6});
        executeAlgorithm(moves);
    }

    // iterates through the list of moves and evaluates them
    public void executeAlgorithm(int[] moves){
        for (int i = 0; i < moves.length; i++) {
            evaluateNumber(moves[i]);
            allMoves.add(moves[i]);
        }
    }

    // shifts the perspective of an algorithm from the default 2 as the front face
    // since the range is 1-4, we want to adjust it to make it 0-3 so that we can use modulus.
    public int[] transposeAlgorithm(int pos, int[] algorithm){
        int shift = pos -2; // -2 to account for 2 being the base setting
        for (int i = 0; i < algorithm.length; i++) {
            if (algorithm[i]>=1 && algorithm[i]<=4) { //If l,f,r,b
                algorithm[i] = algorithm[i]-1+shift;
                if (algorithm[i]<0)algorithm[i]+= 4;
                algorithm[i]=algorithm[i]%4; // since range is 0-3 instead of 1-4, we can use mod properly.
                algorithm[i] +=1; // readjust back into proper range, 1-4
            }
            else if(algorithm[i]>=7 && algorithm[i]<=10) { // If L,F,R,B
                algorithm[i] = algorithm[i]-7+shift;
                if (algorithm[i]<0)algorithm[i]+= 4;
                algorithm[i]=algorithm[i]%4;
                algorithm[i]+=7;
            }
        }
        return algorithm;
    }
    public void convertToString(){
        String s;
        for (int i = 0; i < allMoves.size(); i++) {
            switch(allMoves.get(i)){
                case 0: s = "d"; break;
                case 1: s = "l"; break;
                case 2: s = "f"; break;
                case 3: s = "r"; break;
                case 4: s = "b"; break;
                case 5: s = "u"; break;
                case 6: s = "D"; break;
                case 7: s = "L"; break;
                case 8: s = "F"; break;
                case 9: s = "R"; break;
                case 10: s = "B"; break;
                case 11: s = "U"; break;
                default: s = ""; break;
            }
            System.out.print(s + " ");
        }
        System.out.println(" " +allMoves.size());
    }
    public void userFriendlyOutput(){
        String s;
        for (int i = 0; i < allMoves.size(); i++) {
            switch(allMoves.get(i)){
                case 0: s = "Clockwise white."; break;
                case 1: s = "Clockwise red."; break;
                case 2: s = "Clockwise green."; break;
                case 3: s = "Clockwise orange."; break;
                case 4: s = "Clockwise blue."; break;
                case 5: s = "Clockwise yellow."; break;
                case 6: s = "Counter-clockwise white."; break;
                case 7: s = "Counter-clockwise red."; break;
                case 8: s = "Counter-clockwise green."; break;
                case 9: s = "Counter-clockwise orange."; break;
                case 10: s = "Counter-clockwise blue."; break;
                case 11: s = "Counter-clockwise yellow."; break;
                default: s = ""; break;
            }
            System.out.println((i+1) + ". " + s);
        }
    }
    public void sleep(int ms){
        try{
            Thread.sleep(ms);
        }catch(Exception e){}
    }

    // Important for optimizing solution
    public void cleanSolution(){
        for (int i = allMoves.size()-1; i > 0; i--) {
            if(i<allMoves.size()) {
                if(i>0) {
                    if (allMoves.get(i) - allMoves.get(i - 1) == Math.abs(6)) {
                        allMoves.remove(i);
                        allMoves.remove(i - 1);
                        i++;
                        continue;
                    }
                }
                if(i>1){
                    if(allMoves.get(i)==allMoves.get(i-1) && allMoves.get(i-1)==allMoves.get(i-2)){
                        allMoves.set(i-2, (allMoves.get(i)+6)%12);
                        allMoves.remove(i);
                        allMoves.remove(i-1);
                        i+=2;
                    }
                }
            }
        }
    }
}
