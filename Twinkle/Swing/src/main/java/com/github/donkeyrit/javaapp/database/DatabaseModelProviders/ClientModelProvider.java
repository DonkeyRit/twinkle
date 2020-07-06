package com.github.donkeyrit.javaapp.database.DatabaseModelProviders;

import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.Client;
import com.github.donkeyrit.javaapp.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ClientModelProvider {
    private DatabaseProvider provider;

    public ClientModelProvider(DatabaseProvider provider) {
        this.provider = provider;
    }

    public Stream<Client> getClients() {
        final String query = "SELECT * FROM client";
        List<Client> clientList = new ArrayList<>();
        Client.ClientBuilder clientBuilder = new Client.ClientBuilder();

        try (ResultSet clientSet = provider.select(query)) {

            while (clientSet.next()) {

                clientBuilder.setFirstName(clientSet.getString("firstName"))
                        .setSecondName(clientSet.getString("secondName"))
                        .setMiddleName(clientSet.getString("Patronimic"))
                        .setAddress(clientSet.getString("address"))
                        .setPhoneNumber(clientSet.getString("phoneNumber"))
                        .setIdUser(clientSet.getInt("idUser"));

                clientList.add(clientBuilder.create());
                clientBuilder.flush();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return clientList.stream();
    }

    public Client getClientByUserId(User user) {
        String queryUser = String.format("SELECT idUser FROM user WHERE login = '%s'", user.getLogin());
        Client result = null;

        try (ResultSet clientSet = provider.select(queryUser)) {
            while (clientSet.next()) {
                final int userId = clientSet.getInt("idUser");
                result = getClients().filter(client -> client.getIdUser() == userId).findFirst().orElse(null);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public void createClient(Client client, User user) {
        String queryUser = String.format("SELECT idUser FROM user WHERE login = '%s'", user.getLogin());
        int userId = -1;

        try (ResultSet clientSet = provider.select(queryUser)) {
            while (clientSet.next()) {
                userId = clientSet.getInt("idUser");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String query = String.format("INSERT INTO client(firstName,secondName,Patronimic,address,phoneNumber,idUser) VALUES ('%s','%s','%s','%s','%s',%d)",
                client.getFirstName(), client.getSecondName(), client.getMiddleName(), client.getAddress(), client.getPhoneNumber(), userId
        );

        provider.insert(query);
    }

    public void updateClient(Client client) {
        String query = String.format("UPDATE client SET firstName='%s', secondName='%s', Patronimic='%s', address='%s', phoneNumber='%s' WHERE idUser=%d",
                client.getFirstName(), client.getSecondName(), client.getMiddleName(), client.getAddress(), client.getPhoneNumber(), client.getIdUser()
        );
        provider.update(query);
    }
}
