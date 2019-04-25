package com.sequenceiq.freeipa.converter.network;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.ProviderParameterCalculator;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.network.NetworkV4Response;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;
import com.sequenceiq.freeipa.entity.Network;
import com.sequenceiq.freeipa.entity.Stack;

@Component
public class NetworkToNetworkV4ResponseConverter extends AbstractConversionServiceAwareConverter<Stack, NetworkV4Response> {

    @Inject
    private ProviderParameterCalculator providerParameterCalculator;

    @Override
    public NetworkV4Response convert(Stack source) {
        NetworkV4Response networkResp = null;
        Network network = source.getNetwork();
        if (network != null) {
            networkResp = new NetworkV4Response();
            if (network.getAttributes() != null) {
                Map<String, Object> parameters = cleanMap(network.getAttributes().getMap());
                providerParameterCalculator.parse(parameters, networkResp);
            }
        }
        return networkResp;
    }
}
