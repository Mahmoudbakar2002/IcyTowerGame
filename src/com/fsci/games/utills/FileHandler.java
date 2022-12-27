package com.fsci.games.utills;

import java.io.*;

public class FileHandler {

    public static void SaveFile(String url,String data){
        try (FileWriter writer = new FileWriter(url);
             BufferedWriter bw = new BufferedWriter(writer)) {

            bw.write(data);

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }


    public static String[][] ReadCSV(String url , int r, int c){
        String [][] data=new String[r][c];
        for(int i=0;i<r;i++)for(int j=0;j<c;j++)data[i][j]="";

        String line = "";
        int i=0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(url));
            while ((line = br.readLine()) != null && i<r) {
                String[] columns = line.split(",");
                for(int j=0;j< Math.min(columns.length,c);j++)
                    data[i][j]=columns[j];

                i++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

}
