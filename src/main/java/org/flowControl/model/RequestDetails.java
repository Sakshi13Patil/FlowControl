package org.flowControl.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RequestDetails {
    private int totalResponseTime;
    private int noOfRequests;
    private List<Long> lastRequestsTimings;
    private long notAllowedTill;
}

/*
If average response time of calls is greater than X ms within T ms then allow only R requests for T1 ms
If R requests average response time or average response time of
total requests accumulated in T1 ms is greater than Y ms then do not allow any request for another T1 ms .
Else allow 100% request
Here T, T1 and C are whole numbers(integers). Also X and Y is the response time for individual calls.
Average response time = sum of response time/No. of request .
(Should become clear with the example shown below).
* */