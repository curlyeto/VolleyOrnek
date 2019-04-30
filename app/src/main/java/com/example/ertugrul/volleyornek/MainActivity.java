package com.example.ertugrul.volleyornek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue requestQueue;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        FirebaseApp.initializeApp(this);
        setContentView( R.layout.activity_main );
        mTextViewResult=findViewById( R.id.text_view_result );
        Button buttonParse=findViewById( R.id.button_parse );

        requestQueue= Volley.newRequestQueue( this );

        buttonParse.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        } );

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }
    private void jsonParse(){
        String url="https://api.myjson.com/bins/dd26s";

        JsonObjectRequest request=new JsonObjectRequest( Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray( "employees" );

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject employee=jsonArray.getJSONObject(  i);

                               /*
                               Filtreleme yapıldı firstname 'i ertugrul olan kişiler geldi
                               if (employee.get( "firstname" ).equals( "Ertuğrul" )){
                                    String firstNmae=employee.getString( "firstname" );
                                    int age=employee.getInt( "age" );
                                    String mail=employee.getString( "mail" );
                                    mTextViewResult.append(  firstNmae +", "+String.valueOf( age )+", "+mail+"\n\n");
                                }*/


                                String firstNmae=employee.getString( "firstname" );
                                int age=employee.getInt( "age" );
                                String mail=employee.getString( "mail" );

                                mTextViewResult.append( firstNmae +", "+String.valueOf( age )+", "+mail+"\n\n" );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            }
        } );
        requestQueue.add( request );
    }
}
