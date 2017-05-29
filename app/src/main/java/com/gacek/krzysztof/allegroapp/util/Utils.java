package com.gacek.krzysztof.allegroapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gacek.krzysztof.allegroapp.dto.ErrorResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;


public class Utils {

    public static String concat(String s1, String s2, String delimiter) {
        return s1 + delimiter + s2;
    }

    public static byte[] convertBitMapToBlob(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        Log.d("SIZE", String.valueOf(stream.size()));
        return stream.toByteArray();
    }

    public static Bitmap convertBlobToBitmap(byte[] blob) {
        return BitmapFactory.decodeByteArray(blob, 0, blob.length);
    }

    public static <T> T unserializeXml(String string, Class<T> clazz) {
        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);
        T obj = null;
        try {
            obj = serializer.read(clazz, string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static void handleError(Throwable throwable, View view) {
        if (throwable instanceof HttpException) {
            ResponseBody body = ((HttpException) throwable).response().errorBody();
            String bodyStr = null;
            try {
                bodyStr = body.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ErrorResponse error = unserializeXml(bodyStr, ErrorResponse.class);
            showSnackBarNotification(view, error.getFault().getFaultstring(), MessagesType.ERROR);
        } else {
            showSnackBarNotification(view, throwable.getMessage(), MessagesType.ERROR);
        }

    }

    public static void showSnackBarNotification(View view, String msg, MessagesType messagesType) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        View snackbarLayout = snackbar.getView();
        TextView textView = (TextView)
                snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
        switch (messagesType) {
            case SUCCESS:
                textView.setTextColor(Color.GREEN);
                break;
            case ERROR:
                textView.setTextColor(Color.RED);
                break;
            case WARNING:
                textView.setTextColor(Color.YELLOW);
                break;
        }
        snackbar.show();
    }

    public static void showErrorMessage(TextInputLayout layout, String message) {
        layout.setErrorEnabled(true);
        layout.setError(message);
    }

    public static void hideErrorMessage(TextInputLayout layout) {
        layout.setError(null);
        layout.setErrorEnabled(false);
    }

}
