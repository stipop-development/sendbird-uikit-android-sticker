package com.sendbird.uikit_messaging_android.groupchannel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.sendbird.android.SendBird;
import com.sendbird.android.UserMessageParams;
import com.sendbird.uikit.fragments.ChannelFragment;
import com.sendbird.uikit.log.Logger;
import com.sendbird.uikit.model.DialogListItem;
import com.sendbird.uikit.utils.DialogUtils;
import com.sendbird.uikit.utils.SoftInputUtils;
import com.sendbird.uikit_messaging_android.R;

import io.stipop.Stipop;
import io.stipop.StipopDelegate;
import io.stipop.custom.StipopImageView;
import io.stipop.models.SPPackage;
import io.stipop.models.SPSticker;

public class CustomChannelFragment extends ChannelFragment implements StipopDelegate {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        assert view != null;
        assert getActivity() != null;

        StipopImageView stipopIV = new StipopImageView(view.getContext());
        stipopIV.setId(View.generateViewId());
        stipopIV.setImageResource(R.mipmap.ic_sticker_normal);
        stipopIV.setMaxWidth(24);
        stipopIV.setMaxHeight(24);

        Stipop.Companion.connect(getActivity(), stipopIV, SendBird.getCurrentUser().getUserId(), "en", "US", this);

        stipopIV.setOnClickListener((v) -> {
            Stipop.Companion.showKeyboard();
        });

        ConstraintLayout constraintLayout = view.findViewById(R.id.inputPanel);

        assert constraintLayout != null;

        constraintLayout.addView(stipopIV);

        AppCompatEditText textField = view.findViewById(R.id.etInputText);

        assert textField != null;

        ConstraintSet constraintSet = new ConstraintSet();

        constraintSet.clone(constraintLayout);

        final int textFieldHeight = textField.getHeight();
        final int textFieldPadding = textField.getPaddingRight();
        final int stipopIVHeight = stipopIV.getHeight();
        final int margin = (textFieldHeight - stipopIVHeight + textFieldPadding) / 2;

        constraintSet.connect(stipopIV.getId(), ConstraintSet.RIGHT, R.id.etInputText, ConstraintSet.RIGHT, margin);
        constraintSet.connect(stipopIV.getId(), ConstraintSet.TOP, R.id.etInputText, ConstraintSet.TOP, 0);
        constraintSet.connect(stipopIV.getId(), ConstraintSet.BOTTOM, R.id.etInputText, ConstraintSet.BOTTOM, 0);

        constraintSet.applyTo(constraintLayout);


        return view;
    }

    @Override
    protected void showMediaSelectDialog() {
        if (getContext() == null || getFragmentManager() == null) return;
        DialogListItem[] items = {
                new DialogListItem(com.sendbird.uikit.R.string.sb_text_channel_input_camera, com.sendbird.uikit.R.drawable.icon_camera),
                new DialogListItem(com.sendbird.uikit.R.string.sb_text_channel_input_gallery, com.sendbird.uikit.R.drawable.icon_photo),
                new DialogListItem(com.sendbird.uikit.R.string.sb_text_channel_input_document, com.sendbird.uikit.R.drawable.icon_document),
                new DialogListItem(R.string.stipop_search_sticker, com.sendbird.uikit.R.drawable.icon_search)
        };
        hideKeyboard();
        DialogUtils.buildItemsBottom(items, (view, position, key) -> {
            try {
                if (key == com.sendbird.uikit.R.string.sb_text_channel_input_camera) {
                    takeCamera();
                } else if (key == com.sendbird.uikit.R.string.sb_text_channel_input_gallery) {
                    takePhoto();
                } else if (key == com.sendbird.uikit.R.string.sb_text_channel_input_document) {
                    takeFile();
                } else if (key == R.string.stipop_search_sticker) {
                    Stipop.Companion.showSearch();
                }
            } catch (Exception e) {
                Logger.e(e);
                if (key == com.sendbird.uikit.R.string.sb_text_channel_input_camera) {
                    toastError(com.sendbird.uikit.R.string.sb_text_error_open_camera);
                } else if (key == com.sendbird.uikit.R.string.sb_text_channel_input_gallery) {
                    toastError(com.sendbird.uikit.R.string.sb_text_error_open_gallery);
                } else {
                    toastError(com.sendbird.uikit.R.string.sb_text_error_open_file);
                }
            }
        }).showSingle(getFragmentManager());
    }

    @Override
    public boolean canDownload(@NonNull SPPackage spPackage) {
        return true;
    }

    @Override
    public boolean onStickerSelected(@NonNull SPSticker spSticker) {
        final UserMessageParams userMessageParams = new UserMessageParams(spSticker.getStickerImg());
        userMessageParams.setCustomType("sticker");
        sendUserMessage(userMessageParams);
        return true;
    }

    private void hideKeyboard() {
        if (this.getView() != null) {
            SoftInputUtils.hideSoftKeyboard(this.getView());
        }

    }
}
