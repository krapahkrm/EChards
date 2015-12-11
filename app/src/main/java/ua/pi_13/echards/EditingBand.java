package ua.pi_13.echards;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditingBand extends AppCompatActivity {

    EditText NameBand;
    DatabaseHelper mDatabaseHelper;
    String Type;
    Long _ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_band);

        NameBand = (EditText) findViewById(R.id.editTextNameBand);
        Intent intent = getIntent();

        this.Type = intent.getStringExtra("Type");
        TextView _label = (TextView) findViewById(R.id.textViewLabel);
        _label.setText(this.Type);

        mDatabaseHelper = new DatabaseHelper(this);
        if(Type.equals("Edit")) {
            this._ID = Long.parseLong(intent.getStringExtra("ID"));
            NameBand.setText(intent.getStringExtra("NAME"));
        }
    }

    public void onClickSaveRiff(View view) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues mContentValues =new ContentValues();
        mContentValues.put(mDatabaseHelper.DATACOLUMN_BANDS_NAME,NameBand.getText().toString());

        if(Type.equals("New")){
            db.insert(mDatabaseHelper.DATATABLE_BANDS, null, mContentValues);
        }
        else {
            db.update(mDatabaseHelper.DATATABLE_BANDS, mContentValues, "_id "+"="+_ID,null);
        }


        Intent intent = new Intent(EditingBand.this, BandsView.class);
        startActivity(intent);

        this.finish();
    }
}
