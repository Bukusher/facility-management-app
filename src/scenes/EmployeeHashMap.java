package scenes;

import sample.*;

import java.util.HashMap;

class EmployeeHashMap {

    private HashMap <String, Account> employeeHashMap = new HashMap<>();

    Account getEmployee(String key){
        return employeeHashMap.get(key);
    }

    void putEmployee (String key, Account employee){
        employeeHashMap.put(key, employee);
    }


}
