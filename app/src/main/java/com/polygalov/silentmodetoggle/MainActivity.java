package com.polygalov.silentmodetoggle;

import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //префикс "m" означает что поле не паблик и не статик
    private AudioManager mAudioManager;

    private boolean mPhoneIsSilent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        checkIfPhoneIsSilent();
        setMuttonClickListener();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkIfPhoneIsSilent();
        toggleUI();
    }

    //проверка состояния телефона
    private void checkIfPhoneIsSilent() {
        int ringerMode = mAudioManager.getRingerMode();
        if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
            mPhoneIsSilent = true;
        } else {
            mPhoneIsSilent = false;
        }
    }

    //Кликлистенер
    private void setMuttonClickListener() {
        Button toggleButton = (Button) findViewById(R.id.toggle_button);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Код обработчика события клика
                if (mPhoneIsSilent) {
                    //Переключение на громкий звонок
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    mPhoneIsSilent = false;
                } else {
                    //Переключение в бесшумный режим
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    mPhoneIsSilent = true;
                }
                toggleUI();
                }
        });
    }

    //Переключение рисунка
    private void toggleUI() {
        ImageView imageView = (ImageView) findViewById(R.id.phone_icon);
        Drawable newPhoneImage;
        if (mPhoneIsSilent) {
            newPhoneImage = getResources().getDrawable(R.drawable.phone_off);
        } else {
            newPhoneImage = getResources().getDrawable(R.drawable.phone_on);
        }imageView.setImageDrawable(newPhoneImage);
    }
}