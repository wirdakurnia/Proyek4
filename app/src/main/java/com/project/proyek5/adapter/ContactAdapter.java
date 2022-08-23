package com.project.proyek5.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.proyek5.R;
import com.project.proyek5.model.Contact;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;

    private List<Contact> contacts;

    public ContactAdapter(Activity activity, List<Contact> contacts){
        this.activity = activity;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(layoutInflater == null){
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null){
            view = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView txtId, txtNama, txtAlamat, txtTelp;
        txtId = view.findViewById(R.id.txtId);
        txtNama = view.findViewById(R.id.txtNama);
        txtAlamat = view.findViewById(R.id.txtAlamat);
        txtTelp = view.findViewById(R.id.txtTelp);

        Contact contact = contacts.get(i);
        txtId.setText(contact.getId());
        txtNama.setText(contact.getNama());
        txtAlamat.setText(contact.getAlamat());
        txtTelp.setText(contact.getTelp());

        return view;
    }
}
