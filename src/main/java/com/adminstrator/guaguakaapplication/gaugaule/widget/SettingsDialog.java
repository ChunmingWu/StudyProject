package com.adminstrator.guaguakaapplication.gaugaule.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.gaugaule.function.SaveScratchVoiceStatus;

/**
 * Created by Administrator on 2019/8/17.
 */

@SuppressLint("ValidFragment")
public class SettingsDialog extends DialogFragment {
    public static final int FLAG_MUSIC = 0,FLAG_VOICE = 1;
    private Context mContext;

    @SuppressLint("ValidFragment")
    public SettingsDialog(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.bigDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.layout_settings_dialog,container);
        CheckBox cb_music_settings_dialog = view.findViewById(R.id.cb_music_settings_dialog);
        CheckBox cb_voice_settings_dialog = view.findViewById(R.id.cb_voice_settings_dialog);
        ImageView iv_cha_settings_dialog = view.findViewById(R.id.iv_cha_lost_scratch_result_dialog);

        cb_music_settings_dialog.setChecked(SaveScratchVoiceStatus.getMusicStatus(mContext));
        cb_voice_settings_dialog.setChecked(SaveScratchVoiceStatus.getVoiceStatus(mContext));

        iv_cha_settings_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        cb_music_settings_dialog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(null != mOnCheckListener){
                    mOnCheckListener.onCheck(FLAG_MUSIC,isChecked);
                    SaveScratchVoiceStatus.saveMusicStatus(mContext,isChecked);
                }
            }
        });
        cb_voice_settings_dialog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(null != mOnCheckListener){
                    mOnCheckListener.onCheck(FLAG_VOICE,isChecked);
                    SaveScratchVoiceStatus.saveVoiceStatus(mContext,isChecked);
                }
            }
        });
        return view;
    }

    public interface OnCheckListener{
        void onCheck(int flag,boolean checked);
    }

    private OnCheckListener mOnCheckListener;

    public void setOnCheckListener(OnCheckListener onCheckListener ){
        mOnCheckListener = onCheckListener;
    }

}
