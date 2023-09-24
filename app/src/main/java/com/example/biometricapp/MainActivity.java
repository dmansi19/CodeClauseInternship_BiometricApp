package com.example.biometricapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.biometric.BiometricPrompt;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    Button btn_auth;
    TextView tv_auth;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_auth=findViewById(R.id.btn_auth);
        tv_auth=findViewById(R.id.tv_auth);
        executor= ContextCompat.getMainExecutor(this);
        biometricPrompt= new BiometricPrompt(MainActivity.this, executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                tv_auth.setText("Error : "+errString);

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                tv_auth.setText("Successful Authentication");
                Toast.makeText(MainActivity.this, "Congratulations", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                tv_auth.setText("Failed");
            }
        });


        promptInfo=new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                        .setSubtitle("Login using Fingerprint or Face")
                                .setNegativeButtonText("cancel")

                                        .build();


        btn_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);


            }
        });
    }
}