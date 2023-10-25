

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

    private Map<Long, String> userChoices = new HashMap<>();


    public void onUpdateReceived(Update update) {

        long chatId = update.getMessage().getChatId();
        String messageReceived = update.getMessage().getText();
        System.out.println(messageReceived);

        //Check if Avatar selected
        if (userChoices.containsKey(chatId)) {
            String choice = userChoices.get(chatId);
            sendAvatarInfo(chatId, choice);
            //userChoices.remove(chatId);
        } else {
            if (messageReceived.equals("/start")) {
                sendAvatarSelectionMessage(chatId);
            } else if (messageReceived.equals("warrior") || messageReceived.equals("magician") || messageReceived.equals("shooter")) {
                userChoices.put(chatId, messageReceived);
                sendResponse(chatId, "You've selected the " + messageReceived + " avatar. Please wait for details.");
            } else {
                sendResponse(chatId, "Please start by selecting an avatar: warrior, magician, or shooter.");
            }
        }

        try {
            String chatGptResponse = chatGptCall(messageReceived);
            sendResponse(chatId, chatGptResponse);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }



    public String chatGptCall(String input) {
        OpenAiService service = new OpenAiService("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");


            List<ChatMessage> messages = new ArrayList<>();
            ChatMessage systemMessage = new ChatMessage(ChatMessageRole.USER.value(), "");
            messages.add(systemMessage);


            ChatMessage firstMsg = new ChatMessage(ChatMessageRole.USER.value(), input);
            if (input.equalsIgnoreCase("exit")) {
                System.exit(0);
            }
            messages.add(firstMsg);

            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-3.5-turbo-0613")
                    .messages(messages)
                    .maxTokens(100)
                    .build();

            ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
            messages.add(responseMessage);
            return responseMessage.getContent();

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
