package com.romaingo;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.jcr.RepositoryException;


import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HostParams;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnomiCaller {

    private static final String PROPERTY_VALUE = "value";
    private static final String PROPERTY_VARIATION = "variation";
    private static final String PROPERTY_DESCRIPTION = "description";
    private static String API_URL = "localhost";
    private static String API_path = "/cxs/profiles/search";
    private static String UNOMI_USER = "karaf";
    private static String UNOMI_PWD = "karaf";
    private final static Logger LOGGER = LoggerFactory.getLogger(UnomiCaller.class);

    
	 /**
    *
    * @param path
    * @param params
    * @return
    * @throws RepositoryException
    */
   public static JSONObject callUnomi(final String... params) throws RepositoryException {
       try {
           final HttpClient httpClient = new HttpClient();
           final HttpURL url = new HttpURL(API_URL, 8181, API_path);

         /*  final Map<String, String> m = new LinkedHashMap<String, String>();
           for (int i = 0; i < params.length; i += 2) {
               m.put(params[i], params[i + 1]);
           }

           url.setQuery(m.keySet().toArray(new String[m.size()]), m.values().toArray(new String[m.size()]));
           */
           final long l = System.currentTimeMillis();
           LOGGER.debug("Start request : " + url);
           final PostMethod httpMethod = new PostMethod(url.toString());
           
           httpClient.getState().setCredentials(AuthScope.ANY,
        		   new UsernamePasswordCredentials("karaf", "karaf")
        		   );
           
           String s = "{\"condition\":{\"parameterValues\":{\"operator\":\"and\",\"subConditions\":[{\"parameterValues\":{\"matchType\":\"all\",\"segments\":[\"contacts\"]},\"type\":\"profileSegmentCondition\"},{\"parameterValues\":{\"comparisonOperator\":\"missing\",\"propertyName\":\"mergedWith\"},\"type\":\"profilePropertyCondition\"},{\"parameterValues\":{\"propertyName\":\"systemProperties.isAnonymousProfile\",\"comparisonOperator\":\"missing\"},\"type\":\"profilePropertyCondition\"}]},\"type\":\"booleanCondition\"},\"limit\":25,\"offset\":0,\"sortby\":\"properties.lastVisit:desc\",\"text\":\"\"} ";

           StringRequestEntity t = new StringRequestEntity(s,"application/json","UTF-8");
           
           httpMethod.setRequestEntity(t);
           
           
           /*
           CredentialsProvider provider = new BasicCredentialsProvider();
           UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("user1", "user1Pass");
           provider.setCredentials(AuthScope.ANY, credentials);
           HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
          */
         //  HttpResponse response = client.execute(new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION));

           // Display information to sales
           // => ask francois how to add a property to a user  ? 
           // live role = sales 
           // region - industry - assignedto
           // Our : display dashboard for executive team
           // Module that can serve as a base for you to display any information from unomi to DX
           // examples :
           // story 1 : static setting, just display users, last connection, goals and interests (2 screens), based on a custom industry setting
           // story 2 : display a kibana dashboard - total visits / look at jess dashboard
           try {
               httpClient.executeMethod(httpMethod);
               int code = httpMethod.getStatusCode();
               String response = httpMethod.getResponseBodyAsString();
               LOGGER.debug("response  : " + response);

               if(code == 200) {
               JSONObject jsonResp = new JSONObject(response);
    		   
               return jsonResp;
               } else {
            	   throw new Exception(response);
               }
               

           } finally {
               httpMethod.releaseConnection();
               LOGGER.debug("Request " + url + " done in " + (System.currentTimeMillis() - l) + "ms");
           }
       } catch (java.net.SocketTimeoutException te) {
           LOGGER.warn("Timeout Exception on request to Google Finance API");
           return null;
       } catch (Exception e) {
           LOGGER.error(e.getMessage(), e);
           return null;
       }
   }
}
