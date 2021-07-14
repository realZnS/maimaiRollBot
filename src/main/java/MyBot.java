import lombok.extern.slf4j.Slf4j;
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
import java.util.List;


@Slf4j
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
            String messageText = update.getMessage().getText();
            if(messageText.matches("^\\/roll.*")){
                log.info("got command {} from {} {}",messageText,update.getMessage().getChat().getFirstName(),update.getMessage().getChatId());
                //System.out.println("got command \""+messageText+"\" from id "+update.getMessage().getChatId());
                SendPhoto message = new SendPhoto();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setReplyToMessageId(update.getMessage().getMessageId());
                Song song;
                if (messageText.matches("^\\/roll.*\\d{1,2}\\+?$")) {
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
                        song = Main.fullSongList.get(0);
                    }
                }
                else {
                    song = Main.roll(Main.fullSongList);
                }
                log.info("returned \"{}\"",song.getTitle());
                //System.out.println("returned \""+song.getTitle()+'"');
                message.setCaption(song.toString());
                message.setPhoto(new InputFile(song.getCover()));
                try {
                    execute(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    log.warn("Unexpected error in ",e);
                }
            } else if(update.getMessage().getText().matches(".*(复读机)+.*")){
                log.info("got command {} from {} {}",messageText,update.getMessage().getChat().getFirstName(),update.getMessage().getChatId());
                //System.out.println("got message from id "+update.getMessage().getChatId()+" which says \""+update.getMessage().getText()+"\"");
                SendMessage message = new SendMessage();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText("你才是复读机 哼");
                try {
                    execute(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    log.warn("Unexpected error in ",e);
                }
            } else {
                log.info("got command {} from {} {}",messageText,update.getMessage().getChat().getFirstName(),update.getMessage().getChatId());
                //System.out.println("got message from id "+update.getMessage().getChatId()+" which says \""+update.getMessage().getText()+"\"");
                SendMessage message = new SendMessage();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText(update.getMessage().getText()+"喵～");
                try {
                    execute(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    log.warn("Unexpected error in ",e);
                }
            }
        }

        if(update.hasInlineQuery()){
            String query = update.getInlineQuery().getQuery();
            System.out.println(query);

            InlineQuery inlineQuery = update.getInlineQuery();
            AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
            List<InlineQueryResult> results = new ArrayList<>();
            for(int i=1;i<=5;i++){
                Song s = Main.roll(Main.fullSongList);
                if(Main.slByLevel.containsKey(query)){
                    s = Main.rollByLevel(query);
                }
                InlineQueryResultPhoto result = new InlineQueryResultPhoto("id"+i, s.getCover());
                result.setCaption(s.toString());
//                result.setTitle(s.getTitle());
//                result.setDescription(s.getTitle());
                result.setThumbUrl(s.getCover());
                results.add(result);
            }
            log.info("Inline from {} {}",update.getInlineQuery().getFrom().getUserName(),update.getInlineQuery().getFrom().getId());
            answerInlineQuery.setResults(results);
            answerInlineQuery.setCacheTime(0);
            answerInlineQuery.setInlineQueryId(inlineQuery.getId());

            try {
                execute(answerInlineQuery);
            } catch (TelegramApiException e) {
                log.warn("Unexpected error in ",e);
            }
            //System.out.println("test");
        }
    }
    @Override
    public String toString() {
        return "botUsername='" + this.getBotUsername() + '\'' +
                ", botToken='" + this.getBotToken() + '\'';
    }
}
