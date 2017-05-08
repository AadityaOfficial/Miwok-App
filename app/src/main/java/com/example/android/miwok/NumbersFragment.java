package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by aadit on 4/8/2017.
 */

public class NumbersFragment extends Fragment {

    public NumbersFragment() {
    }

    MediaPlayer mMediaPlayer;
    AudioManager mAudioManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        //TODO:Array of languages
        final ArrayList<Word> words = new ArrayList<>();
        Word newWord[] = new Word[10];
        newWord[0] = new Word("one", "lutti", R.drawable.number_one, R.raw.number_one);
        newWord[1] = new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two);
        newWord[2] = new Word("three", "tolockosu", R.drawable.number_three, R.raw.number_three);
        newWord[3] = new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four);
        newWord[4] = new Word("five", "massokka", R.drawable.number_five, R.raw.number_five);
        newWord[5] = new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six);
        newWord[6] = new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven);
        newWord[7] = new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight);
        newWord[8] = new Word(" nine", "wo’e", R.drawable.number_nine, R.raw.number_nine);
        newWord[9] = new Word("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten);

        for (Word v : newWord)
            words.add(v);

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word w = words.get(i);
                releaseMediaPlayer();
                mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
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
