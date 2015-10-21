package com.example.dkucrut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by subki on 10/13/2015.
 */
public class LoginActivity  extends Activity { //implements OnClickListener {

    Login login = new Login();
    Button btnSignIn, btnSignUp, btnClose;
    String users, passw, role;
    EditText userFill, passFill;

    JSONArray arrayLogin;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
       // btnSignIn = (Button) findViewById(R.id.btnSignIn);
       // btnSignIn.set
    }


    public void onClick(View view) {

        if (view.getId() == R.id.btnSignUp) {
          //  tambahAkun();
        }
    }

    public void Login (View view){

        userFill = (EditText) findViewById(R.id.userFill);
        passFill = (EditText) findViewById(R.id.passFill);

        String user = userFill.getText().toString();
        String pass = passFill.getText().toString();

        cekAkun(user, pass);

    }
    public void cekAkun(String user, String pass) {

        JSONArray arrayPersonal;

        try {

            arrayPersonal = new JSONArray(login.getAkun(user, pass));


            for (int i = 0; i<arrayPersonal.length(); i++ ){
                JSONObject jsonObject = arrayPersonal.getJSONObject(i);
                String userName = jsonObject.optString("user");
                String passWord = jsonObject.optString("pass");
                String roleAuth = jsonObject.optString("role");

                sharedpreferences = getSharedPreferences("MyData", Context.MODE_WORLD_READABLE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("a", userName);
                editor.putString("b", passWord);
                editor.putString("c", roleAuth);
                editor.commit();

                if (userName != null) {
                    if (passWord != null) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "User Name / Password Salah !", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    /* restart acrtivity */
        finish();
        startActivity(getIntent());
    }
}

