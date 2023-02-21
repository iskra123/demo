package helpers;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Map;
import java.util.Set;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.FilterableRequestSpecification;

public class CustomRequestSpecification {

    private FilterableRequestSpecification filterableRequestSpecification;

    public CustomRequestSpecification() {
        filterableRequestSpecification = (FilterableRequestSpecification) given();
    }

    public void addCustomHeader(String headerName, String headerValue) {
        removeCustomHeader(headerName);

        filterableRequestSpecification.header(headerName, headerValue);
    }

    public void removeCustomHeader(String headerName) {
        filterableRequestSpecification.removeHeader(headerName);
    }

    public void addHeaders(Headers headers) {
        if (headers != null) {
            for (Header header : headers) {
                addCustomHeader(header.getName(), header.getValue());
            }
        }
    }

    public void addBodyToRequest(Object body) {
        filterableRequestSpecification.body(body);
    }

    public void addPathParams(Map<String, String> pathParamsMap) {
        Set<String> keys = filterableRequestSpecification.getNamedPathParams().keySet();

        Object[] keyArray = keys.toArray();

        for (Object key : keyArray) {
            filterableRequestSpecification.removeNamedPathParam(key.toString());
        }

        filterableRequestSpecification.pathParams(pathParamsMap);
    }

    public void addQueryParams(Map<String, String> queryParamsMap) {
        Set<String> keys = filterableRequestSpecification.getQueryParams().keySet();

        Object[] keyArray = keys.toArray();

        for (Object key : keyArray) {
            filterableRequestSpecification.removeQueryParam(key.toString());
        }

        filterableRequestSpecification.queryParams(queryParamsMap);
    }

    public void addFormParams(Map<String, String> formParamsMap) {
        filterableRequestSpecification.formParams(formParamsMap);
    }

    public void addBasePath(String basePath) {
        filterableRequestSpecification.basePath(basePath);
    }

    public void addBaseUri(String baseUri) {
        filterableRequestSpecification.baseUri(baseUri);
    }

    public void setContentType(ContentType contentType) {
        filterableRequestSpecification.contentType(contentType);
    }

    public void setContentType(String contentType) {
        filterableRequestSpecification.contentType(contentType);
    }

    public void setRelaxedHttpsValidation() {
        filterableRequestSpecification.relaxedHTTPSValidation();
    }

    public void addMultipart(String controlName, File file, String mimeType) {
        filterableRequestSpecification.multiPart(controlName, file, mimeType);
    }

    public FilterableRequestSpecification getFiltarableRequestSpecification() {
        return filterableRequestSpecification;
    }
}