/*******************************************************************************
 * Copyright  (c) 2015-2016, WSO2.Telco Inc. (http://www.wso2telco.com) All Rights Reserved.
 * <p>
 * WSO2.Telco Inc. licences this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.wso2telco.dep.ratecardservice.resource;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.wso2telco.core.dbutils.exception.BusinessException;
import com.wso2telco.core.dbutils.exception.ServiceError;
import com.wso2telco.dep.ratecardservice.dao.model.ErrorDTO;
import com.wso2telco.dep.ratecardservice.dao.model.RateDefinitionDTO;
import com.wso2telco.dep.ratecardservice.service.RateDefinitionService;

@Path("/ratedefinitions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RateDefinitionResource {

	private final Log log = LogFactory.getLog(RateDefinitionResource.class);
	private RateDefinitionService rateDefinitionService = new RateDefinitionService();

	@GET
	public Response getRateDefinitions(@QueryParam("schema") String schema) {

		List<RateDefinitionDTO> rateDefinitions = null;
		Status responseCode = null;
		Object responseString = null;

		try {

			rateDefinitions = rateDefinitionService.getRateDefinitions(schema);

			responseString = rateDefinitions;
			responseCode = Response.Status.OK;
		} catch (BusinessException e) {

			ErrorDTO error = new ErrorDTO();
			ErrorDTO.RequestError requestError = new ErrorDTO.RequestError();
			ErrorDTO.RequestError.ServiceException serviceException = new ErrorDTO.RequestError.ServiceException();

			serviceException.setMessageId(e.getErrorType().getCode());
			serviceException.setText(e.getErrorType().getMessage());
			requestError.setServiceException(serviceException);
			error.setRequestError(requestError);

			if (e.getErrorType().getCode() == ServiceError.NO_RESOURCES.getCode()) {

				responseCode = Response.Status.NOT_FOUND;
			} else {

				responseCode = Response.Status.BAD_REQUEST;
			}

			responseString = error;
		}

		log.debug("RateDefinitionResource getRateDefinitions -> response code : " + responseCode);
		log.debug("RateDefinitionResource getRateDefinitions -> response body : " + responseString);

		return Response.status(responseCode).entity(responseString).build();
	}

	@POST
	public Response addRateDefinition(RateDefinitionDTO rateDefinition) {

		RateDefinitionDTO newRateDefinition = null;
		Status responseCode = null;
		Object responseString = null;

		try {

			newRateDefinition = rateDefinitionService.addRateDefinition(rateDefinition);

			if (newRateDefinition != null) {

				responseString = newRateDefinition;
				responseCode = Response.Status.CREATED;
			} else {

				log.error(
						"Error in RateDefinitionResource addRateDefinition : rate definition can not insert to database ");
				throw new BusinessException(ServiceError.SERVICE_ERROR_OCCURED);
			}
		} catch (BusinessException e) {

			ErrorDTO error = new ErrorDTO();
			ErrorDTO.RequestError requestError = new ErrorDTO.RequestError();
			ErrorDTO.RequestError.ServiceException serviceException = new ErrorDTO.RequestError.ServiceException();

			serviceException.setMessageId(e.getErrorType().getCode());
			serviceException.setText(e.getErrorType().getMessage());
			requestError.setServiceException(serviceException);
			error.setRequestError(requestError);

			if (e.getErrorType().getCode() == ServiceError.NO_RESOURCES.getCode()) {

				responseCode = Response.Status.NOT_FOUND;
			} else {

				responseCode = Response.Status.BAD_REQUEST;
			}

			responseString = error;
		}

		log.debug("RateDefinitionResource addRateDefinition -> response code : " + responseCode);
		log.debug("RateDefinitionResource addRateDefinition -> response body : " + responseString);

		return Response.status(responseCode).entity(responseString).build();
	}

	@GET
	@Path("/{rateDefId}")
	public Response getRateDefinition(@PathParam("rateDefId") int rateDefId, @QueryParam("schema") String schema) {

		RateDefinitionDTO rateDefinition = null;
		Status responseCode = null;
		Object responseString = null;

		try {

			rateDefinition = rateDefinitionService.getRateDefinition(rateDefId, schema);

			responseString = rateDefinition;
			responseCode = Response.Status.OK;
		} catch (BusinessException e) {

			ErrorDTO error = new ErrorDTO();
			ErrorDTO.RequestError requestError = new ErrorDTO.RequestError();
			ErrorDTO.RequestError.ServiceException serviceException = new ErrorDTO.RequestError.ServiceException();

			serviceException.setMessageId(e.getErrorType().getCode());
			serviceException.setText(e.getErrorType().getMessage());
			requestError.setServiceException(serviceException);
			error.setRequestError(requestError);

			if (e.getErrorType().getCode() == ServiceError.NO_RESOURCES.getCode()) {

				responseCode = Response.Status.NOT_FOUND;
			} else {

				responseCode = Response.Status.BAD_REQUEST;
			}

			responseString = error;
		}

		log.debug("RateDefinitionResource getRateDefinition -> response code : " + responseCode);
		log.debug("RateDefinitionResource getRateDefinition -> response body : " + responseString);

		return Response.status(responseCode).entity(responseString).build();
	}

	@DELETE
	@Path("/{rateDefId}")
	public Response deleteRateDefinition(@PathParam("rateDefId") int rateDefId) {

		boolean status = false;
		Status responseCode = null;
		Object responseString = null;

		try {

			status = rateDefinitionService.deleteRateDefinition(rateDefId);

			if (status) {

				responseCode = Response.Status.NO_CONTENT;
			} else {

				log.error(
						"Error in RateDefinitionResource deleteRateDefinition : rate definition is not found in database ");
				throw new BusinessException(ServiceError.NO_RESOURCES);
			}
		} catch (BusinessException e) {

			ErrorDTO error = new ErrorDTO();
			ErrorDTO.RequestError requestError = new ErrorDTO.RequestError();
			ErrorDTO.RequestError.ServiceException serviceException = new ErrorDTO.RequestError.ServiceException();

			serviceException.setMessageId(e.getErrorType().getCode());
			serviceException.setText(e.getErrorType().getMessage());
			requestError.setServiceException(serviceException);
			error.setRequestError(requestError);

			if (e.getErrorType().getCode() == ServiceError.NO_RESOURCES.getCode()) {

				responseCode = Response.Status.NOT_FOUND;
			} else {

				responseCode = Response.Status.BAD_REQUEST;
			}

			responseString = error;
		}

		log.debug("RateDefinitionResource deleteRateDefinition -> response code : " + responseCode);
		log.debug("RateDefinitionResource deleteRateDefinition -> response body : " + responseString);

		return Response.status(responseCode).build();
	}

	@Path("/{rateDefId}/ratecategories")
	public RateCategoryResource getRateCategoryResource() {

		return new RateCategoryResource();
	}
}
