package com.technova.shopverseapi.batch;

import com.technova.shopverseapi.batch.model.CategoryCsv;
import com.technova.shopverseapi.batch.model.ProductCsv;
import com.technova.shopverseapi.model.Category;
import com.technova.shopverseapi.model.Product;
import com.technova.shopverseapi.repository.CategoryRepository;
import com.technova.shopverseapi.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public BatchConfig(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Bean
    public FlatFileItemReader<CategoryCsv> categoryReader() {
        return new FlatFileItemReaderBuilder<CategoryCsv>()
                .name("categoryReader")
                .resource(new ClassPathResource("data/categories.csv"))
                .linesToSkip(1)
                .delimited()
                .names("id", "name", "description")
                .targetType(CategoryCsv.class)
                .build();
    }

    @Bean
    public FlatFileItemReader<ProductCsv> productReader() {
        return new FlatFileItemReaderBuilder<ProductCsv>()
                .name("productReader")
                .resource(new ClassPathResource("data/products.csv"))
                .linesToSkip(1)
                .delimited()
                .names("id", "name", "description", "price", "category_id")
                .targetType(ProductCsv.class)
                .build();
    }

    @Bean
    public ItemProcessor<CategoryCsv, Category> categoryProcessor() {
        return csv -> new Category(csv.getId(), csv.getName(), csv.getDescription());
    }

    @Bean
    public ItemProcessor<ProductCsv, Product> productProcessor() {
        return csv -> {
            Category category = categoryRepository.findById(csv.getCategory_id())
                    .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada: " + csv.getCategory_id()));
            return new Product(csv.getId(), csv.getName(), csv.getDescription(), csv.getPrice(), category);
        };
    }

    @Bean
    public ItemWriter<Category> categoryWriter() {
        return categoryRepository::saveAll;
    }

    @Bean
    public ItemWriter<Product> productWriter() {
        return productRepository::saveAll;
    }

    @Bean
    public Step importCategoriesStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("importCategoriesStep", jobRepository)
                .<CategoryCsv, Category>chunk(10, transactionManager)
                .reader(categoryReader())
                .processor(categoryProcessor())
                .writer(categoryWriter())
                .build();
    }

    @Bean
    public Step importProductsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("importProductsStep", jobRepository)
                .<ProductCsv, Product>chunk(10, transactionManager)
                .reader(productReader())
                .processor(productProcessor())
                .writer(productWriter())
                .build();
    }

    @Bean
    public Job importCatalogJob(JobRepository jobRepository,
                                Step importCategoriesStep,
                                Step importProductsStep) {
        return new JobBuilder("importCatalogJob", jobRepository)
                .start(importCategoriesStep)
                .next(importProductsStep)
                .build();
    }
}
