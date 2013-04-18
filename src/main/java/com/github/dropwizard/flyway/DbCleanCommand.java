package com.github.dropwizard.flyway;

import com.googlecode.flyway.core.Flyway;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.ConfigurationStrategy;
import net.sourceforge.argparse4j.inf.Namespace;

public class DbCleanCommand<T extends Configuration> extends AbstractFlywayCommand<T> {
    public DbCleanCommand(ConfigurationStrategy<T> strategy, Class<T> configurationClass) {
        super("clean", "Drops all objects (tables, views, procedures, triggers, ...) in the configured schemas.", strategy, configurationClass);
    }

    @Override
    protected void run(Namespace namespace, Flyway flyway) throws Exception {
        flyway.clean();
    }
}
