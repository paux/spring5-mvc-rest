package cc.paukner.controllers.v1;

import cc.paukner.api.v1.model.VendorDto;
import cc.paukner.api.v1.model.VendorListDto;
import cc.paukner.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "The assignment said I should add this in")
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors/";
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "The assignment said I should add this in as well", notes = "You see, the instructor is always right, totally")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDto getAllVendors() {
        return vendorService.getAllVendors();
    }

    @GetMapping("/name={name}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDto getVendorByName(@PathVariable String name) {
        return vendorService.getVendorByName(name);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDto getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDto createVendor(@RequestBody VendorDto vendorDto) {
        return vendorService.createVendor(vendorDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDto updateVendor(@PathVariable Long id, @RequestBody VendorDto vendorDto) {
        return vendorService.updateVendor(id, vendorDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDto patchVendor(@PathVariable Long id, @RequestBody VendorDto vendorDto) {
        return vendorService.patchVendor(id, vendorDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }
}
