package main.app.controllers.interfaces;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public interface IActionMaker {
      void make(Update update, ActionGenerator actionGenerator, TelegramLongPollingBot pollingBot);
}
