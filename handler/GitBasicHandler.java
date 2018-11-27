package com.fr.performance.handler;

import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

/**
 * Created by yuwh on 2018/10/17
 * Description:none
 */
public final class GitBasicHandler {
    private String username;
    private String password;
    private File gitRepositoryPath;
    private String corigin;
    private String cbranch;
    private Git cgit;

    public GitBasicHandler(String path){
        gitRepositoryPath = new File(path);
        try {
            cgit = Git.open(gitRepositoryPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addOperation(String despath){
        try {
            cgit.add().addFilepattern(despath).call();
            return true;
        } catch (GitAPIException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean commitOperation(String comment){
        CommitCommand commitCommand = cgit.commit().setMessage(comment).setAllowEmpty(true);
        try {
            commitCommand.call();
            return true;
        } catch (GitAPIException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean pushOperation(){
        try {
            cgit.push().setRemote(corigin).setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).call();
            return true;
        } catch (GitAPIException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertAuth(String u,String p,String o,String b){
        username = u;
        password = p;
        corigin = o;
        cbranch = b;
    }
}
