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
    Man_first man_first;
    Man_second man_second;

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
        man_first = new Man_first();
        man_second = new Man_second();
        //별똥별
        drop = new Drop();
        for(int i=0;i<starNum;i++)
            myStar[i] = new Star();
    }


    public void draw()
    {
        image(starBg,width/2,height/2);
        woman.display(width/2,height-height/8);
        man_first.display(width/2-100,height-height/8);
        man_second.display(width/2+100,height-height/8);
        image(land,width/2,height-height/20);

        for(int i=0;i<starLimit;i++)
        {
            if(i==1 || i== 3|| i==5 || i==9 || i == 15)
            {
                myStar[i].myStar();
                myStar[i].myDisplay();
            }
            else
            {
                myStar[i].shine();
                myStar[i].display();
            }
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
        float temp = random(5,10);
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
    class Man_first  {
        PImage man;

        Man_first()
        {
            man = loadImage("man1.png");
            man.resize((int)width/10,(int)(width/10*1.5));
        }

        void display(float x, float y)
        {
            image(man,x,y);
        }
    }
    class Man_second  {
        PImage man;

        Man_second()
        {
            man = loadImage("man2.png");
            man.resize((int)width/10,(int)(width/10*1.5));
        }

        void display(float x, float y)
        {
            image(man,x,y);
        }
    }
    //별 객체
    //
    class Star
    {
        PVector location;
        float brightness;
        float starSize;
        float light;
        PVector myStarLoc[] = new PVector[4];
        float dropSpeed = random(10,17);

        Star()
        {
            noStroke();
            location = new PVector(random(width),random(height-height/5));
            starSize = random(3,7);
            for(int i =0;i<4;i++)
            {
                myStarLoc[i] = new PVector(random(location.x-50,location.x+50),
                        random(location.y-50,location.y+50));
            }
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
                brightness = 255;
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
            if(location.x >width || location.y>height-height/5)
            {
                location.x = random(width);
                location.y = random(height/2);
                dropSpeed = random(10,16);
            }
            location.x+=dropSpeed;
            location.y+=dropSpeed;

        }

        void dropDisplay()
        {
            fill(255);
            ellipse(location.x,location.y,starSize,starSize);
        }

        //별자리
        void myDisplay()
        {
            fill(255,brightness);
            pushMatrix();
            stroke(255,brightness);
            ellipse(location.x,location.y,starSize,starSize);
            line(location.x,location.y,myStarLoc[0].x,myStarLoc[0].y);
            ellipse(myStarLoc[0].x,myStarLoc[0].y,starSize,starSize);
            line(location.x,location.y,myStarLoc[1].x,myStarLoc[1].y);
            ellipse(myStarLoc[1].x,myStarLoc[1].y,starSize,starSize);
            line(myStarLoc[1].x,myStarLoc[1].y,myStarLoc[2].x,myStarLoc[2].y);
            ellipse(myStarLoc[2].x,myStarLoc[2].y,starSize,starSize);
            line(myStarLoc[2].x,myStarLoc[2].y,myStarLoc[3].x,myStarLoc[3].y);
            ellipse(myStarLoc[3].x,myStarLoc[3].y,starSize,starSize);
            popMatrix();
            noStroke();
        }

        void myStar()
        {
            if(brightness>255){
                brightness =255;
                light = random(-1,-7);
            }
            if(brightness<0)
            {
                location = new PVector(random(width),random(height-height/5));
                for(int i =0;i<4;i++)
                {
                    myStarLoc[i] = new PVector(random(location.x-50,location.x+50),
                            random(location.y-50,location.y+50));
                }
                light = random(1,7);
                starSize =  random(3,7);
                brightness =0 ;
            }
            brightness +=light;
            for(int i =0;i<4;i++)
            {
                myStarLoc[i].x += noise(1)*random(-2,2);
                myStarLoc[i].y += noise(1)*random(-2,2);
            }
        }

    }
}
