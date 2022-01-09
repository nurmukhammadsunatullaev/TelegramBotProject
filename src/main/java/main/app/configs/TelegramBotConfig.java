package main.app.configs;

import main.app.Config;
import main.app.MainApplication;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class TelegramBotConfig {

    public static SendMessage getYesNo(Long chatId, int lang){
        InlineKeyboardBuilder builder=InlineKeyboardBuilder.create()
                .setChatId(chatId)
                .row()
                    .button(getServiceDecription("title.rule.yes."+lang),"/yes")
                    .button(getServiceDecription("title.rule.no."+lang),"/no")
                .endRow()
                .setText(getServiceDecription("title.rule.agree.question."+lang));
        return builder.build();
    }

    public static KeyboardRow getKeyboardRow(String title){
        KeyboardRow row=new KeyboardRow();
        row.add(title);
        return row;
    }
    public static ReplyKeyboardMarkup getReplyKeyboardMarkup(List<KeyboardRow> keyboardRow){
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRow);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static SendPhoto getPhotoFile(Long chatId,String fileName){
        File images = new File(MainApplication.class.getResource(fileName).getFile());
        SendPhoto  photos=new SendPhoto();
        photos.setChatId(chatId);
        photos.setNewPhoto(images);
        return photos;

    }

    public static SendPhoto getRulePhoto(Long chatId, int lang){
        String []rules=new String[]{"/rules/esud_uz.png","/rules/esud_ru.png","/rules/esud_en.png"};
        String caption=getServiceDecription("title.rule.image.caption."+lang);
        SendPhoto photo=getPhotoFile(chatId,rules[lang]);
        photo.setCaption(caption);
        return photo;
    }


    public static SendDocument getDocumentFile(Long chatId,String fileName){
        File file=new File(MainApplication.class.getResource(fileName).getFile());
        SendDocument sendDocument=new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setNewDocument(file);
        return sendDocument;
    }

    public static SendPhoto[] getSendPhotoFiles(Long chatId){
        File[] images = new File(MainApplication.class.getResource("/info_files/").getFile()).listFiles();
        SendPhoto [] photos=new SendPhoto[images.length];
        for(int i=0;i<images.length;i++) {
            photos[i] = new SendPhoto();
            photos[i].setChatId(chatId);
            photos[i].setNewPhoto(images[i]);
        }
        return photos;
    }



    public static SendMessage getSendMessage(Long chatId, String simple_text) {
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(simple_text);
        return sendMessage;
    }

    public static SendMessage getLanguageChangeAction(Long chatId){
        InlineKeyboardBuilder builder=InlineKeyboardBuilder.create()
                .setChatId(chatId)
                .row()
                .button(Config.LANGUAGES.get(0),"0")
                .button(Config.LANGUAGES.get(1),"1")
                .button(Config.LANGUAGES.get(2),"2")
                .endRow()
                .setText(Config.PLEASE_SELECT_LANGUAGE);

        return builder.build();
    }

    public static SendMessage getBooksList(Long chatId,String [] booksName,String []commands){
        InlineKeyboardBuilder builder=InlineKeyboardBuilder.create()
                .setChatId(chatId);
            for (int i=0;i<booksName.length;i++){
                 builder.row()
                 .button(booksName[i],commands[i])
                 .endRow();
            }
            builder.setText("Керакли китобни танланг юклаш учун!!!");
        return builder.build();
    }




    public static String getServiceDecription(String key) {
        InputStream inputStream=MainApplication.class.getClassLoader().getResourceAsStream("messages.properties");
        Properties properties=new Properties();
        try {
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
