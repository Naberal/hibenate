package ProjectManagementSystem.controller;

import ProjectManagementSystem.dao.DAO;

import java.util.Arrays;


public class Controller {
    private DAO dao;
    private Save save = new Save();
    private Update update = new Update();

    public void getAll(Object obj) {
        dao = (DAO) obj;
        System.out.println(Arrays.toString(dao.getAll().toArray()));
    }

    public void save(Object obj) {
        dao = (DAO) obj;
        save.saveController(dao);
    }

    public void update(Object obj) {
        dao = (DAO) obj;
        update.updateController(dao);
    }

    public void delete(int id, Object obj) {
        dao = (DAO) obj;
         if (dao.getCount() <= id) {
            System.err.println("wrong id");
        } else {
            dao.delete(id);
        }

    }

    public void findByID(int id, Object obj) {
        dao = (DAO) obj;
        if (dao.getCount() < id) {
            System.err.println("wrong id");
        } else {
            try {
                System.out.println(dao.findByID(id).toString());
            }catch (NullPointerException e){
                System.err.println("wrong id");
            }
        }
    }

    public void findByName(String name, Object obj) {
        dao = (DAO) obj;
        try {
            System.out.println(dao.findByName(name).toString());
        }catch (Exception e){
            System.err.println("wrong name");
        }

    }
}
