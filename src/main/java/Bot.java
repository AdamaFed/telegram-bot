

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;


public class Bot extends TelegramLongPollingBot {

    LevelScenario level1 = new LevelScenario("Kolumbien", "Fischereiverbotszone", "Behörden", "fischen oder nicht fischen");


    private Map<Long, String> userChoices = new HashMap<>();


    public void onUpdateReceived(Update update) {
        int i = 0;

        long chatId = update.getMessage().getChatId();
        String messageReceived = update.getMessage().getText();
        System.out.println(messageReceived);

        //Check if Avatar selected

        if (messageReceived.equals("/start")) {
            sendAvatarSelectionMessage(chatId);
            i++;
        } else if (messageReceived.equals("warrior") || messageReceived.equals("magician") || messageReceived.equals("shooter")) {
            userChoices.put(chatId, messageReceived);
            sendResponse(chatId, "You've selected the " + messageReceived + " avatar. Please wait for details.");
            sendAvatarInfo(chatId, messageReceived);
            i++; //2
        } else {
            sendResponse(chatId, "Please start by selecting an avatar: warrior, magician, or shooter.");
        }

        if (i > 0 && messageReceived.equals("level1")) {
            sendResponse(chatId, "You've selected the " + messageReceived + " avatar. Please wait for details.");
            i++;
        }

       /* try {
            String chatGptResponse = ChatGptService.chatGptCall(messageReceived);
            sendResponse(chatId, chatGptResponse);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }*/

    }

    private void sendAvatarSelectionMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Choose an avatar: warrior, magician, or shooter");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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

    private void sendAvatarInfo(long chatId, String choice) {
        Avatar avatar = null;

        if (choice.equals("warrior")) {
            avatar = new WarriorAvatar();
        } else if (choice.equals("magician")) {
            avatar = new MagicianAvatar();
        } else if (choice.equals("shooter")) {
            avatar = new ShooterAvatar();
        }

        if (avatar != null) {
            String info = avatar.getName() + "\n\n" + avatar.getDescription() + "\n\nAbilities: " + avatar.getAbilities();
            sendResponse(chatId, info);
            sendResponse(chatId, level1.createStory(level1.toString()));
        } else {
            sendResponse(chatId, "Invalid avatar choice.");
        }
    }

    @Override
    public String getBotToken() {
        return "6829303358:AAHZgpt7XnIbNkFBCjOdACntVqovNixbeKM";  // TODO: insert your bot token here!
    }

    @Override
    public String getBotUsername() {
        return "AdventureGuide";  // TODO: insert your bots username here
    }

}