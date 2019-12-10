package com.fhad.producer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tvMessage;
    EditText etMessage;
    Button btnSend;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            tvMessage.append("Message read \n");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMessage = findViewById(R.id.et_message);
        tvMessage = findViewById(R.id.tv_answer);
        btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent("com.fhad.consumer.action.producer");
                String sendMessage = etMessage.getText().toString();
                if(!sendMessage.equalsIgnoreCase("")){
                    sendIntent.setPackage("com.fhad.consumer");
                    sendIntent.putExtra("message", sendMessage);
                    sendBroadcast(sendIntent);
                    tvMessage.append(" -  Send to OS - "+sendMessage+"\n");
                    etMessage.getText().clear();
                }else{
                    Toast.makeText(MainActivity.this, "Your message!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        IntentFilter filter = new IntentFilter("com.fhad.producer.action.consumer");

        registerReceiver(receiver, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}

