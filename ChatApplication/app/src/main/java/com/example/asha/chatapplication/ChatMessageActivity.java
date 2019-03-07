package com.example.asha.chatapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asha.chatapplication.data.model.Message;
import com.example.asha.chatapplication.data.model.User;
import com.example.asha.chatapplication.data.remote.APIService;
import com.example.asha.chatapplication.data.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatMessageActivity extends AppCompatActivity {


    RecyclerView chat_recycler_view;
  public   Button btn_sendmsg;
  public   EditText et_sendmsg;
    Integer loggedIn_id;
    String loggedIn_name;
    String loggedIn_token;
    Integer chat_id;
    String message;
    TextView tv_loggedIn_name;
    private APIService mAPIService;
    ArrayList<Message> msgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        chat_recycler_view=(RecyclerView) findViewById(R.id.chat_recycler_view);
        btn_sendmsg=(Button) findViewById(R.id.btn_sendmsg);
        et_sendmsg=(EditText) findViewById(R.id.et_sendmsg);
        mAPIService = ApiUtils.getAPIService();

        SharedPreferences sharedPreferences= getSharedPreferences("login_user_data",MODE_PRIVATE);
        loggedIn_id=sharedPreferences.getInt("id",-1);
        loggedIn_name=sharedPreferences.getString("name","");
        loggedIn_token=sharedPreferences.getString("token","");

       int chat_id1= Integer.parseInt(getIntent().getStringExtra("chat_id"));
       String chat_name=getIntent().getStringExtra("chat_name");

        sharedPreferences=getSharedPreferences("chat_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("chat",true);
        editor.putInt("chat_id",chat_id1);
        editor.putString("chat_name",chat_name);
        editor.commit();

        SharedPreferences sharedPreferences1=getSharedPreferences("chat_data",MODE_PRIVATE);
        chat_id=sharedPreferences1.getInt("chat_id",-1);
        chat_name=sharedPreferences1.getString("chat_name","");

        getChatMessages();

        btn_sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String data=String.valueOf(et_sendmsg.getText()).trim();
                SharedPreferences sharedPreferences=getSharedPreferences("send_message_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("send_message",true);
                editor.putString("msg",data);
                editor.commit();

                SharedPreferences sharedPreferences1=getSharedPreferences("send_message_data",MODE_PRIVATE);
                message=sharedPreferences1.getString("msg","");



                sendMessage(message,chat_id);

            }
        });


    }
    private void getChatMessages()
    {

        Call<List<Message>> call = mAPIService.getMessages(loggedIn_token,chat_id);
        msgs=new ArrayList<Message>();
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                Log.e("Token:",loggedIn_token);
                Log.e("Id:", String.valueOf(chat_id));
                if (response.isSuccessful()) {
                    List<Message> res = response.body();
                    Message msg=null;
                    Log.e("rest response", response.toString());
                    for (int i = 0; i < response.body().size(); i++)
                    {
                      //  Log.e("rest response","Id:"+res.get(i).getId()+ " Message:" + res.get(i).getMessage() + "  From:" + res.get(i).getFromUserId() +"  To:"+res.get(i).getToUserId()+"  Datetime:"+res.get(i).getCreatedDateTime()+"\n");
                        msg = new Message();
                        Integer msg_id=res.get(i).getId();
                        String message=res.get(i).getMessage();
                        Integer from=(res.get(i).getFromUserId());
                        Integer to=res.get(i).getToUserId();
                        String datetime=res.get(i).getCreatedDateTime();
                            msg.setId(msg_id);
                            msg.setMessage(message);
                            msg.setFromUserId(from);
                            msg.setToUserId(to);
                            msg.setCreatedDateTime(datetime);
                            msgs.add(msg);
                    }

                    ChatRecyclerViewAdapter adapter = new ChatRecyclerViewAdapter(msgs);
                    chat_recycler_view.setLayoutManager(new LinearLayoutManager(ChatMessageActivity.this));
                    chat_recycler_view.setAdapter(adapter);



                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "NOT OK", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void sendMessage(String message,Integer chat_id)
    {
        //api call

        Message msg=new Message(message,chat_id);

        Log.e("Date:","loggedIn_token: "+loggedIn_token+"  To: "+msg.getToUserId()+" Message:"+ msg.getMessage());
        Call<Void> call=mAPIService.sendMessage(loggedIn_token,msg);

        call.enqueue(new Callback<Void>()
        {
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if(response.isSuccessful())
                {
                    Log.e("Response: ",response.toString());

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {

                Toast.makeText(getApplicationContext(),"NOT OK",Toast.LENGTH_SHORT).show();
            }
        });
        et_sendmsg.setText("");
        getChatMessages();
    }




}
