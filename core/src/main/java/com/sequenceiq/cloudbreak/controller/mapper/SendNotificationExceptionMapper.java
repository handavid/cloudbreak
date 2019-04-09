package com.sequenceiq.cloudbreak.controller.mapper;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.sequenceiq.cloudbreak.api.endpoint.v4.events.responses.NotificationEventType;
import com.sequenceiq.cloudbreak.controller.json.ExceptionResult;
import com.sequenceiq.cloudbreak.service.StackUnderOperationService;
import com.sequenceiq.cloudbreak.service.event.CloudbreakEventService;

abstract class SendNotificationExceptionMapper<E extends Throwable> extends BaseExceptionMapper<E> {

    @Inject
    private StackUnderOperationService stackUnderOperationService;

    @Inject
    private CloudbreakEventService eventService;

    @Override
    public Response toResponse(E exception) {
        Long stackId = stackUnderOperationService.get();
        Response response = super.toResponse(exception);
        if (stackId != null) {
            String message = ((ExceptionResult) response.getEntity()).getMessage();
            eventService.fireCloudbreakEvent(stackId, NotificationEventType.BAD_REQUEST, message);
        }
        return response;
    }
}
