import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChatGptService {

    public static String chatGptCall(String input) {
        OpenAiService service = new OpenAiService("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", Duration.ofSeconds(60));


        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.USER.value(), "");
        /*messages.add(systemMessage);*/


        ChatMessage firstMsg = new ChatMessage(ChatMessageRole.USER.value(), input);
        if (input.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
        messages.add(firstMsg);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo-0613")
                .messages(messages)
                .maxTokens(360)
                .build();
        try {
            ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
            messages.add(responseMessage);
            return responseMessage.getContent();
        }catch(Exception e){
            System.out.println(e.getMessage());
//            ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
//            messages.add(responseMessage);
//            return responseMessage.getContent();
        }
        return null;
    }


}

