package com.praktikum.connectdb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Lihat extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    Button btnClose;
    TextView textNo, textNama, textTgl, textJk, textAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat);

        dbHelper = new DataHelper(this);

        // Inisialisasi TextView
        textNo = findViewById(R.id.textViewliat1);
        textNama = findViewById(R.id.textViewliat2);
        textTgl = findViewById(R.id.textViewliat3);
        textJk = findViewById(R.id.textViewliat4);
        textAlamat = findViewById(R.id.textViewliat5);

        // Mengambil data dari intent
        String nama = getIntent().getStringExtra("nama");
        loadData(nama);

        // Inisialisasi Button
        btnClose = findViewById(R.id.button1);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    private void loadData(String nama) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata WHERE nama = ?", new String[]{nama});

        if (cursor != null && cursor.moveToFirst()) {
            textNo.setText(cursor.getString(0));
            textNama.setText(cursor.getString(1));
            textTgl.setText(cursor.getString(2));
            textJk.setText(cursor.getString(3));
            textAlamat.setText(cursor.getString(4));
        }

        if (cursor != null) {
            cursor.close(); // Menutup cursor untuk menghindari memory leak
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
