package org.redflag.repository;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import jakarta.annotation.Nullable;
import org.redflag.model.OrganizationNode;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationNodeRepository extends JpaRepository<OrganizationNode, Long> {
    @Query(value = """
            select exists (
                        select 1 from organization_node descendants join organization_node current_node on current_node.id = :nodeId 
                        where current_node.path @> descendants.path AND current_node.path <> descendants.path)
            """, nativeQuery = true)
    Boolean existsDescendants(Long nodeId);

    Boolean existsByOrganization_IdAndName(Long organizationId, String name);

    Boolean existsByOrganization_IdAndId(Long organizationId, Long nodeId);

    Optional<OrganizationNode> findByOrganization_IdAndId(Long organizationId, Long id);

    OrganizationNode findByName(String name);

    @Query(value = """
            with root as(
            select path from organization_node where id = :nodeId)
            delete from organization_node n using root r where n.path <@ r.path
            """, nativeQuery = true)
    void deleteSubtree(Long nodeId);

    @Query(value = """
            select descendants.*
            from organization_node descendants
            where descendants.organization_id = :organizationId
                        and (cast(:parentId as bigint) is null
                        or (descendants.path <@ ( 
                                    select path from  organization_node 
                                    where id = :parentId and organization_id = :organizationId)))
            order by descendants.path
            limit :limit offset :offset;
            """, nativeQuery = true)
    List<OrganizationNode> findAllByOrganizationIdAndParentId(Long organizationId, @Nullable Long parentId, Integer limit, Integer offset);

    @Query(value = """
            select ancestors.*
            from organization_node ancestors
            join organization_node child on (child.organization_id = :organizationId and child.id = :nodeId)
            where ancestors.path @> child.path
            order by nlevel(ancestors.path), ancestors.path
            """, nativeQuery = true)
    List<OrganizationNode> findAllAncestorsById(Long organizationId, Long nodeId);

    @Query(value = """
            SELECT child.*
            FROM organization_node child
            JOIN organization_node root ON (root.organization_id = :organizationId and root.id = :nodeId)
            WHERE child.path <@ root.path
              AND nlevel(child.path) = nlevel(root.path) + 1
            ORDER BY child.path
            """, nativeQuery = true)
    List<OrganizationNode> findAllChildrenById(Long organizationId, Long nodeId);
}
