package edu.fje.dam2.abel.swappuzzle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Utilitat extends AppCompatActivity {

    static ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(9);

    public static ArrayList<Bitmap> splitImage(Bitmap image, int chunkNumbers) {

        //For the number of rows and columns of the grid to be displayed
        int rows, cols;

        //For height and width of the small image chunks
        int chunkHeight, chunkWidth;

        Bitmap original = Bitmap.createScaledBitmap(image, 500, 500, true);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(original, original.getWidth(), original.getHeight(), true);
        rows = cols = (int) Math.sqrt(chunkNumbers);
        chunkHeight = original.getHeight() / rows;
        chunkWidth = original.getWidth() / cols;

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for (int x = 0; x < rows; x++) {
            int xCoord = 0;
            for (int y = 0; y < cols; y++) {
                if (x == 2 && y == 2) {
                    chunkedImages.add(null);
                    break;
                }
                chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }

        return chunkedImages;
    }


    public static boolean comprovaSiComplet(ArrayList<Bitmap> img1, ArrayList<Bitmap> img2) {
        int y = 0;

        for (int x = 0; x < img1.size(); x++) {
            if (img1.get(x) == img2.get(x)) {
                y++;
            }
        }

        if (y == 9) {
            return true;
        }else{
            return false;
        }

    }





}
