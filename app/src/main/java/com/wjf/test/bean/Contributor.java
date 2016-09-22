package com.wjf.test.bean;

/**
 * Created by Administrator on 2016/9/8.
 */
public class Contributor {

    /**
     * login : JakeWharton
     * id : 66577
     * avatar_url : https://avatars.githubusercontent.com/u/66577?v=3
     * gravatar_id :
     * url : https://api.github.com/users/JakeWharton
     * html_url : https://github.com/JakeWharton
     * followers_url : https://api.github.com/users/JakeWharton/followers
     * following_url : https://api.github.com/users/JakeWharton/following{/other_user}
     * gists_url : https://api.github.com/users/JakeWharton/gists{/gist_id}
     * starred_url : https://api.github.com/users/JakeWharton/starred{/owner}{/repo}
     * subscriptions_url : https://api.github.com/users/JakeWharton/subscriptions
     * organizations_url : https://api.github.com/users/JakeWharton/orgs
     * repos_url : https://api.github.com/users/JakeWharton/repos
     * events_url : https://api.github.com/users/JakeWharton/events{/privacy}
     * received_events_url : https://api.github.com/users/JakeWharton/received_events
     * type : User
     * site_admin : false
     * contributions : 795
     */

    private String login;
    private int id;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Contributor{" +
                "login='" + login + '\'' +
                ", id=" + id +
                '}';
    }
}
