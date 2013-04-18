package com.github.dropwizard.flyway;

import com.googlecode.flyway.core.Flyway;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.ConfigurationStrategy;
import net.sourceforge.argparse4j.inf.Namespace;

public class DbInitCommand<T extends Configuration> extends AbstractFlywayCommand<T> {
    public DbInitCommand(ConfigurationStrategy<T> strategy, Class<T> configurationClass) {
        super("init", "Creates and initializes the metadata table in the schema.", strategy, configurationClass);
    }

    @Override
    protected void run(Namespace namespace, Flyway flyway) throws Exception {
        flyway.init();
    }
}
