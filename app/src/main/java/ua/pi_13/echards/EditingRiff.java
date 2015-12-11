package ua.pi_13.echards;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditingRiff extends AppCompatActivity {

    EditText NameRiff, DescribeRiff;
    DatabaseHelper mDatabaseHelper;
    String Type;
    Long _ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_riff);

        NameRiff = (EditText) findViewById(R.id.editTextNameRiff);
        DescribeRiff = (EditText) findViewById(R.id.editTextDescribeRiff);
        Intent intent = getIntent();

        this.Type = intent.getStringExtra("Type");
        TextView _label = (TextView) findViewById(R.id.textViewLabel);
        _label.setText(this.Type);

        mDatabaseHelper = new DatabaseHelper(this);
        if(Type.equals("Edit")) {
            this._ID = Long.parseLong(intent.getStringExtra("ID"));
            NameRiff.setText(intent.getStringExtra("NAME"));
            DescribeRiff.setText(intent.getStringExtra("DESCRIBE"));
        }
    }

    public void onClickSaveRiff(View view) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues mContentValues =new ContentValues();
        mContentValues.put(mDatabaseHelper.DATACOLUMN_RIFFS_NAME,NameRiff.getText().toString());
        mContentValues.put(mDatabaseHelper.DATACOLUMN_RIFFS_PARSELINE,DescribeRiff.getText().toString());
        if(Type.equals("New")){
            db.insert(mDatabaseHelper.DATATABLE_RIFFS, null, mContentValues);
        }
        else {
            db.update(mDatabaseHelper.DATATABLE_RIFFS, mContentValues, "_id "+"="+_ID,null);
        }

        Intent intent = new Intent(EditingRiff.this, RiffsView.class);
        startActivity(intent);

        this.finish();
    }
}
