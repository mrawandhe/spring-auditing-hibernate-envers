package com.example.controller;

import com.example.entity.OrderEntity;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/audit")
public class OrderAuditController {

  @PersistenceContext
  private EntityManager entityManager;

  @GetMapping("/{id}/revisions")
  public List<Object[]> getRevisions(
      @Parameter(description = "Revision number", in = ParameterIn.PATH)
      @PathVariable Long id) {
    AuditReader auditReader = AuditReaderFactory.get(entityManager);
    return auditReader.createQuery()
        .forRevisionsOfEntity(OrderEntity.class, false, true)
        .add(AuditEntity.id().eq(id))
        .getResultList();
  }

  @GetMapping("/{id}/revision/{rev}")
  public OrderEntity getOrderAtRevision(
      @Parameter(description = "Order ID", in = ParameterIn.PATH) @PathVariable Long orderId,
      @Parameter(description = "Revision number", in = ParameterIn.PATH) @PathVariable
      int revision) {
    AuditReader auditReader = AuditReaderFactory.get(entityManager);
    return auditReader.find(OrderEntity.class, orderId, revision);
  }

  @GetMapping("/{id}/revisionNumbers")
  public List<Number> getRevisionNumbers(
      @Parameter(description = "Order ID", in = ParameterIn.PATH)
      @PathVariable Long orderId) {
    AuditReader auditReader = AuditReaderFactory.get(entityManager);
    return auditReader.getRevisions(OrderEntity.class, orderId);
  }

}