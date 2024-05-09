package com.upravad.cookbot.core.senders;

import static com.upravad.cookbot.exception.ExceptionMessage.PHOTO_CAPTION;
import static com.upravad.cookbot.exception.ExceptionMessage.PHOTO_SIMPLE;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.upravad.cookbot.exception.BaseException;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;

/**
 * Processing photo sending.
 *
 * @see Update
 * @see SendPhoto
 * @author daktah
 * @version 1.0.0
 */
@Component
public class PhotoSender {

  /**
   * Send a simple photo.
   *
   * @param update from telegram
   * @param url from dishes
   * @return prepared SendPhoto for commit
   * @see SendPhoto
   * @see InputFile
   */
  public SendPhoto sendPhoto(Update update, String url) {
    try {
      SendPhoto photo = new SendPhoto();
      photo.setChatId(update.getMessage().getChatId());
      photo.setPhoto(new InputFile(new URL(url).openStream(), url));
      return photo;

    } catch (IOException e) {
      throw new BaseException(e, PHOTO_SIMPLE.getMessage());
    }
  }

  /**
   * Send a photo with the caption.
   *
   * @param update from telegram
   * @param url from dishes
   * @param caption from dishes
   * @return prepared SendPhoto for commit
   * @see SendPhoto
   * @see InputFile
   */
  public SendPhoto sendPhoto(Update update, String url, String caption) {
    try {
      SendPhoto photo = new SendPhoto();
      photo.setChatId(update.getMessage().getChatId());
      photo.setCaption(caption);
      photo.setPhoto(new InputFile(new URL(url).openStream(), url));
      return photo;

    } catch (IOException e) {
      throw new BaseException(e, PHOTO_CAPTION.getMessage());
    }
  }

}
