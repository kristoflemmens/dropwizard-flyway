package com.example.dropwizard.flyway;

import com.googlecode.flyway.core.Flyway;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.ConfigurationStrategy;
import net.sourceforge.argparse4j.inf.Namespace;

import static com.googlecode.flyway.core.info.MigrationInfoDumper.dumpToAsciiTable;

public class DbInfoCommand<T extends Configuration> extends AbstractFlywayCommand<T> {
    public DbInfoCommand(ConfigurationStrategy<T> strategy, Class<T> configurationClass) {
        super("info", "Prints the details and status information about all the migrations.", strategy, configurationClass);
    }

    @Override
    public void run(Namespace namespace, Flyway flyway) throws Exception {
        System.out.println(dumpToAsciiTable(flyway.info().all()));
    }
}
