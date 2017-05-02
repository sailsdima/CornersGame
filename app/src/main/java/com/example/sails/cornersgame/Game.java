package com.example.sails.cornersgame;

import com.example.sails.cornersgame.model.Move;
import com.example.sails.cornersgame.model.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sails on 10.04.2017.
 */

public class Game {

    int[][] board = {
            {1, 1, 1, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 2, 2, 2},
            {0, 0, 0, 0, 0, 2, 2, 2},
            {0, 0, 0, 0, 0, 2, 2, 2},
    };
    int[][] tempBoard;

    int[][] marksPlayer1 = {
            {10, 9, 8, 7, 6, 5, 5, 5},
            {9, 8, 7, 6, 5, 4, 4, 4},
            {8, 7, 6, 5, 4, 3, 3, 3},
            {7, 6, 5, 4, 3, 2, 2, 2},
            {6, 5, 4, 3, 2, 1, 1, 1},
            {5, 4, 3, 2, 1, 0, 0, 0},
            {5, 4, 3, 2, 1, 0, 0, 0},
            {5, 4, 3, 2, 1, 0, 0, 0},
    };
    int[][] marksPlayer2 = {
            {0, 0, 0, 1, 2, 3, 4, 5},
            {0, 0, 0, 1, 2, 3, 4, 5},
            {0, 0, 0, 1, 2, 3, 4, 5},
            {1, 1, 1, 2, 3, 4, 5, 6},
            {2, 2, 2, 3, 4, 5, 6, 7},
            {3, 3, 3, 4, 5, 6, 7, 8},
            {4, 4, 4, 5, 6, 7, 8, 9},
            {5, 5, 5, 6, 7, 8, 9, 10},
    };

    int boardSize;
    Set<Point> possibleMoves;
    int whoseTurn = 1;

    public Game() {
        boardSize = board.length;
    }

    public List<Integer> getCellsList() {
        List<Integer> cellsList = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++)
                cellsList.add(board[i][j]);
        }
        return cellsList;
    }

    private Point translateCellIdIntoCoordinates(int cellId) {
        Point point = new Point(0, 0);
        if (cellId < Math.pow(boardSize, 2) - 1 && cellId < 0)
            return point;

        point.setX(cellId / boardSize);
        point.setY(cellId % boardSize);

        return point;
    }

    private int translateCoordinatesIntoId(Point point) {
        int k = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (i == point.getX() && j == point.getY())
                    return k;
                k++;
            }
        }
        return k;
    }

    private boolean cellHasFigureIn(Point point) {
        return board[point.getX()][point.getY()] != 0;
    }

    public boolean cellHasFigureIn(int cellId) {
        return cellHasFigureIn(translateCellIdIntoCoordinates(cellId));
    }

    public boolean cellHasFigureIn(int cellId, int player) {
        return board[translateCellIdIntoCoordinates(cellId).getX()][translateCellIdIntoCoordinates(cellId).getY()] == player;
    }

    private void addMovesByJumping(Point point) {
        //        if (!possibleMoves.contains(point)) {

        Point pointLeft = point.getPointLeft().getPointLeft();
        Point pointDown = point.getPointDown().getPointDown();
        Point pointTop = point.getPointTop().getPointTop();
        Point pointRight = point.getPointRight().getPointRight();

        if (point.getX() - 2 >= 0 && cellHasFigureIn(point.getPointLeft()) && !cellHasFigureIn(pointLeft)) {
            if (!possibleMoves.contains(pointLeft)) {
                possibleMoves.add(pointLeft);
                addMovesByJumping(pointLeft);
            }
        }
        if (point.getY() - 2 >= 0 && cellHasFigureIn(point.getPointTop()) && !cellHasFigureIn(pointTop)) {
            if (!possibleMoves.contains(pointTop)) {
                possibleMoves.add(pointTop);
                addMovesByJumping(pointTop);
            }
        }
        if (point.getX() + 2 < boardSize && cellHasFigureIn(point.getPointRight()) && !cellHasFigureIn(pointRight)) {
            if (!possibleMoves.contains(pointRight)) {
                possibleMoves.add(pointRight);
                addMovesByJumping(pointRight);
            }
        }
        if (point.getY() + 2 < boardSize && cellHasFigureIn(point.getPointDown()) && !cellHasFigureIn(pointDown)) {
            if (!possibleMoves.contains(pointDown)) {
                possibleMoves.add(pointDown);
                addMovesByJumping(pointDown);
            }
        }
//        }
    }


    public Set<Integer> getPossibleMovesFromCell(int from) {
        Set<Integer> moves = new HashSet<>();
        for (Point p : getPossibleMovesFromCell(translateCellIdIntoCoordinates(from))) {
            moves.add(translateCoordinatesIntoId(p));
        }
        return moves;
    }

    private Set<Point> getPossibleMovesFromCell(Point from) {
        possibleMoves = new HashSet<>();
        //left
        if (from.getX() - 1 >= 0) {
            Point pointLeft = from.getPointLeft();
            if (!cellHasFigureIn(pointLeft))
                possibleMoves.add(from.getPointLeft());
        }
        //right
        if (from.getX() + 1 < boardSize) {
            Point pointRight = from.getPointRight();
            if (!cellHasFigureIn(pointRight))
                possibleMoves.add(pointRight);
        }
        //top
        if (from.getY() - 1 >= 0) {
            Point pointTop = from.getPointTop();
            if (!cellHasFigureIn(pointTop))
                possibleMoves.add(pointTop);
        }
        //down
        if (from.getY() + 1 < boardSize) {
            Point pointDown = from.getPointDown();
            if (!cellHasFigureIn(pointDown))
                possibleMoves.add(pointDown);
        }
        addMovesByJumping(from);
        return possibleMoves;
    }

    public int checkWinner() {
        boolean secondWon = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 2) {
                    secondWon = false;
                    break;
                }
            }
            if (!secondWon)
                break;
        }

        boolean firstWon = true;
        for (int i = 5; i < boardSize; i++) {
            for (int j = 5; j < boardSize; j++) {
                if (board[i][j] != 1) {
                    firstWon = false;
                    break;
                }
            }
            if (!firstWon)
                break;
        }

        if (secondWon && firstWon)
            return 3;
        if (secondWon)
            return 2;
        if (firstWon)
            return 1;
        return 0;
    }

    public boolean makeMove(int from, int to) {
        System.out.println("from =  " + from + " coord " + translateCellIdIntoCoordinates(from));
        return makeMove(translateCellIdIntoCoordinates(from), translateCellIdIntoCoordinates(to));
    }

    public boolean makeMove(Point from, Point to) {

        if (board[from.getX()][from.getY()] != whoseTurn)
            return false;

        if (getPossibleMovesFromCell(from).contains(to)) {
            board[to.getX()][to.getY()] = board[from.getX()][from.getY()];
            board[from.getX()][from.getY()] = 0;
            return true;
        }
        return false;
    }

    private boolean makeMove(Move move) {
        return makeMove(move.getFrom(), move.getTo());
    }

    public int findPlayersMark(int player) {
        int mark = 0;
        if (player == 1) {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (board[i][j] == player) {
                        mark += marksPlayer1[i][j];
                    }
                }
            }
        } else if (player == 2) {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (board[i][j] == player) {
                        mark += marksPlayer2[i][j];
                    }
                }
            }
        }

        return mark;
    }

    private Set<Move> generateAllPossibleMoves(int player) {
        Set<Move> moves = new HashSet<>();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == player) {
                    Set<Point> points = getPossibleMovesFromCell(new Point(i, j));

                    for (Point point : points) {
                        moves.add(new Move(new Point(i, j), point, board.clone()));
                    }
                }
            }
        }

        for (Move move : moves) {
            move.setOldMark(findPlayersMark(player));
            makeMove(move.getFrom(), move.getTo());
            move.setNewMark(findPlayersMark(player));
            makeMove(move.getTo(), move.getFrom());
        }
        return moves;
    }

    private Move findMove(int player) {
        Set<Move> moves = generateAllPossibleMoves(player);

        Move move = moves.iterator().next();
        int minMark = move.getNewMark();
        for (Move m : moves) {
            if (m.getNewMark() < minMark) {
                move = m;
                minMark = m.getNewMark();
            }
        }
        return move;
    }

    public void computeAndMakeMove(int player) {
        makeMove(findMove(player));
        toggleTurn();
    }

    public void showBoard() {

        System.out.println();
        System.out.println();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public void toggleTurn() {
        if (whoseTurn == 1)
            whoseTurn = 2;
        else whoseTurn = 1;
    }

}
