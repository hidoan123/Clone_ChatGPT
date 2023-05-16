package com.example.test_chatgpt.HistoryChat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_chatgpt.R;

import org.w3c.dom.Text;

import java.util.List;

public class ChatAdapter extends BaseAdapter {
    private HistoryChat context;
    private int layout;
    private List<ChatOop> chatOopList;

    public ChatAdapter(HistoryChat context, int layout, List<ChatOop> chatOopList) {
        this.context = context;
        this.layout = layout;
        this.chatOopList = chatOopList;
    }
    public void remove(int position) {
        chatOopList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return chatOopList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView tv_requestChat;
        TextView tv_relyChat;
        ImageView  img_delete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.tv_requestChat = (TextView) view.findViewById(R.id.tv_requestchat);
            holder.tv_relyChat = (TextView) view.findViewById(R.id.tv_relychat);
            holder.img_delete = (ImageView) view.findViewById(R.id.img_delete);
            view.setTag(holder);
        }else
        {
            holder = (ViewHolder) view.getTag();
        }
        ChatOop chatOop = chatOopList.get(i);
        holder.tv_requestChat.setText(chatOop.getRequestChat());
        holder.tv_relyChat.setText(chatOop.getRelyChat());
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaChat(chatOop.getId());
            }
        });
        return view;
    }
}
