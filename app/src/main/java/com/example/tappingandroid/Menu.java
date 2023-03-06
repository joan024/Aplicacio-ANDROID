package com.example.tappingandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import GestioDeRegistres.IniciSessio;
import GestioDeRegistres.Registre;

public class Menu extends AppCompatActivity {


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navigation, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btn_dades:
                anarA("dades");
                return true;

                case R.id.btn_preferits:
                    anarA("preferit");
                    return true;
                    case R.id.btn_descompte:
                        anarA("descompte");
                        return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void anarA(String valor) {
        Intent intent = null;
        if (valor.contains("dades")){
            intent = new Intent(this, IniciSessio.class);
            intent.putExtra("dades", valor);

        }

        if (valor.contains("preferit")){
            intent = new Intent(this, Registre.class);
            intent.putExtra("preferit", valor);
        }

        if (valor.contains("descompte")){
            intent = new Intent(this, Inici.class);
            intent.putExtra("descompte", valor);
        }
        startActivity(intent);


    }

}
