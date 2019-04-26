package com.sequenceiq.freeipa.service;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.cloud.event.platform.GetPlatformTemplateRequest;
import com.sequenceiq.freeipa.api.model.create.CreateFreeIpaRequest;
import com.sequenceiq.freeipa.converter.CreateFreeIpaRequestToStackConverter;
import com.sequenceiq.freeipa.converter.cloud.CredentialToCloudCredentialConverter;
import com.sequenceiq.freeipa.entity.SecurityConfig;
import com.sequenceiq.freeipa.entity.Stack;
import com.sequenceiq.freeipa.repository.StackRepository;

import reactor.bus.EventBus;

@Service
public class StackCreationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StackCreationService.class);

    @Inject
    private EventBus eventBus;

    @Inject
    private CredentialToCloudCredentialConverter credentialConverter;

    @Inject
    private ExecutorService executorService;

    @Inject
    private TlsSecurityService tlsSecurityService;

    @Inject
    private StackRepository stackRepository;

    @Inject
    private CreateFreeIpaRequestToStackConverter stackConverter;

    @Inject
    private StackTemplateService templateService;

    public void launchStack(CreateFreeIpaRequest request) {
        Stack stack = stackConverter.convert(request);
        GetPlatformTemplateRequest getPlatformTemplateRequest = templateService.triggerGetTemplate(stack);

        SecurityConfig securityConfig = tlsSecurityService.generateSecurityKeys();
        stack.setSecurityConfig(securityConfig);

        String template = templateService.waitGetTemplate(stack, getPlatformTemplateRequest);
        stack.setTemplate(template);
        stackRepository.save(stack);
        /*saveInstanceMetadata(stack);
        Location location = Location.location(Region.region(request.getRegion()), AvailabilityZone.availabilityZone(request.getAvailabilityZone()));
        CloudContext cloudCtx = new CloudContext(stack.getId(), request.getName(), "AWS", "AWS", location, "1", 1L);
        CloudCredential cloudCredential = new CloudCredential(1L, "aws-key",
                Map.of("accessKey", request.getAccessKey(),
                        "secretKey", request.getSecretKey(),
                        "govCloud", false,
                        "selector", "key-based",
                        "smartSenseId", "null"));

        GetPlatformTemplateRequest getPlatformTemplateRequest = new GetPlatformTemplateRequest(cloudCtx, cloudCredential);
        GetPlatformTemplateResult templateResult = null;
        try {
            eventBus.notify(getPlatformTemplateRequest.selector(), new Event<>(getPlatformTemplateRequest));
            templateResult = getPlatformTemplateRequest.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Security security = new Security(Collections.singletonList(new SecurityRule("0.0.0.0/0",
                new PortDefinition[] {new PortDefinition("22", "22"), new PortDefinition("9443", "9443")}, "TCP")), Collections.emptyList());
        InstanceAuthentication instanceAuthentication = new InstanceAuthentication(request.getPublicKey(), null, "cloudbreak");
        List<Volume> volumes = Collections.singletonList(new Volume("/mnt/vol","standard", 100));
        Map<String, Object> parameters = Collections.emptyMap();
        InstanceTemplate instanceTemplate = new InstanceTemplate("m5.large", "testGroup", 1234L, volumes,
                InstanceStatus.CREATE_REQUESTED, parameters, 1234L, request.getImageId());
        CloudInstance cloudInstance = new CloudInstance(null, instanceTemplate, instanceAuthentication);
        List<CloudInstance> instances = Collections.singletonList(cloudInstance);
        Group group = new Group("testGroup", InstanceGroupType.GATEWAY, instances, security, cloudInstance, instanceAuthentication,
                "coudbreak", request.getPublicKey(), 50);
        Map<InstanceGroupType, String> userData = userData(stack, cloudCtx, cloudCredential);
        Image image = new Image("ami-00a6ece830a9d2cfb", userData, "amazonlinux2", "amazonlinux2",
                "https://cloudbreak-imagecatalog.s3.amazonaws.com/v2-prod-cb-image-catalog.json", "imagecatalog",
                request.getImageId(), Collections.emptyMap());
        Network network = new Network(new Subnet("10.10.0.0/16"));
        CloudStack cloudStack = new CloudStack(Collections.singleton(group), network, image,
                Collections.emptyMap(), Map.of("Owner", "test"), templateResult.getTemplate(), instanceAuthentication, "cloudbreak",
                request.getPublicKey(), null);
        LaunchStackRequest launchStackRequest = new LaunchStackRequest(cloudCtx, cloudCredential, cloudStack, AdjustmentType.EXACT, 1L);
        eventBus.notify(launchStackRequest.selector(), new Event<>(launchStackRequest));*/
    }

/*    private void saveInstanceMetadata(Stack stack) {
        InstanceMetaData instanceMetaData = new InstanceMetaData();
        instanceMetaData.setStack(stack);
        instanceMetaData.setInstanceMetadataType(InstanceMetadataType.GATEWAY_PRIMARY);
        instanceMetaDataRepository.save(instanceMetaData);
    }

    private Map<InstanceGroupType, String> userData(Stack stack, CloudContext cloudContext, CloudCredential cloudCredential) {
        PlatformParameterRequest parameterRequest = new PlatformParameterRequest(cloudContext, cloudCredential);
        eventBus.notify(parameterRequest.selector(), new Event<>(parameterRequest));
        PlatformParameterResult res;
        try {
            res = parameterRequest.await();
        } catch (InterruptedException e) {
            throw new OperationException(e);
        }
        Platform platform = platform(stack.getCloudPlatform());
        String region = stack.getRegion();
        SecurityConfig securityConfig = stack.getSecurityConfig();
        SaltSecurityConfig saltSecurityConfig = securityConfig.getSaltSecurityConfig();
        String cbPrivKey = saltSecurityConfig.getSaltBootSignPrivateKey();
        byte[] cbSshKeyDer = PkiUtil.getPublicKeyDer(new String(Base64.decodeBase64(cbPrivKey)));
        String sshUser = stack.getStackAuthentication().getLoginUserName();
        String cbCert = securityConfig.getClientCert();
        String saltBootPassword = saltSecurityConfig.getSaltBootPassword();
        return userDataBuilder.buildUserData(platform, cbSshKeyDer, sshUser, res.getPlatformParameters(), saltBootPassword, cbCert);
    }

    private Stack createStack(CreateFreeIpaRequest request) {
        Stack stack = new Stack();
        stack.setName(request.getName());
        stack.setRegion(request.getRegion());
        stack.setAvailabilityZone(request.getAvailabilityZone());
        stack.setCloudPlatform("AWS");
        stack.setPlatformvariant("AWS");
        StackAuthentication stackAuthentication = new StackAuthentication();
        stackAuthentication.setLoginUserName("cloudbreak");
        stackAuthentication.setPublicKey(request.getPublicKey());
        stack.setStackAuthentication(stackAuthentication);
        stack = stackRepository.save(stack);
        SecurityConfig securityConfig = tlsSecurityService.generateSecurityKeys();
        securityConfig.setStack(stack);
        securityConfig = securityConfigRepository.save(securityConfig);
        stack.setSecurityConfig(securityConfig);
        return stackRepository.save(stack);
    }*/
}
