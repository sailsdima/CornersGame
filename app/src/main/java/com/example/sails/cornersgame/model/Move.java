package com.example.sails.cornersgame.model;

/**
 * Created by sails on 11.04.2017.
 */

public class Move {

    Point from;
    Point to;
    int oldMark;
    int newMark;
    int[][]board;

    public Move(Point from, Point to, int[][]board) {
        this.from = from;
        this.to = to;
        this.board = board;


    }

    public int getMarkDifference(){
        return oldMark - newMark;
    }

    public void makeMove(){
        board[to.getX()][to.getY()] = board[from.getX()][from.getY()];
        board[from.getX()][from.getY()] = 0;
    }

    public int[][] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "Move{" +
                "newMark=" + newMark +
                ", oldMark=" + oldMark +
                ", to=" + to +
                ", from=" + from +
                '}';
    }

    public int getOldMark() {
        return oldMark;
    }

    public void setOldMark(int oldMark) {
        this.oldMark = oldMark;
    }

    public int getNewMark() {
        return newMark;
    }

    public void setNewMark(int newMark) {
        this.newMark = newMark;
    }

    public Move(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (!from.equals(move.from)) return false;
        return to.equals(move.to);

    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }

}
