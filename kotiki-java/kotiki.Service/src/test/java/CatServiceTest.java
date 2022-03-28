import Models.Cat;
import dao.CatDao;
import enums.CatColors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.CatService;

import java.sql.Timestamp;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class CatServiceTest
{

    @Mock
    private CatDao catDao;
    private CatService catService;

    public CatServiceTest(){
        MockitoAnnotations.openMocks(this);
        this.catService = new CatService(catDao);
    }

    @Test
    void findCat()
    {
        given(catDao.findById(1)).willReturn(new Cat("Boris", Timestamp.valueOf("2000-11-11 11:11:11"), "Egyptian", CatColors.White));
        Assertions.assertEquals(catService.findCat(1).getName(), "Boris");
    }

    @Test
    void saveCat()
    {
        Cat cat = new Cat("Boris", Timestamp.valueOf("2000-11-11 11:11:11"), "Egyptian", CatColors.White);
        catService.saveCat(cat);
        verify(catDao).save(cat);
    }

    @Test
    void updateCat()
    {
        Cat cat = new Cat("Boris", Timestamp.valueOf("2000-11-11 11:11:11"), "Egyptian", CatColors.White);
        catService.updateCat(cat);
        verify(catDao).update(cat);
    }

    @Test
    void deleteCat()
    {
        Cat cat = new Cat("Boris", Timestamp.valueOf("2000-11-11 11:11:11"), "Egyptian", CatColors.White);
        catService.deleteCat(cat);
        verify(catDao).delete(cat);
    }

    @Test
    void findAllCats()
    {
        catService.findAllCats();
        verify(catDao).findAll();
    }
}