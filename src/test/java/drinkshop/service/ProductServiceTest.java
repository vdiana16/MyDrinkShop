package drinkshop.service;

import drinkshop.domain.CategorieBautura;
import drinkshop.domain.Product;
import drinkshop.domain.TipBautura;
import drinkshop.repository.Repository;
import drinkshop.service.validator.ProductValidator;
import drinkshop.service.validator.ValidationException;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private ProductService service;
    private ProductValidator validator;

    private Repository<Integer, Product> createMockRepo() {
        return new Repository<>() {
            public Product save(Product entity) { return entity; }
            public Product findOne(Integer id) { return null; }
            public List<Product> findAll() { return null; }
            public Product delete(Integer id) { return null; }
            public Product update(Product entity) { return null; }
        };
    }

    @BeforeEach
    void setUp() {
        // Se executa inaintea fiecarui test (pregateste mediul)
        service = new ProductService(createMockRepo());
        validator = new ProductValidator();
    }

    @AfterEach
    void tearDown() {
        // Curata mediul dupa fiecare test
        service = null;
        validator = null;
    }

    // --- TESTE BVA PENTRU PARAMETRUL: ID ---

    @Test
    @DisplayName("TC1 Valid: ID-ul este exact la limita minimă admisă (1)")
    @Tag("BVA_ID")
    @Timeout(value = 1, unit = TimeUnit.SECONDS) // Adnotare diferita 1, 2, 3
    void addProduct_IdAtLowerBoundary_Success() {
        // Arrange
        Product validProduct = new Product(1, "Limonada", 15.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(validProduct);
            service.addProduct(validProduct);
        };

        // Assert
        assertDoesNotThrow(act, "Produsul cu id=1 trebuie să fie salvat cu succes.");
    }

    @Test
    @DisplayName("TC2 Valid: ID-ul este imediat peste limita admisă (2)")
    @Tag("BVA_ID")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void addProduct_IdJustAboveLowerBoundary_Success() {
        // Arrange
        Product validProduct = new Product(2, "Limonada", 15.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(validProduct);
            service.addProduct(validProduct);
        };

        // Assert
        assertDoesNotThrow(act, "Produsul cu id=2 trebuie să fie salvat cu succes.");
    }

    @Test
    @DisplayName("TC3 Invalid: ID-ul este imediat sub limita admisă (0)")
    @Tag("BVA_ID")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void addProduct_IdJustBelowLowerBoundary_ThrowsException() {
        // Arrange
        Product invalidProduct = new Product(0, "Limonada", 15.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(invalidProduct);
            service.addProduct(invalidProduct);
        };

        // Assert
        ValidationException exception = assertThrows(ValidationException.class, act);
        assertTrue(exception.getMessage().contains("ID invalid!"));
    }

    @Test
    @DisplayName("TC4 Invalid: ID-ul este o valoare negativa extremă (-1)")
    @Tag("BVA_ID")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void addProduct_IdNegative_ThrowsException() {
        // Arrange
        Product invalidProduct = new Product(-1, "Limonada", 15.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(invalidProduct);
            service.addProduct(invalidProduct);
        };

        // Assert
        ValidationException exception = assertThrows(ValidationException.class, act);
        assertTrue(exception.getMessage().contains("ID invalid!"));
    }


    // --- TESTE BVA PENTRU PARAMETRUL: PRET ---

    @Test
    @DisplayName("TC5 Valid: Pretul este prima valoare pozitiva validă (0.01)")
    @Tag("BVA_Pret")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void addProduct_PriceAtLowerBoundary_Success() {
        // Arrange
        Product validProduct = new Product(10, "Limonada", 0.01, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(validProduct);
            service.addProduct(validProduct);
        };

        // Assert
        assertDoesNotThrow(act);
    }

    @Test
    @DisplayName("TC6 Valid: Pretul este imediat peste prima valoare validă (0.02)")
    @Tag("BVA_Pret")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void addProduct_PriceJustAboveLowerBoundary_Success() {
        // Arrange
        Product validProduct = new Product(10, "Limonada", 0.02, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(validProduct);
            service.addProduct(validProduct);
        };

        // Assert
        assertDoesNotThrow(act);
    }

    @Test
    @DisplayName("TC7 Invalid: Pretul este exact pe limita invalida (0.0)")
    @Tag("BVA_Pret")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void addProduct_PriceZero_ThrowsException() {
        // Arrange
        Product invalidProduct = new Product(10, "Limonada", 0.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(invalidProduct);
            service.addProduct(invalidProduct);
        };

        // Assert
        ValidationException exception = assertThrows(ValidationException.class, act);
        assertTrue(exception.getMessage().contains("Pret invalid!"));
    }

    @Test
    @DisplayName("TC8 Invalid: Pretul este negativ (-1.0)")
    @Tag("BVA_Pret")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void addProduct_PriceNegative_ThrowsException() {
        // Arrange
        Product invalidProduct = new Product(10, "Limonada", -1.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(invalidProduct);
            service.addProduct(invalidProduct);
        };

        // Assert
        ValidationException exception = assertThrows(ValidationException.class, act);
        assertTrue(exception.getMessage().contains("Pret invalid!"));
    }


    // --- TESTE BVA PENTRU PARAMETRUL: NUME ---

    @Test
    @DisplayName("TC9 Valid: Numele are lungimea la limita minimă admisă (1 caracter)")
    @Tag("BVA_Nume")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void addProduct_NameLengthAtLowerBoundary_Success() {
        // Arrange
        Product validProduct = new Product(10, "A", 15.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(validProduct);
            service.addProduct(validProduct);
        };

        // Assert
        assertDoesNotThrow(act, "Produsul cu numele de un caracter trebuie salvat cu succes.");
    }

    @Test
    @DisplayName("TC10 Valid: Numele are lungimea imediat peste limita admisă (2 caractere)")
    @Tag("BVA_Nume")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void addProduct_NameLengthJustAboveLowerBoundary_Success() {
        // Arrange
        Product validProduct = new Product(10, "Ab", 15.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(validProduct);
            service.addProduct(validProduct);
        };

        // Assert
        assertDoesNotThrow(act, "Produsul cu numele de doua caractere trebuie salvat cu succes.");
    }

    @Test
    @DisplayName("TC11 Invalid: Numele este un string gol (lungime 0)")
    @Tag("BVA_Nume")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void addProduct_NameEmpty_ThrowsException() {
        // Arrange
        Product invalidProduct = new Product(10, "", 15.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(invalidProduct);
            service.addProduct(invalidProduct);
        };

        // Assert
        ValidationException exception = assertThrows(ValidationException.class, act);
        assertTrue(exception.getMessage().contains("Numele nu poate fi gol!"));
    }

    @Test
    @DisplayName("TC12 Invalid: Numele este null")
    @Tag("BVA_Nume")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void addProduct_NameNull_ThrowsException() {
        // Arrange
        Product invalidProduct = new Product(10, null, 15.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(invalidProduct);
            service.addProduct(invalidProduct);
        };

        // Assert
        ValidationException exception = assertThrows(ValidationException.class, act);
        assertTrue(exception.getMessage().contains("Numele nu poate fi gol!"));
    }

    // --- TESTE ECP PENTRU PARAMETRUL: PRET ---
    @Test
    @DisplayName("TC1_ECP Valid: Pretul este in clasa de echivalenta valida (> 0)")
    @Tag("ECP_Pret")
    void addProduct_ECP_ValidPrice_Success() {
        // Arrange
        Product validProduct = new Product(10, "Limonada", 5.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(validProduct);
            service.addProduct(validProduct);
        };

        // Assert
        assertDoesNotThrow(act, "Produsul cu pretul 5.0 trebuie să fie salvat cu succes.");
    }

    @Test
    @DisplayName("TC2_ECP Invalid: Pretul este in clasa de echivalenta invalida (<= 0)")
    @Tag("ECP_Pret")
    void addProduct_ECP_InvalidPrice_ThrowsException() {
        // Arrange
        Product invalidProduct = new Product(10, "Limonada", -5.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        //Act
        Executable act = () -> {
            validator.validate(invalidProduct);
            service.addProduct(invalidProduct);
        };

        // Assert
        ValidationException exception = assertThrows(ValidationException.class, act);
        assertTrue(exception.getMessage().contains("Pret invalid!") || exception.getMessage().toLowerCase().contains("pret"),
                "Trebuie aruncată o excepție pentru prețul negativ.");
    }

    // --- TESTE ECP PENTRU PARAMETRUL: NUME ---

    @Test
    @DisplayName("TC3_ECP Valid: Numele este în clasa de echivalență validă (String nevid)")
    @Tag("ECP_Nume")
    void addProduct_ECP_ValidName_Success() {
        // Arrange
        Product validProduct = new Product(10, "Latte Machiato", 18.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(validProduct);
            service.addProduct(validProduct);
        };

        // Assert
        assertDoesNotThrow(act, "Produsul cu nume valid trebuie salvat cu succes.");
    }

    @Test
    @DisplayName("TC4_ECP Invalid: Numele este în clasa de echivalență invalidă (String gol)")
    @Tag("ECP_Nume")
    void addProduct_ECP_EmptyName_ThrowsException() {
        // Arrange
        Product invalidProduct = new Product(10, "", 18.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            validator.validate(invalidProduct);
            service.addProduct(invalidProduct);
        };

        // Assert
        ValidationException exception = assertThrows(ValidationException.class, act);
        // Verificăm dacă mesajul conține "nume" (aici poți ajusta în funcție de eroarea exactă pe care o dă validatorul vostru)
        assertTrue(exception.getMessage().toLowerCase().contains("nume"),
                "Sistemul trebuie să arunce eroare pentru nume gol.");
    }
}
