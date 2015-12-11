package ua.pi_13.echards;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
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

public class RegistrationActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword, editTextName, editTextPassword2;
    ServerConnect mServerConnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPassword2 = (EditText) findViewById(R.id.editTextPasswordConfirm);
        editTextName =(EditText) findViewById(R.id.editTextName);
        mServerConnect=new ServerConnect(this);
    }

    public void onClickRegistration(View view) {
        if(!mServerConnect.isConnected()) {
            Toast toast = Toast.makeText(getApplicationContext(),"Подключение к интернету недоступно!",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        if(!mServerConnect.isValidFields(editTextEmail.getText().toString(), editTextPassword.getText().toString(),editTextPassword2.getText().toString(),editTextName.getText().toString())){
            Toast toast = Toast.makeText(getApplicationContext(), (CharSequence) mServerConnect.INVALID_FIELDS, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        if(!mServerConnect.isValidEmailAddress(editTextEmail.getText().toString())){
            Toast toast = Toast.makeText(getApplicationContext(), (CharSequence) mServerConnect.INVALID_EMAIL, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        if(!mServerConnect.isValidPassword(editTextPassword.getText().toString(), editTextPassword2.getText().toString()))
        {
            Toast toast = Toast.makeText(getApplicationContext(), (CharSequence) mServerConnect.INVALID_PASSWORD2, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        if(!mServerConnect.isValidName(editTextName.getText().toString()))
        {
            Toast toast = Toast.makeText(getApplicationContext(), (CharSequence) mServerConnect.INVALID_NAME, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        HttpPOSTRequestWithParameters();
    }

    public void HttpPOSTRequestWithParameters(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, mServerConnect.url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("error")=="false")
                            {
                                Toast.makeText(getApplicationContext(),"Вам на почтовый ящик выслано сообщение для подтверждения, после чего авторизуйтесь!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                intent.putExtra("email",editTextEmail.getText().toString());
                                intent.putExtra("password",editTextPassword.getText().toString());
                                startActivity(intent);
                                closeActivity();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),json.getString("error_msg"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", "error => " + error.toString());
                    }
                }
        ) {
            // this is the relevant method
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("tag", "register");
                params.put("email", editTextEmail.getText().toString());
                params.put("password", editTextPassword.getText().toString());
                params.put("name", editTextName.getText().toString());
                return params;
            }
        };
        queue.add(postRequest);
    }

    void closeActivity(){
        this.finish();
    }
}
