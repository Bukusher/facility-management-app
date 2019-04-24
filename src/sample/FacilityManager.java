package sample;

public class FacilityManager extends Account {

    public FacilityManager(String name, String surname, String email, String password) {
        super(name, surname, email, password, AccountType.MANAGER);
    }
}
