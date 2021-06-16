package com.aprilinnovations.autoloungeindia.helper;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.Settings.Secure;
import androidx.core.app.NotificationCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static android.content.Context.NOTIFICATION_SERVICE;


public class UniversalHelper {

    private static Context myContext;
    private Activity myActivity;

    protected final String FILETYPE = ".bin";

    private static final String RANTCHAT = "rantchat_pref";

    private static final String TAG = "UniversalHelper";

    ProgressBar pb = null;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView cur_val;

    public UniversalHelper(Context context) {
        // TODO Auto-generated constructor stub
        myContext = context;
        try {
            myActivity = (Activity) context;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //gps = new GPSTracking(myContext);
    }

    /**
     * Get Device screen width
     *
     * @return {@link int} value if return -1 then some error while getting width
     * if not then it return proper width
     */
    public int getScreenWidth() {
        try {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) myContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int width = displaymetrics.widthPixels;
            System.out.println("Width:----> " + width);
            return width;
        } catch (Exception e) {
            System.err.println("Error in getting width====> " + e.toString());
            return -1;
        }
    }

    /**
     * save the Object Using the SharedPreferences.
     *
     * @param key   specific StringId for fetch the data  key.
     * @param value object which is save using sharedPreferance.
     */
    public static void savePreferences(String key, String value) {
        @SuppressWarnings("static-access")
        SharedPreferences preferences = myContext.getSharedPreferences(RANTCHAT, myContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Load the Object Usign the SharedPreferences.
     *
     * @param key specific StringId for fetch the object.
     */
    public static String loadPreferences(String key) {
        String strValue = "";
        @SuppressWarnings("static-access")
        SharedPreferences preferences = myContext.getSharedPreferences(RANTCHAT, myContext.MODE_PRIVATE);
        strValue = preferences.getString(key, "");

        return strValue;
    }

    /**
     * for clearing preferences
     */
    public static void clearPreferences(String key) {
        @SuppressWarnings("static-access")
        SharedPreferences preferences = myContext.getSharedPreferences(RANTCHAT, myContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * save the Object Usign the SharedPreferences.
     *
     * @param key   specific StringId for fetch the data  key.
     * @param value object which is save using sharedPreferance.
     */
    public static void savePreferencesInt(String key, int value) {
        @SuppressWarnings("static-access")
        SharedPreferences preferences = myContext.getSharedPreferences(RANTCHAT, myContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void savePreferencesSet(String key, Set<String> value) {
        @SuppressWarnings("static-access")
        SharedPreferences preferences = myContext.getSharedPreferences(RANTCHAT, myContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, new HashSet<String>(value));
        editor.apply();
    }

    /**
     * Load the Object Usign the SharedPreferences.
     *
     * @param key specific StringId for fetch the object.
     */
    public static int loadPreferencesInt(String key) {
        int strValue = 0;
        @SuppressWarnings("static-access")
        SharedPreferences preferences = myContext.getSharedPreferences(RANTCHAT, myContext.MODE_PRIVATE);
        strValue = preferences.getInt(key, 0);

        return strValue;
    }

    public Set<String> loadPreferencesSet(String key) {
        @SuppressWarnings("static-access")
        SharedPreferences preferences = myContext.getSharedPreferences(RANTCHAT, myContext.MODE_PRIVATE);
        Set<String> strValue = new HashSet<String>(preferences.getStringSet(key, new HashSet<String>()));
        Log.d("testiNG", ">>>>>>>>>>>" + strValue);
        return strValue;
    }

    public String loadPreferences(String key, Context myContext) {
        String strValue = "";
        Log.d("myContexthelper","myContexthelper"+myContext);
        @SuppressWarnings("static-access")


        SharedPreferences preferences = myContext.getSharedPreferences(RANTCHAT, myContext.MODE_PRIVATE);
        strValue = preferences.getString(key, "");
        return strValue;
    }

    /**
     * set the Typeface for the View.
     *
     * @param view For applying the Typeface.
     */
    public  void  setTypefaceGeneral(TextView view) {

        Typeface typeface = Typeface.createFromAsset(myContext.getAssets(), "fonts/Montserrat-ExtraLight.ttf");
        view.setTypeface(typeface);

    }

    public String dateFormatter(String date){

        String dateToBeFormatted = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy");
        String newFormat = formatter.format(testDate);
        Log.d("dateFormatte", ".....Date..."+newFormat);

        return newFormat;
    }

    /**
     * it convert the miliseconds into dateformate string.
     *
     * @param milliSeconds miliseconds for converting the date.
     * @param dateFormat   Specific date formate which you want to convert.
     * @return return the String of the formatted date.
     */
    public String convertMillisToDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    /**
     * Prepare the query.
     *
     * @param nameValuePairs contains the JsonString
     * @return It returns the query string.
     */
    private static String getPostDataString(HashMap<String, String> nameValuePairs) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : nameValuePairs.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    /**
     * Upload the Image On PHP Webserver for the RANTCHAT.
     *
     * @param nameValuePairs contains the JsonString
     * @param urlString      contains the URL of the WS for Upload the Image
     * @return It returns the Response from the Ws.
     */

    public static String uploadImageOnServer(HashMap<String, String> nameValuePairs, String urlString) {
        String response = "";
        try {

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(nameValuePairs));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";

            }
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Used for the Json WS Parsing for the rantchat.
     *
     * @param nameValuePairs Contains the Jsonobject's string.
     * @param urlString      Contains the Ws URL
     * @return It retuns the Response from the Ws.
     */
    /*public static String callJsonWS(final HashMap<String, String> nameValuePairs, String urlString) {
        String response = "";
        try {
            Log.d("TesTiNG", "trace 2");
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority(urlString);
            for (Map.Entry<String, String> entry : nameValuePairs.entrySet()) {
                builder.appendQueryParameter(entry.getKey(), entry.getValue());
            }

            URL url = new URL(Uri.decode(builder.build().toString()));

            Log.d("URI", Uri.decode(builder.build().toString()));
            Log.d("URL", url.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
//            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);

//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(getPostDataString(nameValuePairs));
//
//            writer.flush();
//            writer.close();
//            os.close();

            int responseCode = conn.getResponseCode();
            Log.d("TesTIng", "HERE ERROR" + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }*/

    public static String callJsonWS(final HashMap<String, String> nameValuePairs, String urlString, String requestType) {
        String response = "";

        try {
            String userImages = "";
            Log.d("TesTiNG", "trace 2");
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http").authority(urlString);

            for (Map.Entry<String, String> entry : nameValuePairs.entrySet()) {
                if (entry.getKey().equals("userImages")) {
                    userImages = entry.getValue();
                } else {
                    builder.appendQueryParameter(entry.getKey(), entry.getValue());
                }
            }

            URL url = new URL(Uri.decode(builder.build().toString()));

            Log.d("URI", Uri.decode(builder.build().toString()));
            Log.d("URL", url.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(requestType);



            if (!userImages.isEmpty() && userImages.length() > 0) {
                OutputStream outputStream = conn.getOutputStream();
                OutputStream os = conn.getOutputStream();
                HashMap<String, String> nameValuePairsFile = new HashMap<>();
                //nameValuePairsFile.put("userImages", userImages);
                //Log.d("userIMages==inUlper==>", userImages);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(nameValuePairsFile));
                writer.close();
                outputStream.close();
            } else {
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }

            int responseCode = conn.getResponseCode();
            Log.d("TesTIng", "HERE ERROR" + responseCode);

            Log.d("TesTIng", "HERE ERROR" + conn.getResponseMessage());

            if (!TextUtils.isEmpty(String.valueOf(responseCode))){

                savePreferences("statusCode", String.valueOf(responseCode));

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }

            }

            /*if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else if(responseCode == HttpURLConnection.HTTP_CREATED) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response +=  line;
                }
            }*/else {
                return response = "";
            }
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String sendDataJsonWS(final HashMap<String, String> nameValuePairs, String urlString) {
        String response = "";
        try {
            Log.d("TesTiNG", "trace 2");
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority(urlString);
            for (Map.Entry<String, String> entry : nameValuePairs.entrySet()) {
                builder.appendQueryParameter(entry.getKey(), entry.getValue());
            }

//            URL url = new URL(Uri.decode(builder.build().toString()));
            URL url = new URL(Uri.decode(builder.build().toString()));
            Log.d("URI", Uri.decode(builder.build().toString()));
            Log.d("URL", url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
//            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);

//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(getPostDataString(nameValuePairs));
//
//            writer.flush();
//            writer.close();
//            os.close();

            int responseCode = conn.getResponseCode();
            Log.d("TesTIng", "HERE ERROR" + responseCode);
            Log.d("TesTIng", conn.getContent().toString());
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";


            }
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * Used for the Json WS Parsing for the Google APIs.
     *
     * @return It retuns the Response from the Ws.
     */
    public static String callGoogleJsonWS(String strUrl) {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.d("url======>", strUrl);
            Log.d("LinkUrl======>", url.toString());
            urlConnection.connect();
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuilder sb = new StringBuilder();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception in url dnld", e.toString());
        } finally {
            try {
                assert iStream != null;
                iStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            urlConnection.disconnect();
        }

        return data;
    }

    /**
     * It Convert the InputStream in to the String format.
     *
     * @param is InputStream
     * @return It returns the String Response
     * @throws IOException throws IOException For I/O related Errors.
     */
    @SuppressWarnings("unused")
    private static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * It Checks the Request/Response From the Ws is Valid JsonObject/Jsonarray or not?
     *
     * @param test Request/Response From the WS.
     * @return It Returns the True For the Valid Json otherwise false for the Invalid Json.
     */
    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
            return true;
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
                return true;
            } catch (JSONException ex1) {
                return false;
            }
        }
    }

    /**
     * Used to store the data in cache file.
     *
     * @param filename Set the Unique file name for fetch and set the Filename.
     * @param obj      the data to save in the cache file.
     * @param append   true if wanted to append the data otherwise false.
     */
    protected void writeToBinary(String filename, Object obj, boolean append) {
        File file = new File(filename);
        ObjectOutputStream out = null;

        try {
            if (!file.exists() || !append) {
                out = new ObjectOutputStream(myContext.openFileOutput(filename, Context.MODE_PRIVATE));
            } else {
                out = new AppendableObjectOutputStream(new FileOutputStream(filename, append));
            }
            out.writeObject(obj);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Display the Toast Message.
     *
     * @param message Entering the Message to be displayed on the screen.
     */
    public static void showToast(String message) {

        Toast toast = Toast.makeText(myContext, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    /**
     * Display the Alert Dialog.
     */

    /**
     * It set the UnderLinefor the Text.
     *
     * @param string Enter the text that you want to underline.
     * @return it retuns the spannable String.
     */
    public SpannableString setUnderLineText(String string) {
        SpannableString content = new SpannableString(string);
        content.setSpan(new UnderlineSpan(), 0, string.length(), 0);
        content.setSpan(new StyleSpan(Typeface.BOLD), 0, string.length(), 0);
        return content;
    }

    /**
     * It set the Bold for the Text.
     *
     * @param string Enter the text that you want to bold.
     * @return it retuns the spannable String.
     */
    public SpannableString setBoldText(String string) {
        SpannableString content = new SpannableString(string);
        content.setSpan(new StyleSpan(Typeface.BOLD), 0, string.length(), 0);
        return content;
    }

    /**
     * It Generate the SHA Key.
     */
    public void getShaKey() {

        try {
            PackageInfo info = myContext.getPackageManager().getPackageInfo("com.aprilinnovationslab.rantchat", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.v("KeyHash:", "KeyHash:" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

    }

    private class AppendableObjectOutputStream extends ObjectOutputStream {
        AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
        }
    }

    /**
     * It Hide the KeyBorad While It is open
     */
    @SuppressWarnings("static-access")
    public void hideSoftKeyboard() {
        if (myActivity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) myActivity.getSystemService(myActivity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(myActivity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * It Resize and Cropped the ImageBitmap according to the Image Height and width.
     *
     * @param bitmp The Image Bitmap for the Resizing.
     * @return It returns the Resized and cropped Bitmap.
     */
    public Bitmap getResizedAndCroppedBitMap(Bitmap bitmp) {

        // image will crop with value of (bitmap.getWidth())
        bitmp = getResizedBitmap(bitmp, bitmp.getWidth(), bitmp.getWidth());
        bitmp = getCroppedBitmap(bitmp);

        return bitmp;
    }

    /**
     * It Resize the Image Bitmap according to the Image Height and width.
     *
     * @param bm        ImageBitmap for Resizing.
     * @param newHeight Height for the newly resized Image.
     * @param newWidth  width for the newly resized Image.
     * @return It returns the Resized Bitmap.
     */
    private Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    private Bitmap getCroppedBitmap(Bitmap bitmap) {

        int bitHeight = bitmap.getHeight();
        int bitWidth = bitmap.getWidth();

        Bitmap output = Bitmap.createBitmap(bitWidth, bitHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitWidth, bitHeight);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitWidth / 2, bitHeight / 2, bitWidth / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    public void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {

            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                // Read byte from input stream
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                // Write byte from output stream
                os.write(bytes, 0, count);
            }
        } catch (Exception ignored) {
        }
    }

    public byte[] convertBitmapToByteArray(Bitmap bitmap) {

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);

        return bStream.toByteArray();
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                  int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public String convertBase64String(Bitmap bitmap) {
        if (bitmap == null) {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bao);
            byte[] ba = bao.toByteArray();
            String base64Str = Base64.encodeToString(ba, 0);
            writeStringAsFile(base64Str, "rantchat.txt");
            return null;
        } else {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bao);
            byte[] ba = bao.toByteArray();

            return Base64.encodeToString(ba, 0);
        }

    }

    private void writeStringAsFile(final String fileContents, String fileName) {

        //String  file=Environment.getExternalStorageDirectory().getAbsoluteFile()+"";
        //System.out.println("File path==> "+file);
        File Directory = new File("/sdcard/Rantchat/");
        // have the object build the directory structure, if needed.
        Directory.mkdirs();

        try {
            FileWriter out = new FileWriter(new File(Directory, fileName));
            out.write(fileContents);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getdeviceToken() {
        String android_id = Secure.getString(myContext.getContentResolver(), Secure.ANDROID_ID);
        return android_id;
    }

    public Bitmap getBitmapFromURL(String src) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        try {
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String savedImgBitmapPath(Bitmap bitmapImage) {
        //File file = new File(Environment.getExternalStorageDirectory()+"profile.png");
        File file = new File(myContext.getFilesDir().getPath() + "profile.png");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public Bitmap loadImageFromStorage(String path) {
        try {
            File f = new File(path);
            System.out.println("File Name===>>" + f);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTimeDiff(Long dateOne, Long dateTwo) {
        long timeDiff = Math.abs(dateOne - dateTwo);

        String day = String.format("%d", TimeUnit.MILLISECONDS.toDays(timeDiff));
        String hours = String.format("%d", TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.HOURS.toHours(TimeUnit.MILLISECONDS.toDays(timeDiff)));
        String minutes = String.format("%d", TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        String Seconds = String.format("%d", TimeUnit.MILLISECONDS.toSeconds(timeDiff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDiff)));

        if (!day.equals("0")) {
            if (day.equals("1")) {
                return day + " day Left";
            } else {
                return day + " days Left";
            }
        } else if (!hours.equals("0")) {
            if (hours.equals("1")) {
                return hours + " hour Left";
            } else {
                return hours + " hours Left";
            }
        } else if (!minutes.equals("0")) {
            if (minutes.equals("1")) {
                return minutes + " min Left";
            } else {
                return minutes + " mins Left";
            }
        } else/* if (!Seconds.equals("0"))*/ {
            return Seconds + " secs Left";
        }

		/*diff = String.format("%d hour(s) %d min(s) %d Seconds(s)",
                TimeUnit.MILLISECONDS.toHours(timeDiff),
                TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)),
                TimeUnit.MILLISECONDS.toSeconds(timeDiff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDiff))
        		);*/
        //  return diff;
    }

    public void sendNotification(String your_string_here) /*the string you need to pass in notification*/{

        Log.i("NextActivity", "startNotification");

        // Sets an ID for the notification
        int mNotificationId = 001;
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        /*to set sound for notification use
        Builder.setSound(alarmSound)*/

        /*to use style for notification use
        Builder.setStyle(new NotificationCompat.InboxStyle())*/

        // Build Notification , setOngoing keeps the notification always in status bar
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(myContext)/*this*/
                        .setSmallIcon(R.drawable.ic_menu_camera)/*logo you want to show to user in notification bar*/
                        .setContentTitle("FEBA")/*title*/
                        .setContentText("Offer...!!!")/*text*/
                        .setOngoing(true);

        // Create pending intent, mention the Activity which needs to be
        //triggered when user clicks on notification(StopScript.class in this case)

        Log.d("DownloadActivity...", "class is called");

        Intent i = new Intent(myContext, MainActivity.class);
        i.putExtra("your_key_here", your_string_here);
        PendingIntent contentIntent = PendingIntent.getActivity(myContext, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) myContext.getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }

    public void removeNotification(){
        NotificationManager notifManager= (NotificationManager) myContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.cancelAll();
    }

    public void saveContact(String name, String phoneNumber, String HomeNumber, String emailID, String WorkNumber, String company, String jobTitle){


        /*To use this method you need to provide read and write contact permission in Manifest file*/

        ArrayList<ContentProviderOperation> ops = new ArrayList< ContentProviderOperation >();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());


        if (name != null) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            name).build());
        }

        //------------------------------------------------------ Mobile Number
        if (phoneNumber != null) {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        //------------------------------------------------------ Home Numbers
        if (HomeNumber != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                    .build());
        }

        //------------------------------------------------------ Work Numbers
        if (WorkNumber != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                    .build());
        }

        //------------------------------------------------------ Email
        if (emailID != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());
        }

        //------------------------------------------------------ Organization
        if (!company.equals("") && !jobTitle.equals("")) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .build());
        }

        try {
            myContext.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            showToast("contact saved");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(myContext, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void putListString(String key, ArrayList<String> stringList) {
        checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        SharedPreferences preferences = myContext.getSharedPreferences(RANTCHAT, myContext.MODE_PRIVATE);
        preferences.edit().putString(key, TextUtils.join("â€šâ€—â€š", myStringList)).apply();
    }

    public void checkForNullKey(String key){
        if (key == null){
            throw new NullPointerException();
        }
    }

    public ArrayList<String> getListString(String key) {
        SharedPreferences preferences = myContext.getSharedPreferences(RANTCHAT, myContext.MODE_PRIVATE);
        return new ArrayList<String>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "â€šâ€—â€š")));
    }


    public void showError(final String err){
        myActivity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(myContext, err, Toast.LENGTH_LONG).show();
            }
        });
    }


    //Check if internet is present or not
    public boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            showToast("Unable to process your request.Please check your internet connection.");
            return false;
    }


    public String getAddress(String lat, String lon) {

        Log.d("iamingetAdress==>", "min");

        Geocoder geocoder = new Geocoder(myContext, Locale.ENGLISH);
        String ret = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1);
            if(addresses != null && !addresses.isEmpty()) {
                Address returnedAddress = addresses.get(0);
                String cityname = addresses.get(0).getLocality();
                StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
            }
            else{
                ret = "No_Address_returned";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ret = "Cant_get_address";
        }
        return ret;
    }


    // TODO Use below code when you want to call a activity method from your adapter
    /*
    * if(context instanceof YourActivityName){
    * ((YourActivityName)context).YourMethodName(context);}
    */

    public void showSoftKeyboard() {
        if (myActivity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) myActivity.getSystemService(myActivity.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInputFromInputMethod(myActivity.getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void  AlertDialogcall(String title,String positiveButton,String negativeButton, boolean setPossitiveButton,boolean setNegativeButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext, R.style.TintTheme);

        builder.setTitle(title);

        if (setPossitiveButton == true){
            builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });
        }
        else if(setPossitiveButton == false){

        }


        if(setNegativeButton == true){
            builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });
        }
        else if (setNegativeButton == false){

        }




        builder.show();
    }


}
