

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
    public void onUpdateReceived(Update update) {

        long chatId = update.getMessage().getChatId();
        String messageReceived = update.getMessage().getText();
        System.out.println(messageReceived);
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

    @Override
    public String getBotToken() {
        return "6829303358:AAHZgpt7XnIbNkFBCjOdACntVqovNixbeKM";  // TODO: insert your bot token here!
    }

    @Override
    public String getBotUsername() {
        return "AdventureGuide";  // TODO: insert your bots username here
    }

}
