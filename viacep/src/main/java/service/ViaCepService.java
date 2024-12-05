package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Address;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViaCepService {
    public Address getEndereco (String cep) throws IOException {
        Address address = new Address();

        HttpGet request = new HttpGet("https://viacep.com.br/ws/"+ cep +"/json/");

        try (
                CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build()

        ){
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if(entity != null) {
                String str = EntityUtils.toString(entity);
                Gson gson = new Gson();

                address = gson.fromJson(str, Address.class);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        return address;
    }

    public List<Address> findAddress(String uf, String cidade, String logradouro) {
        List<Address> addresses = new ArrayList<>();

        if ((cidade.length() <= 3 || logradouro.length() <= 3)) {
            return addresses;
        } else {

            String cidadeFormatada = cidade.replace(" ", "%20");
            String logradouroFormatado  = logradouro.replace(" ", "%20");
            System.out.println(cidadeFormatada);
            System.out.println(logradouroFormatado);

            HttpGet request = new HttpGet("https://viacep.com.br/ws/" + uf + "/" + cidadeFormatada + "/" + logradouroFormatado + "/json/");

            try (
                    CloseableHttpClient httpClient = HttpClientBuilder.create().build()
            ) {
                CloseableHttpResponse response = httpClient.execute(request);
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    String str = EntityUtils.toString(entity);
                    Gson gson = new Gson();

                    Type listType = new TypeToken<List<Address>>() {
                    }.getType();

                    return addresses = gson.fromJson(str, listType);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            return addresses;
        }
    }
}
