package com.sendbird.uikit_messaging_android.groupchannel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.sendbird.android.MessageListParams;
import com.sendbird.uikit.activities.ChannelActivity;
import com.sendbird.uikit.activities.adapter.MessageListAdapter;
import com.sendbird.uikit.consts.StringSet;
import com.sendbird.uikit.fragments.ChannelFragment;
import com.sendbird.uikit_messaging_android.R;

import io.stipop.Stipop;
import io.stipop.StipopDelegate;
import io.stipop.extend.StipopImageView;
import io.stipop.model.SPPackage;
import io.stipop.model.SPSticker;

public class CustomChannelActivity extends ChannelActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    @Override
    protected ChannelFragment createChannelFragment(@NonNull String channelUrl) {
        final boolean useMessageGroupUI = false;
        final Intent intent = getIntent();
        ChannelFragment.Builder builder = new ChannelFragment.Builder(channelUrl)
                .setCustomChannelFragment(new CustomChannelFragment())
                .setMessageListAdapter(new CustomMessageListAdapter(useMessageGroupUI))
                .setUseHeader(true)
                .setStartingPoint(intent.getLongExtra(StringSet.KEY_STARTING_POINT, Long.MAX_VALUE));
        if (intent.hasExtra(StringSet.KEY_HIGHLIGHT_MESSAGE_INFO)) {
            builder.setHighlightMessageInfo(intent.getParcelableExtra(StringSet.KEY_HIGHLIGHT_MESSAGE_INFO));
        }
        if (intent.hasExtra(StringSet.KEY_FROM_SEARCH_RESULT)) {
            builder.setUseHeaderRightButton(intent.getBooleanExtra(StringSet.KEY_FROM_SEARCH_RESULT, false));
        }
        return builder.build();
    }
}

