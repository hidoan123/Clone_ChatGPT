package com.example.test_chatgpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_chatgpt.HistoryChat.HistoryChat;
import com.example.test_chatgpt.login.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Database database;
    RecyclerView recyclerView;
    TextView welcomTextView;
    EditText messageEditText;
    ImageView sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    ImageView History;
    ImageView btn_logout;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // tao database
        database = new Database(this, "History.sqlite", null, 1);
        // tao bang
        database.QueryData("CREATE TABLE IF NOT EXISTS LichSu(Id INTEGER PRIMARY KEY AUTOINCREMENT,question VARCHAR(1000),reply VARCHAR(1000))");
        //truy van test
        //database.QueryData("INSERT INTO LichSu VALUES(NULL,'Van Doan Nguyen dep trai ko','van doan rat dep trai')");
        //history
        History = findViewById(R.id.img_History);
        btn_logout = findViewById(R.id.img_logout);

        messageList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        welcomTextView = findViewById(R.id.welcome_Text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);
        // setup recycler view
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

      sendButton.setOnClickListener((v)->{
          String question = messageEditText.getText().toString().trim();
          addToChat(question,Message.SENT_BY_ME);
          messageEditText.setText("");
          welcomTextView.setVisibility(View.GONE);
          callAPI(question);
      });
      //khi an vao lich su
        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryChat.class);
                startActivity(intent);
            }
        });


    }

    protected void addToChat(String message, String sentBy)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response)
    {
        addToChat(response,Message.SENT_BY_BOT);
    }
    void callAPI(String question)
    {
        //httpok
        JSONObject jsonbody = new JSONObject();
        try {
            jsonbody.put("model","text-davinci-003");
            jsonbody.put("prompt",question);
            jsonbody.put("max_tokens",4000);
            jsonbody.put("temperature",0);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonbody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("kết nối thất bại "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                        database.QueryData("INSERT INTO LichSu VALUES(NULL,'"+ question +"','"+ result +"')");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    addResponse("kết nối thất bại "+response.body().toString());
                }

            }
        });
    }
}