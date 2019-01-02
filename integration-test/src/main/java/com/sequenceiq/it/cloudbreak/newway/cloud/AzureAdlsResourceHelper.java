package com.sequenceiq.it.cloudbreak.newway.cloud;

import static com.sequenceiq.cloudbreak.api.endpoint.v4.database.base.DatabaseType.HIVE;
import static com.sequenceiq.cloudbreak.api.endpoint.v4.database.base.DatabaseType.RANGER;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.storage.CloudStorageV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.storage.azure.AdlsCloudStorageParametersV4;
import com.sequenceiq.it.cloudbreak.filesystem.CloudStorageTypePathPrefix;
import com.sequenceiq.it.cloudbreak.newway.RdsConfig;
import com.sequenceiq.it.cloudbreak.newway.TestParameter;
import com.sequenceiq.it.cloudbreak.parameters.RequiredInputParameters.Azure.Database.Hive;
import com.sequenceiq.it.cloudbreak.parameters.RequiredInputParameters.Azure.Database.Ranger;
import com.sequenceiq.it.cloudbreak.parameters.RequiredInputParameters.Azure.Storage.Adls;

public class AzureAdlsResourceHelper extends ResourceHelper<AdlsCloudStorageParametersV4> {

    private static final String RANGER_RDS_ENTITY_ID = "AZURE_RANGER_DB_CONFIG";

    private static final String HIVE_RDS_ENTITY_ID = "AZURE_HIVE_DB_CONFIG";

    AzureAdlsResourceHelper(TestParameter testParameter) {
        super(testParameter);
    }

    AzureAdlsResourceHelper(TestParameter testParameter, String postfix) {
        super(testParameter, postfix);
    }

    @Override
    public RdsConfig aValidHiveDatabase() {
        var hive = RdsConfig.isCreated(HIVE_RDS_ENTITY_ID);
        hive.setRequest(createRdsRequestWithProperties(Hive.CONFIG_NAME, Hive.USER_NAME_KEY, Hive.PASSWORD_KEY, Hive.CONNECTION_URL_KEY, HIVE));
        return hive;
    }

    @Override
    public RdsConfig aValidRangerDatabase() {
        var ranger = RdsConfig.isCreated(RANGER_RDS_ENTITY_ID);
        ranger.setRequest(createRdsRequestWithProperties(Ranger.CONFIG_NAME, Ranger.USER_NAME_KEY, Ranger.PASSWORD_KEY, Ranger.CONNECTION_URL_KEY, RANGER));
        return ranger;
    }

    @Override
    public CloudStorageV4Request getCloudStorageRequestForDatalake() {
        var request = new CloudStorageV4Request();
        request.setAdls(getCloudStorage());
        request.setLocations(defaultDatalakeStorageLocations(CloudStorageTypePathPrefix.ADLS, getTestParameter().get(Adls.ACCOUNT_NAME)));
        return request;
    }

    @Override
    public CloudStorageV4Request getCloudStorageRequestForAttachedCluster() {
        return getCloudStorageForAttachedCluster(CloudStorageTypePathPrefix.ADLS, getTestParameter().get(Adls.ACCOUNT_NAME), getCloudStorage());
    }

    @Override
    protected AdlsCloudStorageParametersV4 getCloudStorage() {
        var parameters = new AdlsCloudStorageParametersV4();
        parameters.setCredential(getTestParameter().get("INTEGRATIONTEST_AZURERMCREDENTIAL_ACCESSKEY"));
        parameters.setClientId(getTestParameter().get("INTEGRATIONTEST_AZURERMCREDENTIAL_SECRETKEY"));
        parameters.setAccountName(getTestParameter().get(Adls.ACCOUNT_NAME));
        return parameters;
    }
}
