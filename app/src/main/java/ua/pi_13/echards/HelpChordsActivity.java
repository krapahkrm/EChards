package ua.pi_13.echards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HelpChordsActivity extends AppCompatActivity {

    String[] notes, forms;
    TextView Chord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_chords);

        Chord = (TextView) findViewById(R.id.textChord);

        notes=getResources().getString(R.string.array_notes).split(" ");
        forms=getResources().getString(R.string.array_forms).split(" ");
        forms[0] = " "; // Major

        ArrayAdapter<String> adapter_notes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notes);
        ArrayAdapter<String> adapter_forms = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, forms);

        adapter_notes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_forms.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinnerNote = (Spinner) findViewById(R.id.spinnerNote);
        spinnerNote.setAdapter(adapter_notes);
        final Spinner spinnerForm = (Spinner) findViewById(R.id.spinnerForm);
        spinnerForm.setAdapter(adapter_forms);

        // заголовок
        spinnerNote.setPrompt("Нота");
        spinnerForm.setPrompt("Форма");

        // выделяем элемент
        spinnerNote.setSelection(0);
        spinnerForm.setSelection(0);

        // устанавливаем обработчик нажатия
        spinnerNote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Chord.setText(spinnerNote.getSelectedItem().toString()+spinnerForm.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerForm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Chord.setText(spinnerNote.getSelectedItem().toString()+spinnerForm.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void onClickGoToChordsView(View view) {
        Intent intent = new Intent(HelpChordsActivity.this, ChordsViewActivity.class);
        intent.putExtra("Name", Chord.getText().toString());
        startActivity(intent);
    }
}
