package main.app.controllers.interfaces;

import main.app.MainApplication;
import main.app.configs.InlineKeyboardBuilder;
import main.app.configs.TelegramBotConfig;
import main.app.controllers.constants.ItemType;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public interface ActionGenerator {
    List<ActionGenerator> getChildren();
    void make(Update update, TelegramLongPollingBot pollingBot);
    List<String> getCommandNameList();
    default String getServiceDecription(String key) {
        return TelegramBotConfig.getServiceDecription(key+getLanguage());
    }
    int getLanguage();
}
