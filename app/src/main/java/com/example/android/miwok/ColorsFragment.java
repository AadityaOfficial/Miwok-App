package com.example.android.miwok;

import android.app.Fragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by aadit on 4/8/2017.
 */

public class ColorsFragment extends android.support.v4.app.Fragment {
    MediaPlayer mMediaPlayer;
    AudioManager mAudioManager;

    public ColorsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        //TODO:Array of languages

        final ArrayList<Word> words = new ArrayList<>();
        Word newWord[] = new Word[8];
        newWord[0] = new Word("red", "wetetti", R.drawable.color_red, R.raw.color_red);
        newWord[1] = new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green);
        newWord[2] = new Word("brown", "takaakki", R.drawable.color_brown, R.raw.color_brown);
        newWord[3] = new Word("gray", "topoppi", R.drawable.color_gray, R.raw.color_gray);
        newWord[4] = new Word("black", "kululli", R.drawable.color_black, R.raw.color_black);
        newWord[5] = new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white);
        newWord[6] = new Word("dusty yellow", "kenekaku", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow);
        newWord[7] = new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow);


        for (Word v : newWord)
            words.add(v);

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_colors);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word w = words.get(i);
                releaseMediaPlayer();
                mAudioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
                int requestResult = mAudioManager.requestAudioFocus(mAudioFocusListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //mAudioManager.registerMediaButtonEventReceiver(RemoteControlReciever);
                    mMediaPlayer = MediaPlayer.create(getActivity(), w.getaudioResourceID());
                    mMediaPlayer.start();
                }
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        releaseMediaPlayer();
                    }
                });

            }
        });
        return rootView;

    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mAudioFocusListener);
        }
    }

    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            switch (i) {

                case AudioManager.AUDIOFOCUS_GAIN:
                    mMediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    releaseMediaPlayer();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                    break;
            }
        }
    };
}