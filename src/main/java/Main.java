import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
public class Main {
    public static ArrayList<Song> fullSongList = Main.getSongList(Main.getJson(new File("maimaidxCN.json")));
    public static HashMap<String, ArrayList<Song>> slByLevel = new HashMap<String, ArrayList<Song>>();

    public static void main(String[] args) {
        File config = new File("config.json");
        MyBot bot;
        if (!config.exists()) {
            System.out.println("请先填写config.json");
            FileWriter writer = null;
            try {
                config.createNewFile();
                writer = new FileWriter(config);
                writer.write("{\"botUsername\": null, \"botToken\": null}");
            } catch (IOException e) {
                //e.printStackTrace();
                log.warn("Unexpected error in ",e);
            } finally {
                try {
                    if (writer != null)
                        writer.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                    log.warn("Unexpected error in ",e);
                }
            }
            return;
        } else {
            Gson gson = new Gson();
            bot = gson.fromJson(Main.getJson(config), MyBot.class);
            log.info(bot.toString());
            //System.out.println(bot.toString());
        }
        Main.init();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            //e.printStackTrace();
            log.warn("Unexpected error in ",e);
        }
    }


    //读取文件
    public static String getJson(File jsonFile) {
        StringBuffer content = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(jsonFile));
            String line = null;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            log.warn("Unexpected error in ",e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                //e.printStackTrace();
                log.warn("Unexpected error in ",e);
            }
        }
        return content.toString();
    }


    class Jsonfile{
        int data;
        ArrayList<Song> songList;
    }
    //从字符串创建曲目列表
    public static ArrayList<Song> getSongList(String s) {
        Gson gson = new Gson();
        Jsonfile j = gson.fromJson(s,Jsonfile.class);
        ArrayList<Song> songList = j.songList;
        return songList;
    }

    public static Song roll(ArrayList<Song> songList) {
        return songList.get((int) (Math.random() * 114514) % songList.size());
    }


    private static void init() {
        //System.out.println("Starting...");
        log.info("Starting...");
        for (Song song : fullSongList) {
            for (String l : song.getLevel().values()) {//创建等级歌单
                if (!slByLevel.containsKey(l)) {
                    slByLevel.put(l, new ArrayList<Song>());
                }
                slByLevel.get(l).add(song);
            }
        }
//        for(String s: slByLevel.keySet()){System.out.println(s);}
//        System.out.println(slByLevel.toString());
        //System.out.println("Started.");
        log.info("Started.");
    }

    public static Song rollByLevel(String level) {
        return roll(slByLevel.get(level));
    }
}