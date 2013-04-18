package com.example.dropwizard.flyway;

import com.googlecode.flyway.core.Flyway;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.ConfigurationStrategy;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

import java.util.SortedMap;

import static com.google.common.collect.Maps.newTreeMap;

public class DbCommand<T extends Configuration> extends AbstractFlywayCommand<T> {
    private static final String COMMAND_NAME_ATTR = "subCommand";
    private final SortedMap<String, AbstractFlywayCommand<T>> subCommands;

    public DbCommand(ConfigurationStrategy<T> strategy, Class<T> configurationClass) {
        super("db", "Run database migration tasks", strategy, configurationClass);
        this.subCommands = newTreeMap();
        addSubCommand(new DbMigrateCommand<T>(strategy, configurationClass));
        addSubCommand(new DbCleanCommand<T>(strategy, configurationClass));
        addSubCommand(new DbInitCommand<T>(strategy, configurationClass));
        addSubCommand(new DbValidateCommand<T>(strategy, configurationClass));
        addSubCommand(new DbInfoCommand<T>(strategy, configurationClass));
        addSubCommand(new DbRepairCommand<T>(strategy, configurationClass));
    }

    private void addSubCommand(AbstractFlywayCommand<T> subCommand) {
        subCommands.put(subCommand.getName(), subCommand);
    }

    @Override
    public void configure(Subparser subparser) {
        for (AbstractFlywayCommand<T> subCommand : subCommands.values()) {
            final Subparser cmdParser = subparser.addSubparsers()
                    .addParser(subCommand.getName())
                    .setDefault(COMMAND_NAME_ATTR, subCommand.getName())
                    .description(subCommand.getDescription());
            subCommand.configure(cmdParser);
        }
    }

    @Override
    public void run(Namespace namespace, Flyway flyway) throws Exception {
        subCommands.get(namespace.getString(COMMAND_NAME_ATTR)).run(namespace, flyway);
    }
}
