package com.sendbird.uikit_messaging_android.groupchannel;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sendbird.android.BaseChannel;
import com.sendbird.android.BaseMessage;
import com.sendbird.android.Sender;
import com.sendbird.uikit.activities.viewholder.MessageViewHolder;
import com.sendbird.uikit.consts.MessageGroupType;
import com.sendbird.uikit.utils.DrawableUtils;
import com.sendbird.uikit_messaging_android.R;
import com.sendbird.uikit_messaging_android.databinding.ViewCustomMessageOtherHolderBinding;

public class CustomMessageOtherViewHolder extends MessageViewHolder {
    final private ViewCustomMessageOtherHolderBinding binding;

    public CustomMessageOtherViewHolder(ViewCustomMessageOtherHolderBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    public void bind(BaseChannel channel, @NonNull BaseMessage message, MessageGroupType messageGroupType) {
        Context context = binding.getRoot().getContext();
        boolean sendingState = message.getSendingStatus() == BaseMessage.SendingStatus.SUCCEEDED;
        binding.tvSentAt.setVisibility(sendingState ? View.VISIBLE : View.GONE);
        String sentAt = DateUtils.formatDateTime(context, message.getCreatedAt(), DateUtils.FORMAT_SHOW_TIME);
        binding.tvSentAt.setText(sentAt);

        Sender sender = message.getSender();
        String nickname = sender == null || TextUtils.isEmpty(sender.getNickname()) ?
                context.getString(R.string.sb_text_channel_list_title_unknown) :
                sender.getNickname();
        binding.tvNickname.setText(nickname);

        String url = "";
        if (sender != null && !TextUtils.isEmpty(sender.getProfileUrl())) {
            url = sender.getProfileUrl();
        }

        Drawable errorIcon = DrawableUtils.createOvalIcon(binding.getRoot().getContext(),
                R.color.background_300, R.drawable.icon_user, R.color.ondark_01);
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(new ObjectKey(url))
                .error(errorIcon)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivProfileView);

        Glide.with(context).load(message.getMessage()).into(binding.ivSticker);
    }

    @Override
    public View getClickableView() {
        return binding.ivSticker;
    }
}
