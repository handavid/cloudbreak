package com.sequenceiq.it.cloudbreak.newway.testcase.mock;

import static com.sequenceiq.it.cloudbreak.newway.context.RunningParameter.key;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sequenceiq.it.cloudbreak.newway.action.credential.CredentialTestAction;
import com.sequenceiq.it.cloudbreak.newway.action.region.RegionTestAction;
import com.sequenceiq.it.cloudbreak.newway.action.vmtypes.PlatformVmTypesTestAction;
import com.sequenceiq.it.cloudbreak.newway.context.Description;
import com.sequenceiq.it.cloudbreak.newway.context.MockedTestContext;
import com.sequenceiq.it.cloudbreak.newway.context.TestCaseDescription;
import com.sequenceiq.it.cloudbreak.newway.context.TestCaseDescription.TestCaseDescriptionBuilder;
import com.sequenceiq.it.cloudbreak.newway.context.TestContext;
import com.sequenceiq.it.cloudbreak.newway.entity.credential.CredentialTestDto;
import com.sequenceiq.it.cloudbreak.newway.entity.region.RegionTestDto;
import com.sequenceiq.it.cloudbreak.newway.entity.vmtypes.PlatformVmTypesTestDto;
import com.sequenceiq.it.cloudbreak.newway.testcase.AbstractIntegrationTest;

public class VmTypesTest extends AbstractIntegrationTest {

    @BeforeMethod
    public void beforeMethod(Object[] data) {
        createDefaultUser((TestContext) data[0]);
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "a credential with random name",
            when = "retrive platform VMs by the credential name",
            then = "list of platform VMs")
    public void testGetPlatformVmtypesByCredentialName(MockedTestContext testContext) {
        String credentialName = getNameGenerator().getRandomNameForResource();
        testContext
                .given(CredentialTestDto.class)
                .withName(credentialName)
                .when(CredentialTestAction::create)
                .given(PlatformVmTypesTestDto.class)
                .withCredentialName(credentialName)
                .when(PlatformVmTypesTestAction::getPlatformVmtypes);
    }

    @Test(dataProvider = "contextWithCredentialNameAndException")
    public void testGetPlatformVmtypesByCredentialNameWhenCredentialIsInvalid(MockedTestContext testContext, String credentialName, String exceptionKey,
            Class<Exception> exception, @Description TestCaseDescription description) {
        testContext
                .given(RegionTestDto.class)
                .withCredentialName(credentialName)
                .when(RegionTestAction::getRegions, key(exceptionKey))
                .expect(exception, key(exceptionKey))
                .validate();
    }

    @DataProvider(name = "contextWithCredentialNameAndException")
    public Object[][] provideInvalidAttributes() {
        return new Object[][]{
                {getBean(MockedTestContext.class), "", "badRequest", BadRequestException.class,
                        new TestCaseDescriptionBuilder()
                                .given("a region")
                                .when("without credential name")
                                .then("throw bad request exception")},
                {getBean(MockedTestContext.class), null, "badRequest", BadRequestException.class,
                        new TestCaseDescriptionBuilder()
                                .given("a region")
                                .when("credential name is empty")
                                .then("throw bad request exception")},
                {getBean(MockedTestContext.class), "andNowForSomethingCompletelyDifferent", "forbidden", ForbiddenException.class,
                        new TestCaseDescriptionBuilder()
                                .given("a region")
                                .when("credential name is null")
                                .then("throw bad request exception")}
        };
    }

}
