import ru.itmo.persistence.model.Cat;
import ru.itmo.persistence.repo.CatRepository;
import ru.itmo.persistence.model.enums.CatColors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itmo.web.service.CatService;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class CatServiceTest
{
    @Mock
    private CatRepository catRepository;
    private final CatService catService;

    public CatServiceTest(){
        MockitoAnnotations.openMocks(this);
        this.catService = new CatService(catRepository);
    }

    @Test
    void findCat()
    {
        given(catRepository.findById(1)).willReturn(new Cat("Boris", Timestamp.valueOf("2000-11-11 11:11:11"), "Egyptian", CatColors.White));
        Assertions.assertEquals(catService.findCat(1).getName(), "Boris");
    }

    @Test
    void saveCat()
    {
        Cat cat = new Cat("Boris", Timestamp.valueOf("2000-11-11 11:11:11"), "Egyptian", CatColors.White);
        catService.saveCat(cat);
        verify(catRepository).save(cat);
    }

    @Test
    void deleteCat()
    {
        Cat cat = new Cat("Boris", Timestamp.valueOf("2000-11-11 11:11:11"), "Egyptian", CatColors.White);
        catService.deleteCat(cat);
        verify(catRepository).delete(cat);
    }

    @Test
    void findAllCats()
    {
        catService.findAllCats();
        verify(catRepository).findAll();
    }
}
