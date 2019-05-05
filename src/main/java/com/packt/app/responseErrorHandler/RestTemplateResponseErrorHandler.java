package com.packt.app.responseErrorHandler;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {

        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            try {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (ServerErrorException e) {
                e.getMessage();
            }
        } else if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            throw new IllegalArgumentException();
        }
        if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            try {
                throw new ChangeSetPersister.NotFoundException();
            } catch (ChangeSetPersister.NotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
