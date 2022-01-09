package main.app;

import main.app.controllers.interfaces.ActionGenerator;
import main.app.models.ApplicationMainConfig;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;


public class MainApplication {
    public static void main(String[] args) throws IOException {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi=new TelegramBotsApi();
        try{
            botsApi.registerBot(new CourtBot());

        }catch (TelegramApiException e){
            System.out.println(e.getMessage());
        }
    }
}
