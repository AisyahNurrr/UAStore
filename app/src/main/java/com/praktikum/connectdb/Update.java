package com.praktikum.connectdb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Update extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    Button btnUpdate, btnCancel;
    EditText textNo, textNama, textTgl, textJk, textAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHelper = new DataHelper(this);

        // Inisialisasi EditText
        textNo = findViewById(R.id.editTextup1);
        textNama = findViewById(R.id.editTextup2);
        textTgl = findViewById(R.id.editTextup3);
        textJk = findViewById(R.id.editTextup4);
        textAlamat = findViewById(R.id.editTextup5);

        // Mengambil data berdasarkan nama
        String nama = getIntent().getStringExtra("nama");
        loadData(nama);

        // Inisialisasi Button
        btnUpdate = findViewById(R.id.button1);
        btnCancel = findViewById(R.id.button2);

        // Daftarkan event onClick pada btnUpdate
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                updateData();
            }
        });

        // Daftarkan event onClick pada btnCancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
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

    private void updateData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String no = textNo.getText().toString();
        String nama = textNama.getText().toString();
        String tgl = textTgl.getText().toString();
        String jk = textJk.getText().toString();
        String alamat = textAlamat.getText().toString();

        // Menggunakan parameterized query untuk keamanan
        db.execSQL("UPDATE biodata SET nama=?, tgl=?, jk=?, alamat=? WHERE no=?",
                new String[]{nama, tgl, jk, alamat, no});

        Toast.makeText(getApplicationContext(), "Berhasil diupdate", Toast.LENGTH_LONG).show();
        MainActivity.ma.RefreshList();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
