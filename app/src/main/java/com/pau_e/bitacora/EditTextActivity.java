package com.pau_e.bitacora;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditTextActivity extends AppCompatActivity {

    private EditText edit_text;
    private String text_after_edit;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        edit_text = findViewById(R.id.edit_text);

        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        pos = intent.getIntExtra("pos",0);
        if (text != null){
            edit_text.setText(text);
            text_after_edit = text;
        }
    }

    @Override
    public void onBackPressed() {
        if (!text_after_edit.equals(edit_text.getText().toString())){
            String msg = getString(R.string.NoSavedChanges);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.NoSavedChangesAlert);
            builder.setMessage(msg);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   saveResult();
                }
            });
            builder.setNegativeButton(android.R.string.no, null);
            builder.create().show();
        }
        else{
            finish();
        }
    }

    private void saveResult() {
        Intent data = new Intent();
        data.putExtra("text",edit_text.getText().toString());
        data.putExtra("pos",pos);
        setResult(RESULT_OK,data);
        finish();
    }

    public void onSave(View view) {
        saveResult();
    }
}
