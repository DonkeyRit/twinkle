package com.github.donkeyrit.javaapp.database.DatabaseModelProviders;

import com.github.donkeyrit.javaapp.database.DatabaseProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InjuryModelProvider {

    private DatabaseProvider provider;

    public InjuryModelProvider(DatabaseProvider provider) {
        this.provider = provider;
    }

    public Stream<String> getInjuries() {
        final String queryReturnCar = "SELECT * FROM injury";
        List<String> injuryNames = new ArrayList<>();

        try (ResultSet queryReturnCarSer = provider.select(queryReturnCar);) {
            while (queryReturnCarSer.next()) {
                injuryNames.add(queryReturnCarSer.getString("injuryName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return injuryNames.stream();
    }
}
