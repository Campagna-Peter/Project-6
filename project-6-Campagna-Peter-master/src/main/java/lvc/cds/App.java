package lvc.cds;

import java.io.IOException;

public final class App {

    public static void main(String[] args) throws IOException {

        Mapping map = new Mapping("test.txt");
        var list = map.userScorePairs();

        System.out.println("Below is a list of all the users on the Good 'ole Boy newtwork and their associated influencer scores");
        System.out.printf("%s      %s%n", "User", "Score");
        for (int i = 0; i < list.length; i++) {
            System.out.printf("%-10s%-10.4f%n", list[i].name, list[i].score);
        }

        System.out.println();
        System.out.println("The top ten influencers on the Good 'ole Boy network are");
        for(int i = 0; i < 10; i++){
            System.out.println(i+1 + ". " + list[i].name);
        }        

        //The runtime complexity for influencer ranking is O(v^2 + ve). I know this because for every vertex in the graph we do
        //bfs which is O(v + e), calculate average distance and compute which is O(v), and compute the score which is O(1). Therefore,
        //we do O(v + e) work for every vertex, v, which gives us a final runtime of O(v^2 + ve).

    }
}
