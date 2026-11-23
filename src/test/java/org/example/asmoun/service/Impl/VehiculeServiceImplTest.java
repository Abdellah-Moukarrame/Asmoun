package org.example.asmoun.service.Impl;

import org.example.asmoun.dto.request.VehiculeCreateRequest;
import org.example.asmoun.dto.request.VehiculeUpdateRequest;
import org.example.asmoun.dto.response.VehiculeResponse;
import org.example.asmoun.entity.Conducteur;
import org.example.asmoun.entity.Utilisateur;
import org.example.asmoun.entity.Vehicule;
import org.example.asmoun.exception.InvalidValueException;
import org.example.asmoun.exception.ResourceNotFoundException;
import org.example.asmoun.mapper.VehiculeMapper;
import org.example.asmoun.repository.UtilisateurRepository;
import org.example.asmoun.repository.VehiculeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehiculeServiceImplTest {

    @Mock
    private VehiculeRepository vehiculeRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private VehiculeMapper vehiculeMapper;

    @InjectMocks
    private VehiculeServiceImpl vehiculeService;

    @Test
    void createVehicule_shouldSaveVehicleAndReturnResponse() {
        Long conducteurId = 1L;
        Conducteur conducteur = new Conducteur();
        VehiculeCreateRequest request =
                new VehiculeCreateRequest("Dacia", "Logan", "12345-A-1", 4);

        Vehicule vehicule = new Vehicule();
        Vehicule savedVehicule = new Vehicule();
        VehiculeResponse expectedResponse =
                new VehiculeResponse(
                        10L, "Dacia", "Logan", "12345-A-1", 4, conducteurId
                );

        when(utilisateurRepository.findById(conducteurId))
                .thenReturn(Optional.of(conducteur));
        when(vehiculeMapper.toEntityu(request)).thenReturn(vehicule);
        when(vehiculeRepository.save(vehicule)).thenReturn(savedVehicule);
        when(vehiculeMapper.toResonse(savedVehicule))
                .thenReturn(expectedResponse);

        VehiculeResponse result =
                vehiculeService.createVehicule(conducteurId, request);

        assertEquals(expectedResponse, result);
        assertSame(conducteur, vehicule.getConducteur());

        verify(utilisateurRepository).findById(conducteurId);
        verify(vehiculeMapper).toEntityu(request);
        verify(vehiculeRepository).save(vehicule);
        verify(vehiculeMapper).toResonse(savedVehicule);
    }

    @Test
    void createVehicule_shouldThrowExceptionWhenUserDoesNotExist() {
        Long conducteurId = 1L;
        VehiculeCreateRequest request =
                new VehiculeCreateRequest("Dacia", "Logan", "12345-A-1", 4);

        when(utilisateurRepository.findById(conducteurId))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> vehiculeService.createVehicule(conducteurId, request)
        );

        assertEquals("no user found", exception.getMessage());
        verify(vehiculeRepository, never()).save(any());
        verifyNoInteractions(vehiculeMapper);
    }

    @Test
    void createVehicule_shouldThrowExceptionWhenUserIsNotAConducteur() {
        Long userId = 2L;
        Utilisateur utilisateur = org.mockito.Mockito.mock(Utilisateur.class);
        VehiculeCreateRequest request =
                new VehiculeCreateRequest("Dacia", "Logan", "12345-A-1", 4);

        when(utilisateurRepository.findById(userId))
                .thenReturn(Optional.of(utilisateur));

        InvalidValueException exception = assertThrows(
                InvalidValueException.class,
                () -> vehiculeService.createVehicule(userId, request)
        );

        assertEquals("u can do this u are not a driver", exception.getMessage());
        verify(vehiculeRepository, never()).save(any());
        verifyNoInteractions(vehiculeMapper);
    }

    @Test
    void getVehiculeById_shouldReturnVehicleResponse() {
        Long vehiculeId = 10L;
        Vehicule vehicule = new Vehicule();
        VehiculeResponse expectedResponse =
                new VehiculeResponse(
                        vehiculeId, "Dacia", "Logan", "12345-A-1", 4, 1L
                );

        when(vehiculeRepository.findById(vehiculeId))
                .thenReturn(Optional.of(vehicule));
        when(vehiculeMapper.toResonse(vehicule))
                .thenReturn(expectedResponse);

        VehiculeResponse result =
                vehiculeService.getVehiculeById(vehiculeId);

        assertEquals(expectedResponse, result);
        verify(vehiculeRepository).findById(vehiculeId);
        verify(vehiculeMapper).toResonse(vehicule);
    }

    @Test
    void getVehiculeById_shouldThrowExceptionWhenVehicleDoesNotExist() {
        Long vehiculeId = 10L;

        when(vehiculeRepository.findById(vehiculeId))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> vehiculeService.getVehiculeById(vehiculeId)
        );

        assertEquals("no user found", exception.getMessage());
        verifyNoInteractions(vehiculeMapper);
    }

    @Test
    void updateVehicule_shouldUpdateAndReturnResponse() {
        Long conducteurId = 1L;
        Long vehiculeId = 10L;

        Conducteur conducteur = new Conducteur();
        Vehicule vehicule = new Vehicule();
        VehiculeUpdateRequest request =
                new VehiculeUpdateRequest("Renault", "Clio", "67890-B-2", 5);
        VehiculeResponse expectedResponse =
                new VehiculeResponse(
                        vehiculeId, "Renault", "Clio", "67890-B-2", 5,
                        conducteurId
                );

        when(utilisateurRepository.findById(conducteurId))
                .thenReturn(Optional.of(conducteur));
        when(vehiculeRepository.findByIdAndConducteurId(
                vehiculeId, conducteurId
        )).thenReturn(Optional.of(vehicule));
        when(vehiculeRepository.save(vehicule)).thenReturn(vehicule);
        when(vehiculeMapper.toResonse(vehicule))
                .thenReturn(expectedResponse);

        VehiculeResponse result =
                vehiculeService.updateVehicule(
                        vehiculeId, conducteurId, request
                );

        assertEquals(expectedResponse, result);
        verify(vehiculeMapper).updateEntity(request, vehicule);
        verify(vehiculeRepository).save(vehicule);
        verify(vehiculeMapper).toResonse(vehicule);
    }

    @Test
    void updateVehicule_shouldThrowExceptionWhenVehicleDoesNotBelongToConducteur() {
        Long conducteurId = 1L;
        Long vehiculeId = 10L;
        Conducteur conducteur = new Conducteur();
        VehiculeUpdateRequest request =
                new VehiculeUpdateRequest("Renault", "Clio", "67890-B-2", 5);

        when(utilisateurRepository.findById(conducteurId))
                .thenReturn(Optional.of(conducteur));
        when(vehiculeRepository.findByIdAndConducteurId(
                vehiculeId, conducteurId
        )).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> vehiculeService.updateVehicule(
                        vehiculeId, conducteurId, request
                )
        );

        assertEquals(
                "no vehicule found with this driver id",
                exception.getMessage()
        );
        verify(vehiculeMapper, never()).updateEntity(any(), any());
        verify(vehiculeRepository, never()).save(any());
    }
}