package com.example.tappingandroid;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public class HomeFragment extends Fragment {

    private static final String TEXT_ID = "text_id";

    public static HomeFragment newInstance(@StringRes int textId) {
        HomeFragment frag = new HomeFragment();

        Bundle args = new Bundle();
        args.putInt(TEXT_ID, textId);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.home_fragment, container, false);

        if (getArguments() != null) {
            String text = getString(getArguments().getInt(TEXT_ID));
            ((TextView) layout.findViewById(R.id.text)).setText(text);
        } else {
            throw new IllegalArgumentException("Argument " + TEXT_ID + " is mandatory");
        }

        return layout;
    }
}
