package com.example.smartgrocery;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;


import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;


import java.util.concurrent.CountDownLatch;


public class OCRProcessor {
    private Context ctx;


    public OCRProcessor(Context ctx) {
        this.ctx = ctx;
    }


    public String recognizeText(Bitmap bitmap) {
        try {
            InputImage image = InputImage.fromBitmap(bitmap, 0);
            com.google.mlkit.vision.text.TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);


            final StringBuilder sb = new StringBuilder();
            CountDownLatch latch = new CountDownLatch(1);


            recognizer.process(image)
                    .addOnSuccessListener(text -> {
                        sb.append(text.getText());
                        latch.countDown();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("OCR", "failed", e);
                        latch.countDown();
                    });


            latch.await();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}