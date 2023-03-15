package com.example.tappingandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        Missatge message = getItem(position);

        TextView tvSender = convertView.findViewById(R.id.textView_nom);
        TextView tvTime = convertView.findViewById(R.id.textView_hora);
        TextView tvMessage = convertView.findViewById(R.id.textView_missatge);

        tvSender.setText(message.getUsuari());
        tvTime.setText(message.getHora());
        tvMessage.setText(message.getMissatge());

        return convertView;
    }
}

