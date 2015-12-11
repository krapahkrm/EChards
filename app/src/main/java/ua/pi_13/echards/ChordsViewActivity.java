package ua.pi_13.echards;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.media.SoundPool.OnLoadCompleteListener;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class ChordsViewActivity extends AppCompatActivity {

    private String ChordName;
    //private ServerConnect mServerConnect;
    private DatabaseHelper mDatabaseHelper;

    ArrayList<ItemChord> chords;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chords_view);
        Intent intent = getIntent();
        ChordName = intent.getStringExtra("Name").replaceAll(" ","").toLowerCase();

        chords = GetSearchResults();
        ListView listView = (ListView) findViewById(R.id.listViewChords);
        listView.setAdapter(new ItemListChordBaseAdapter(this, chords));

    }


    private ArrayList<ItemChord> GetSearchResults() {
        ArrayList<ItemChord> results = new ArrayList<ItemChord>();

        mDatabaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        String sql = "SELECT " +  mDatabaseHelper.DATACOLUMN_CHORDSFROMDB_NAME + ", " +
                mDatabaseHelper.DATACOLUMN_CHORDSFROMDB_IMAGE_SRS +
                ", "+ mDatabaseHelper.DATACOLUMN_CHORDSFROMDB_MUSIC_SRS+
                " FROM " + mDatabaseHelper.DATATABLE_CHORDSFROMDB + " WHERE "
                + mDatabaseHelper.DATACOLUMN_CHORDSFROMDB_NAME + "='" + ChordName+"'";

        Cursor cursor = db.rawQuery(sql,null);
        TextView test = (TextView) findViewById(R.id.test);
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            int version = 1;
            do {
                ItemChord chord = new ItemChord();
                chord.setNumber(version);
                chord.setName(cursor.getString(0) + " var" + Integer.toString(version++));
                chord.setImage(cursor.getString(1));
                test.setText(cursor.getString(1));
                chord.setMusic(cursor.getString(2));
                results.add(chord);
            }
            while(cursor.moveToNext());
        }
        return results;
    }

}
