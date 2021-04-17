package cc.paukner.api.v1.mapper;

import cc.paukner.api.v1.model.VendorDto;
import cc.paukner.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {

    public static final String NAME = "Vendor, Inc.";
//    public static final long ID = 1L;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDto() {
        // given
        Vendor vendor = Vendor.builder().name(NAME).build();

        // when
        VendorDto vendorDto = vendorMapper.vendorToVendorDto(vendor);

        // then
//        assertEquals(Long.valueOf(ID), vendorDto.getId());
        assertEquals(NAME, vendorDto.getName());
    }

    @Test
    public void vendorDtoToVendor() {
        // given
        VendorDto vendorDto = VendorDto.builder().name(NAME).build();

        // when
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDto);

        // then
//        assertEquals(Long.valueOf(ID), vendor.getId());
        assertEquals(NAME, vendor.getName());
    }
}
