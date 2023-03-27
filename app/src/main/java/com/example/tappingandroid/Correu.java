package com.example.tappingandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;

public class Correu extends AppCompatActivity {
    Button btnMensaje;
    //Session session;
    String email,contra;
    EditText correo, asunto, mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correu);

        btnMensaje = findViewById(R.id.btn_enviar);
        email = "m.avila@insjoanbrudieu.cat";
        contra = "avila2017";

        btnMensaje.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {

                correo = findViewById(R.id.contacte_correo);
                asunto = findViewById(R.id.contacta_assumpte);
                mensaje = findViewById(R.id.contacta_correu);



                String enviarasunto = asunto.getText().toString();
                String enviarmensaje = mensaje.getText().toString();
                //enviarConGMail("tappinges@gmail.com",enviarasunto,enviarmensaje);

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Properties properties = new Properties();
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.socketFactory.port", "587");
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.port", "587");

                /*try {
                    session = Session.getDefaultInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(email, contra);
                        }
                    });
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(email));
                        message.setSubject(enviarasunto);
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("marioavilasuarez2017@gmail.com"));
                        message.setContent(enviarmensaje, "text/html; charset=utf-8");
                        Transport.send(message);
                    } catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }*/

                volver();
                Toast.makeText(getApplicationContext(),"Missatge Enviat", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void volver() {
        Intent intent = new Intent(this, Inici.class);
        intent.putExtra("usuari", btnMensaje.getText().toString());
        startActivity(intent);
    }
  /*  private void enviarConGMail(String destinatario, String asunto, String cuerpo) {

        //La dirección de correo de envío
        String remitente = "m.avila@insjoanbrudieu.cat";
        //La clave de aplicación obtenida según se explica en este artículo:
        String claveemail = "avila2017";

        new Thread(new Runnable() {
            @Override
            public void run() {

                Properties props = System.getProperties();
                props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
                props.put("mail.smtp.user", remitente);
                props.put("mail.smtp.clave", claveemail);    //La clave de la cuenta
                props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
                props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
                props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

                Session session = Session.getDefaultInstance(props);
                MimeMessage message = new MimeMessage(session);

                try {
                    message.setFrom(new InternetAddress(remitente));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
                    message.setSubject(asunto);
                    message.setText(cuerpo);
                    Transport transport = session.getTransport("smtp");
                    transport.connect("smtp.gmail.com", remitente, claveemail);
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                }
                catch (MessagingException me) {
                    me.printStackTrace();   //Si se produce un error
                }
            }
        }).start();
    }*/
}

