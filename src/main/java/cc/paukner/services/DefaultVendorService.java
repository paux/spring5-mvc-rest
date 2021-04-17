package cc.paukner.services;

import cc.paukner.api.v1.mapper.VendorMapper;
import cc.paukner.api.v1.model.VendorDto;
import cc.paukner.api.v1.model.VendorListDto;
import cc.paukner.domain.Vendor;
import cc.paukner.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static cc.paukner.controllers.v1.VendorController.BASE_URL;

@Service
public class DefaultVendorService implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public DefaultVendorService(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorListDto getAllVendors() {
        List<VendorDto> vendorDtos = vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDto vendorDto = vendorMapper.vendorToVendorDto(vendor);
                    vendorDto.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDto;
                })
                .collect(Collectors.toList());

        return new VendorListDto(vendorDtos);
    }

    @Override
    public VendorDto getVendorByName(String name) {
        return vendorMapper.vendorToVendorDto(vendorRepository.findByName(name));
    }

    @Override
    public VendorDto getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDto)
                .map(vendorDto -> {
                    vendorDto.setVendorUrl(getVendorUrl(id));
                    return vendorDto;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDto createVendor(VendorDto vendorDto) {
        return saveVendor(vendorMapper.vendorDtoToVendor(vendorDto));
    }

    @Override
    public VendorDto updateVendor(Long id, VendorDto vendorDto) {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDto);
        vendor.setId(id);
        return saveVendor(vendor);
    }

    private VendorDto saveVendor(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDto savedVendorDto = vendorMapper.vendorToVendorDto(savedVendor);
        savedVendorDto.setVendorUrl(getVendorUrl(savedVendor.getId()));
        return savedVendorDto;
    }

    private VendorDto saveVendorByDto(Long id, VendorDto vendorDto) {
        Vendor vendorToSave = vendorMapper.vendorDtoToVendor(vendorDto);
        vendorToSave.setId(id);
        return saveVendor(vendorToSave);
    }

    @Override
    public VendorDto patchVendor(Long id, VendorDto vendorDto) {
        return vendorRepository.findById(id).map(vendor -> {
            if (vendorDto.getName() != null) {
                vendor.setName(vendorDto.getName());
            }
            VendorDto returnDto = vendorMapper.vendorToVendorDto(vendorRepository.save(vendor));
            returnDto.setVendorUrl(getVendorUrl(id));
            return returnDto;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private String getVendorUrl(Long id) {
        return BASE_URL + id;
    }
}
