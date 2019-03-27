package com.sequenceiq.cloudbreak.converter.v4.clustertemplate;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.clusterdefinition.responses.DependenciesV4Responses;
import com.sequenceiq.cloudbreak.api.endpoint.v4.clusterdefinition.responses.ServiceDependencyMatrixV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.clusterdefinition.responses.ServicesV4Responses;
import com.sequenceiq.cloudbreak.cmtemplate.generator.dependencies.domain.ServiceDependencyMatrix;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;

@Component
public class ServiceDependencyMatrixToServiceDependencyMatrixV4Response
        extends AbstractConversionServiceAwareConverter<ServiceDependencyMatrix, ServiceDependencyMatrixV4Response> {

    @Override
    public ServiceDependencyMatrixV4Response convert(ServiceDependencyMatrix source) {
        ServiceDependencyMatrixV4Response serviceDependencyMatrixV4Response = new ServiceDependencyMatrixV4Response();

        DependenciesV4Responses dependenciesV4Responses = new DependenciesV4Responses();
        dependenciesV4Responses.setServices(source.getDependencies().getServices());
        serviceDependencyMatrixV4Response.setDependencies(dependenciesV4Responses);

        ServicesV4Responses servicesV4Responses = new ServicesV4Responses();
        servicesV4Responses.setServices(source.getServices().getServices());
        serviceDependencyMatrixV4Response.setServices(servicesV4Responses);

        return serviceDependencyMatrixV4Response;
    }
}
