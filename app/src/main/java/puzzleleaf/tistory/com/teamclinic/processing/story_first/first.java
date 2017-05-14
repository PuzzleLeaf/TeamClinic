package puzzleleaf.tistory.com.teamclinic.processing.story_first;

import android.util.Log;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Created by cmtyx on 2017-04-08.
 */

public class first extends PApplet {

    String storyFile[] = {"chapter_first_a.png","chapter_first_b.png","chapter_first_c.png","chapter_first_d.png",
            "chapter_first_e.png","chapter_first_f.png","chapter_first_g.png","chapter_first_h.png","chapter_first_i.png",
            "chapter_first_j.png","chapter_first_k.png","chapter_first_l.png","chapter_first_m.png","chapter_first_n.png",
            "chapter_first_o.png","chapter_first_p.png","chapter_first_q.png","chapter_first_r.png","chapter_first_s.png",
            "chapter_first_t.png","chapter_first_u.png","chapter_first_v.png","chapter_first_w.png","chapter_first_x.png",
            "chapter_first_y.png","chapter_first_z.png"};
    PImage starBg;
    PImage land;
    PImage story[] = new PImage[30];
    Woman woman;
    boolean isLoading = false;
    int storyIdx = 0;

    public void settings() {
        fullScreen();
    }

    public void setup()
    {
        imageMode(CENTER);
        background(0);
        starBg = loadImage("mainbg.png");
        land = loadImage("land.png");
        land.resize(width,height/10);
        starBg.resize(width,height);
        woman = new Woman();
        story[0] = loadImage(storyFile[0]);
        story[0].resize(width, height);
    }


    public void draw()
    {

        if(isLoading) {
            try {
                if (storyIdx < 4) {
                    image(story[storyIdx], width / 2, height / 2);
                }
                if (storyIdx == 2) {
                    image(starBg, width / 2, height / 2);
                    image(story[2], width / 2, height / 2);
                    woman.display(width / 2, height - height / 8);
                    image(land, width / 2, height - height / 20);
                }
                if (storyIdx == 4) {
                    image(starBg, width / 2, height / 2);
                    woman.display(width / 2, height - height / 8);
                    image(land, width / 2, height - height / 20);
                    image(story[4], width / 2, height / 2);
                }
                if (storyIdx > 4) {
                    image(starBg, width / 2, height / 2);
                    woman.display(width / 2, height - height / 8);
                    image(land, width / 2, height - height / 20);
                    image(story[storyIdx], width / 2, height / 2);

                }
            }
            catch (Exception e)
            {

            }
        }
        if(!isLoading) {
            story[storyIdx] = loadImage(storyFile[storyIdx]);
            story[storyIdx].resize(width, height);
            isLoading=!isLoading;
        }


    }


    public void mouseReleased()
    {
        if(storyIdx<25) {
            storyIdx++;
            isLoading=!isLoading;
        }
    }



   class Woman  {
        PImage woman;
        Woman()
        {
            woman = loadImage("woman.png");
            woman.resize((int)width/10,(int)(width/10*1.5));
        }

        void display(float x, float y)
        {
            image(woman,x,y);
        }
    }

    @Override
    public void onDestroy() {
        for(int i=0;i<=storyIdx;i++)
            removeCache(story[i]);
        System.gc();
        Log.d("Qwe","qweqwe");
        super.onDestroy();
    }
}
