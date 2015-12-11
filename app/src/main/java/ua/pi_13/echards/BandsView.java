package ua.pi_13.echards;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class BandsView extends AppCompatActivity {

    private DatabaseHelper mDatabaseHelper;

    ArrayList<ItemBand> bands;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bands_view);

        bands = GetSearchResults();
        ListView listView = (ListView) findViewById(R.id.listViewBands);
        listView.setAdapter(new ItemListBandBaseAdapter(this, bands));
    }

    public void onClickGoToEditingBand(View view) {
        Intent intent = new Intent(BandsView.this, EditingBand.class);
        intent.putExtra("Type", "New");
        startActivity(intent);
    }

    private ArrayList<ItemBand> GetSearchResults() {
        ArrayList<ItemBand> results = new ArrayList<ItemBand>();

        mDatabaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        String sql = "SELECT _id, " +  mDatabaseHelper.DATACOLUMN_BANDS_NAME + ", " +
                mDatabaseHelper.DATACOLUMN_BANDS_COUNTSONG +
                " FROM " + mDatabaseHelper.DATATABLE_BANDS ;

        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            do {
                ItemBand band = new ItemBand();
                band.set_ID(Long.parseLong(cursor.getString(0)));
                band.setName(cursor.getString(1));
                band.setCounts(Integer.parseInt(cursor.getString(2)));
                results.add(band);
            }
            while(cursor.moveToNext());
        }
        return results;
    }
}
