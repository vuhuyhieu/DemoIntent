package com.example.kiemtrathuchanh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
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
    public static final int REQUEST_ADD_NEW = 1;
    public static final int REQUEST_UPDATE = 2;

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
            case R.id.menu_update:
                openUpdateBookActivity();
                break;
            case R.id.menu_delete:
                openDeleteDialog();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.header_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_new:
                openAddNewBookActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openUpdateBookActivity() {
        Intent intent = new Intent(this, UpdateBookActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", selectedBook);
        intent.putExtra("data", bundle);
        startActivityForResult(intent, REQUEST_UPDATE);
    }

//    private void openTest() {
//        Log.d("Make call exception", "vao day");
//        String phoneNumber = "0982960442";
//        try{
//            Uri uri = Uri.parse("tel:"+phoneNumber);
//            Intent intent = new Intent(Intent.ACTION_CALL, uri);
//            if (intent.resolveActivity(getPackageManager()) != null &&
//                    ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
//                startActivity(intent);
//            }
//        }catch (Exception ex){
//            Log.d("Make call exception", ex.toString());
//        }
//
//    }

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
        startActivityForResult(intent, REQUEST_ADD_NEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_ADD_NEW) {
                Bundle bundle = data.getBundleExtra("data");
                Book book = (Book) bundle.getSerializable("book");
                databaseBook.addNewBook(book);
                refresh();
            } else if (requestCode == REQUEST_UPDATE) {
                Bundle bundle = data.getBundleExtra("updatedData");
                Book book = (Book) bundle.getSerializable("updatedBook");
                databaseBook.updateBook(book);
                refresh();
            }
        }
    }

    private void refresh() {
        listBook.clear();
        ArrayList<Book> newList = databaseBook.getListBook();
        listBook.addAll(newList);
        adapterBook.notifyDataSetChanged();
    }
}
