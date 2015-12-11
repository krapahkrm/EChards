package ua.pi_13.echards;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClickBackToMain(View view) {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void onClickGoToHelp(View view) {
        Intent intent = new Intent(MenuActivity.this, HelpActivity.class);
        startActivity(intent);
    }

    public void onClickGoToAbout(View view) {
        Intent intent = new Intent(MenuActivity.this, About.class);
        startActivity(intent);
    }

    public void onClickGoToBands(View view) {
        Intent intent = new Intent(MenuActivity.this, BandsView.class);
        startActivity(intent);
    }
}
