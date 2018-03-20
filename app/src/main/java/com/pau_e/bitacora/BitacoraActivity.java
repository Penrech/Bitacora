package com.pau_e.bitacora;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BitacoraActivity extends AppCompatActivity {

    public static final String FILENAME = "items.text";
    public static final int MAX_BYTES = 10000;
    private ListView list;
    private ArrayList<logItem> items; // Model de dades
    private BitacoraListAdapter adapter;
    private EditText new_item;

    private void writeItemList(){
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (logItem item : items) {
                String line = String.format("%tQ;%s\n",item.getFechaHora(),item.getLog());
                fos.write(line.getBytes());
                Log.d("Linea", line);
            }
            fos.close();
        }
        catch (FileNotFoundException e) {

        } catch (IOException e) {
            Toast.makeText(this, R.string.CantWriteFile,Toast.LENGTH_SHORT).show();
        }
    }


    private  boolean readItemList(){
        items = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            byte[] buffer = new byte[MAX_BYTES];
            int nread = fis.read(buffer);
            if (nread > 0){
                String content = new String(buffer,0,nread);
                String[] lines = content.split("\n");
                for (String line : lines){
                    if (!line.isEmpty()){
                        String[] parts = line.split(";");
                        long date = Long.parseLong(parts[0]);
                        items.add(new logItem(new Date(date),parts[1]));
                    }
                }
            }
            fis.close();
            return true;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            Toast.makeText(this, R.string.CantWriteFile,Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        writeItemList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitacora);

        readItemList();

        list = (ListView) findViewById(R.id.list);
        new_item = (EditText) findViewById(R.id.new_item);

        adapter = new BitacoraListAdapter(this, R.layout.log_item, items);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                edit(items.get(pos).getLog(),pos,0);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                onRemoveItem(pos);
                adapter.notifyDataSetChanged();
                return true;
            }
        });


    }

    private void onRemoveItem(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(
                String.format(Locale.getDefault(), getString(R.string.DeleteConfirmation),
                        items.get(pos).getDateString(),items.get(pos).getHourString())
        );
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                items.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();

    }

    public void OnAddItem(View view) {
        if (!(new_item.getText().toString().isEmpty())) {
            Date itemDate = new Date();
            String itemText = new_item.getText().toString();
            logItem item = new logItem(itemDate, itemText);
            if (!(item == null)) {
                items.add(item);
                adapter.notifyDataSetChanged();
                new_item.setText("");
                list.smoothScrollToPosition(items.size() - 1);
            }
        }
    }

    private  void edit(String text, int pos, int requestCode){
        Intent intent = new Intent(this,EditTextActivity.class);
        intent.putExtra("text",text);
        intent.putExtra("pos",pos);
        startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String text = data.getStringExtra("text");
            int pos = data.getIntExtra("pos", 0);
            items.get(pos).setLog(text);
            adapter.notifyDataSetChanged();
        }
    }
}