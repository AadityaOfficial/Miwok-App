package com.example.android.miwok;

import android.app.Fragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
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

public class PhrasesFragment extends android.support.v4.app.Fragment {
    MediaPlayer mMediaPlayer;
    AudioManager mAudioManager;

    public PhrasesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        //TODO:Array of languages
        final ArrayList<Word> words = new ArrayList<>();
        Word newWord[] = new Word[10];
        newWord[0] = new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going);
        newWord[1] = new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name);
        newWord[2] = new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is);
        newWord[3] = new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling);
        newWord[4] = new Word("I'm feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good);
        newWord[5] = new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming);
        newWord[6] = new Word("Yes, I'm coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming);
        newWord[7] = new Word("I'm coming.", "әәnәm", R.raw.phrase_im_coming);
        newWord[8] = new Word("Let's go.", "yoowutis", R.raw.phrase_lets_go);
        newWord[9] = new Word("Come here.", "әnni'nem", R.raw.phrase_come_here);

        for (Word v : newWord)
            words.add(v);

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_phrases);
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
