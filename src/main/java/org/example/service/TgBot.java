package org.example.service;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TgBot extends TelegramLongPollingBot {



    @Override
    public String getBotToken() {
        return "8599356362:AAET-5i2VfPZcQanp1HvrHdzg-LxVsxKzYk";
    }
    @Override
    public String getBotUsername() {
        return "Podruzhanya_bot";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText();


            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);

            if(message.equals("/start") || message.equals("/help")){
                sendMessage.setText("Привет! Выбери свой знак зодиака ✨");
                ReplyKeyboardMarkup keyboardMarkup = createZodiacKeyBoard();
                sendMessage.setReplyMarkup(keyboardMarkup);
            } else if(message.contains("Овен") || message.contains("Телец")|| message.contains("Дева") || message.contains("Рак")
            || message.contains("Лев") || message.contains("Близнецы") || message.contains("Весы") || message.contains("Скорпон")
                || message.contains("Стрелец") || message.contains("Козерог") || message.contains("Водолей") || message.contains("Рыба")){

                sendMessage.setText(String.valueOf(ParserGoroskop.getZodiacs().get(message.substring(0, message.length()-2))));

            }else {
                sendMessage.setText("Привет! Выбери свой знак зодиака ✨");
            }

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    public ReplyKeyboardMarkup createZodiacKeyBoard(){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Овен ♈");
        row1.add("Телец ♉");
        row1.add("Близнецы ♊");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Рак ♋");
        row2.add("Лев ♌");
        row2.add("Дева ♍");

        KeyboardRow row3 = new KeyboardRow();
        row3.add("Весы ♎");
        row3.add("Скорпион ♏");
        row3.add("Стрелец ♐");

        KeyboardRow row4 = new KeyboardRow();
        row4.add("Козерог ♑");
        row4.add("Водолей ♒");
        row4.add("Рыбы ♓");

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);
        keyboardRows.add(row4);

        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);
        keyboardMarkup.setSelective(true);
        return keyboardMarkup;
    }
}