
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {

    private Quiz q;

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = update.getMessage().getChatId();
        String messageReceived = update.getMessage().getText();
        System.out.println(messageReceived);

        // start to evaluate the messages you received
        // 1. Main menu
        if (messageReceived.toLowerCase().startsWith("hello")) {
            sendResponse(chatId, "Welcome, human being 🤖");
            sendResponse(chatId, "Let's have some fun now.... 😎");
            sendResponse(chatId, "1. Type 'quiz', if you want me to ask you something smart");
            sendResponse(chatId, "2. Type any text, if you want me to count its letters");
        }



    private void sendResponse(long chatId, String s) {
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText(s);

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPollToUser(long chatId) {
        SendPoll sendPoll = new SendPoll();
        sendPoll.setChatId(chatId);
        sendPoll.setQuestion("Which programming language do you like the most?");
        sendPoll.setOptions(List.of("Java", "Python", "JavaScript", "C++"));
        try {
            execute(sendPoll);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return "xxxxx";  // TODO: insert your bot token here!
    }

    @Override
    public String getBotUsername() {
        return "xxxxx";  // TODO: insert your bots username here
    }
}