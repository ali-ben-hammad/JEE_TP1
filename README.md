# Activité Pratique N°1 - Inversion de contrôle et Injection des dépendances
## Partie 1
### Interface DAO 
```java

package Dao;

public interface IDao {
    public String getDate();
}

```
### IDao Implémentation
```java
package Dao;

import org.springframework.stereotype.Component;

@Component("dao")
public class DaoImp implements IDao{
    public String getDate() {
        //return the current date
        return new java.util.Date().toString();
    }
}
```
### Interface Metier
```java
package Metier;

public interface IMetier {
    public int calcul();
}
```
### IMetier implémentation
```java
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
```
### Injection des dépendances
#### Instantiation statique
```java
package Presentation;

import Dao.DaoImp;
import Metier.MetierImp;

public class Main {
    public static void main(String[] args) {
        // Injection des dépendances par instanciation statique
        DaoImp dao = new DaoImp();
        MetierImp metier = new MetierImp(dao);
       // metier.setDao(new DaoImp());
        String days = metier.calcul() + " jours restants";
        System.out.println("resultat avec injection statique = "
                + days);
    }
}
```
#### Instantiation dynamique
```java
package Presentation;

import Dao.IDao;
import Metier.IMetier;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/config.txt"));
            String daoClassName = scanner.nextLine();
            Class cDao = Class.forName(daoClassName);
            IDao dao = (IDao) cDao.newInstance();
            String metierClassName = scanner.nextLine();
            Class cMetier = Class.forName(metierClassName);
            IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);

            Method method = cMetier.getMethod("setDao", IDao.class);
            method.invoke(metier, dao);
            String days = metier.calcul() + " jours restants";
            System.out.println("resultat avec injection dynamique = " + days);
        } catch (FileNotFoundException | ClassNotFoundException | InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


} 
```
Fichier config.txt
```txt
Dao.DaoImp
Metier.MetierImp
```
#### Spring : Version XML
```java
package Presentation;

import Metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringXML {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        IMetier metier = (IMetier) context.getBean("metier");
        String days = metier.calcul() + " jours restants";
        System.out.println("resultat avec Spring XML = " + days);
        ((ClassPathXmlApplicationContext) context).close();
    }
}
```
#### Spring : Version Annotation
```java
package Presentation;

import Dao.IDao;
import Metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringAnnotation {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("Dao", "Metier");
        IMetier metier = context.getBean(IMetier.class);
        String days = metier.calcul() + " jours restants";
        System.out.println("resultat avec Spring Annotation  = " + days);

    }
}
```

