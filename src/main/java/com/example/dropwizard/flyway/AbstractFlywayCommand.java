package com.example.dropwizard.flyway;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.api.FlywayException;
import com.yammer.dropwizard.cli.ConfiguredCommand;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.ConfigurationStrategy;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import net.sourceforge.argparse4j.inf.Namespace;

abstract class AbstractFlywayCommand<T extends Configuration> extends ConfiguredCommand<T> {
    private final ConfigurationStrategy<T> strategy;
    private final Class<T> configurationClass;

    AbstractFlywayCommand(String name,
                          String description,
                          ConfigurationStrategy<T> strategy,
                          Class<T> configurationClass) {
        super(name, description);
        this.strategy = strategy;
        this.configurationClass = configurationClass;
    }

    @Override
    protected Class<T> getConfigurationClass() {
        return configurationClass;
    }

    @Override
    protected void run(Bootstrap<T> bootstrap, Namespace namespace, T configuration) throws Exception {
        final DatabaseConfiguration dbConfig = strategy.getDatabaseConfiguration(configuration);
        dbConfig.setMaxSize(1);
        dbConfig.setMinSize(1);

        Flyway flyway;
        try {
            flyway = new Flyway();
            flyway.setDataSource(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
            run(namespace, flyway);
        } catch (FlywayException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    protected abstract void run(Namespace namespace, Flyway flyway) throws Exception;
}
