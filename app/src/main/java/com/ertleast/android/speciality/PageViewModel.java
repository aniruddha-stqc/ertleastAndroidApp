package com.ertleast.android.speciality;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private String[][] specialityArray;

    public String[][] getSpecialityArray() {
        return specialityArray;
    }

    public void setSpecialityArray(String[][] specialityArray) {
        this.specialityArray = specialityArray;
    }

    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {

            //Switch case
            String speciality_uri = null;
/*
            switch (input) {
                case 1:
                    speciality_uri = "http://freetesting.site/bio_web/specialty.php?by=1";
                    break;
                case 2:
                    speciality_uri = "http://freetesting.site/bio_web/specialty.php?by=2";
                    break;
                case 3:
                    speciality_uri = "http://freetesting.site/bio_web/specialty.php?by=3";
                    break;
                case 4:
                    speciality_uri = "http://avoir.in/biogenics/news_highlights.php";
                    break;
                case 5:
                    speciality_uri = "http://freetesting.site/bio_web/specialty.php?by=1";
                    break;
                case 6:
                    speciality_uri = "https://bit.ly/2tslluf";
                    break;
                case 7:
                    speciality_uri = "https://bit.ly/2tslluf";
                    break;
                case 8:
                    speciality_uri = "https://bit.ly/2tslluf";
                    break;
                case 9:
                    speciality_uri = "https://bit.ly/2tslluf";
                    break;
                case 10:
                    speciality_uri = "https://bit.ly/2tslluf";
                    break;
                case 11:
                    speciality_uri = "https://bit.ly/2tslluf";
                    break;
                case 12:
                    speciality_uri = "https://bit.ly/2tslluf";
                    break;
                case 13:
                    speciality_uri = "https://bit.ly/2tslluf";
                    break;
                case 14:
                    speciality_uri = "https://bit.ly/2tslluf";
                    break;
                case 15:
                    speciality_uri = "https://bit.ly/2tslluf";
                    break;
                case 16:
                    speciality_uri = "https://bit.ly/2tslluf";
                    break;
            }*/

            //speciality_uri = "http://thaipharmaindex.com/bio_web/specialty.php?by=" + specialityArray[input][0];
            speciality_uri = "http://thaipharmaindex.com/specialty.php?by=" + specialityArray[input][0];
            // return "Hello world from section: " + input;
            return speciality_uri;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}