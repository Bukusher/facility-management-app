package scenes;

import sample.Account;
import sample.Employee;

import java.util.HashMap;

public class EmployeeHashMap {

    private HashMap <String, Account> employeeHashMap = new HashMap<>(5);

    public Account getEmployee(String key){
        return employeeHashMap.get(key);
    }

    public void putEmployee (String key, Account employee){
        employeeHashMap.put(key, employee);
    }


}
