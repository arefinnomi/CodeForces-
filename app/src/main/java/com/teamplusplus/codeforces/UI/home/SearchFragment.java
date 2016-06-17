package com.teamplusplus.codeforces.UI.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.teamplusplus.codeforces.R;
import com.teamplusplus.codeforces.UI.userDetails.UserDetailsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        final EditText editText = (EditText) view.findViewById(R.id.editText_search_handle);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Intent userDetailsIntent = new Intent(getActivity(), UserDetailsActivity.class);
                    userDetailsIntent.putExtra("handle", editText.getText().toString());
                    startActivity(userDetailsIntent);
                    handled = true;
                }
                return handled;
            }
        });

        final ImageButton imageButton = (ImageButton) view.findViewById(R.id.search_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == imageButton) {

                    Intent userDetailsIntent = new Intent(getActivity(), UserDetailsActivity.class);
                    userDetailsIntent.putExtra("handle", editText.getText().toString());
                    startActivity(userDetailsIntent);
                }
            }
        });

        return view;
    }

}
