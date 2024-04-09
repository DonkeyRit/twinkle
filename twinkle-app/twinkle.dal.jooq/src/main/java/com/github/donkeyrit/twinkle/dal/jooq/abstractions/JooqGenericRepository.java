package com.github.donkeyrit.twinkle.dal.jooq.abstractions;

import com.github.donkeyrit.twinkle.dal.common.repositories.GenericRepository;
import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

import java.util.stream.Stream;

import org.jooq.TableRecord;
import org.jooq.Table;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public abstract class JooqGenericRepository<T extends Identifiable, R extends TableRecord<R>> implements GenericRepository<T> {

	private static final Logger logger = LoggerFactory.getLogger(JooqGenericRepository.class);

	protected DSLContext context;
	protected Table<R> table;

	public JooqGenericRepository(DSLContext dslContext, Table<R> table) {
		this.context = dslContext;
		this.table = table;
	}

	@Override
	public T findById(int id) {
		logger.debug("Finding by id - %d", id);
		R record = this.context
			.selectFrom(this.table)
			.where(DSL.field("id").eq(id))
			.fetchOne();
		
		return record != null ? mapRecordToEntity(record) : null;
	}

	@Override
	public Stream<T> findAll() {
		logger.debug("Finding all records");
		return this.context
			.selectFrom(this.table)
			.fetchStream()
			.map(this::mapRecordToEntity);
	}

	@Override
	public boolean save(T entity) {
		logger.debug("Save entity with id - %d", entity.getId());
		R record = entityToRecord(entity);
		this.context
			.insertInto(this.table)
			.set(record)
			.execute();
		return true;
	}

	@Override
	public boolean delete(T entity) {
		logger.debug("Delete entity with id - %d", entity.getId());
		this.context
			.deleteFrom(this.table)
			.where(DSL.field("id").eq(entity.getId()))
			.execute();
		return true;
	}

	@Override
	public boolean update(T entity) {
		logger.debug("Update entity with id - %d", entity.getId());
		R record = entityToRecord(entity); // Assumes existence of this method
		this.context
			.update(this.table)
			.set(record)
			.where(DSL.field("id").eq(entity.getId()))
			.execute();
		return true;
	}

	protected abstract T mapRecordToEntity(R record);

	protected abstract R entityToRecord(T entity);
}
