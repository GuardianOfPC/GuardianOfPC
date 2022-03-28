import Models.Owner;
import dao.OwnerDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.OwnerService;
import java.sql.Timestamp;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

public class OwnerServiceTest
{
    @Mock
    private OwnerDao ownerDao;
    private OwnerService ownerService;

    public OwnerServiceTest(){
        MockitoAnnotations.openMocks(this);
        this.ownerService = new OwnerService(ownerDao);
    }

    @Test
    void findOwner()
    {
        given(ownerDao.findById(1)).willReturn(new Owner("Alexey", Timestamp.valueOf("2000-11-11 11:11:11")));
        Assertions.assertEquals(ownerService.findOwner(1).getName(), "Alexey");
    }

    @Test
    void saveOwner()
    {
        Owner owner = new Owner("Alexey", Timestamp.valueOf("2000-11-11 11:11:11"));
        ownerService.saveOwner(owner);
        verify(ownerDao).save(owner);
    }

    @Test
    void updateOwner()
    {
        Owner owner = new Owner("Aboba", Timestamp.valueOf("2000-11-11 11:11:11"));
        ownerService.updateOwner(owner);
        verify(ownerDao).update(owner);
    }

    @Test
    void deleteOwner()
    {
        Owner owner = new Owner("Aboba", Timestamp.valueOf("2000-11-11 11:11:11"));
        ownerService.deleteOwner(owner);
        verify(ownerDao).delete(owner);
    }

    @Test
    void findAllOwners()
    {
        ownerService.findAllOwners();
        verify(ownerDao).findAll();
    }
}
