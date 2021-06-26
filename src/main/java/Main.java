import com.google.gson.Gson;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static SongList sl = Main.getSongList(Main.getJson(new File("maimaidxCN.json")));
    public static void main(String[] args) {
        try{
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyBot());
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }


    //从文件中读取Json
    public static String getJson(File jsonFile){
        StringBuffer content = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(jsonFile));
            String line = null;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }

    //从字符串创建曲目列表
    public static SongList getSongList(String s){
        Gson gson= new Gson();
        SongList songList = gson.fromJson(s,SongList.class);
        return songList;
    }
    public static Song roll(SongList songList){
        return songList.songList.get((int) (System.currentTimeMillis()*123%songList.songList.size()-1));
    }
}