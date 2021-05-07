package cc.paukner.services;

import cc.paukner.api.v1.model.VendorDto;
import cc.paukner.api.v1.model.VendorListDto;

public interface VendorService {

    VendorListDto getAllVendors();

    VendorDto getVendorByName(String name);

    VendorDto getVendorById(Long id);

    VendorDto createVendor(VendorDto vendorDto);

    VendorDto updateVendor(Long id, VendorDto vendorDto);

    VendorDto patchVendor(Long id, VendorDto vendorDto);

    void deleteVendorById(Long id);
}
