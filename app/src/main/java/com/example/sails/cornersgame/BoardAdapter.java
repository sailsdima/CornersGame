package com.example.sails.cornersgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sails on 10.04.2017.
 */
public class BoardAdapter extends BaseAdapter {

    private int size;
    private Context context;
    private final float scale;
    private int colCount;
    private List<Integer> cellsList;
    private Game game;
    private int positionClicked;
    private Set<Integer> possibleMoves;

    private int[] cellsColor = {R.color.colorBrightCell, R.color.colorDarkCell};
    private int firstColor = 1;
    private int secondColor = 0;

    public BoardAdapter(Context context, int position, Game game) {
        this.context = context;
        scale = context.getResources().getDisplayMetrics().density;

        this.game = game;
        this.cellsList = game.getCellsList();
        size = cellsList.size();
        colCount = (int) Math.sqrt(size);
        this.positionClicked = position;
        this.possibleMoves = game.getPossibleMovesFromCell(position);

    }


    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (colCount % 2 == 0) {
            if ((position / colCount) % 2 == 0) {
                firstColor = 0;
                secondColor = 1;
            } else {
                firstColor = 1;
                secondColor = 0;
            }
        }


        if (view == null) {
            gridView = inflater.inflate(R.layout.cell, null);

            TextView textViewCell = (TextView) gridView.findViewById(R.id.textViewCellNumber);
            textViewCell.setText(String.valueOf(position));
            LinearLayout linearLayoutCell = (LinearLayout) gridView.findViewById(R.id.cellLayout);
            ImageView imageView = (ImageView) gridView.findViewById(R.id.imageViewCell);

            if (position % 2 == 0) {
                linearLayoutCell.setBackgroundResource(cellsColor[firstColor]);
            } else
                linearLayoutCell.setBackgroundResource(cellsColor[secondColor]);

            if (cellsList.get(position) == 1) {
                imageView.setImageResource(R.drawable.white);
            } else if (cellsList.get(position) == 2) {
                imageView.setImageResource(R.drawable.black);
            }

            if(game.cellHasFigureIn(positionClicked) && game.getPossibleMovesFromCell(positionClicked).contains(position)){
                imageView.setImageResource(R.drawable.circle);
            }

            if(positionClicked==position){
                linearLayoutCell.setBackgroundResource(R.color.colorCellFigureIsIn);
            }


        } else {
            gridView = view;
        }

        return gridView;
    }

}