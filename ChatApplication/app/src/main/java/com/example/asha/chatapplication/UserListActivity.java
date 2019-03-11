package com.example.asha.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.asha.chatapplication.data.model.User;
import com.example.asha.chatapplication.data.remote.APIService;
import com.example.asha.chatapplication.data.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity {
    //Button button_userlist;
    private APIService mAPIService;
    List<User> users;
    RecyclerView recyclerView;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        username=(TextView) findViewById(R.id.username);
        username.setText("User List");
        mAPIService = ApiUtils.getAPIService();
        users = new ArrayList<>();

        if(!isNetworkAvailable())
        {
            //if network is not available then fetch from db
            Toast.makeText(this,"Internet Off",Toast.LENGTH_SHORT).show();
            if(MainActivity.dbClass.userInterfaceDao().getUsers() !=null)
            {
                users=MainActivity.dbClass.userInterfaceDao().getUsers();
                adapter();
            }
            else
            {
                Toast.makeText(this,"Network Not available",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            getUserList();


        }
    }


//apiCall
    private void getUserList()
    {
        Call<List<User>> call = mAPIService.getUsers(getIntent().getStringExtra("userToken"));
        users = new ArrayList<User>();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> res = response.body();
                    User user = null;
                    Log.e("rest response", response.toString());

                    for (int i = 0; i < response.body().size(); i++) {
                        user = new User();
                        Integer id = Integer.parseInt(String.valueOf(res.get(i).getId()));
                        String name = String.valueOf(res.get(i).getName());
                        user.setId(id);
                        user.setName(name);
                        users.add(user);
                    }

                    if (MainActivity.dbClass.userInterfaceDao().getUsers() != users) {
                        MainActivity.dbClass.userInterfaceDao().deleteUsers();
                        for (User u : users) {
                            MainActivity.dbClass.userInterfaceDao().addUser(u);
                        }
                    }
                    adapter();

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "NOT OK", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void adapter()
    {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(users);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserListActivity.this));
        recyclerView.setAdapter(adapter);
    }
    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}