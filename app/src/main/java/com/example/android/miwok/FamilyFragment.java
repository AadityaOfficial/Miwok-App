package com.example.android.miwok;

import android.app.Fragment;
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

public class FamilyFragment extends android.support.v4.app.Fragment {

    public FamilyFragment() {
    }

    MediaPlayer mMediaPlayer;
    AudioManager mAudioManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        final ArrayList<Word> words = new ArrayList<>();
        Word newWord[] = new Word[10];
        newWord[0] = new Word("father", "әpә", R.drawable.family_father, R.raw.family_father);
        newWord[1] = new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother);
        newWord[2] = new Word("son", "angsi", R.drawable.family_son, R.raw.family_son);
        newWord[3] = new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter);
        newWord[4] = new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother);
        newWord[5] = new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother);
        newWord[6] = new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister);
        newWord[7] = new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister);
        newWord[8] = new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother);
        newWord[9] = new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather);

        for (Word v : newWord)
            words.add(v);

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_family);
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

    @Override
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