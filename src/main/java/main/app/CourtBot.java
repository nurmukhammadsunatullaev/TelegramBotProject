package main.app;

import main.app.configs.TelegramBotConfig;
import main.app.controllers.interfaces.ActionGenerator;
import main.app.controllers.objects.CommandController;
import main.app.databases.SqliteHelper;
import main.app.models.ApplicationMainConfig;
import main.app.models.UserModel;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;

public class CourtBot extends TelegramLongPollingBot {

    public static Long chatId;
    private static UserModel model;
    private String activeCommand;
    private ActionGenerator activeGenerator;
    private ApplicationMainConfig mainConfig;
    public CourtBot(){
        mainConfig=new ApplicationMainConfig(0);
        activeGenerator=mainConfig.root;
    }

    public void onUpdateReceived(Update update) {
        try {
            Message message=update.getMessage();
            if(message!=null&& message.hasText()) {
                chatId=message.getChatId();
                model=SqliteHelper.getHelper().getUserModelById(message.getChat().getId());
                activeCommand=message.getText();
                if(activeCommand.equals("/start")||CommandController.isCommand(activeCommand,Config.CHANGE_LANGUAGE)){
                    execute(TelegramBotConfig.getLanguageChangeAction(message.getChatId()));
                }
                else if(CommandController.isCommand(activeCommand,Config.COME_BACK)){
                    activeGenerator=mainConfig.getActionGenerator(mainConfig.root,"/start");
                    activeGenerator.make(update,this);
                }
                else {
                    ActionGenerator generator=mainConfig.getActionGenerator(mainConfig.root,message.getText());
                    if(generator!=null){
                        activeGenerator=generator;
                    }
                    if(activeGenerator!=null) {
                        activeGenerator.make(update, this);
                    }
                }
            }
            else if(update.hasCallbackQuery()){
                String command=update.getCallbackQuery().getData();
                if(command.equals("0")||command.equals("1")||command.equals("2")) {
                    int langValue=Integer.parseInt(update.getCallbackQuery().getData());
                    mainConfig.setLanguageId(langValue);
                    sendPhoto(TelegramBotConfig.getRulePhoto(chatId,langValue));
                    execute(TelegramBotConfig.getYesNo(chatId,langValue));
                }
                else if(command.equals("/yes")||command.equals("/no")){
                    if(command.equals("/yes") && model==null){
                        SqliteHelper.registration(update);
                        activeGenerator = mainConfig.getActionGenerator(mainConfig.root, "/start");
                        activeGenerator.make(update, this);
                    }
                }
                else{
                    SendPhoto photo=TelegramBotConfig.getPhotoFile(chatId,command+".jpg");
                    SendDocument document=TelegramBotConfig.getDocumentFile(chatId,command+".pdf");
                    sendPhoto(photo);
                    sendDocument(document);
                }
            }
        } catch (TelegramApiException | SQLException e) {
            e.printStackTrace();
        }
    }
    public String getBotUsername() {
        return Config.BOT_USER_NAME;
    }
    public String getBotToken() {
        return Config.TOKEN;
    }
}

