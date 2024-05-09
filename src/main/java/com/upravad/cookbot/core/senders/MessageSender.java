package com.upravad.cookbot.core.senders;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Component;

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
   * @param text from botService
   * @return prepared SendPhoto for commit
   * @see SendMessage
   */
  public SendMessage sendMessage(Update update, String text) {
    SendMessage message = new SendMessage();
    message.setChatId(update.getMessage().getChatId());
    message.setText(text);
    return message;
  }

}
