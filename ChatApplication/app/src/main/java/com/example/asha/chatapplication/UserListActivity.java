package com.example.asha.chatapplication;

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
    ArrayList<User> users;
    RecyclerView recyclerView;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        username=(TextView) findViewById(R.id.username);
        username.setText("User List");
        mAPIService = ApiUtils.getAPIService();
        users = new ArrayList<>();
        clickForUserList();

    }

    private void clickForUserList() {
        Call<List<User>> call = mAPIService.getUsers(getIntent().getStringExtra("userToken"));
        users = new ArrayList<User>();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> res = response.body();
                    User user = null;
                    Log.e("rest response", response.toString());

                    for (int i = 0; i < response.body().size(); i++)
                    {
                        user = new User();
                        Integer id = Integer.parseInt(String.valueOf(res.get(i).getId()));
                        String name = String.valueOf(res.get(i).getName());
                        user.setId(id);
                        user.setName(name);
                        users.add(user);

                    }

                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(users);
                    recyclerView.setLayoutManager(new LinearLayoutManager(UserListActivity.this));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "NOT OK", Toast.LENGTH_SHORT).show();

            }
        });


    }

}