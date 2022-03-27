package services;

import dao.CatDao;
import Models.Cat;

import java.util.List;

public class CatService
{
    private CatDao catDao = new CatDao();

    public CatService() {
    }

    public CatService(CatDao catDao) {
        this.catDao = catDao;
    }

    public Cat findCat(int id) {
        return catDao.findById(id);
    }

    public void saveCat(Cat cat) {
        catDao.save(cat);
    }

    public void updateCat(Cat cat) {
        catDao.update(cat);
    }

    public void deleteCat(Cat cat) {
        catDao.delete(cat);
    }

    public List<Cat> findAllCats() {
        return catDao.findAll();
    }
}
