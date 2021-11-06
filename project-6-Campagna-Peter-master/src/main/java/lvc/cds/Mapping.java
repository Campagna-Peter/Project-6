package lvc.cds;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Mapping {
    private HashMap<String, Integer> dict = new HashMap<>();
    private String[] userList;
    private ALGraph followGraph;

    public Mapping(String filename) throws IOException {
        int count;
        String[] temp;
        File file = new File(filename);
        try (Scanner sc = new Scanner(file)) {

            count = sc.nextInt();
            sc.nextLine();
            followGraph = new ALGraph(count);
            temp = new String[count];
            for(int i = 0; i < temp.length; i++){
                temp[i] = sc.nextLine();
            }
        }

        userList = new String[count];
        String[] follows = new String[count];
        // split off each name and put it in userList
        for(int i = 0; i < temp.length; i++){
            String str = temp[i];
            var splitRes = str.split(":");
            userList[i] = splitRes[0];
            follows[i] = splitRes[1].trim();
            dict.put(userList[i], i);
        }

        for(int i = 0; i < count; i ++){
            var myFollows = follows[i].split(" ");
            // add edges from userList[i] to each entry in myFollows
            for (var follow : myFollows) {
                // add an edge from userList[i] to follow
                followGraph.addEdge(dict.get(follow), i);
            }
        }
    }

    public static class user{
        String name;
        double score;
        user(String name, double score){
            this.name = name;
            this.score = score;
        }
    }

    public user[] userScorePairs(){
        user[] pair = new user[userList.length];

        for(int i = 0; i < pair.length; i++){
            pair[i] = new user(userList[i], followGraph.influencerScore(i));
        }

        Arrays.sort(pair, (u1, u2) -> Double.compare(u1.score, u2.score));

        return pair;
    }
}
