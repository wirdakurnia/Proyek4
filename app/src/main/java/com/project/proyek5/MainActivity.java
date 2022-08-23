package com.project.proyek5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.proyek5.adapter.ContactAdapter;
import com.project.proyek5.helper.DatabaseHelper;
import com.project.proyek5.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    DatabaseHelper databaseHelper;
    ListView listView;
    List<Contact> contacts;
    ContactAdapter adapter;
    AlertDialog.Builder builder;

    public static final String TAG_CONTACT = "CONTACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("CRUD SQLite");

        fab = findViewById(R.id.btnfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TambahEditActivity.class);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.list_view);
        contacts = new ArrayList<>();
        adapter = new ContactAdapter(MainActivity.this, contacts);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = contacts.get(i).getId();
                String nama = contacts.get(i).getNama();
                String alamat = contacts.get(i).getAlamat();
                String telp = contacts.get(i).getTelp();

                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setItems(new String[]{"Edit","Delete"}, ((dialogInterface, i1) -> {
                    if(i1 == 0){
                        Intent intent = new Intent(MainActivity.this, TambahEditActivity.class);
                        intent.putExtra(TAG_CONTACT, new Contact(id, nama, alamat, telp));
                        startActivity(intent);
                    }else{
                        if(databaseHelper.delete(id)){
                            Toast.makeText(MainActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                            getAllData();
                        }
                    }
                })).show();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllData();
    }

    private void getAllData(){
        List<Contact> all = databaseHelper.getAll();
        contacts.clear();
        contacts.addAll(all);
        adapter.notifyDataSetChanged();
    }

}