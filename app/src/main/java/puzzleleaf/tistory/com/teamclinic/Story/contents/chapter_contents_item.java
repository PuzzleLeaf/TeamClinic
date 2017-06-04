package puzzleleaf.tistory.com.teamclinic.Story.contents;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.ArrayList;

import puzzleleaf.tistory.com.teamclinic.R;


public class chapter_contents_item extends PagerAdapter
{

    Context context;
    ArrayList<Integer> res;

    chapter_contents_item(Context context, ArrayList<Integer> res)
    {
        this.context = context;
        this.res = res;
    }
    @Override
    public int getCount() {
        return res.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_chapter_contents_item, container, false);
        ImageView image = (ImageView)layout.findViewById(R.id.chapter_first_contents_image);
        image.setImageResource(res.get(position));
        container.addView(layout, 0);
        return layout;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View)object;
    }
}