package edu.wmich.cs.johnharvey.weatherhw4_johnharvey;

import android.media.MediaCodec;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by John on 6/19/2017.
 */

public class Validation {

    private static final String ZIP_REGEX = "^[0-9]{5}(?:-[0-9]{4})?$";

    private static final String REQUIRED_MSG = "Zip Required";
    private static final String ZIP_MSG = "Invalid Zip";

    public static boolean isZipCode(String editText, boolean required){
        return isValid(editText, ZIP_REGEX, ZIP_MSG, required);
    }


    public static boolean isValid(String editText, String regex, String errMsg, boolean required ){

        //String text = editText.getText().toString().trim();
        //editText.setError(null);

        editText.trim();

        if (required && !hasText(editText)) {
            return false;
        }

        if (required && !Pattern.matches(regex, editText)){
            //editText.setError(errMsg);
            return false;
        }

        return true;
    }

    public static boolean hasText(String editText){

        //String text = editText.getText().toString().trim();
        //editText.setError(null);

        editText.trim();

        if (editText.length() == 0){
            //editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }


}
