package com.example.arashi.angelhack;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();


        Button loginButton = (Button) findViewById(R.id.button_login_ok);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(getApplicationContext(), EventMap.class);
//                startActivity(i);


                EditText username = (EditText) findViewById(R.id.edittext_login_username);
                EditText pass = (EditText) findViewById(R.id.edittext_login_password);

                createUserWithEmailAndPassword(username.getText().toString(),pass.getText().toString());
            }
        });
    }

    private void createUserWithEmailAndPassword(String email, String password) {
//        // emailとパスワードでアカウント作成
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (!task.isSuccessful()) {
//                    Log.d("Login#onComplete", task.getException().toString());
//                    // アカウント作成あるいはログイン失敗時にはメッセージを表示する
//                    Toast.makeText(MainActivity.this, "作成失敗",
//                            Toast.LENGTH_SHORT).show();
//                }else{
//                    Log.d("onComplete", "アカウント作成及びログインに成功しました。");
//                    Intent intent = new Intent(getApplicationContext(), EventMap.class);
//                    startActivity(intent);
//                }
//
//            }
//        });

        // emailとパスワードでログイン
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Log.d("Login#onComplete", task.getException().toString());
                            // ログイン失敗
                            Toast.makeText(MainActivity.this, "ログイン失敗",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d("Login#onComplete", "ログインに成功しました。");
                            Intent i = new Intent(getApplicationContext(), EventMap.class);
                            startActivity(i);
                        }
                    }
                });
    }
}
