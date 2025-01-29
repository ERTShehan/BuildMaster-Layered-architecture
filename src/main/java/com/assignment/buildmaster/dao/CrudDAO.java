package com.assignment.buildmaster.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO <T> extends SuperDAO{
    public boolean save(T dto) throws SQLException;
    public boolean update(T dto) throws SQLException;
    public String getNextId() throws SQLException;
    public T findById(String selectedId) throws SQLException;
    public ArrayList<String> getAllIds() throws SQLException;
    public boolean delete(String ID) throws SQLException;
    public List<T> getAll() throws SQLException;
    public int getCount() throws SQLException;

    public String getName(String Id) throws SQLException;
    public String getInfo(String Id) throws SQLException;

    public String getUnit(String Id) throws SQLException;

    public ArrayList<String> findAllIds() throws SQLException;
    public String findNameById(String Id) throws SQLException;
    public ArrayList<String> getAllIdsBy(String Id) throws SQLException;

//    public T get (T id);
}
