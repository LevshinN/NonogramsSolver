package red.moccasins.nonogramssolver.nono;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Автор: Левшин Николай, 707 группа.
 * Дата создания: 19.10.2015.
 */
public class Nonogram {

    public String Name;

    private int width;
    private int height;

    private int paramsWidth;
    private int paramsHeight;

    private float tableWidth;
    private float tableHeight;
    private int tableColor;

    private Paint tablePen;

    // цифорки, которые расположены сверху
    public ArrayList<ArrayList<Integer>> verticalParams;

    // цифорки, которые расположены сбоку
    public ArrayList<ArrayList<Integer>> horizontalParams;

    private ArrayList<ArrayList<Boolean>> solution;

    public Nonogram(int width, int height) {
        this.width = width;
        this.height = height;

        paramsWidth = 0;
        paramsHeight = 0;

        verticalParams = new ArrayList<>(width);
        horizontalParams = new ArrayList<>(height);
    }

    public void initializeTable(float measureWidth, float measureHeight, Context context){
        tableWidth = measureWidth;
        tableHeight = measureHeight;
        tableColor = context.getResources().getColor(android.R.color.black);

        tablePen = new Paint(Paint.ANTI_ALIAS_FLAG);
        tablePen.setColor(tableColor);
    }


    public void draw(Canvas canvas) {

        for (ArrayList<Integer> vParams : verticalParams) {
            if (vParams.size() > paramsHeight) {
                paramsHeight = vParams.size();
            }
        }

        for (ArrayList<Integer> hParams : horizontalParams) {
            if (hParams.size() > paramsWidth) {
                paramsWidth = hParams.size();
            }
        }

        float cellWidth = tableWidth / (paramsWidth + width);
        float cellHeight = tableHeight / (paramsHeight + height);

        for (int i = 0; i < height + paramsHeight; ++i) {
            if ( i >= paramsHeight && (i - paramsHeight) % 5 == 0 ) {
                tablePen.setStrokeWidth(5.0f);
            } else {
                tablePen.setStrokeWidth(1.0f);
            }
            canvas.drawLine(0, i * cellHeight,
                    tableWidth, i * cellHeight, tablePen);
        }

        tablePen.setStrokeWidth(5.0f);
        canvas.drawLine(0, tableHeight,
                tableWidth, tableHeight, tablePen);

        for (int i = 0; i < width + paramsWidth; ++i) {
            if ( i >= paramsWidth && (i - paramsWidth) % 5 == 0 ) {
                tablePen.setStrokeWidth(5.0f);
            } else {
                tablePen.setStrokeWidth(1.0f);
            }
            canvas.drawLine(i * cellWidth, 0,
                    i * cellWidth, tableHeight, tablePen);
        }

        tablePen.setStrokeWidth(5.0f);
        canvas.drawLine(tableWidth, 0,
                tableWidth, tableHeight, tablePen);
    }
}
