package services;

import dao.OwnerDao;
import models.Owner;

import java.util.List;

public class OwnerService
{
    private OwnerDao ownerDao = new OwnerDao();

    public OwnerService() {
    }

    public OwnerService(OwnerDao ownerDao) {
        this.ownerDao = ownerDao;
    }

    public Owner findOwner(int id) {
        return ownerDao.findById(id);
    }

    public void saveOwner(Owner user) { ownerDao.save(user); }

    public void updateOwner(Owner user) { ownerDao.update(user); }

    public void deleteOwner(Owner user) { ownerDao.delete(user); }

    public List<Owner> findAllOwners() {
        return ownerDao.findAll();
    }
}
