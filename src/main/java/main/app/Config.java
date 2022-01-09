package main.app;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public final static String PLEASE_SELECT_LANGUAGE="Илтимос, телеграм бот билан ишлаш учун тилни танланг!" +
            "\n\nПожалуйста, что бы работать с телеграм ботом выбрайте яъзык!" +
            "\n\nPlease, select language for the working with Telegram bot!";

    public static final   List<String> LANGUAGES=new ArrayList<>();

    static {
        LANGUAGES.add("\uD83C\uDDFA\uD83C\uDDFF Uzbek");
        LANGUAGES.add("\uD83C\uDDF8\uD83C\uDDEE Русский");
        LANGUAGES.add("\uD83C\uDDF1\uD83C\uDDF7 English");
    }
    public static final String [] CHANGE_LANGUAGE=new String[]{"\uD83C\uDF10 Тил","\uD83C\uDF10 Язык","\uD83C\uDF10 Language"};
    public static final String [] COME_BACK=new String[]{"Асосий меню","Главной меню","Main menu"};
    public final static String BOT_USER_NAME="test_esud_bot";
    public final static String TOKEN="582571488:AAHpniYQuwYUGX9nsBrC8KBSiD_nlov_Hf0";

}
