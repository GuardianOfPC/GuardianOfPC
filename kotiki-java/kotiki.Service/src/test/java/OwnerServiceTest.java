import ru.itmo.persistence.model.Owner;
import ru.itmo.persistence.repo.OwnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itmo.web.service.OwnerService;

import java.sql.Timestamp;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

public class OwnerServiceTest
{
    @Mock
    private OwnerRepository ownerRepository;
    private OwnerService ownerService;

    public OwnerServiceTest(){
        MockitoAnnotations.openMocks(this);
        this.ownerService = new OwnerService(ownerRepository);
    }

    @Test
    void findOwner()
    {
        given(ownerRepository.findById(1)).willReturn(new Owner("Alexey", Timestamp.valueOf("2000-11-11 11:11:11")));
        Assertions.assertEquals(ownerService.findOwner(1).getName(), "Alexey");
    }

    @Test
    void saveOwner()
    {
        Owner owner = new Owner("Alexey", Timestamp.valueOf("2000-11-11 11:11:11"));
        ownerService.saveOwner(owner);
        verify(ownerRepository).save(owner);
    }

    @Test
    void deleteOwner()
    {
        Owner owner = new Owner("Aboba", Timestamp.valueOf("2000-11-11 11:11:11"));
        ownerService.deleteOwner(owner);
        verify(ownerRepository).delete(owner);
    }

    @Test
    void findAllOwners()
    {
        ownerService.findAllOwners();
        verify(ownerRepository).findAll();
    }
}
