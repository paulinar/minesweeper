package paulina.projects.minesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import paulina.projects.minesweeper.models.Board;
import paulina.projects.minesweeper.models.Cell;
import paulina.projects.minesweeper.view.CellView;

import static paulina.projects.minesweeper.models.Cell.State.CLOSED;
import static paulina.projects.minesweeper.models.Cell.State.FLAGGED;

public class BoardActivity extends Activity {

    private static int BOARD_SIZE = 10;
    private static int NUM_MINES = 5;

    private GridView boardView;

    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        board = new Board(BOARD_SIZE, NUM_MINES);

        boardView = findViewById(R.id.board);

        boardView.setNumColumns(BOARD_SIZE);

        BoardAdapter adapter = new BoardAdapter();
        boardView.setAdapter(adapter);
        adapter.updateCells(board.getCells());

    }

    class BoardAdapter extends BaseAdapter {

        Cell[][] cells = new Cell[][]{};

        @Override
        public int getCount() {
            if (cells.length > 0) {
                return cells.length * cells[0].length;
            }
            return 0;
        }

        @Override
        public Cell getItem(int i) {
            int rowIndex = i / BOARD_SIZE;
            int colIndex = i % BOARD_SIZE;
            return cells[rowIndex][colIndex];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        void updateCells(Cell[][] cells) {
            this.cells = cells;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final TextView textView = new CellView(viewGroup.getContext());
            final Cell c = getItem(i);
            if (c != null) {
                switch (c.getState()) {
                    case OPEN:
                        if (c.isMine()) {
                            textView.setText("X");
                        } else {
                            textView.setText("O");
                        }
                        break;
                    case CLOSED:
                        textView.setText("");
                        break;
                    case FLAGGED:
                        textView.setText("F");
                        break;
                }
            }
            textView.setTextColor(ContextCompat.getColor(viewGroup.getContext(), R.color.cellText));
            textView.setBackgroundColor(ContextCompat.getColor(viewGroup.getContext(), R.color.cellBackground));

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c != null) {
                        if (c.getState() == FLAGGED) {
                            c.setState(CLOSED);
                        } else {
                            c.setState(FLAGGED);
                        }
                        notifyDataSetChanged();
                    }
                }
            });

            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (c != null) {
                        if (c.isMine()) {
                            board.openAllCells();
                        } else {
                            board.openNeighbors(c);
                        }
                        notifyDataSetChanged();
                    }
                    return true;
                }
            });

            return textView;
        }
    }
}

