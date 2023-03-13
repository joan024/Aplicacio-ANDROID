package com.example.tappingandroid;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import GestioDeRegistres.IniciSessio;
import GestioDeRegistres.Registre;

public class Menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int titleId = getTitle(menuItem);
        showFragment(titleId);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private int getTitle(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.btn_dades:
                return R.string.les_meves_dades;
            case R.id.btn_preferits:
                return R.string.preferits;
            case R.id.btn_descompte:
                return R.string.descomptes;
            case R.id.btn_preguntes:
                return R.string.preguntes_freq_ents;
            case R.id.btn_contacte:
                return R.string.contacte_amb_nosaltres;
            default:
                throw new IllegalArgumentException("menu option not implemented!!");
        }
    }
    private void showFragment ( @StringRes int titleId){
        Fragment fragment = HomeFragment.newInstance(titleId);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(androidx.navigation.ui.R.anim.nav_default_enter_anim, androidx.navigation.ui.R.anim.nav_default_exit_anim)
                .replace(R.id.home_content, fragment)
                .commit();

        setTitle(getString(titleId));
    }
}






