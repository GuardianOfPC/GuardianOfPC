import org.junit.jupiter.api.Test;
import ru.itmo.persistence.model.Cat;
import ru.itmo.persistence.model.enums.CatColors;
import ru.itmo.web.dto.CatDto;
import ru.itmo.web.util.DtoMappingUtil;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DtoTest {
    private final DtoMappingUtil mappingUtil = new DtoMappingUtil();

    @Test
    public void whenConvertCatEntityToCatDto_thenCorrect() {
        Cat cat = new Cat();
        cat.setId(1);
        cat.setName("Boris");
        cat.setDateOfBirth(Timestamp.valueOf("2000-11-11 11:11:11"));
        cat.setBreed("Red");
        cat.setColor(CatColors.Black);

        CatDto catDto = mappingUtil.mapToCatDto(cat);
        assertEquals(cat.getId(), catDto.getId());
        assertEquals(cat.getName(), catDto.getName());
        assertEquals(cat.getDateOfBirth(), catDto.getDateOfBirth());
        assertEquals(cat.getBreed(), catDto.getBreed());
        assertEquals(cat.getColor(), catDto.getColor());
    }

    @Test
    public void whenConvertCatDtoToCatEntity_thenCorrect() {
        CatDto catDto = new CatDto();
        catDto.setId(1);
        catDto.setName("Boris");
        catDto.setDateOfBirth(Timestamp.valueOf("2000-11-11 11:11:11"));
        catDto.setBreed("Red");
        catDto.setColor(CatColors.Black);

        Cat cat = mappingUtil.mapToCatEntity(catDto);
        assertEquals(cat.getId(), catDto.getId());
        assertEquals(cat.getName(), catDto.getName());
        assertEquals(cat.getDateOfBirth(), catDto.getDateOfBirth());
        assertEquals(cat.getBreed(), catDto.getBreed());
        assertEquals(cat.getColor(), catDto.getColor());
    }
}
