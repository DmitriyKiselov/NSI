/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap;

import java.awt.Color;
import java.awt.Component;

/**
 *
 * @author dima
 */
public class ColorRend {

    public static int COLOR_DESP_70 = 70;
    public static int COLOR_DESP_50 = 50;
    public static int COLOR_DESP_20 = 20;
    int arrayColor[][] = new int[20][4];

    public ColorRend() {
    }

    public Color changeColor(Component cell, int number, int colorDesp) {
        arrayColorFill(cell, number);

        return new Color(getNewColor(arrayColor[number][1], colorDesp), getNewColor(arrayColor[number][2], colorDesp), getNewColor(arrayColor[number][3], colorDesp));
    }

    private void arrayColorFill(Component cell, int number){
        int colorRed, colorGreen, colorBlue;

        if (arrayColor[number][0] == number) {
//            colorRed = arrayColor[number][1];
//            colorGreen = arrayColor[number][2];
//            colorBlue = arrayColor[number][3];
        } else {
            colorRed = cell.getBackground().getRed();
            colorGreen = cell.getBackground().getGreen();
            colorBlue = cell.getBackground().getBlue();
            saveColorToArray(number, colorRed, colorGreen, colorBlue);
        }
    }
    
    public Color changeColor(Component cell, int number, Color color) {
        arrayColorFill(cell, number);

        return color;
    }

    public Color restoreColor(Component cell, int number) {
        if (arrayColor[number][0] != number) {
            saveColorToArray(number, cell.getBackground().getRed(), cell.getBackground().getGreen(), cell.getBackground().getBlue());
        }

        return new Color(arrayColor[number][1], arrayColor[number][2], arrayColor[number][3]);
    }

    private void saveColorToArray(int number, int colorRed, int colorGreen, int colorBlue) {
        arrayColor[number][0] = number;
        arrayColor[number][1] = colorRed;
        arrayColor[number][2] = colorGreen;
        arrayColor[number][3] = colorBlue;
    }

    private int getNewColor(int color, int colorDesp) {
        if (color - colorDesp >= 0) {
            color = color - colorDesp;
        }
        return color;
    }
}
