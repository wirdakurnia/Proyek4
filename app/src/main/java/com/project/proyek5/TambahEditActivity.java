package com.project.proyek5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.proyek5.helper.DatabaseHelper;
import com.project.proyek5.model.Contact;

public class TambahEditActivity extends AppCompatActivity {
    private EditText etId, etNama, etAlamat, etTelp;
    private Button btnSimpan, btnBatal;
    
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_edit);
        
        databaseHelper = new DatabaseHelper(this);
        etId = findViewById(R.id.etId);
        etNama = findViewById(R.id.etNama);
        etAlamat = findViewById(R.id.etAlamat);
        etTelp = findViewById(R.id.etTelp);

        btnSimpan = findViewById(R.id.btnSimpan);
        btnBatal = findViewById(R.id.btnBatal);

        if(getIntent().getExtras() != null){
            setTitle("Edit Data");
            Contact contact = (Contact) getIntent().getSerializableExtra(MainActivity.TAG_CONTACT);
            etId.setText(contact.getId());
            etNama.setText(contact.getNama());
            etAlamat.setText(contact.getAlamat());
            etTelp.setText(contact.getTelp());
        }else setTitle("Tambah Data");

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString();
                String nama = etNama.getText().toString();
                String alamat = etAlamat.getText().toString();
                String telp = etTelp.getText().toString();
                if(id.equals("") && nama.equals("") && alamat.equals("") && telp.equals("")){
                    Toast.makeText(TambahEditActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else if(telp.length()>12 || telp.length()<5){
                    Toast.makeText(TambahEditActivity.this, "No Telepon salah", Toast.LENGTH_SHORT).show();
                }else{
                    if(getTitle().equals("Tambah Data")){
                        long insert = databaseHelper.insert(new Contact(id, nama, alamat, telp));
                        if(insert > 0){
                            Toast.makeText(TambahEditActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        if(databaseHelper.update(new Contact(id, nama, alamat, telp)))
                            Toast.makeText(TambahEditActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    }
                    clear();
                    finish();
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                finish();
            }
        });
        
    }

    private void clear(){
        etNama.setText("");
        etAlamat.setText("");
        etTelp.setText("");
    }

}