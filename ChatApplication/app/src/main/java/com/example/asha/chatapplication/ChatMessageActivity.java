package com.example.asha.chatapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.asha.chatapplication.data.model.extraMessage;
import com.example.asha.chatapplication.data.remote.APIService;
import com.example.asha.chatapplication.data.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private APIService mAPIService;
    List<Message> msgs;
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

        MainActivity.dbClass.extraMessageInterfaceDao().deleteExtraMessage(chat_id);

        SharedPreferences sharedPreferences1=getSharedPreferences("chat_data",MODE_PRIVATE);
        chat_id=sharedPreferences1.getInt("chat_id",-1);

        getChatMessages();


        if(isNetworkAvailable() && MainActivity.dbClass.extraMessageInterfaceDao().getExtraMessage(chat_id) !=null)
        {
            for (extraMessage e:MainActivity.dbClass.extraMessageInterfaceDao().getExtraMessage(chat_id))
            {
                Message m=new Message();
                m.setMessage(e.getMessage());
                m.setToUserId(e.getToUserId());
                m.setFromUserId(e.getFromUserId());
                sendMessage(m.getMessage(),m.getToUserId());
            }
            MainActivity.dbClass.extraMessageInterfaceDao().deleteExtraMessage(chat_id);
        }


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

        if(!isNetworkAvailable() && MainActivity.dbClass.messageInterfaceDao()!=null)
        {
            msgs=new ArrayList<Message>();
            msgs=MainActivity.dbClass.messageInterfaceDao().getMessages(chat_id);

            for (int i=0;i<msgs.size();i++)
            {
                Log.e("Message:",msgs.get(i).getMessage());
            }
            adapter();
        }
        else
        {

            //Log.e("Message size=",String.valueOf(msgs.size()));
           // apiCallForGetMessages();

            Timer timer = new Timer();
            timer.scheduleAtFixedRate( new TimerTask()
            {
                public void run() {

                    try {

                        new AsyncTask().execute();

                    } catch (Exception e) {

                    }

                }
            }, 0, 1000);
        }
           // new AsyncTask().execute();

        }



    private  void sendMessage(String message,Integer chat_id)
    {
        if(!isNetworkAvailable())
        {
            extraMessage e=new extraMessage();
            e.setMessage(message);
            e.setToUserId(chat_id);
            e.setFromUserId(loggedIn_id);
            MainActivity.dbClass.extraMessageInterfaceDao().addExtraMessage(e);

            Log.e("Extra Message:",String.valueOf(MainActivity.dbClass.extraMessageInterfaceDao().getCount(chat_id)));
            Toast.makeText(this,"Message Will be sent as soon as network available",Toast.LENGTH_SHORT).show();

        }
        else
        {
            Message msg=new Message(message,chat_id);
            Toast.makeText(this,"Message sent",Toast.LENGTH_SHORT).show();

            apiCallForSendMessage(msg);
        }
        et_sendmsg.setText("");
        getChatMessages();

    }




    //api calls
    private void apiCallForSendMessage(Message msg)
    {

        Log.e("Data",msg.getMessage()+" "+String.valueOf(msg.getToUserId()));
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
    }

    private void apiCallForGetMessages()
    {
        //msgs=null;
        msgs=new ArrayList<Message>();
        Call<List<Message>> call = mAPIService.getMessages(loggedIn_token,chat_id);

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

                 //   Log.e("Message size=",String.valueOf(msgs.size()));

                    if(MainActivity.dbClass.messageInterfaceDao().getMessages(chat_id) != msgs)
                    {
                        MainActivity.dbClass.messageInterfaceDao().deleteMessage(chat_id);
                        for(Message m:msgs)
                        {
                            Log.e("",m.getMessage());
                            MainActivity.dbClass.messageInterfaceDao().addMessage(m);
                            Log.e("Message in db:",String.valueOf(MainActivity.dbClass.messageInterfaceDao().getMessages(chat_id).size()));
                        }
                    }
                    adapter();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "NOT OK", Toast.LENGTH_SHORT).show();
                Log.e("Response:",t.toString());
            }
        });
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private  void adapter()
    {
        ChatRecyclerViewAdapter adapter= new ChatRecyclerViewAdapter(msgs);

            chat_recycler_view.setLayoutManager(new LinearLayoutManager(ChatMessageActivity.this));
            chat_recycler_view.setAdapter(adapter);// it works second time and later


    }

    public class AsyncTask extends android.os.AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            apiCallForGetMessages();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("done","done");
        }
    }
}