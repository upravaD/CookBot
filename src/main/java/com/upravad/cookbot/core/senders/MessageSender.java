package com.upravad.cookbot.core.senders;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

/**
 * Processing photo sending.
 *
 * @see Update
 * @see SendMessage
 * @author daktah
 * @version 1.0.0
 */
@Component
public class MessageSender {

  /**
   * Send a simple message.
   *
   * @param update from telegram
   * @param text from service
   * @return prepared SendMessage for commit
   * @see SendMessage
   */
  public SendMessage sendMessage(Update update, String text) {
    SendMessage message = new SendMessage();
    message.setChatId(update.getMessage().getChatId());
    message.setText(text);
    return message;
  }

  /**
   * Send a simple message with view keyboard.
   *
   * @param update from telegram
   * @param text from service
   * @param keyboardMarkup from view
   * @return prepared SendMessage for commit
   * @see SendMessage
   */
  public SendMessage sendMessage(Update update, String text, ReplyKeyboard keyboardMarkup) {
    SendMessage message = new SendMessage();
    message.setChatId(update.getMessage().getChatId());
    message.setText(text);
    message.setReplyMarkup(keyboardMarkup);
    return message;
  }

  /**
   * Send an error message.
   *
   * @param update from telegram
   * @param text from service
   * @return prepared SendMessage for commit
   */
  public SendMessage errorMessage(Update update, String text) {
    SendMessage message = new SendMessage();
    message.setChatId(update.getMessage().getChatId());
    message.setReplyToMessageId(update.getMessage().getMessageId());
    message.setText(text);
    return message;
  }

}
