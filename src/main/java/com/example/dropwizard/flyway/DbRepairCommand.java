package com.example.dropwizard.flyway;

import com.googlecode.flyway.core.Flyway;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.ConfigurationStrategy;
import net.sourceforge.argparse4j.inf.Namespace;

public class DbRepairCommand<T extends Configuration> extends AbstractFlywayCommand<T> {
    public DbRepairCommand(ConfigurationStrategy<T> strategy, Class<T> configurationClass) {
        super("repair", "Not necessary for databases with DDL transaction support. Repairs the Flyway metadata table after a failed migration. User objects left behind must still be cleaned up manually.", strategy, configurationClass);
    }

    @Override
    public void run(Namespace namespace, Flyway flyway) throws Exception {
        flyway.repair();
    }
}
