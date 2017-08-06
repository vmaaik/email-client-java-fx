package com.gebarowski.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.HashMap;
import java.util.Map;

public class SampleData {

    final ObservableList<EmailMessageBean> Inbox = FXCollections.observableArrayList(
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 123236642, "<html>asdasdasdadsadadasdadadas</html>",false),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 134362, "<html>RERRERGEH</html>",true),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 78788, "<html>SDFsdadsadadasdadadas</html>",false),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 14452, "<html>XBSAB</html>",false),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 16333362, "<html>DFHSAAABAB</html>",true)
    );


    final ObservableList<EmailMessageBean> Sent = FXCollections.observableArrayList(
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 123236642, "<html>aWWWWWsdasdasdadsadadasdadadas</html>",false),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 134362, "<html>RERREWWWWRGEH</html>",true),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 78788, "<html>SDFsDFGGGGWdadsadadasdadadas</html>",false),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 14452, "<html>XBSEAB</html>",true),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 16333362, "<html>DFHSWWWWAAABAB</html>",true)
    );


    final ObservableList<EmailMessageBean> Spam = FXCollections.observableArrayList(
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 123633642, "<html>aDFGREW323ERGEFGH</html>",true),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 74438788, "<html>SDFDF234234Dsdadsadadasdadadas</html>",false),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 3444352, "<html>XBSDFGF2342GAB</html>",false),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 514433362, "<html>DFHS234234234ADFGAABAB</html>",false)
    );


    // We dont know how many foders are in app
    public Map<String, ObservableList<EmailMessageBean>> emailFolders = new HashMap<String, ObservableList<EmailMessageBean>>();

    public SampleData(){

        emailFolders.put("Inbox", Inbox);
        emailFolders.put("Sent", Sent);
        emailFolders.put("Spam", Spam);
    }
}
