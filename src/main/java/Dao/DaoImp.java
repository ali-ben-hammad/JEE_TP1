package Dao;

import org.springframework.stereotype.Component;

@Component("dao")
public class DaoImp implements IDao{
    public String getDate() {
        //return the current date
        return new java.util.Date().toString();
    }
}
