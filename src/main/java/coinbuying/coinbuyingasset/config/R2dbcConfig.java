package coinbuying.coinbuyingasset.config;

import coinbuying.coinbuyingasset.model.UserAssetFactory;
import coinbuying.coinbuyingasset.repository.UserAssetRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class R2dbcConfig {

    private final UserAssetFactory userAssetFactory;

    @Autowired
    public R2dbcConfig(UserAssetFactory userAssetFactory) {
        this.userAssetFactory = userAssetFactory;
    }

    @Bean
    public ConnectionFactoryInitializer dbInit(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer init = new ConnectionFactoryInitializer();
        init.setConnectionFactory(connectionFactory);
        init.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        return init;
    }

    @Bean
    public CommandLineRunner dataInit(UserAssetRepository userAssetRepository) {
        return (args) -> {
            userAssetRepository.saveAll(
                    userAssetFactory.setupListBuilder()
            ).blockLast();
        };
    }


}
