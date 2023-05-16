package com.example.test_chatgpt.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test_chatgpt.MainActivity;
import com.example.test_chatgpt.R;
import com.example.test_chatgpt.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText edt_login;
    private EditText edt_password;
    private Button btn_LogIn;
    private List<User> mlistUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_login = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        btn_LogIn = findViewById(R.id.btn_Login);
        mlistUser = new ArrayList<>();
        getListUser();
        btn_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLogIn();
            }
        });
    }
    private void clickLogIn()
    {
        String username = edt_login.getText().toString().trim();
        String pass = edt_password.getText().toString().trim();
        boolean isHasUser = true;
        if(username == "" && pass == "")
        {
            isHasUser = false;
            Toast.makeText(this, "please input username and password", Toast.LENGTH_SHORT).show();
        }
        if(mlistUser == null || mlistUser.isEmpty())
        {
            Toast.makeText(this, "khong co du lieu", Toast.LENGTH_SHORT).show();
        }

        for(User user:mlistUser)
        {
            if(username.equals(user.getUserName()) && pass.equals(user.getPassw()))
            {
                isHasUser = true;
                break;
            }
        }
        if(isHasUser == true)
        {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "ket noi that bai", Toast.LENGTH_SHORT).show();
        }
    }
    private void getListUser() {
        ApiService.apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful())
                {
                    mlistUser = response.body();
                }else {
                    Toast.makeText(Login.this, "Response error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(Login.this, "Call API failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
