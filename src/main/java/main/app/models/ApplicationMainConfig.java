package main.app.models;

import main.app.Config;
import main.app.CourtBot;
import main.app.databases.PostgreSQLConnection;
import main.app.configs.TelegramBotConfig;
import main.app.controllers.interfaces.ActionGenerator;
import main.app.controllers.interfaces.IActionMaker;
import main.app.controllers.objects.CommandController;
import main.app.controllers.objects.MenuGenerator;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationMainConfig {
    public ActionGenerator root;
    private ActionGenerator backObject=new MenuGenerator(this,Config.COME_BACK,null);

    public int applicationLanguage;
    public ApplicationMainConfig(int language) {
        this.applicationLanguage=language;
        this.root = new MenuGenerator(this,new String[]{"/start","/start","/start"},makerObject);
        MenuGenerator service=new MenuGenerator(this,new String[]{"Текшириш","Проверка","Checking"},makerObject);

        MenuGenerator objByName=new MenuGenerator(this,new String[]{"Исми оркали","По имени","By Name"},
                (update,actionGenerator,pollingBot)->{
                    if(!actionGenerator.getCommandNameList().contains(update.getMessage().getText())) {
                        List<String> messages = null;
                        try {
                            messages = PostgreSQLConnection.getSqlConnection().getByFullName(update.getMessage().getText());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println(update.getMessage().getText());
                        for (String message : messages) {
                            try {
                                pollingBot.execute(TelegramBotConfig.getSendMessage(update.getMessage().getChatId(), message));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }

                        }
                        if(messages.isEmpty()){
                            try {
                                pollingBot.execute(TelegramBotConfig.getSendMessage(update.getMessage().getChatId(),actionGenerator.getServiceDecription("title.message.not.found.")));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }else {
                        try {
                            pollingBot.execute(TelegramBotConfig.getSendMessage(update.getMessage().getChatId(),actionGenerator.getServiceDecription("title.rule.checkbyname.")));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }

                    }

                });

        service.add(objByName);
        service.add(new MenuGenerator(this,new String[]{"СТИР оркали","По ИНН","By TIN"},
                (update,actionGenerator,pollingBot)->{
            if(!actionGenerator.getCommandNameList().contains(update.getMessage().getText())) {
                List<String> messages = null;
                try {
                    messages = PostgreSQLConnection.getSqlConnection().getByID(update.getMessage().getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (String message : messages) {
                    try {
                        pollingBot.execute(TelegramBotConfig.getSendMessage(update.getMessage().getChatId(), message));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                }
                if(messages.isEmpty()){
                    try {
                        pollingBot.execute(TelegramBotConfig.getSendMessage(
                                update.getMessage().getChatId(),
                                actionGenerator.getServiceDecription("title.message.not.found.")));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                try {
                    pollingBot.execute(TelegramBotConfig.getSendMessage(update.getMessage().getChatId(),actionGenerator.getServiceDecription("title.message.search.by.inn.")));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }

        }));
         service.add(backObject);

        ((MenuGenerator) this.root).add(new MenuGenerator(this,new String[]{"Инфографикалар ва флаерлар","Инфографики и флаеры","Infographics and flyers"},(update,actionGenerator,pollingBot)->{
                    if(actionGenerator.getCommandNameList().contains(update.getMessage().getText())){
                        try {
                            pollingBot.execute(TelegramBotConfig.getSendMessage(update.getMessage().getChatId(),"Расмлар"));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        SendPhoto []photos=TelegramBotConfig.getSendPhotoFiles(update.getMessage().getChatId());
                        for (SendPhoto photo:photos) {
                            try {
                                pollingBot.sendPhoto(photo);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            )
        );
        ((MenuGenerator) this.root).add(new MenuGenerator(this,new String[]{"Адабиётлар","Литературы","Literature"},(update,actionGenerator,pollingBot)->{
                    if(actionGenerator.getCommandNameList().contains(update.getMessage().getText())){
                        try {
                            pollingBot.execute(TelegramBotConfig.getBooksList(update.getMessage().getChatId(),
                                    new String[]{
                                            "ФИБ судларда экпертиза\n тайинлаш хусусиятлари".toUpperCase(),
                                           // "Оила ва никох муносабатларидан".toUpperCase(),//\nкелиб чикувчи низолар суд амалиёти",
                                           // "ОИЛА ВА НИКОҲ \nМУНОСАБАТЛАРИНИНГ ҲУҚУҚИЙ ТАРТИБИ",
                                           // "ИҚТИСОДИЙ СУДЛАРГА\n МУРОЖААТ ҚИЛИШ ТАРТИБИ",
                                            "АЛЬТЕРНАТИВНЫЕ МЕХАНИЗМЫ\n РАЗРЕШЕНИЯ СПОРОВ",
                                           // "ВОРИСЛИК ҲУҚУҚИ",
                                            "ЎЗБЕКИСТОНДА МЕРОС ҲУҚУҚИ",
                                            "КЎЧМАС МУЛК ВА МУЛК ҲУҚУҚИ",
                                            "НАСЛЕДСТВЕННОЕ ПРАВО В УЗБЕКИСТАНЕ",
                                          //  "Судларга мурожаат қилиш".toUpperCase()// ва\nсуд ҳужжатлари устидан шикоят бериш тартиби"

                                    },
                                    new String[]{
                                            "/books/1",
                                          //  "/books/2",
                                          //  "/books/3",
                                          //  "/books/4",
                                            "/books/5",
                                           // "/books/6",
                                            "/books/7",
                                            "/books/8",
                                            "/books/9",
                                          //  "/books/10",
                            })
                            );
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                })
        );

        ((MenuGenerator) this.root).add(new CommandController(this,Config.CHANGE_LANGUAGE));
        ((MenuGenerator) this.root).add(service);

    }

    public ActionGenerator getActionGenerator(ActionGenerator generator, String command){
        if(generator.getCommandNameList().contains(command)){
            return generator;
        }
        List<ActionGenerator> children=generator.getChildren();
        ActionGenerator findGenerator=null;
        for (ActionGenerator child : children) {
            if(child.getCommandNameList().contains(command)){
                return child;
            }
            findGenerator=getActionGenerator(child,command);
        }
        return findGenerator;
    }

    private IActionMaker makerObject=new IActionMaker() {
        @Override
        public void make(Update update, ActionGenerator actionGenerator, TelegramLongPollingBot pollingBot) {
            SendMessage message=new SendMessage();
            if(update.getMessage()==null) {
                message.setChatId(CourtBot.chatId);
            }else{
                message.setChatId(update.getMessage().getChatId());
            }
            message.setText(actionGenerator.getServiceDecription("title.message.search."));
            List<KeyboardRow> keyboardRows=new ArrayList<>();
            for (ActionGenerator generator: actionGenerator.getChildren()) {
                keyboardRows.add(TelegramBotConfig.getKeyboardRow(generator.getCommandNameList().get(actionGenerator.getLanguage())));
            }
            message.setReplyMarkup(TelegramBotConfig.getReplyKeyboardMarkup(keyboardRows));
            try {
                pollingBot.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    };

    public int getLanguageId() {
        return applicationLanguage;

    }
    public void setLanguageId(int languageId){
        applicationLanguage=languageId;
    }
}

