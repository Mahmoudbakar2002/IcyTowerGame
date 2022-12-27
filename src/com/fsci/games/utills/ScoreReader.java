package com.fsci.games.utills;

import java.io.*;
import java.util.Scanner;

public class ScoreReader {
    private static Score[] scores;
    static {
        String[][] data=FileHandler.ReadCSV(Config.SCORE_FILE_URL,10,2);
        scores=new Score[10];
        for(int i=0;i< scores.length;i++){
            String name=data[i][0];
            int score=data[i][1].trim().isEmpty()?0:Integer.parseInt(data[i][1]);
            scores[i]=new Score(name,score);
        }
    }


    public static class Score{
        String name;
        int score;

        public Score(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return name+"\t"+score;
        }
    }
    public static boolean isHighScore(int score){
        return score>scores[scores.length-1].score||scores[scores.length-1].score==0 ;
    }
    public static void addNewScore(Score score){
        int idx=0;
        for(;idx<scores.length;idx++){
            if(score.score>scores[idx].score)break;
        }
        for(int i= scores.length-1;i>idx;i--){
            scores[i]=scores[i-1];
        }
        scores[idx]=score;

        saveScore();
    }
    public static ScoreReader.Score[] scoreData(){
        return scores;
    }

    private static  void saveScore(){
        String data="";
        for(Score score:scores){
            data+=score.name+","+score.score+"\n";
        }
        FileHandler.SaveFile(Config.SCORE_FILE_URL,data);
    }


}
