package com.ushkov.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ushkov.exceptions.IllegalRequestParamException;
import com.ushkov.exceptions.IncompatiblePageNumberFormatException;
import com.ushkov.responces.PageNumberResponse;
import com.ushkov.services.PageNumberService;


@Path("/reducedPageNumbers")
public class PageNumberController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response reducePageNumbers(@QueryParam("rawPageNumbers") String pageNumbers){
        //I think this is not necessary to use global error handling in that case.
        try{
            String result = PageNumberService.reducePageNumbers(pageNumbers);
            return Response.status(200).entity(new PageNumberResponse(pageNumbers, result)).build();
        }
        catch (IncompatiblePageNumberFormatException | IllegalRequestParamException ex){
            return Response.status(400).entity(ex.getMessage()).build();
        }
        catch (Exception ex){
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }

}
