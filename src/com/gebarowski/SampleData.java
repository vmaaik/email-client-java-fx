package com.gebarowski;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class SampleData {

    final ObservableList<EmailMessageBean> Inbox = FXCollections.observableArrayList(
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 123236642, "<html>asdasdasdadsadadasdadadas</html>"),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 134362, "<html>RERRERGEH</html>"),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 78788, "<html>SDFsdadsadadasdadadas</html>"),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 14452, "<html>XBSAB</html>"),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 16333362, "<html>DFHSAAABAB</html>")
    );


    final ObservableList<EmailMessageBean> Sent = FXCollections.observableArrayList(
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 123236642, "<html>aWWWWWsdasdasdadsadadasdadadas</html>"),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 134362, "<html>RERREWWWWRGEH</html>"),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 78788, "<html>SDFsDFGGGGWdadsadadasdadadas</html>"),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 14452, "<html>XBSEAB</html>"),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 16333362, "<html>DFHSWWWWAAABAB</html>")
    );


    final ObservableList<EmailMessageBean> Spam = FXCollections.observableArrayList(
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 123633642, "<html>aDFGREW323ERGEFGH</html>"),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 74438788, "<html>SDFDF234234Dsdadsadadasdadadas</html>"),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 3444352, "<html>XBSDFGF2342GAB</html>"),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 514433362, "<html>DFHS234234234ADFGAABAB</html>")
    );


    // We dont know how many foders are in app
    private Map<String, ObservableList<EmailMessageBean>> emailFolders = new HashMap<String, ObservableList<EmailMessageBean>>();

    public SampleData(){

        emailFolders.put("Inbox", Inbox);
        emailFolders.put("Sent", Sent);
        emailFolders.put("Spam", Spam);
    }
}
