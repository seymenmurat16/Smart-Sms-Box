package com.example.murat.smartsmsbox;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Murat on 30.05.2018.
 */

@Dao
public interface PersonDao {

    @Insert
    void insertPerson(Person person);

    @Insert
    void insertPeople(List<Person> people);

    @Delete
    void deletePerson(Person person);

    @Update
    void updatePerson(Person person);

    @Query("SELECT * FROM person ORDER BY person_name ASC")
    List<Person> getAllPeople();

    @Query("DELETE FROM person")
    void deleteall();

    @Query("SELECT * FROM person WHERE uid = :personId")
    Person getPerson(int personId);
}
