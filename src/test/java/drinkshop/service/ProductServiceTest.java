package drinkshop.service;

import drinkshop.domain.CategorieBautura;
import drinkshop.domain.Product;
import drinkshop.domain.TipBautura;
import drinkshop.repository.Repository;
import drinkshop.service.validator.ProductValidator;
import drinkshop.service.validator.ValidationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceBvaTest {
    private Repository<Integer, Product> createMockRepo() {
        return new Repository<>() {
            public Product save(Product entity) { return entity; }
            public Product findOne(Integer id) { return null; }
            public List<Product> findAll() { return null; }
            public Product delete(Integer id) { return null; }
            public Product update(Product entity) { return null; }
        };
    }

    // --- TESTE BVA PENTRU PARAMETRUL: ID ---

    @Test
    @DisplayName("TC1 Valid: ID-ul este exact la limita minimă admisă (1)")
    @Tag("BVA_ID")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void addProduct_IdAtLowerBoundary_Success() {
        // Arrange
        ProductService service = new ProductService(createMockRepo());
        Product validProduct = new Product(1, "Limonada", 15.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            new ProductValidator().validate(validProduct);
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
        ProductService service = new ProductService(createMockRepo());
        Product validProduct = new Product(2, "Limonada", 15.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            new ProductValidator().validate(validProduct);
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
        ProductService service = new ProductService(createMockRepo());
        Product invalidProduct = new Product(0, "Limonada", 15.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            new ProductValidator().validate(invalidProduct);
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
        ProductService service = new ProductService(createMockRepo());
        Product invalidProduct = new Product(-1, "Limonada", 15.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            new ProductValidator().validate(invalidProduct);
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
        ProductService service = new ProductService(createMockRepo());
        Product validProduct = new Product(10, "Limonada", 0.01, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            new ProductValidator().validate(validProduct);
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
        ProductService service = new ProductService(createMockRepo());
        Product validProduct = new Product(10, "Limonada", 0.02, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            new ProductValidator().validate(validProduct);
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
        ProductService service = new ProductService(createMockRepo());
        Product invalidProduct = new Product(10, "Limonada", 0.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            new ProductValidator().validate(invalidProduct);
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
        ProductService service = new ProductService(createMockRepo());
        Product invalidProduct = new Product(10, "Limonada", -1.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);

        // Act
        Executable act = () -> {
            new ProductValidator().validate(invalidProduct);
            service.addProduct(invalidProduct);
        };

        // Assert
        ValidationException exception = assertThrows(ValidationException.class, act);
        assertTrue(exception.getMessage().contains("Pret invalid!"));
    }
}