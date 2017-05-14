package puzzleleaf.tistory.com.teamclinic.processing;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import puzzleleaf.tistory.com.teamclinic.MainActivity;

/**
 * Created by cmtyx on 2017-04-08.
 */

public class StarTitle extends PApplet {

    PImage starBg;
    PImage land;
    Woman woman;

    //별 개수
    int starNum = 300;
    int starLimit = 50;

    Star myStar[] = new Star[starNum];

    Drop drop;

    public void settings() {
        fullScreen();
    }

    public void setup()
    {
        imageMode(CENTER);

        starBg = loadImage("mainbg.png");
        land = loadImage("land.png");
        land.resize(width,height/10);
        starBg.resize(width,height);

        woman = new Woman();
        //별똥별
        drop = new Drop();
        for(int i=0;i<starNum;i++)
            myStar[i] = new Star();
    }


    public void draw()
    {
        image(starBg,width/2,height/2);
        woman.display(width/2,height-height/8);
        image(land,width/2,height-height/20);

        for(int i=0;i<starLimit;i++)
        {
            if((i+1)%(starLimit/5)==0)
                drop.display();
            myStar[i].shine();
            myStar[i].display();
        }
    }

    public void mouseReleased()
    {
        if(starLimit+25<starNum)
            starLimit+=25;
    }

    class Drop
    {
        PVector location;
        PVector vel;
        float temp = random(1,5);
        float size = 4;

        Drop()
        {
            location = new PVector(80,80);
            vel = new PVector(temp,temp);
        }

        void display()
        {
            fill(255);
            location.add(vel);
            ellipse(location.x,location.y,size,size);
            for(int i=(int)size*3;i>0;i--)
            {
                ellipse(location.x-i,location.y-i,size-i/3,size-i/3);
            }

            if(location.y>height-height/12){
                location = new PVector(random(-width,width/2),0);
                temp = random(1,5);
                size = random(2,4);
                vel = new PVector(temp,temp);
            }

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
    //별 객체
    class Star
    {
        float xpos, ypos;
        PVector location;
        float brightness;
        float starSize;
        float light;


        Star()
        {
            noStroke();
            location = new PVector(random(width),random(height-height/5));
            starSize = random(3,7);
            light = random(3,7);
            brightness =30;
        }

        void display()
        {

            for(int i=0;i<starSize;i++)
            {
                fill(255,brightness-i*5);
                ellipse(location.x,location.y,i,i);
            }
        }

        //빛나는 효과
        void shine()
        {
            if(brightness>255){
                brightness =255;
                light = random(-3,-7);

            }
            if(brightness<0)
            {
                location = new PVector(random(width),random(height-height/5));
                light = random(3,7);
                starSize = random(7);
                brightness =0 ;
            }
            brightness +=light;

        }

        void drop()
        {
            if((int)random(1,100)%66==0)
            {
                PVector acc = new PVector(10,200);
                location.add(acc);

            }

            if(location.x>width)
                location = new PVector(random(width),random(height-height/8));
        }

    }
}
