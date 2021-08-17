package com.sendbird.uikit_messaging_android.groupchannel;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.sendbird.android.BaseMessage;
import com.sendbird.android.UserMessage;
import com.sendbird.uikit.activities.adapter.MessageListAdapter;
import com.sendbird.uikit.activities.viewholder.MessageViewHolder;
import com.sendbird.uikit.utils.MessageUtils;
import com.sendbird.uikit.utils.TextUtils;
import com.sendbird.uikit_messaging_android.R;

public class CustomMessageListAdapter extends MessageListAdapter {

    public static final int VIEW_HIGHLIGHT_MESSAGE_ME_TYPE = 1001;
    public static final int VIEW_HIGHLIGHT_MESSAGE_OTHER_TYPE = 1002;

    public CustomMessageListAdapter(boolean useMessageGroupUI) {
        super(null, null, null, useMessageGroupUI);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO: Create the custom ViewHolder and return it.
        // Create your custom ViewHolder or call super.onCreateViewHolder() if you want to use the default.
        if (viewType == VIEW_HIGHLIGHT_MESSAGE_ME_TYPE) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new CustomMessageMeViewHolder(DataBindingUtil.inflate(inflater, R.layout.view_custom_message_me_holder, parent, false));
        } else if (viewType == VIEW_HIGHLIGHT_MESSAGE_OTHER_TYPE) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new CustomMessageOtherViewHolder(DataBindingUtil.inflate(inflater, R.layout.view_custom_message_other_holder, parent, false));
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        // You must call the super. You can use methods that MessageViewHolder provides
        super.onBindViewHolder(holder, position);
        // TODO: Bind the custom ViewHolder
    }

    @Override
    public int getItemViewType(int position) {
        BaseMessage message = getItem(position);

        String customType = message.getCustomType();

        if (!TextUtils.isEmpty(customType) &&
                customType.equals("sticker") &&
                message instanceof UserMessage) {
            if (MessageUtils.isMine(message)) {
                return VIEW_HIGHLIGHT_MESSAGE_ME_TYPE;
            } else {
                return VIEW_HIGHLIGHT_MESSAGE_OTHER_TYPE;
            }
        }

        return super.getItemViewType(position);
    }
}

