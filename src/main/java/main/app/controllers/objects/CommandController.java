package main.app.controllers.objects;

import main.app.models.ApplicationMainConfig;


public class CommandController extends MenuGenerator {
    public CommandController(ApplicationMainConfig mainConfig,String [] array) {
        super(mainConfig, array, null);
    }
    public static boolean isCommand(String title,String [] array){
        for (String lang:array) {
            if(lang.equals(title)){
                return true;
            }
        }
        return false;
    }
}
