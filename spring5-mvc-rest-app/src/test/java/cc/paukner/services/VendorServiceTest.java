package cc.paukner.services;

import cc.paukner.api.v1.mapper.VendorMapper;
import cc.paukner.api.v1.model.VendorDto;
import cc.paukner.api.v1.model.VendorListDto;
import cc.paukner.domain.Vendor;
import cc.paukner.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendorServiceTest {

    public static final Long ID = 1L;
    public static final String NAME = "ACME";

    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        vendorService = new DefaultVendorService(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getVendorById() {
        // Mockito BDD syntax
        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(Vendor.builder().name(NAME).build()));

        // when
        VendorDto vendorDto = vendorService.getVendorById(1L);

        // then
        then(vendorRepository).should().findById(anyLong());
        assertThat(vendorDto.getName(), is(equalTo(NAME)));
    }

    @Test
    public void getVendorByIdNotFound() {
        // Mockito BDD syntax
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        try {
            vendorService.getVendorById(1L);
        } catch (ResourceNotFoundException ignored) {
            // How is this done best?
        }

        // then
        then(vendorRepository).should().findById(anyLong());
    }

    @Test
    public void getAllVendors() {
        //given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());

        when(vendorRepository.findAll()).thenReturn(vendors);

        // when
        VendorListDto vendorDtos = vendorService.getAllVendors();

        // then
        then(vendorRepository).should().findAll();
        assertThat(vendorDtos.getVendors().size(), is(equalTo(3)));
    }

    @Test
    public void createVendor() {
        // given
        VendorDto vendorDto = VendorDto.builder().name(NAME).build();
        Vendor vendor = Vendor.builder().id(ID).name(vendorDto.getName()).build();

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        // when
        VendorDto savedDto = vendorService.createVendor(vendorDto);

        // then
        assertEquals(vendorDto.getName(), savedDto.getName());
//        assertEquals(BASE_URL + 2, savedDto.getCustomerUrl());
    }

    @Test
    public void updateVendor() {
        // given
        VendorDto vendorDto = VendorDto.builder().name("Hugo").build();
        Vendor vendor = Vendor.builder().id(ID).name(vendorDto.getName()).build();

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        // when
        VendorDto savedDto = vendorService.updateVendor(ID, vendorDto);

        // then
        assertEquals(vendorDto.getName(), savedDto.getName());
//        assertEquals(BASE_URL + 2, savedDto.getCustomerUrl());
    }

    @Test
    public void deleteVendorById() {
        vendorService.deleteVendorById(1L);

        verify(vendorRepository).deleteById(anyLong());
    }
}
