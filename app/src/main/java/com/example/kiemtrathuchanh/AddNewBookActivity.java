package com.example.kiemtrathuchanh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.model.Book;

public class AddNewBookActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextDayPublish;
    private RadioButton radioDocument;
    private RadioButton radioNovel;
    private Button buttonCancelAddNewBook;
    private Button buttonAcceptAddNewBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);
        initView();
        initEvent();
    }

    private void initEvent() {
        buttonCancelAddNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearInput();
            }
        });
        buttonAcceptAddNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewBook();
            }
        });
    }

    private void clearInput() {
        editTextName.setText("");
        editTextDayPublish.setText("");
        radioDocument.setChecked(true);
    }

    private void addNewBook() {
        if (!editTextName.getText().toString().equals("") || !editTextDayPublish.getText().toString().equals("")) {
            Intent intent = getIntent();
            Book book = new Book();
            book.setName(editTextName.getText().toString());
            book.setDayPublish(Integer.parseInt(editTextDayPublish.getText().toString()));
            book.setType(radioNovel.isChecked() ? true : false);
            Bundle bundle = new Bundle();
            bundle.putSerializable("book", book);
            intent.putExtra("data", bundle);
            setResult(RESULT_OK, intent);
            onBackPressed();
        } else {
            finish();
        }
    }

    private void initView() {
        editTextName = findViewById(R.id.editTextName);
        editTextDayPublish = findViewById(R.id.editTextDayPublish);
        radioDocument = findViewById(R.id.radioDocument);
        radioNovel = findViewById(R.id.radioNovel);
        buttonCancelAddNewBook = findViewById(R.id.buttonCancelAddNewBook);
        buttonAcceptAddNewBook = findViewById(R.id.buttonAcceptAddNewBook);
        radioDocument.setChecked(true);
    }
}
