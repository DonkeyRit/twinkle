package com.github.donkeyrit.twinkle.dal.jooq.abstractions;

import com.github.donkeyrit.twinkle.dal.common.repositories.GenericRepository;
import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

import java.util.stream.Stream;

import javax.sql.DataSource;

import org.jooq.TableRecord;
import org.jooq.Table;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public abstract class JooqGenericRepository<T extends Identifiable, R extends TableRecord<R>> implements GenericRepository<T> {

	protected DSLContext context;
	protected Table<R> table;

	public JooqGenericRepository(DataSource dataSource, Table<R> table) {
		this.context = DSL.using(dataSource, SQLDialect.POSTGRES);
		this.table = table;
	}

	@Override
	public T findById(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findById'");
	}

	@Override
	public Stream<T> findAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findAll'");
	}

	@Override
	public boolean save(T o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'save'");
	}

	@Override
	public boolean delete(T o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}

	@Override
	public boolean update(T o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}
	
}
