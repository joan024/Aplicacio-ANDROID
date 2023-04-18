package com.example.tappingandroid.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tappingandroid.Dades.Missatge;
import com.example.tappingandroid.R;

import java.util.List;

public class XatAdapter extends ArrayAdapter<Missatge> {

    private Context context;
    private int resource;

    public XatAdapter(Context context, int resource, List<Missatge> messages) {
        super(context, resource, messages);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Missatge missatge = getItem(position);

        if (missatge != null) {
            TextView tvNom = convertView.findViewById(R.id.textView_nom);
            TextView tvMissatge = convertView.findViewById(R.id.textView_missatge);
            TextView tvHora = convertView.findViewById(R.id.textView_hora);

            tvNom.setText(missatge.getUsuari());
            tvMissatge.setText(missatge.getMissatge());
            tvHora.setText(missatge.getHora());

            LinearLayout.LayoutParams params;
            if (missatge.getUsuari().equals("Jo")) {
                params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.gravity = Gravity.END;
            } else {
                params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.gravity = Gravity.START;
            }
            tvMissatge.setLayoutParams(params);
            tvNom.setLayoutParams(params);
            tvHora.setLayoutParams(params);
        }
        return convertView;
    }
}

