package ua.pi_13.echards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void onClickGoToChordsHelper(View view) {
        Intent intent = new Intent(HelpActivity.this, HelpChordsActivity.class);
        startActivity(intent);
    }

    public void onClickGoToRiffsHelper(View view) {
        Intent intent = new Intent(HelpActivity.this, RiffsView.class);
        startActivity(intent);
    }
}
