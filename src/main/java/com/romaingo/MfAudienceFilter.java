package com.romaingo;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jahia.services.content.JCRCallback;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.content.JCRTemplate;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.filter.AbstractFilter;
import org.jahia.services.render.filter.RenderChain;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MfAudienceFilter extends AbstractFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(MfAudienceFilter.class);


    /**
     *
     * @param renderContext
     * @param resource
     * @param chain
     * @return
     * @throws Exception
     */
    @Override
    public String prepare(final RenderContext renderContext,
                          final Resource resource,
                          final RenderChain chain) throws Exception {
        final JSONObject stock = UnomiCaller.callUnomi("");
        final String stockValue;
        final String stockVariation;
        final String stockDescription;

        
        renderContext.getRequest().setAttribute("stockValue", "toto");
        renderContext.getRequest().setAttribute("stockVariation", "titi");
        renderContext.getRequest().setAttribute("stockDescription", "tata");

        return super.prepare(renderContext, resource, chain);
    }

   


}