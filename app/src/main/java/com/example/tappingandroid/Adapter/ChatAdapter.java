package com.example.tappingandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tappingandroid.Dades.Missatge;
import com.example.tappingandroid.R;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private List<Missatge> messages;
    private LayoutInflater inflater;

    public ChatAdapter(Context context, List<Missatge> messages) {
        this.messages = messages;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.misatge_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = convertView.findViewById(R.id.textView_nom);
            viewHolder.messageTextView = convertView.findViewById(R.id.textView_missatge);
            viewHolder.timeTextView = convertView.findViewById(R.id.textView_hora);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Missatge message = (Missatge) getItem(position);
        viewHolder.nameTextView.setText(message.getUsuari());
        viewHolder.messageTextView.setText(message.getMissatge());
        viewHolder.timeTextView.setText(message.getHora());

        return convertView;
    }

    private static class ViewHolder {
        TextView nameTextView;
        TextView messageTextView;
        TextView timeTextView;
    }
}
