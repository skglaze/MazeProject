package com.skglaze.mazeproject;
import java.util.Scanner;

public class MazeRunner {

    public static void mainGameLoop(){
        Maze maze = new Maze();
        boolean isGameActive = true;
        int moveCounter = 0;
        String userMove;

        intro(maze);
        while(isGameActive) {
            String possibleMoves = "";
            possibleMoves = getPossibleMoves(maze, possibleMoves);
            System.out.println(possibleMoves);
            userMove = getUserMoveAndCheck(possibleMoves, maze);
            checkAndNavigatePit(maze, userMove, possibleMoves);
            moveCounter = incrementMoveCounter(moveCounter);
            movesMessage(moveCounter);
            isGameActive = checkMoveLimit(moveCounter, isGameActive);
            isGameActive = checkWinCondition(maze, isGameActive, moveCounter);
            maze.printMap();
        }
    }

    public static void intro(Maze maze){
        System.out.println("Welcome to the Maze Runner!");
        System.out.println("Here is your current position:");
        maze.printMap();
    }

    public static String getPossibleMoves(Maze maze, String possibleMoves){
        if(maze.canIMoveRight()) possibleMoves = possibleMoves + "R";
        if(maze.canIMoveLeft()) possibleMoves = possibleMoves + "L";
        if(maze.canIMoveUp()) possibleMoves = possibleMoves + "U";
        if(maze.canIMoveDown()) possibleMoves = possibleMoves + "D";
        return possibleMoves;
    }

    public static String getUserMoveAndCheck(String possibleMoves, Maze maze){
        Scanner scanner = new Scanner(System.in);
        String validMoves = "RLUP";
        System.out.println("Please input L, R, U, or D to move.");
        String userMove = scanner.nextLine();
        if(!validMoves.contains(userMove.trim().toUpperCase())){
            System.out.println("Not a valid input.");
            userMove = getUserMoveAndCheck(possibleMoves, maze);
            System.out.println(userMove);
        }
        if(!possibleMoves.contains(userMove) && !maze.isThereAPit(userMove)){
            System.out.println("Sorry, you've hit a wall.");
            userMove = getUserMoveAndCheck(possibleMoves, maze);
        }
        return userMove;
    }

    public static void checkAndNavigatePit(Maze maze, String userMove, String possibleMoves){
        if(maze.isThereAPit(userMove)){
            System.out.println("Watch out! There's a pit ahead. Do you want to jump it?");
            Scanner scanner = new Scanner(System.in);
            String willJump = scanner.nextLine().trim().toUpperCase();
            if(willJump.startsWith("Y")){
                maze.jumpOverPit(userMove);
            } else {
                movePlayer(maze, getUserMoveAndCheck(possibleMoves, maze));
            }
        } else {
            movePlayer(maze, userMove);
        }
    }

    public static void movePlayer(Maze maze, String userMove){
        if(!maze.isThereAPit(userMove)) {
            if (userMove.equals("R")) maze.moveRight();
            if (userMove.equals("L")) maze.moveLeft();
            if (userMove.equals("U")) maze.moveUp();
            if (userMove.equals("D")) maze.moveDown();
        }
    }

    public static int incrementMoveCounter(int moveCounter){
        moveCounter =  moveCounter + 1;
        return moveCounter;
    }

    public static void movesMessage(int moveCounter){
        if(moveCounter == 50) System.out.println("Warning: You have made 50 moves, you have 50 remaining before the maze exit closes");
        if(moveCounter == 75) System.out.println("Alert! You have made 75 moves, you only have 25 moves left to escape.");
        if(moveCounter == 90) System.out.println("DANGER! You have made 90 moves, you only have 10 moves left to escape!!");
        if(moveCounter == 100) System.out.println("Oh no! You took too long to escape, and now the maze exit is closed FOREVER >:[");
    }

    public static boolean checkMoveLimit(int moveCounter, boolean isGameActive){
        if(moveCounter == 100){
            System.out.println("Sorry but you didn't escape in time- you lose!");
            isGameActive = false;
        }
        return isGameActive;
    }

    public static boolean checkWinCondition(Maze maze, boolean isGameActive, int moveCounter){
        if(isGameActive) {
            if (maze.didIWin()) {
                System.out.println("Congratulations! You made it out alive!");
                System.out.println("It took you " + moveCounter + " turns!");
                isGameActive = false;
            }
        }
        return isGameActive;
    }
}
