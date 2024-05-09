package org.example;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Азизбек
 * @project TelegramBot
 * @date 08.05.2024
 * @time 8:33
 */
public class MyAmazingBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            menu(sendText(update.getMessage().getChatId(), "Your message: " + message));
        } else if (update.hasMessage() && update.getMessage().hasContact()) {
            Contact contact = update.getMessage().getContact();
            menu(sendText(update.getMessage().getChatId(), "Your phone number: " + contact.getPhoneNumber()));
        } else if (update.hasMessage() && update.getMessage().hasLocation()) {
            Location location = update.getMessage().getLocation();
            menu(sendText(update.getMessage().getChatId(), "Your location: " + location.getLatitude() + ", " + location.getLongitude()));
        }
    }

    @Override
    public String getBotToken() {
        return "7004921798:AAHFxtFJA94LKlqN5eYcuyzaCZoZrOSIIxo";
    }

    @Override
    public String getBotUsername() {
        return "my_online_market_bot";
    }

    public void menu(SendMessage message) {
        List<KeyboardRow> list = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(EmojiParser.parseToUnicode(":telephone_receiver:") + " Kontakt yuborish");
        row1.get(0).setRequestContact(true);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(EmojiParser.parseToUnicode(":round_pushpin:") + " Lokatsiya yuborish");
        row2.get(0).setRequestLocation(true);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(EmojiParser.parseToUnicode(":gear:") + " Sozlamalar");

        list.add(row1);
        list.add(row2);
        list.add(row3);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setKeyboard(list);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public SendMessage sendText(Long to, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(to));
        message.setText(text);
        return message;
    }
}
