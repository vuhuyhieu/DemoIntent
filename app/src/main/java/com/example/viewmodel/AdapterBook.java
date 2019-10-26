package com.example.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kiemtrathuchanh.R;
import com.example.model.Book;

import java.util.List;

public class AdapterBook extends ArrayAdapter<Book> {
    Context context;
    int resource;
    List<Book> objects;

    public AdapterBook(@NonNull Context context, int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.item_list_book, parent, false);
        ImageView imageViewAvatar = row.findViewById(R.id.imageViewAvatar);
        TextView textViewName = row.findViewById(R.id.textViewName);
        TextView textViewDayPublish = row.findViewById(R.id.textViewDayPublish);

        Book book = objects.get(position);
        int resID = book.isNovel() ? R.drawable.novel : R.drawable.document;
        imageViewAvatar.setImageResource(resID);
        textViewName.setText(book.getName());
        textViewDayPublish.setText("Năm xuất bản: " + book.getDayPublish());
        return row;
    }
}
