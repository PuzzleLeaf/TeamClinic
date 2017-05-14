package puzzleleaf.tistory.com.teamclinic.Review;


import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import puzzleleaf.tistory.com.teamclinic.R;
import puzzleleaf.tistory.com.teamclinic.processing.ReviewTree;
import puzzleleaf.tistory.com.teamclinic.processing.StarTitle;

/**
 * Created by cmtyx on 2017-01-22.
 */

public class ReviewItem extends FragmentStatePagerAdapter {

   ReviewItem(FragmentManager fragmentManager)
   {
       super(fragmentManager);
   }

    @Override
    public Fragment getItem(int position) {
        return null;
    }


    @Override
    public int getCount() {
        return 0;
    }
}
