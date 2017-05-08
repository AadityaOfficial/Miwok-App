package com.example.android.miwok;

/**
 * Created by aadit on 4/1/2017.
 */

public class Word {
    String mMiwokTranslation;
    String mDefaultTranslation;
    int mImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;
    int maudioResourceID;

    public Word(String englishTranslation, String miwokWord, int imageResourceId, int maudioResourceID) {
        this.mMiwokTranslation = miwokWord;
        this.mDefaultTranslation = englishTranslation;
        this.mImageResourceId = imageResourceId;
        this.maudioResourceID = maudioResourceID;
    }

    public Word(String englishTranslation, String miwokWord, int maudioResourceID) {
        this.mMiwokTranslation = miwokWord;
        this.mDefaultTranslation = englishTranslation;
        this.maudioResourceID = maudioResourceID;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getaudioResourceID() {
        return maudioResourceID;
    }
}
