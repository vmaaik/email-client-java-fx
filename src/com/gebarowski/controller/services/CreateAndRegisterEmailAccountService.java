package com.gebarowski.controller.services;

import com.gebarowski.controller.ModelAccess;
import com.gebarowski.model.EmailAccountBean;
import com.gebarowski.model.EmailConstants;
import com.gebarowski.model.folder.EmailFolderBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CreateAndRegisterEmailAccountService extends Service<Integer> {


    private String emailAddress;
    private String password;
    private EmailFolderBean<String> folderRoot;
    private ModelAccess modelAccess;


    public CreateAndRegisterEmailAccountService(String emailAddress, String password, EmailFolderBean<String> folderRoot, ModelAccess modelAccess) {
        this.modelAccess = modelAccess;
        this.emailAddress = emailAddress;
        this.password = password;
        this.folderRoot = folderRoot;
    }

    @Override
    protected Task<Integer> createTask() {

        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                EmailAccountBean emailAccount = new EmailAccountBean(emailAddress, password);
                if (emailAccount.getLoginState() == EmailConstants.LOGIN_STATE_SUCCEDED) {
                    modelAccess.addAccount(emailAccount);
                    EmailFolderBean<String> emailFolderBean = new EmailFolderBean<String>(emailAddress);
                    folderRoot.getChildren().add(emailFolderBean);
                    // fetching folders
                    FetchFoldersService fetchFoldersService = new FetchFoldersService(folderRoot, emailAccount, modelAccess);
                    fetchFoldersService.start();


                }
                return emailAccount.getLoginState();
            }
        };
    }
}
