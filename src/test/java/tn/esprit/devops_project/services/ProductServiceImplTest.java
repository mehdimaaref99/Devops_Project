package tn.esprit.devops_project.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void addProduct() {
        Long stockId = 1L;
        Stock stock = new Stock();
        stock.setIdStock(stockId);
        Product product = new Product();
        product.setIdProduct(1L);
        product.setStock(stock);

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.addProduct(product, stockId);

        assertNotNull(savedProduct);
        assertEquals(stock, savedProduct.getStock());
        verify(stockRepository).findById(stockId);
        verify(productRepository).save(product);
    }

    @Test
    void retrieveProduct() {
        Long productId = 1L;
        Product product = new Product();
        product.setIdProduct(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product foundProduct = productService.retrieveProduct(productId);

        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getIdProduct());
        verify(productRepository).findById(productId);
    }

    @Test
    void retreiveAllProduct() {
        List<Product> productList = Arrays.asList(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> products = productService.retreiveAllProduct();

        assertEquals(2, products.size());
        verify(productRepository).findAll();
    }

    @Test
    void retrieveProductByCategory() {
        ProductCategory category = ProductCategory.ELECTRONICS;
        List<Product> productList = Arrays.asList(new Product(), new Product());
        when(productRepository.findByCategory(category)).thenReturn(productList);

        List<Product> productsByCategory = productService.retrieveProductByCategory(category);

        assertEquals(2, productsByCategory.size());
        verify(productRepository).findByCategory(category);
    }

    @Test
    void deleteProduct() {
        Long productId = 1L;
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository).deleteById(productId);
    }

    @Test
    void retreiveProductStock() {
        Long stockId = 1L;
        List<Product> productList = Arrays.asList(new Product(), new Product());
        when(productRepository.findByStockIdStock(stockId)).thenReturn(productList);

        List<Product> productsInStock = productService.retreiveProductStock(stockId);

        assertEquals(2, productsInStock.size());
        verify(productRepository).findByStockIdStock(stockId);
    }
}
