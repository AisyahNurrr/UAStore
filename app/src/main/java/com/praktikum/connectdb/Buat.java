package com.praktikum.connectdb;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Buat extends AppCompatActivity {
    private DataHelper dbHelper;
    private Button btnSave, btnCancel;
    private EditText editNo, editNama, editTgl, editJk, editAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat);

        dbHelper = new DataHelper(this);

        // Initialize EditTexts
        editNo = findViewById(R.id.editText1);
        editNama = findViewById(R.id.editText2);
        editTgl = findViewById(R.id.editText3);
        editJk = findViewById(R.id.editText4);
        editAlamat = findViewById(R.id.editText5);

        // Initialize Buttons
        btnSave = findViewById(R.id.button1);
        btnCancel = findViewById(R.id.button2);

        // Save button listener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                saveData();
            }
        });

        // Cancel button listener
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    private void saveData() {
        if (!validateInputs()) return; // Validate inputs

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction(); // Start transaction
        try {
            // Using parameterized query for security
            String sql = "INSERT INTO biodata(no, nama, tgl, jk, alamat) VALUES(?, ?, ?, ?, ?)";
            db.execSQL(sql, new String[]{
                    editNo.getText().toString(),
                    editNama.getText().toString(),
                    editTgl.getText().toString(),
                    editJk.getText().toString(),
                    editAlamat.getText().toString()
            });
            db.setTransactionSuccessful(); // Mark transaction as successful
            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
            MainActivity.ma.RefreshList();
            finish();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Gagal menyimpan data", Toast.LENGTH_LONG).show();
        } finally {
            db.endTransaction(); // End transaction
        }
    }

    private boolean validateInputs() {
        if (editNo.getText().toString().isEmpty() ||
                editNama.getText().toString().isEmpty() ||
                editTgl.getText().toString().isEmpty() ||
                editJk.getText().toString().isEmpty() ||
                editAlamat.getText().toString().isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}