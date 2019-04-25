package com.sequenceiq.cloudbreak.repository.environment;

import javax.transaction.Transactional;

import org.springframework.data.repository.NoRepositoryBean;

import com.sequenceiq.cloudbreak.aspect.DisableHasPermission;
import com.sequenceiq.cloudbreak.domain.environment.BaseNetwork;
import com.sequenceiq.cloudbreak.repository.workspace.WorkspaceResourceRepository;

@NoRepositoryBean
@Transactional(Transactional.TxType.REQUIRED)
@DisableHasPermission
public interface BaseNetworkRepository<T extends BaseNetwork> extends WorkspaceResourceRepository<T, Long> {
}
