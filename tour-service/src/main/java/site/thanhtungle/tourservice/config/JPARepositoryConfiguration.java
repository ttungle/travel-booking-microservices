package site.thanhtungle.tourservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import site.thanhtungle.tourservice.repository.impl.SearchRepositoryImpl;

@Configuration
@EnableJpaRepositories(
        basePackages = "site.thanhtungle.tourservice.repository",
        repositoryBaseClass = SearchRepositoryImpl.class
)
public class JPARepositoryConfiguration {
}
