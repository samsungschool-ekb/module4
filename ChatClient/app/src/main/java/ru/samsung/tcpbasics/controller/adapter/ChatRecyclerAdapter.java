package ru.samsung.tcpbasics.controller.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.samsung.tcpbasics.R;
import ru.samsung.tcpbasics.controller.model.Message;
import ru.samsung.tcpbasics.controller.viewholder.MessageViewHolder;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    public static final int TYPE_MESSAGE_1 = 1;
    public static final int TYPE_MESSAGE_2 = 2;

    private List<Message> messageList;

    public ChatRecyclerAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = null;
        switch (viewType) {
            case TYPE_MESSAGE_1:
                view = inflater.inflate(R.layout.message_1, viewGroup, false);
                break;
            case TYPE_MESSAGE_2:
                view = inflater.inflate(R.layout.message_2, viewGroup, false);
        }
        assert view != null;
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int position) {
        Message message = messageList.get(position);
        switch (getItemViewType(position)) {
            case TYPE_MESSAGE_1:
            case TYPE_MESSAGE_2:
                messageViewHolder.getMessageTextView().setText(message.getText());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getName().equalsIgnoreCase("x"))
            return TYPE_MESSAGE_1;
        else
            return TYPE_MESSAGE_2;
    }
}
