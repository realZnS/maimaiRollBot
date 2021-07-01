import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class MyBot extends TelegramLongPollingBot {


    public String botUsername,botToken;

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text

        if (update.hasMessage() && update.getMessage().hasText()) {
            Date date = new Date();
            String messageText = update.getMessage().getText();
            System.out.printf("[%tF %tT]%n",date,date);
            if(messageText.matches("^\\/roll.*")){
                System.out.println("got command \""+messageText+"\" from id "+update.getMessage().getChatId());
                SendPhoto message = new SendPhoto();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setReplyToMessageId(update.getMessage().getMessageId());
                Song song;

                if (messageText.matches("^\\/roll.*\\d{1,2}\\+{0,1}$")) {
                    String l;
                    if(messageText.endsWith("+")) {
                        l=messageText.substring(6, Math.min(6 + 3, messageText.length()));
                    }
                    else {
                        l=messageText.substring(6, Math.min(6 + 2, messageText.length()));
                    }


//                    Pattern r = Pattern.compile("^\\/roll.*\\d{1,2}\\+{0,1}");
//                    String l = r.matcher(update.getMessage().getText()).group(1);
//                    System.out.println(l);


                    if(Main.slByLevel.containsKey(l)){
                        song = Main.rollByLevel(l);
                    }
                    else {
                        song = Main.fullSongList.songList.get(0);
                    }
                }
                else {
                    song = Main.roll(Main.fullSongList.songList);
                }
                System.out.println("returned \""+song.getTitle()+'"');
                message.setCaption(song.toString());
                message.setPhoto(new InputFile(song.getCover()));
                try {
                    execute(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if(update.getMessage().getText().matches(".*(复读机)+.*")){
                System.out.println("got message from id "+update.getMessage().getChatId()+" which says \""+update.getMessage().getText()+"\"");
                SendMessage message = new SendMessage();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText("你才是复读机 哼");
                try {
                    execute(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("got message from id "+update.getMessage().getChatId()+" which says \""+update.getMessage().getText()+"\"");
                SendMessage message = new SendMessage();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText(update.getMessage().getText()+"喵～");

                try {
                    execute(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        if(update.hasInlineQuery()){
            Date date = new Date();
            System.out.printf("[%tF %tT]%n",date,date);
            String query = update.getInlineQuery().getQuery();
            System.out.println(query);

            InlineQuery inlineQuery = update.getInlineQuery();
            AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
            List<InlineQueryResult> results = new ArrayList<InlineQueryResult>();
            for(int i=1;i<=5;i++){
                Song s = Main.roll(Main.fullSongList.songList);
                if(Main.slByLevel.containsKey(query)){
                    s = Main.rollByLevel(query);
                }
                InlineQueryResultPhoto result = new InlineQueryResultPhoto("id"+i, s.getCover());
                result.setCaption(s.toString());
//                result.setTitle(s.getTitle());
//                result.setDescription(s.getTitle());
                result.setThumbUrl(s.getCover());
                results.add(result);
                System.out.println(s.getTitle());
            }
            answerInlineQuery.setResults(results);
            answerInlineQuery.setCacheTime(0);
            answerInlineQuery.setInlineQueryId(inlineQuery.getId());

            try {
                execute(answerInlineQuery);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            //System.out.println("test");
        }
    }
    @Override
    public String toString() {
        return "MyBot{" +
                "botUsername='" + this.getBotUsername() + '\'' +
                ", botToken='" + this.getBotToken() + '\'' +
                '}';
    }
}
