package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class Chat extends AppCompatActivity {

    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton;
    private ImageView iv_tornar;

    private ArrayAdapter<String> chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Obtener referencias a los elementos de la interfaz de usuario
        chatListView = findViewById(R.id.chatListView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        iv_tornar = findViewById(R.id.iv_tornar);

        iv_tornar.setOnClickListener(v -> onBackPressed());

        // Crear un adaptador para la vista de lista de chat y configurarlo
        chatAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        chatListView.setAdapter(chatAdapter);

        // Configurar el botón de envío de mensajes para agregar nuevos mensajes a la vista de lista
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageEditText.getText().toString();
                chatAdapter.add(message);
                messageEditText.setText("");
            }
        });
    }
}
