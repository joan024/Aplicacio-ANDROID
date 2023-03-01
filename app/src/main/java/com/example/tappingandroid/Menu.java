package com.example.tappingandroid;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navigation, menu);
    }

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

        if (valor.contains("dades")){
            Intent intent = new Intent(this, Inici.class);
            intent.putExtra("dades", valor);
        }

        if (valor.contains("preferit")){
            Intent intent = new Intent(this, Inici.class);
            intent.putExtra("preferit", valor);
        }

        if (valor.contains("descompte")){
            Intent intent = new Intent(this, Inici.class);
            intent.putExtra("descompte", valor);
        }


    }

}
