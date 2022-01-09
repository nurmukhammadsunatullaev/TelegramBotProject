package main.app.controllers.objects;

import main.app.controllers.interfaces.ActionGenerator;
import main.app.controllers.interfaces.IActionMaker;
import main.app.models.ApplicationMainConfig;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuGenerator implements ActionGenerator {

    private ApplicationMainConfig  mainConfig;
    private List<ActionGenerator> children=new ArrayList<>();
    private List<String> commandName;
    protected IActionMaker doIt;

    public MenuGenerator(ApplicationMainConfig mainConfig, String [] commandName, IActionMaker doIt) {
        this.commandName=Arrays.asList(commandName);
        this.doIt=doIt;
        this.mainConfig=mainConfig;
    }

    public void add(ActionGenerator actionGenerator){
        children.add(actionGenerator);
    }


    @Override
    public List<ActionGenerator> getChildren() {
        return children;
    }

    @Override
    public void make(Update update, TelegramLongPollingBot pollingBot) {
        if(doIt!=null) {
            doIt.make(update, this, pollingBot);
        }
    }

    @Override
    public List<String> getCommandNameList() {
        return commandName;
    }



    @Override
    public int getLanguage() {
        return mainConfig.getLanguageId();
    }


}
