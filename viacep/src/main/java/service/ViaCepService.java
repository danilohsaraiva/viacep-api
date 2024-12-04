package service;

import com.google.gson.Gson;
import model.Address;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

public class ViaCepService {
    public Address getEndereco (String cep) throws IOException {
        Address address = new Address();

        HttpGet request = new HttpGet("https://viacep.com.br/ws/"+ cep +"/json/");

        try (
                CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
                CloseableHttpResponse response = httpClient.execute(request);
        ){
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
}
