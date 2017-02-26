package com.example.dell.phonebookapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.phonebookapp.Model.Contact;
import com.example.dell.phonebookapp.R;

import java.util.List;

/**
 * Created by DELL on 23/02/2017.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private int resource;
    private List<Contact> arrayContact;

    public ContactAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrayContact = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new viewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.contact_item_list, parent, false);
            viewHolder.imgAvatar = (ImageView) convertView.findViewById(R.id.img_avatar);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvNumber = (TextView) convertView.findViewById(R.id.tv_number);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (viewHolder) convertView.getTag();
        }
        Contact contact = arrayContact.get(position);
        //Get name and number of contact
        viewHolder.tvName.setText(contact.getmName());
        viewHolder.tvNumber.setText(contact.getmNumber());
        //Check gender then set icon
        if (contact.isMale()) {
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.male);
        } else {
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.female);
        }
        return convertView;
    }

    public class viewHolder {
        ImageView imgAvatar;
        TextView tvName;
        TextView tvNumber;
    }
}
