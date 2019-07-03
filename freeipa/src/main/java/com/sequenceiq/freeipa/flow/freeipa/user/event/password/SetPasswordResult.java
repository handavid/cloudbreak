package com.sequenceiq.freeipa.flow.freeipa.user.event.password;

import com.sequenceiq.freeipa.flow.freeipa.user.event.FreeIpaClientResult;

public class SetPasswordResult extends FreeIpaClientResult<SetPasswordRequest> {

    public SetPasswordResult(SetPasswordRequest request) {
        super(request);
    }

    public SetPasswordResult(String statusReason, Exception errorDetails, SetPasswordRequest request) {
        super(statusReason, errorDetails, request);
    }
}
