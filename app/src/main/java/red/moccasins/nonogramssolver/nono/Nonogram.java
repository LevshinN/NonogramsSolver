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

    private float startX;
    private float startY;
    private float cellSize;

    private int tableColor;
    private int selectedCellColor;

    private Paint tablePen;

    private int selectedRow;
    private int selectedColumn;

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

        startX = 0;
        startY = 0;

        cellSize = 0;

        selectedColumn = -1;
        selectedRow = -1;

        verticalParams = new ArrayList<>(width);
        horizontalParams = new ArrayList<>(height);
    }

    public void initializeTable(float measureWidth, float measureHeight, Context context){
        tableWidth = measureWidth;
        tableHeight = measureHeight;
        tableColor = context.getResources().getColor(android.R.color.black);
        selectedCellColor = context.getResources().getColor(android.R.color.darker_gray);

        tablePen = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    public void draw(Canvas canvas) {

        if (selectedRow >= 0 && selectedColumn >= 0) {

            tablePen.setStrokeWidth(1.0f);
            tablePen.setColor(selectedCellColor);

            canvas.drawRect(startX + selectedColumn * cellSize,
                    startY,
                    startX + selectedColumn * cellSize + cellSize,
                    startY + (paramsHeight + height) * cellSize, tablePen);

            canvas.drawRect(startX,
                    startY + selectedRow * cellSize,
                    startX + (paramsWidth + width) * cellSize,
                    startY + selectedRow * cellSize + cellSize, tablePen);
        }

        tablePen.setColor(tableColor);

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

        cellSize = Math.min(cellWidth, cellHeight);

        startX = (tableWidth - cellSize * (paramsWidth + width)) / 2;
        startY = (tableHeight - cellSize * (paramsHeight + height)) / 2;

        for (int i = 0; i < height + paramsHeight; ++i) {
            if (i >= paramsHeight && (i - paramsHeight) % 5 == 0) {
                tablePen.setStrokeWidth(5.0f);
            } else {
                tablePen.setStrokeWidth(1.0f);
            }
            canvas.drawLine(startX,
                    startY + i * cellSize,
                    startX + cellSize * (paramsWidth + width),
                    startY + i * cellSize,
                    tablePen);
        }

        tablePen.setStrokeWidth(5.0f);
        canvas.drawLine(startX,
                startY + cellSize * (paramsHeight + height),
                startX + cellSize * (paramsWidth + width),
                startY + cellSize * (paramsHeight + height),
                tablePen);

        for (int i = 0; i < width + paramsWidth; ++i) {
            if (i >= paramsWidth && (i - paramsWidth) % 5 == 0) {
                tablePen.setStrokeWidth(5.0f);
            } else {
                tablePen.setStrokeWidth(1.0f);
            }
            canvas.drawLine(startX + i * cellSize,
                    startY,
                    startX + i * cellSize,
                    startY + cellSize * (paramsHeight + height),
                    tablePen);
        }

        tablePen.setStrokeWidth(5.0f);
        canvas.drawLine(startX + cellSize * (paramsWidth + width),
                startY,
                startX + cellSize * (paramsWidth + width),
                startY + cellSize * (paramsHeight + height),
                tablePen);


    }

    public boolean SelectCell(float x, float y) {
        if (x < startX
                || y < startY
                || x > startX + cellSize * (paramsWidth + width)
                || y > startY + cellSize * (paramsHeight + height)) {
            return false;
        } else {
            selectedColumn = (int)((x - startX) / cellSize);
            selectedRow = (int)((y - startY) / cellSize);
            return true;
        }
    }


}
