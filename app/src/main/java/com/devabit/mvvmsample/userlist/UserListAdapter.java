package com.devabit.mvvmsample.userlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.devabit.mvvmsample.R;
import com.devabit.mvvmsample.model.User;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {
    private List<User> users;
    private Context context;

    public UserListAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, R.layout.list_row_layout, objects);

        this.users = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_layout, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.text);
        String name = users.get(position).getLogin();
        textView.setText(name);
        return convertView;
    }
}
