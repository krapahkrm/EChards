package ua.pi_13.echards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by user on 16.11.2015.
 */
public class ServerConnect {
    public  static final String url = "http://guitarchords.8nio.com/";
    private Activity activity; // activity
    public JSONObject json;

    public ServerConnect(Activity mActivity){
        activity=mActivity;
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public boolean validate(){
        return true;
    }

    public final String INVALID_PASSWORD = "Ошибка. Пароль должен иметь от 6 символов длину и должен состоять из букв латинского алфавита и цифр!";
    public final String INVALID_EMAIL = "Ошибка. Вы неправильно ввели email!";
    public final String INVALID_PASSWORD2 = "Ошибка. Пароли должны совпадать и иметь длину от 6 символов, пароль должен состоять из букв латинского алфавита и цифр!";
    public final String INVALID_NAME = "Ошибка. Имя должно быть длиной от 3-символов и должно состоять только из букв латинского алфавита и цифр!";
    public final String ERROR_CONNECT = "Проверьте подключение к интернету.";
    public final String INVALID_FIELDS = "Вы заполнили не все поля!";

    public static  boolean isValidFields(String... fields)
    {
        for(int i=0;i<fields.length;i++)
        {
            if(fields[i].length()==0){
                return false;
            }
        }
        return true;
    }

    public static boolean isValidEmailAddress(String email) {
        Pattern Email = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        boolean result = true;
        if(!Email.matcher(email).find()) {
            result = false;
        }
        return result;
    }


    public static  boolean isValidName(String name)
    {
        Pattern hasSpecialChar = Pattern.compile("[^a-zA-Z0-9]");
        boolean result = true;
        if(name.length()<3 || hasSpecialChar.matcher(name).find()){
            result = false;
        }
        return result;
    }

    public static  boolean isValidPassword(String password)
    {
        Pattern hasSpecialChar = Pattern.compile("[^a-zA-Z0-9]");
        boolean result = true;
        if(password.length()<6 || hasSpecialChar.matcher(password).find()){
            result = false;
        }
        return result;
    }

    public static  boolean isValidPassword(String password1, String password2)
    {
        boolean result = true;
        if(!password1.equals(password2)){
            result = false;
        }
        return result && isValidPassword(password1) && isValidPassword(password2);
    }
}
