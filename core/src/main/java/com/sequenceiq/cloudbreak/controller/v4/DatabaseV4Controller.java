package com.sequenceiq.cloudbreak.controller.v4;

import java.util.Set;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Controller;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.EnvironmentNames;
import com.sequenceiq.cloudbreak.api.endpoint.v4.database.DatabaseV4Endpoint;
import com.sequenceiq.cloudbreak.api.endpoint.v4.database.requests.DatabaseTestV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.database.requests.DatabaseV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.database.responses.DatabaseTestV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.database.responses.DatabaseV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.database.responses.DatabaseV4Responses;
import com.sequenceiq.cloudbreak.api.endpoint.v4.events.responses.NotificationEventType;
import com.sequenceiq.cloudbreak.api.util.ConverterUtil;
import com.sequenceiq.cloudbreak.authorization.WorkspaceResource;
import com.sequenceiq.cloudbreak.domain.RDSConfig;
import com.sequenceiq.cloudbreak.service.rdsconfig.RdsConfigService;
import com.sequenceiq.cloudbreak.util.WorkspaceEntityType;

@Controller
@Transactional(TxType.NEVER)
@WorkspaceEntityType(RDSConfig.class)
public class DatabaseV4Controller extends NotificationController implements DatabaseV4Endpoint {

    @Inject
    private RdsConfigService databaseService;

    @Inject
    private ConverterUtil converterUtil;

    @Override
    public DatabaseV4Responses list(Long workspaceId, String environment, Boolean attachGlobal) {
        Set<RDSConfig> allInWorkspaceAndEnvironment = databaseService.findAllInWorkspaceAndEnvironment(workspaceId, environment, attachGlobal);
        return new DatabaseV4Responses(converterUtil.convertAllAsSet(allInWorkspaceAndEnvironment, DatabaseV4Response.class));
    }

    @Override
    public DatabaseV4Response get(Long workspaceId, String name) {
        RDSConfig database = databaseService.getByNameForWorkspaceId(name, workspaceId);
        return converterUtil.convert(database, DatabaseV4Response.class);
    }

    @Override
    public DatabaseV4Response create(Long workspaceId, DatabaseV4Request request) {
        RDSConfig database = databaseService.createInEnvironment(converterUtil.convert(request, RDSConfig.class), request.getEnvironments(), workspaceId);
        DatabaseV4Response response = converterUtil.convert(database, DatabaseV4Response.class);
        notify(response, NotificationEventType.CREATE_SUCCESS, WorkspaceResource.RDS, workspaceId);
        return response;
    }

    @Override
    public DatabaseV4Response delete(Long workspaceId, String name) {
        RDSConfig deleted = databaseService.deleteByNameFromWorkspace(name, workspaceId);
        DatabaseV4Response response = converterUtil.convert(deleted, DatabaseV4Response.class);
        notify(response, NotificationEventType.DELETE_SUCCESS, WorkspaceResource.RDS, workspaceId);
        return response;
    }

    @Override
    public DatabaseTestV4Response test(Long workspaceId, DatabaseTestV4Request databaseTestV4Request) {
        RDSConfig rdsConfig = converterUtil.convert(databaseTestV4Request.getDatabase(), RDSConfig.class);
        String connectionResult = databaseService.testRdsConnection(workspaceId, databaseTestV4Request.getExistingDatabaseName(), rdsConfig);
        return new DatabaseTestV4Response(connectionResult);
    }

    @Override
    public DatabaseV4Request getRequest(Long workspaceId, String name) {
        RDSConfig database = databaseService.getByNameForWorkspaceId(name, workspaceId);
        return converterUtil.convert(database, DatabaseV4Request.class);
    }

    @Override
    public DatabaseV4Response attach(Long workspaceId, String name, EnvironmentNames environmentNames) {
        return databaseService.attachToEnvironmentsAndConvert(name, environmentNames.getEnvironmentNames(), workspaceId, DatabaseV4Response.class);
    }

    @Override
    public DatabaseV4Response detach(Long workspaceId, String name, EnvironmentNames environmentNames) {
        return databaseService.detachFromEnvironmentsAndConvert(name, environmentNames.getEnvironmentNames(), workspaceId, DatabaseV4Response.class);
    }
}