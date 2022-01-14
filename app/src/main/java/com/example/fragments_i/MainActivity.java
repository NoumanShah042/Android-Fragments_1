package com.example.fragments_i;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener {
    // we implements the interface defined in the fragment class to get data back from fragment

    private FrameLayout fragmentContainer;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        editText = (EditText) findViewById(R.id.edittext);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                openFragment(text);  // my own method
            }
        });
    }

    public void openFragment(String text) {
        BlankFragment fragment = BlankFragment.newInstance(text); // creating new instance of BlankFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // (      AnimationForOpenFragment ,  AnimationForToCloseActivity , forBackButtonMainActivityEnter   , forBackButtonFragmentExit )
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        // if we pass only first two arguments , animations will not occur if we press our back button

        transaction.addToBackStack(null); // if we not done this, so when we press back button it wil close our whole activity, but we want to close only our fragment

        transaction.add(R.id.fragment_container, fragment, "BLANK_FRAGMENT").commit(); // with this tag we can later find this fragment
    }

    @Override
    public void onFragmentInteraction(String sendBackText) {
        editText.setText(sendBackText);     //  get text from fragment and set value of view ( editText )
        onBackPressed();
    }
}