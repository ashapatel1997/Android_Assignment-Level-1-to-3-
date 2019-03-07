package com.example.asha.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asha.chatapplication.data.model.User;
import com.example.asha.chatapplication.data.remote.APIService;
import com.example.asha.chatapplication.data.remote.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
     Button loginbutton;
     EditText usernameAdd;
     TextView responseTv;
    private APIService mAPIService;
   public SharedPreferences sp;
    public String userToken;
    EditText txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAPIService = ApiUtils.getAPIService();

            allClickevents();
    }

    private void allClickevents() {
        loginbutton=(Button) findViewById(R.id.login);
        usernameAdd=(EditText) findViewById(R.id.username);
        responseTv=(TextView) findViewById(R.id.responseTv);

        loginbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String name = usernameAdd.getText().toString().trim();
                if (name.isEmpty()) {
                    usernameAdd.setError("Field can't be empty");
                    return;
                }
                for (int i = 0; i < name.length(); i++) {
                    char x = name.charAt(i);
                    if (Character.isWhitespace(x)) {
                        usernameAdd.setError("name should not contain white space");
                        return;
                    }
                }

                sendUser(name);

            }

    public void sendUser(String name)
    {

        final User user=new User(name);
        Call<User> call=mAPIService.saveUser(user);
        call.enqueue(new Callback<User>()
        {
            public void onResponse(Call<User> call, Response<User> response)
                    {
                        if(response.isSuccessful())
                        {

                            User res=response.body();

                            sp=getSharedPreferences("login_user_data", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sp.edit();
                            editor.putBoolean("loggedIn",true);
                            editor.putInt("id",res.getId());
                            editor.putString("name",res.getName());
                            editor.putString("token",res.getToken());
                            editor.commit();

                            userToken=res.getToken();

                            Intent i=new Intent(getApplicationContext(),UserListActivity.class);
                           i.putExtra("userToken",userToken);
                            i.putExtra("name",res.getName());
                            i.putExtra("id",res.getId());
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t)
                    {

                        Toast.makeText(getApplicationContext(),"NOT OK",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
