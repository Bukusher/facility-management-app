package scenes;

public class User {

    private String mail;
    private String name;
    private String surname;
    private String account;

    public User(String mail, String name, String surname, String account) {
        this.mail = mail;
        this.name = name;
        this.surname = surname;
        this.account = account;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
