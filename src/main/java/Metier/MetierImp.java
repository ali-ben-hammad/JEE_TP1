package Metier;

import Dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("metier")
public class MetierImp implements IMetier{

  //  @Autowired
    private IDao dao;

    public MetierImp(IDao dao) {
        this.dao = dao;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
    public int calcul() {
        String date = dao.getDate();
        // return the days remaining to the end of the month
        return 30 - Integer.parseInt(date.substring(8, 10));
    }
}
