package com.github.dropwizard.flyway;

import com.googlecode.flyway.core.Flyway;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.ConfigurationStrategy;
import net.sourceforge.argparse4j.inf.Namespace;

public class DbValidateCommand<T extends Configuration> extends AbstractFlywayCommand<T> {
    public DbValidateCommand(ConfigurationStrategy<T> strategy, Class<T> configurationClass) {
        super("validate", "Validates the applied migrations against the ones available on the classpath. The build fails if differences in migration names, types or checksums are found.", strategy, configurationClass);
    }

    @Override
    protected void run(Namespace namespace, Flyway flyway) throws Exception {
        flyway.validate();
    }
}
