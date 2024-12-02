package com.praktikum.connectdb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private String[] daftar;
    private ListView listView;
    private DataHelper dbHelper;
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ma = this;
        dbHelper = new DataHelper(this);

        Button btnAdd = findViewById(R.id.button2);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, Buat.class);
                startActivity(intent);
            }
        });

        refreshList();
    }

    public void refreshList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM biodata", null);
        daftar = new String[cursor.getCount()];

        if (cursor.moveToFirst()) {
            for (int cc = 0; cc < cursor.getCount(); cc++) {
                daftar[cc] = cursor.getString(1); // Assuming column 1 is nama
                cursor.moveToNext();
            }
        }
        cursor.close(); // Menutup cursor untuk mencegah memory leak

        listView = findViewById(R.id.listView1);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, daftar));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection = daftar[position];
                showOptionsDialog(selection);
            }
        });
    }

    private void showOptionsDialog(final String selection) {
        final CharSequence[] dialogItems = {"Lihat Biodata", "Update Biodata", "Hapus Biodata"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Pilihan");
        builder.setItems(dialogItems, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        Intent intentView = new Intent(getApplicationContext(), Lihat.class);
                        intentView.putExtra("nama", selection);
                        startActivity(intentView);
                        break;
                    case 1:
                        Intent intentUpdate = new Intent(getApplicationContext(), Update.class);
                        intentUpdate.putExtra("nama", selection);
                        startActivity(intentUpdate);
                        break;
                    case 2:
                        deleteBiodata(selection);
                        break;
                }
            }
        });
        builder.create().show();
    }

    private void deleteBiodata(String selection) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM biodata WHERE nama = ?", new String[]{selection});
        refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void RefreshList() {
    }
}
