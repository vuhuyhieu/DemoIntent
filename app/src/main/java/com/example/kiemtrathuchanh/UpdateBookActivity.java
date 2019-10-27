package com.example.kiemtrathuchanh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.model.Book;

public class UpdateBookActivity extends AppCompatActivity {
    private EditText editTextNameUpdate, editTextDayPublishUpdate;
    private RadioButton radioDocumentUpdate, radioNovelUpdate;
    private Button buttonAcceptUpdateBook, buttonCancelUpdateBook;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);
        initView();
        fetchData();
        initEvent();
    }

    private void fetchData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        book = (Book) bundle.getSerializable("book");
        editTextNameUpdate.setText(book.getName());
        editTextDayPublishUpdate.setText(String.valueOf(book.getDayPublish()));
        if (book.isNovel()) {
            radioNovelUpdate.setChecked(true);
        } else {
            radioDocumentUpdate.setChecked(true);
        }
    }

    private void initEvent() {
        buttonCancelUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buttonAcceptUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBook();
            }
        });
    }

    private void updateBook() {

        book.setName(editTextNameUpdate.getText().toString());
        book.setDayPublish(Integer.parseInt(editTextDayPublishUpdate.getText().toString()));
        book.setType(radioNovelUpdate.isChecked());
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        bundle.clear();
        bundle.putSerializable("updatedBook", book);
        intent.putExtra("updatedData", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initView() {
        editTextDayPublishUpdate = findViewById(R.id.editTextDayPublishUpdate);
        editTextNameUpdate = findViewById(R.id.editTextNameUpdate);
        radioDocumentUpdate = findViewById(R.id.radioDocumentUpdate);
        radioNovelUpdate = findViewById(R.id.radioNovelUpdate);
        buttonAcceptUpdateBook = findViewById(R.id.buttonAcceptUpdateBook);
        buttonCancelUpdateBook = findViewById(R.id.buttonCancelUpdateBook);
    }
}
