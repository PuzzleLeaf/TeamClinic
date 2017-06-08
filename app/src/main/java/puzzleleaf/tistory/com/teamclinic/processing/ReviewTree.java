package puzzleleaf.tistory.com.teamclinic.processing;



import processing.core.PApplet;
import processing.core.PVector;


/**
 * Created by cmtyx on 2017-05-07.
 */

public class ReviewTree extends PApplet {


    public void settings() {

         fullScreen();
    }

    pathfinder[] paths;

    public void setup() {
        background(7,20,29);
        ellipseMode(CENTER);
        noStroke();
        smooth();
        paths = new pathfinder[1];
        paths[0] = new pathfinder();
    }
    public void draw() {
        for (int i=0;i<paths.length;i++) {
            PVector loc = paths[i].location;
            float diam = paths[i].diameter;
            fill(255,50);
            ellipse(loc.x, loc.y, diam, diam);
            paths[i].update();
        }
    }
    public void mousePressed() {
        background(7,20,29);
        paths = new pathfinder[1];
        paths[0] = new pathfinder();
    }

    class pathfinder {
        PVector location;
        PVector velocity;
        float diameter;

        pathfinder() {
            location = new PVector(width/2, height);
            velocity = new PVector(0, -1);
            diameter = StageData.treeNum;
        }

        pathfinder(pathfinder parent) {
            location = parent.location.get();
            velocity = parent.velocity.get();
            float area = sq(parent.diameter/2); //제곱
            float newDiam = sqrt(area/2)*2;
            diameter = newDiam;
            parent.diameter = newDiam;
        }
        void update() {
            if (diameter>0.5) {
                location.add(velocity);
                PVector bump = new PVector(random(-1,1), random(-1,1));
                bump.mult((float)0.1);
                velocity.add(bump);//누적해서 더해짐
                velocity.normalize();//가속도를 1미만으로 정규화

                if (random(0, (float)1.1)<0.02) {
                    paths = (pathfinder[]) append(paths, new pathfinder(this));
                    //재귀 호출
                }
            }
        }
    }

}
