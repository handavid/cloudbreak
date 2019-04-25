package com.sequenceiq.cloudbreak.service.environment;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.domain.environment.AwsNetwork;
import com.sequenceiq.cloudbreak.domain.environment.AzureNetwork;
import com.sequenceiq.cloudbreak.repository.environment.AwsNetworkRepository;
import com.sequenceiq.cloudbreak.repository.environment.AzureNetworkRepository;

@Service
public class EnvironmentNetworkService {
    @Inject
    private AwsNetworkRepository awsNetworkRepository;

    @Inject
    private AzureNetworkRepository azureNetworkRepository;

    public AwsNetwork save(AwsNetwork awsNetwork) {
        return awsNetworkRepository.save(awsNetwork);
    }

    public AzureNetwork save(AzureNetwork azureNetwork) {
        return azureNetworkRepository.save(azureNetwork);
    }
}
