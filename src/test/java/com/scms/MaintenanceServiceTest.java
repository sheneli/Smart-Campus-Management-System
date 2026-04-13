package com.scms;

import com.scms.model.MaintenanceRequest;
import com.scms.service.MaintenanceService;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Maintenance Service Tests")
class MaintenanceServiceTest {

    private MaintenanceService maintenanceService;

    @BeforeEach
    void setUp() {
        maintenanceService = new MaintenanceService();
    }

    @Test
    @DisplayName("Should create a maintenance request successfully")
    void testCreateRequest() {
        MaintenanceRequest request = maintenanceService.createRequest(
                "RM-001", "STF-001", "Projector not working", MaintenanceRequest.Urgency.HIGH);

        assertNotNull(request);
        assertEquals("RM-001", request.getRoomId());
        assertEquals("STF-001", request.getReportedBy());
        assertEquals(MaintenanceRequest.Status.PENDING, request.getStatus());
        assertEquals(MaintenanceRequest.Urgency.HIGH, request.getUrgency());
    }

    @Test
    @DisplayName("Should assign maintenance request to a team")
    void testAssignRequest() {
        MaintenanceRequest request = maintenanceService.createRequest(
                "RM-003", "STU-001", "Computer broken", MaintenanceRequest.Urgency.MEDIUM);

        maintenanceService.assignRequest(request.getRequestId(), "IT Support Team");

        assertEquals(MaintenanceRequest.Status.ASSIGNED, request.getStatus());
        assertEquals("IT Support Team", request.getAssignedTo());
    }

    @Test
    @DisplayName("Should update maintenance request status")
    void testUpdateStatus() {
        MaintenanceRequest request = maintenanceService.createRequest(
                "RM-005", "STF-002", "Light flickering", MaintenanceRequest.Urgency.LOW);

        maintenanceService.updateStatus(request.getRequestId(), MaintenanceRequest.Status.IN_PROGRESS);
        assertEquals(MaintenanceRequest.Status.IN_PROGRESS, request.getStatus());

        maintenanceService.updateStatus(request.getRequestId(), MaintenanceRequest.Status.COMPLETED);
        assertEquals(MaintenanceRequest.Status.COMPLETED, request.getStatus());
    }

    @Test
    @DisplayName("Should throw exception for non-existent request assignment")
    void testAssignNonExistentRequest() {
        assertThrows(IllegalArgumentException.class, () ->
                maintenanceService.assignRequest("NON-EXISTENT", "Team A"));
    }

    @Test
    @DisplayName("Should retrieve requests by user")
    void testGetRequestsByUser() {
        maintenanceService.createRequest("RM-001", "STU-002", "Issue 1", MaintenanceRequest.Urgency.LOW);
        maintenanceService.createRequest("RM-002", "STU-002", "Issue 2", MaintenanceRequest.Urgency.MEDIUM);

        List<MaintenanceRequest> requests = maintenanceService.getRequestsByUser("STU-002");
        assertFalse(requests.isEmpty());
        assertTrue(requests.stream().allMatch(r -> r.getReportedBy().equals("STU-002")));
    }

    @Test
    @DisplayName("Should filter requests by status")
    void testGetRequestsByStatus() {
        List<MaintenanceRequest> completed = maintenanceService.getRequestsByStatus(MaintenanceRequest.Status.COMPLETED);
        assertNotNull(completed);
        assertTrue(completed.stream().allMatch(r -> r.getStatus() == MaintenanceRequest.Status.COMPLETED));
    }

    @Test
    @DisplayName("Should track urgency distribution")
    void testUrgencyDistribution() {
        var distribution = maintenanceService.getRequestCountByUrgency();
        assertNotNull(distribution);
    }
}
