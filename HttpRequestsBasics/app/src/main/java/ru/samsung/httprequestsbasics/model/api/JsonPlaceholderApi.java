package ru.samsung.httprequestsbasics.model.api;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.samsung.httprequestsbasics.model.entities.Address;
import ru.samsung.httprequestsbasics.model.entities.Company;
import ru.samsung.httprequestsbasics.model.entities.Geo;
import ru.samsung.httprequestsbasics.model.entities.User;

public class JsonPlaceholderApi {
    private String urlPath;

    public JsonPlaceholderApi(String urlPath) {
        this.urlPath = urlPath;
    }

    public User getUser(int index) throws IOException, JSONException {
        String userJsonStroke = getJsonFromServer(String.format("%s/%d", urlPath, index), 0);

        JSONObject userRoot = new JSONObject(userJsonStroke);
        JSONObject userAddress = userRoot.getJSONObject("address");
        JSONObject userCompany = userRoot.getJSONObject("company");
        JSONObject addressGeo = userAddress.getJSONObject("geo");

        int userId = userRoot.getInt("id");
        String userName = userRoot.getString("name");
        String userNameName = userRoot.getString("username");
        String userEmail = userRoot.getString("email");
        String userPhone = userRoot.getString("phone");
        String userWebSite = userRoot.getString("website");

        String addressStreet = userAddress.getString("street");
        String addressSuite = userAddress.getString("suite");
        String addressCity = userAddress.getString("city");
        String addressZipcode = userAddress.getString("zipcode");

        double geoLat = addressGeo.getDouble("lat");
        double getLon = addressGeo.getDouble("lng");

        String companyName = userCompany.getString("name");
        String companyCatchPhrase = userCompany.getString("catchPhrase");
        String companyBs = userCompany.getString("bs");

        Geo geo = new Geo(geoLat, getLon);
        Address address = new Address(addressStreet, addressSuite, addressCity, addressZipcode, geo);
        Company company = new Company(companyName, companyCatchPhrase, companyBs);

        return new User(userId, userName, userNameName, userEmail, address, userPhone, userWebSite, company);
    }

    private String getJsonFromServer(String urlPath, int timeout) throws IOException {
        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        connection.connect();

        int serverResponseCode = connection.getResponseCode();
        switch (serverResponseCode) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String tmpLine;
                while ((tmpLine = br.readLine()) != null) {
                    sb.append(tmpLine).append("\n");
                }
                br.close();
                return sb.toString();
            case 404:
                Log.d(JsonPlaceholderApi.class.getName(), "page not found!");
                break;
            case 400:
                Log.d(JsonPlaceholderApi.class.getName(), "Bad request!");
                break;
            case 500:
                Log.d(JsonPlaceholderApi.class.getName(), "Internal server error");
                break;
        }

        return null;
    }
}
