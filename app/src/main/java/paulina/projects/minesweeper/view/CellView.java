package paulina.projects.minesweeper.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;

public class CellView extends AppCompatTextView {

    public CellView(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // This forces the cells to be square.
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
