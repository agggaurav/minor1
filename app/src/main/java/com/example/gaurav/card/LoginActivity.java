package com.example.gaurav.card;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    UserSession session;
     public String url = "http://192.168.1.102:8000/user/";
    RequestQueue requestQueue;

    public EditText email,password;
    public Button login,signup;
    public String pass,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // session = new UserSession(getApplicationContext());
        //session.createUserLoginSession("admin", "admin");
       // EditText editText = (EditText) findViewById(R.id.editText);

        requestQueue = Volley.newRequestQueue(this);
        login=(Button) findViewById(R.id.signin);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.pa);


        // Casts results into the TextView found within the main layout XML with id jsonData
login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       // String value = editText.getText().toString();
        id=email.getText().toString();
        pass= password.getText().toString();
        url=url+id+"/?format=json";
        if(id==null || pass==null)
        Toast.makeText(getApplicationContext(),"Please fill the details", Toast.LENGTH_SHORT).show();
        checklogin(id,pass);

    }
});

signup=(Button)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent admin=new Intent(getApplicationContext(),SignUp.class);
                startActivity(admin);
            }
        });
    }

public void checklogin(String ids, final String passw)
{

    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
            url, null, new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
            Log.d("TAG", response.toString());

            try {
                String b = response.getString("password");
                String a = response.getString("email_id");
               // Toast.makeText(getApplicationContext(),a+"--"+b,Toast.LENGTH_SHORT).show();
                if (a == null) {
                    Toast.makeText(getApplicationContext(), "Invalid Email Id", Toast.LENGTH_SHORT).show();
                }

               // Toast.makeText(getApplicationContext(),"pass"+passw,Toast.LENGTH_SHORT).show();
                if (b.equals(passw)) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Password  ", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Error: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            VolleyLog.d("TAG", "Error: " + error.getMessage());
            Toast.makeText(getApplicationContext(),
                    error.getMessage(), Toast.LENGTH_SHORT).show();
                  }
    });


    // Adding request to request queue
    AppController.getInstance().addToRequestQueue(jsonObjReq);

    email.setText("");
    password.setText("");
    id="";
    pass="";
    url = "http://192.168.1.102:8000/user/";


}

    public String getid()
    {
        return id;
    }

    public String getpass()
    {
        return pass;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
