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

public class MyBot extends TelegramLongPollingBot {


    public String getBotUsername() {
        return "ZnSTestBot";
    }

    public String getBotToken() {
        return "1626709682:AAGfkeykc0q7wMwIdNg4hmVWY1xwdbWdZMQ";
    }

    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            Date date = new Date();
            System.out.printf("[%tF %tT]%n",date,date);
            if(update.getMessage().getText().matches("^\\/roll.*")){
                System.out.println("got command \"/roll\" from id "+update.getMessage().getChatId());
                SendPhoto message = new SendPhoto();
                message.setChatId(update.getMessage().getChatId().toString());

                Song song = Main.roll(Main.sl);
                System.out.println("returned \""+song.getTitle()+'"');
                message.setCaption(song.toString());
                message.setPhoto(new InputFile(song.getCover()));
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

            InlineQuery inlineQuery = update.getInlineQuery();
            AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
            List<InlineQueryResult> results = new ArrayList<InlineQueryResult>();
            for(int i=1;i<=5;i++){
                Song s = Main.roll(Main.sl);
                InlineQueryResultPhoto result = new InlineQueryResultPhoto("id"+i, s.getCover());
                result.setCaption(s.toString());
                result.setTitle(s.getTitle());
                result.setDescription(s.getTitle());
                result.setThumbUrl(s.getCover());
                results.add(result);
            }
            answerInlineQuery.setResults(results);
            answerInlineQuery.setCacheTime(0);
            answerInlineQuery.setInlineQueryId(inlineQuery.getId());

            try {
                execute(answerInlineQuery);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            System.out.println("test");
        }
    }


}
