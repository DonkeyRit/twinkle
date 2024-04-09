package com.github.donkeyrit.twinkle.dal.jooq.listeners;

import org.jooq.ExecuteListener;
import org.jooq.ExecuteContext;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class CustomLoggerListener implements ExecuteListener {
    
	private static final Logger logger = LoggerFactory.getLogger(CustomLoggerListener.class);

    @Override
    public void executeStart(ExecuteContext ctx) {
        // Log the SQL statement when execution starts
        String sql = ctx.sql();
        if (sql != null) {
            logger.info("Executing SQL: {}", sql);
        }
    }

    @Override
    public void executeEnd(ExecuteContext ctx) {
        // Log the execution time
        logger.info("Execution finished. Query executed: {}", ctx.query());
    }

	@Override
    public void exception(ExecuteContext ctx) {
        // Log the exception if the execution resulted in an error
        if (ctx.exception() != null) {
            logger.error("Execution resulted in an exception: ", ctx.exception());
        }
    }
}