package ua.pi_13.echards;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RiffsView extends AppCompatActivity {

    private DatabaseHelper mDatabaseHelper;

    ArrayList<ItemRiff> riffs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riffs_view);

        riffs = GetSearchResults();
        ListView listView = (ListView) findViewById(R.id.listViewRiffs);
        listView.setAdapter(new ItemListRiffBaseAdapter(this, riffs));
    }

    public void onClickGoToEditRiff(View view) {
            Intent intent = new Intent(RiffsView.this, EditingRiff.class);
            intent.putExtra("Type","New");
            startActivity(intent);
    }

    private ArrayList<ItemRiff> GetSearchResults() {
        ArrayList<ItemRiff> results = new ArrayList<ItemRiff>();

        mDatabaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        String sql = "SELECT _id, " +  mDatabaseHelper.DATACOLUMN_RIFFS_NAME + ", " +
                mDatabaseHelper.DATACOLUMN_RIFFS_PARSELINE +
                " FROM " + mDatabaseHelper.DATATABLE_RIFFS ;

        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            do {
                ItemRiff riff = new ItemRiff();
                riff.setID(Long.parseLong(cursor.getString(0)));
                riff.setName(cursor.getString(1));
                riff.setParseLine(cursor.getString(2));
                results.add(riff);
            }
            while(cursor.moveToNext());
        }
        return results;
    }
}
