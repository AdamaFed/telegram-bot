

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;


public class Bot extends TelegramLongPollingBot {

    LevelScenario level1 = new LevelScenario("Regenwald", "Die Einheimischen sprechen von einem geheimnisvollen Ungeheuer", "Es soll in der Nacht kommen, hat große rote Augen und einen schreckliches gebrüll", "finde das ungeheuer oder gehe schnell zurück in dein Hotel");
    LevelScenario level2 = new LevelScenario("Regenwald", "Man ist nun im Regenwald. Der Weg spaltet sich. Der eine Weg ist frei. Der andere hingegen versperrt durch ein großes Spinnennetz", "", "Nimm den einfachen Weg oder tritt durch das Spinnennetz und gehe diesen Weg");
    LevelScenario level3 = new LevelScenario("Regenwald", "Der Ort wo das Ungeheuer sein soll ist erreicht. Es raschelt im Busch. Könnte es das Ungeheuer sein?", "Ist es das Ungeheuer welches von den Einheimischen beschrieben wurde?", "Laufe weg oder stelle dich dem Ungeheuer");

    private long currentLevel = 1;
    private int maxLevels = 3;
    private Random random = new Random();

    private Map<Long, String> userChoices = new HashMap<>();


    public void onUpdateReceived(Update update) {

        long chatId = update.getMessage().getChatId();
        String messageReceived = update.getMessage().getText();
        System.out.println(messageReceived);

        if (currentLevel > maxLevels) {
            sendResponse(chatId, "Spiel beendet. Du hast alle Level gespielt.");
        } else {
            System.out.println("Level " + currentLevel + ": " + messageReceived);

            if (messageReceived.equals("/start")) {
                sendAvatarSelectionMessage(chatId);
            } else if (messageReceived.equals("warrior") || messageReceived.equals("magician") || messageReceived.equals("shooter")) {
                userChoices.put(chatId, messageReceived);
                sendResponse(chatId, "Du hast den " + messageReceived + " Avatar ausgewählt. Bitte warte auf weitere Details.");
                sendAvatarInfo(chatId, messageReceived);
                //Spiel
            } else if (messageReceived.equals("/level" + currentLevel)) {
                sendResponse(chatId, getCurrentLevel().createStory());
                setRandomCorrectAnswer(chatId);
            } else if (messageReceived.toLowerCase().equals("/a") || messageReceived.toLowerCase().equals("/b")) {
                boolean isCorrectAnswer = checkAnswer(chatId, messageReceived);
                processAnswer(chatId, isCorrectAnswer);
                if(isCorrectAnswer) {
                    sendResponse(chatId, getCurrentLevel().createStory());
                    setRandomCorrectAnswer(chatId);
                }
            } else {
                sendResponse(chatId, "Ungültige Eingabe. Bitte wähle 'a' oder 'b'.");
            }
        }
    }

    private LevelScenario getCurrentLevel() {
        if (currentLevel == 1) {
            return level1;
        } else if (currentLevel == 2) {
            return level2;
        } else if (currentLevel == 3) {
            return level3;
        }
        return null;
    }

    private void setRandomCorrectAnswer(long chatId) {
        if (currentLevel > 0) {
            String correctAnswer = random.nextBoolean() ? "/a" : "/b";
            userChoices.put(chatId, correctAnswer);
        }
    }

    private void processAnswer(long chatId, boolean isCorrectAnswer) {
        if (isCorrectAnswer) {
            sendResponse(chatId, "Richtig! Nächste Level: Level " + (currentLevel + 1));
            currentLevel++;
        } else {
            sendResponse(chatId, "Falsche Antwort. Du hast verloren.");
        }
    }

    private boolean checkAnswer(long chatId, String answer) {
        LevelScenario currentLevel = getCurrentLevel();
        if (currentLevel != null) {
            String correctAnswer = userChoices.get(chatId);
            return correctAnswer != null && correctAnswer.equalsIgnoreCase(answer);
        }
        return false;
    }



       /* try {
            String chatGptResponse = ChatGptService.chatGptCall(messageReceived);
            sendResponse(chatId, chatGptResponse);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }*/


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
            sendResponse(chatId, level1.createStory());
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