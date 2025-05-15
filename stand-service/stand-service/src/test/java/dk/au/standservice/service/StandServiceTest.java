package dk.au.standservice.service;

import dk.au.standservice.model.Stand;
import dk.au.standservice.repository.StandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StandServiceTest {

    @Mock
    private StandRepository standRepository;

    @InjectMocks
    private StandService standService;

    private Stand testStand;

    @BeforeEach
    void setUp() {
        testStand = new Stand();
        testStand.setId(1L);
        testStand.setCustomerNumber("CUST001");
        testStand.setSquareMetres(25.5);
        testStand.setFair("Book Fair 2024");
        testStand.setLocation("Hall A, Section 3");
    }

    @Test
    void getAllStands_ShouldReturnListOfStands() {
        when(standRepository.findAll()).thenReturn(Arrays.asList(testStand));

        List<Stand> stands = standService.getAllStands();

        assertThat(stands).hasSize(1);
        assertThat(stands.get(0).getId()).isEqualTo(testStand.getId());
        assertThat(stands.get(0).getCustomerNumber()).isEqualTo(testStand.getCustomerNumber());
        verify(standRepository).findAll();
    }

    @Test
    void getStandById_WhenStandExists_ShouldReturnStand() {
        when(standRepository.findById(1L)).thenReturn(Optional.of(testStand));

        Optional<Stand> found = standService.getStandById(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(testStand.getId());
        assertThat(found.get().getCustomerNumber()).isEqualTo(testStand.getCustomerNumber());
        verify(standRepository).findById(1L);
    }

    @Test
    void getStandById_WhenStandDoesNotExist_ShouldReturnEmpty() {
        when(standRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Stand> found = standService.getStandById(1L);

        assertThat(found).isEmpty();
        verify(standRepository).findById(1L);
    }

    @Test
    void createStand_ShouldReturnCreatedStand() {
        when(standRepository.save(any(Stand.class))).thenReturn(testStand);

        Stand created = standService.createStand(testStand);

        assertThat(created.getId()).isEqualTo(testStand.getId());
        assertThat(created.getCustomerNumber()).isEqualTo(testStand.getCustomerNumber());
        verify(standRepository).save(testStand);
    }

    @Test
    void updateStand_WhenStandExists_ShouldReturnUpdatedStand() {
        when(standRepository.existsById(1L)).thenReturn(true);
        when(standRepository.save(any(Stand.class))).thenReturn(testStand);

        Stand updated = standService.updateStand(1L, testStand);

        assertThat(updated.getId()).isEqualTo(testStand.getId());
        assertThat(updated.getCustomerNumber()).isEqualTo(testStand.getCustomerNumber());
        verify(standRepository).existsById(1L);
        verify(standRepository).save(testStand);
    }

    @Test
    void updateStand_WhenStandDoesNotExist_ShouldReturnNull() {
        when(standRepository.existsById(1L)).thenReturn(false);

        Stand updated = standService.updateStand(1L, testStand);

        assertThat(updated).isNull();
        verify(standRepository).existsById(1L);
        verify(standRepository, never()).save(any());
    }

    @Test
    void deleteStand_ShouldCallRepository() {
        standService.deleteStand(1L);

        verify(standRepository).deleteById(1L);
    }
} 