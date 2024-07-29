package com.api.group.controller;

import com.api.group.domain.Group;
import com.api.group.dto.GroupUpdateRequestDto;
import com.api.group.exception.GroupIsNotExistException;
import com.api.group.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/group")
@Tag(name = "Group Management System", description = "Operations pertaining to team in Equipo Management System")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Operation(summary = "Devuelve una lista de Grupos con todos los Grupos")
    @GetMapping
    public List<Group> getAllGroup() {
        return groupService.getAllGroup();
    }

    @Operation(summary = "Devuelve un grupo con un Id de grupo en particular")
    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(
            @Parameter(description = "ID del grupo a devolver", required = true)
            @PathVariable Long id) {
        Optional<Group> equipo = groupService.getGroupById(id);
        return equipo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crea un nuevo grupo")
    @PostMapping
    public Group createGroup(
            @Parameter(description = "Objeto grupo a crear", required = true)
            @RequestBody Group group) {
        return groupService.saveGroup(group);
    }

    @Operation(summary = "Devuelve un grupo con un atributo actualizado")
    @PatchMapping("/{id}")
    public ResponseEntity<Group> actualizarGrupoById(
            @Parameter(description = "ID del grupo a parchear", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto Request para parchear un grupo", required = true)
            @RequestBody GroupUpdateRequestDto groupUpdateRequestDto) {
        Optional<Group> group = groupService.getGroupById(id);
        if (group.isPresent()) {
            Group groupEncontrado = group.get();
            if (groupUpdateRequestDto.getName() != null) {
                groupEncontrado.setName(groupUpdateRequestDto.getName());
            }
            if (groupUpdateRequestDto.getDescription() != null) {
                groupEncontrado.setDescription(groupUpdateRequestDto.getDescription());
            }

            Group updatedGroup = groupService.saveGroup(groupEncontrado);
            return ResponseEntity.ok(updatedGroup);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualiza un grupo existente")
    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(
            @Parameter(description = "Id del grupo a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto de grupo actualizado", required = true)
            @RequestBody Group groupDetails) {
        Optional<Group> group = groupService.getGroupById(id);
        if (group.isPresent()) {
            Group existingGroup = group.get();
            existingGroup.setName(groupDetails.getName());
            existingGroup.setDescription(groupDetails.getDescription());
            Group updateEquipo = groupService.saveGroup(existingGroup);
            return ResponseEntity.ok(updateEquipo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Borra un grupo con un id en particular")
    @DeleteMapping("/{id}")
    public void deleteGroupById(
            @Parameter(description = "Id del grupo a borrar", required = true)
            @PathVariable Long id) {
        Optional<Group> group = groupService.getGroupById(id);
        if (group.isPresent())
            groupService.deleteGroupById(id);
        throw new GroupIsNotExistException("El equipo que quiere eliminar no existe");
    }

}
