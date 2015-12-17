package ru.fizteh.fivt.students.ValeriyaSinevich.miniORM;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import org.junit.Test;
import ru.fizteh.fivt.students.ValeriyaSinevich.miniORM.DatabaseService;
import java.util.List;


public class DatabaseServiceTest {
    clazz instance = new clazz();
    DatabaseService<clazz> db = new DatabaseService<>(clazz.class);

    @Test
    public void testTable() throws Exception {
        db.createTable();
        db.insert(new clazz("Boris", "Pasternak", 203));
        db.insert(new clazz("John", "Tolkin", 561));
        db.insert(new clazz("Ennio", "Morricone", 560));
        db.insert(new clazz("Bogomil", "Rainov", 399));
        db.insert(new clazz("Bogomil", "Rainov", 398));
        db.insert(new clazz("John", "Foer", 112));
        db.delete("Ennio");

        List<clazz> instance = db.queryById("Bogomil");
        assertThat(instance.get(0).surname, is("Rainov"));

        db.dropTable();
    }

}

@DatabaseService.Table
class clazz {
    @DatabaseService.Column
    @DatabaseService.PrimaryKey
    String name;

    @DatabaseService.Column
    String surname;

    @DatabaseService.Column
    int score;

    public clazz() {
    }

    public clazz(String givenName, String givenSurname, int givenScore) {
        name = givenName;
        surname = givenSurname;
        score = givenScore;
    }
}
