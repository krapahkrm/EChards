package ua.pi_13.echards;


import android.content.ContentValues;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

@SuppressWarnings("ALL" )
public class MainActivity extends AppCompatActivity {




    EditText editTextEmail, editTextPassword;
    ServerConnect mServerConnect;
    DatabaseHelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mServerConnect = new ServerConnect(this);
        mDatabaseHelper = new DatabaseHelper(this);
        checkAutorization();

        Intent intent = getIntent();
        editTextEmail.setText(intent.getStringExtra("email"));
        editTextPassword.setText(intent.getStringExtra("password"));



    }

    private void checkAutorization(){

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        String sql = "SELECT " + mDatabaseHelper.DATACOLUMN_USERS_EMAIL + ", " + mDatabaseHelper.DATACOLUMN_USERS_PASSWORD +
                " FROM " + mDatabaseHelper.DATATABLE_USERS + " WHERE " + mDatabaseHelper.DATACOLUMN_USERS_AUTORIZATION + "=1";
        Cursor cursor = db.rawQuery(sql,null);
        if(!cursor.moveToFirst()) return;
        editTextEmail.setText(cursor.getString(0));
        editTextPassword.setText(cursor.getString(1));
        HttpPOSTRequestWithParameters();
    }

    public void onClickGoToRegistration(View view) {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void onClickGoToMenu(View view) {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
        //this.finish();
    }


    // JSON
    public void onClickLogin(View view) throws JSONException {



        if(!mServerConnect.isConnected()) {
            Toast toast = Toast.makeText(getApplicationContext(),"Подключение к интернету недоступно!",Toast.LENGTH_SHORT);
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
        if(!mServerConnect.isValidPassword(editTextPassword.getText().toString())){
            Toast toast = Toast.makeText(getApplicationContext(), (CharSequence) mServerConnect.INVALID_PASSWORD, Toast.LENGTH_SHORT);
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
                                Toast.makeText(getApplicationContext(),"Добро пожаловать!",Toast.LENGTH_SHORT).show();
                                writeNewUser(json.getString("name"), json.getString("email"),json.getString("password"));
                                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
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
                params.put("tag", "login");
                params.put("email", editTextEmail.getText().toString());
                params.put("password", editTextPassword.getText().toString());
                return params;
            }
        };
        queue.add(postRequest);

    }


    // DATABASE

    public void writeNewUser(String name,String email, String password){
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query(mDatabaseHelper.DATATABLE_USERS, new String[]{mDatabaseHelper.DATACOLUMN_USERS_EMAIL}, mDatabaseHelper.DATACOLUMN_USERS_EMAIL + " = ?",
                new String[]{String.valueOf(email)}, null, null, null, null);


        db = mDatabaseHelper.getWritableDatabase();
        // UPDATE 1->0
        ContentValues mContentValues = new ContentValues();
        mContentValues.put(mDatabaseHelper.DATACOLUMN_USERS_AUTORIZATION,"0");
        long rows = db.update(mDatabaseHelper.DATATABLE_USERS,mContentValues,mDatabaseHelper.DATACOLUMN_USERS_AUTORIZATION+"=1",null);


        if (cursor != null)
        {
            mContentValues = new ContentValues();
            mContentValues.put(mDatabaseHelper.DATACOLUMN_USERS_AUTORIZATION,String.valueOf(1));
            mContentValues.put(mDatabaseHelper.DATACOLUMN_USERS_EMAIL,String.valueOf(email));
            mContentValues.put(mDatabaseHelper.DATACOLUMN_USERS_NAME,String.valueOf(name));
            mContentValues.put(mDatabaseHelper.DATACOLUMN_USERS_PASSWORD,String.valueOf(password));

            db.insert(mDatabaseHelper.DATATABLE_USERS,null,mContentValues);
        }
        else{
            mContentValues = new ContentValues();
            mContentValues.put(mDatabaseHelper.DATACOLUMN_USERS_AUTORIZATION,String.valueOf(1));
            db.update(mDatabaseHelper.DATATABLE_USERS,mContentValues,mDatabaseHelper.DATACOLUMN_USERS_EMAIL+" = ?",new String[]{String.valueOf(email)});
        }

    }

    void closeActivity(){
        this.finish();
    }
}
