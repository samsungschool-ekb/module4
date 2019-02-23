package ru.samsung.tcpbasics.controller.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.samsung.tcpbasics.R;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    private TextView messageTextView;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        messageTextView = itemView.findViewById(R.id.messageTextView);
    }

    public TextView getMessageTextView() {
        return messageTextView;
    }
}
