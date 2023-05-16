package com.example.test_chatgpt.HistoryChat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.test_chatgpt.R;

import java.util.ArrayList;

public class HistoryChat extends AppCompatActivity {

    ListView lvChat;
    ArrayList<ChatOop> chatOopArrayList;
    ChatAdapter adapter;
    ChatOop chatOop;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_chat);
        // Khởi tạo các đối tượng và thiết lập adapter cho ListView
        lvChat = findViewById(R.id.lvChat);
        chatOopArrayList = new ArrayList<>();
        adapter = new ChatAdapter(this, R.layout.history_chat, chatOopArrayList);
        lvChat.setAdapter(adapter);
        // Mở kết nối tới database và truy vấn dữ liệu từ bảng History
        database = SQLiteDatabase.openDatabase(getDatabasePath("History.sqlite").getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = database.rawQuery("SELECT * FROM LichSu", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String requestChat = cursor.getString(1);
            String relychat = cursor.getString(2);
            chatOopArrayList.add(new ChatOop(id,requestChat,relychat));
        }
        // Đóng cursor và database
        cursor.close();
        database.close();
        // Cập nhật adapter để hiển thị danh sách lên ListView
        adapter.notifyDataSetChanged();

    }
    public void DialogXoaChat(int id)
    {
        database = SQLiteDatabase.openDatabase(getDatabasePath("History.sqlite").getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa k?");
        dialogXoa.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, final int i) {
                database.delete("LichSu", "Id=?", new String[]{String.valueOf(id)});
                Toast.makeText(HistoryChat.this, "đã xóa thành công!", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                database.close();
            }
        });
        dialogXoa.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogXoa.show();

    }
}