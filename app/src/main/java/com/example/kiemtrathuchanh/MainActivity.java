package com.example.kiemtrathuchanh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.database.DatabaseBook;
import com.example.model.Book;
import com.example.viewmodel.AdapterBook;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listViewBook;
    private ArrayList<Book> listBook;
    private AdapterBook adapterBook;
    private DatabaseBook databaseBook;
    private Book selectedBook;
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initEvent() {
        listViewBook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBook = listBook.get(i);
                return false;
            }
        });
    }

    private void initView() {
        listViewBook = findViewById(R.id.listViewBook);
        databaseBook = new DatabaseBook(this);
        if (databaseBook.getBookCount() == 0) {
            databaseBook.addDemoTenBooks();
        }
        listBook = databaseBook.getListBook();
        adapterBook = new AdapterBook(this, R.layout.item_list_book, listBook);
        listViewBook.setAdapter(adapterBook);
        registerForContextMenu(listViewBook);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_new:
                openAddNewBookActivity();
                break;
            case R.id.menu_delete:
                openDeleteDialog();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void openDeleteDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa dữ liệu");
        builder.setMessage("Bạn có chắc chắn muốn xóa?");
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteBook();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void deleteBook() {
        databaseBook.deleteBook(selectedBook);
        refresh();
    }

    private void openAddNewBookActivity() {
        Intent intent = new Intent(this, AddNewBookActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getBundleExtra("data");
            Book book = (Book) bundle.getSerializable("book");
            databaseBook.addNewBook(book);
            refresh();
        }
    }

    private void refresh() {
        listBook.clear();
        ArrayList<Book> newList = databaseBook.getListBook();
        listBook.addAll(newList);
        adapterBook.notifyDataSetChanged();
    }
}
